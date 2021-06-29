package com.zhaish.network;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * @datetime:2020/1/15 15:23
 * @author: zhaish
 * @desc:
 **/
public class FindFourNumber {
    public static void main(String[] args) throws IOException {
        File f = new File("D:\\DOWNLOAD\\math-deep.pdf1.pdf");
        File f2 = new File("D:\\DOWNLOAD\\math-deep.nojiamipdf");

        FileUtils.copyFile(f,f2);
    }


}
