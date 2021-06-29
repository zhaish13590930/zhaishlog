import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @datetime:2019/9/9 20:02
 * @author: zhaish
 * @desc:
 **/
public class TestLog {
    private static Logger logger = LoggerFactory.getLogger(TestLog.class);
    public static void main(String[] args) {
/*        TSDB_URL=iovtboxdata.tsdb-ufg5pk5q605d.tsdb.iot.bj.baidubce.com
        KAFKA_808_BASICS_TOPIC=7ebc921ced3b4fc49deb6af32ba21d07__TOPIC-TEST-OLAFUWU-TBOX-808-REPORTS
        KAFKA_BOOTSTRAP_SERVERS=
                KAFKA_808_BASICS_GROUPID=7ebc921ced3b4fc49deb6af32ba21d07__GID-TEST-OLAFUWU-TBOX-808-REPORTS
        TSDB_DATABASE=iovtboxdata
        TSDB_PORT=
                TSDB_PWD=72ed25cd7d1046e0b1c4333afb039c34
                TSDB_KEY=92dcce22c6244eea80d57de6f45ad452*/
      log();

    }
    public static final void log(){
        logger.debug("123131");
        logger.info("123131");
        logger.warn("123131");
        logger.error("123131");
    }
}
