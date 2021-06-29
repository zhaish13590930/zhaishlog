/**
 * Copyright (C), -2018, 杨坚
 * FileName: HiTSDBUtil
 * Author:   yj
 * Date:     2018/12/28 15:30
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 */

import com.alibaba.fastjson.JSON;
import com.baidubce.BceClientConfiguration;
import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.services.tsdb.TsdbClient;
import com.baidubce.services.tsdb.model.*;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.FastDateParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;

/**
 * @ClassName HiTSDBUtil
 * @Description TOTD 阿里云时序数据库操作
 * @Author yj
 * @Date 2018/12/28
 * @Version 1.0.0
 */
public class HiTsdbUtil {
    protected static final Logger log = LoggerFactory.getLogger(HiTsdbUtil.class);
    private static String BAIDUTSDB_ACCESS_KEY = "92dcce22c6244eea80d57de6f45ad452";
    private static String database = "iovtboxdata";
    protected static BceClientConfiguration config = new BceClientConfiguration()
            .withCredentials(new DefaultBceCredentials(BAIDUTSDB_ACCESS_KEY, "72ed25cd7d1046e0b1c4333afb039c34"))
            .withEndpoint("iovtboxdata.tsdb-ufg5pk5q605d.tsdb.iot.bj.baidubce.com");

    // 初始化一个TsdbClient
    protected static TsdbClient tsdbClient = new TsdbClient(config);

    public static void main(String[] args) {
        long begin =LocalDateTime.of(2019,12,4,10,45).toInstant(ZoneOffset.ofHours(+8)).getEpochSecond();
        System.out.println(begin * 1000);
        long end =LocalDateTime.of(2019,12,4,10,50).toInstant(ZoneOffset.ofHours(+8)).getEpochSecond();
        System.out.println(end * 1000);
        List<Track> ss = getTrackListByTboxcode("90419018458", begin*1000,end*1000);
        System.out.println(JSON.toJSONString(ss));
        tsdbClient.shutdown();
    }

    public static List<Track> getTrackListByTboxcode(String tboxcode,long begintime,long endtime){
        String field1 = "longitude";
        String field2 = "latitude";
        String field5 = "speed";
        String field6 = "altitude";
        String field7 = "direction";
        String field8 = "gwtime";
        String field9 = "cptime";
        String field10 = "gpstime";
        List<String> fields = Arrays.asList(field1, field2, field5, field6, field7, field8, field9, field10);
        List<Query> queries = Arrays.asList(new Query().withMetric(database).withFields(fields).withFilters(new Filters().withAbsoluteStart(begintime).withAbsoluteEnd(endtime)));
        long query_begintime=System.currentTimeMillis();
        QueryDatapointsResponse response = tsdbClient.queryDatapoints(queries);
        long query_endtime=System.currentTimeMillis();
        long query_time=query_endtime-query_begintime;
        log.info("查询轨迹请求耗时"+query_time+"ms");
        ArrayList<Track> tracks = new ArrayList<>();
        for (Result result:response.getResults()){
            for (Group group:result.getGroups()){
                try {
                    log.info("一共查询"+ group.getTimeAndValueList().size()+"条");
                    for (Group.TimeAndValue timeAndValue:group.getTimeAndValueList()){
                        try {
                            double longitude = timeAndValue.getDoubleValue(0);
                            double latitude = timeAndValue.getDoubleValue(1);
                            double speed = timeAndValue.getDoubleValue(2);
                            double altitude = timeAndValue.getDoubleValue(3);
                            double direction = timeAndValue.getDoubleValue(4);
                            String gwtime = timeAndValue.getStringValue(5);
                            String cptime = timeAndValue.getStringValue(6);
                            String gpstime = timeAndValue.getStringValue(7);
                            Track track = new Track();
                            track.setGpsno(tboxcode);
                            track.setLng(longitude);
                            track.setLat(latitude);
                            track.setSpeed(new Double(speed).intValue());
                            track.setAltitude(new Double(altitude).intValue());
                            track.setDirection(new Double(direction).intValue());
                            track.setGwtime(gwtime);
                            track.setCptime(cptime);
                            track.setGpstime(gpstime);
                            tracks.add(track);
                        } catch (Exception e) {
                            log.error("解析轨迹异常:"+e.getMessage());
                        }
                    }
                } catch (Exception e) {
                    log.error("解析轨迹异常"+e.getMessage());
                }
            }
        }
        return tracks;


    }

  /*  public static void insertDataPoints(List<LocationInfoEntity> locationInfoEntities) {
        try {
            List<Datapoint> dataPoints = Lists.newArrayList();
            locationInfoEntities.forEach(entity -> {
                Map<String, String> tagMap = Maps.newHashMap();
                tagMap.put("tboxcode", entity.getTboxcode());
                long timestamp = entity.getUpdate();
                dataPoints.add(new Datapoint().withMetric(ApplicationConfig.HITSDB_DATABASE).withTags(tagMap)
                        .withField("longitude").addDoubleValue(timestamp, entity.getLongitude()));
                dataPoints.add(new Datapoint().withMetric(ApplicationConfig.HITSDB_DATABASE).withTags(tagMap)
                        .withField("latitude").addDoubleValue(timestamp, entity.getLatitude()));
                dataPoints.add(new Datapoint().withMetric(ApplicationConfig.HITSDB_DATABASE).withTags(tagMap)
                        .withField("blongitude").addDoubleValue(timestamp, entity.getBlongitude()));
                dataPoints.add(new Datapoint().withMetric(ApplicationConfig.HITSDB_DATABASE).withTags(tagMap)
                        .withField("blatitude").addDoubleValue(timestamp, entity.getBlatitude()));
                dataPoints.add(new Datapoint().withMetric(ApplicationConfig.HITSDB_DATABASE).withTags(tagMap)
                        .withField("speed").addDoubleValue(timestamp, entity.getSpeed()));
                dataPoints.add(new Datapoint().withMetric(ApplicationConfig.HITSDB_DATABASE).withTags(tagMap)
                        .withField("altitude").addDoubleValue(timestamp, entity.getAltitude()));
                dataPoints.add(new Datapoint().withMetric(ApplicationConfig.HITSDB_DATABASE).withTags(tagMap)
                        .withField("gpsmiles").addDoubleValue(timestamp, entity.getGpsmiles()));
                dataPoints.add(new Datapoint().withMetric(ApplicationConfig.HITSDB_DATABASE).withTags(tagMap)
                        .withField("direction").addDoubleValue(timestamp, entity.getDirection()));
                dataPoints.add(new Datapoint().withMetric(ApplicationConfig.HITSDB_DATABASE).withTags(tagMap)
                        .withField("pointstatus").addStringValue(timestamp, entity.getStatusfieldword()));
                dataPoints.add(new Datapoint().withMetric(ApplicationConfig.HITSDB_DATABASE).withTags(tagMap)
                        .withField("alarmssign").addStringValue(timestamp, entity.getAlarmssign()));
                dataPoints.add(new Datapoint().withMetric(ApplicationConfig.HITSDB_DATABASE).withTags(tagMap)
                        .withField("isAlarm").addLongValue(timestamp, entity.getIsAlarm()));
                dataPoints.add(new Datapoint().withMetric(ApplicationConfig.HITSDB_DATABASE).withTags(tagMap)
                        .withField("alignType").addLongValue(timestamp, entity.getAlignType()));
             *//*   dataPoints.add(new Datapoint().withMetric(ApplicationConfig.HITSDB_DATABASE).withTags(tagMap)
                        .withField("datapack").addStringValue(timestamp, entity.getDatapack()));*//*
                dataPoints.add(new Datapoint().withMetric(ApplicationConfig.HITSDB_DATABASE).withTags(tagMap)
                        .withField("gwtime").addStringValue(timestamp, entity.getGwtime()));
                dataPoints.add(new Datapoint().withMetric(ApplicationConfig.HITSDB_DATABASE).withTags(tagMap)
                        .withField("cptime").addStringValue(timestamp, entity.getCptime()));
                dataPoints.add(new Datapoint().withMetric(ApplicationConfig.HITSDB_DATABASE).withTags(tagMap)
                        .withField("gpstime").addStringValue(timestamp, entity.getGpstime()));
            });
            if(!dataPoints.isEmpty()){
                tsdbClient.writeDatapoints(dataPoints);
            }
            logger.info("车辆最新状态信息插入时序数据库成功 size:{} ",dataPoints.size());
        }catch (Exception e){
            logger.error("车辆最新状态信息插入时序数据库失败:{} ",e.getMessage());
        }
    }*/
}
