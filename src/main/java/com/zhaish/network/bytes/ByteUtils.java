/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zhaish.network.bytes;


import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;

/**
 * @Author: fudaohui
 * @Description: byte解析类
 * @Date: 2019/7/25 16:26
 */
public class ByteUtils {


    /**
     * 将Interger等长度不超过255的数据转为byte
     *
     * @param src
     * @return
     */
    public static byte getByte(Integer src) {
        return (byte) (src & 0xff);
    }


    /**
     * 转换short为byte
     *
     * @param src 需要转换的short
     */
    public static byte[] getShortBytes(short src) {
        byte[] dst = new byte[2];
        dst[0] = (byte) (src >> 8);
        dst[1] = (byte) (src >> 0);
        return dst;
    }

    /**
     * 通过byte数组取到short
     *
     * @param b
     * @param index 第几位开始取
     * @return
     */
    public static int getShortFromBytes(byte[] b, int index) {
        int ret = (short) (((b[index + 0] << 8) | b[index + 1] & 0xff));
        //设备上报都是无符号数据，short为0~65535，java平台范围都是有符号，-32768~32766，需要使用int处理
        if (ret < 0) {
            ret = ret + 65535;
        }
        return ret;
    }

    /**
     * 转换int为byte数组
     *
     * @param x
     */
    public static byte[] getIntBytes(int x) {
        byte[] bb = new byte[4];
        bb[0] = (byte) (x >> 24);
        bb[1] = (byte) (x >> 16);
        bb[2] = (byte) (x >> 8);
        bb[3] = (byte) (x >> 0);
        return bb;
    }

    /**
     * 通过byte数组取到int
     *
     * @param bb
     * @param index 第几位开始
     * @return 返回long类型，终端上报是无符号类型，java，int范围可能不够用
     */
    public static long getIntFromBytes(byte[] bb, int index) {
        return (int) ((((bb[index + 0] & 0xff) << 24)
                | ((bb[index + 1] & 0xff) << 16)
                | ((bb[index + 2] & 0xff) << 8) | ((bb[index + 3] & 0xff) << 0)));
    }

    /**
     * 转换long型为byte数组
     *
     * @param x
     */
    public static byte[] getLongBytes(long x) {
        byte[] bb = new byte[8];
        bb[0] = (byte) (x >> 56);
        bb[1] = (byte) (x >> 48);
        bb[2] = (byte) (x >> 40);
        bb[3] = (byte) (x >> 32);
        bb[4] = (byte) (x >> 24);
        bb[5] = (byte) (x >> 16);
        bb[6] = (byte) (x >> 8);
        bb[7] = (byte) (x >> 0);
        return bb;
    }

    /**
     * 通过byte数组取到long
     *
     * @param bb
     * @param index
     * @return
     */
    public static long getLongFromBytes(byte[] bb, int index) {
        return ((((long) bb[index + 0] & 0xff) << 56)
                | (((long) bb[index + 1] & 0xff) << 48)
                | (((long) bb[index + 2] & 0xff) << 40)
                | (((long) bb[index + 3] & 0xff) << 32)
                | (((long) bb[index + 4] & 0xff) << 24)
                | (((long) bb[index + 5] & 0xff) << 16)
                | (((long) bb[index + 6] & 0xff) << 8) | (((long) bb[index + 7] & 0xff) << 0));
    }


    /**
     * 从bb中第index开始截取length个byte根据charset转为string类型
     *
     * @param bb
     * @param index
     * @param length
     * @param charset
     * @return
     */
    public static String getStrFrombytes(byte[] bb, int index, int length, String charset) {
        if (index < 0 || (index + length > bb.length)) {
            return null;
        }
        byte[] retByte = new byte[length];
        System.arraycopy(bb, index, retByte, 0, length);
        byte[] trimZeroByte = trimZeroByte(retByte);
        String retStr = null;
        if (StringUtils.isEmpty(charset)) {
            retStr = new String(trimZeroByte);
        } else {
            try {
                retStr = new String(trimZeroByte, charset);
            } catch (UnsupportedEncodingException e) {
                retStr = new String(retByte);
            }
        }
        return retStr;
    }

    /**
     * hex转可识别的字符串数据
     *
     * @param src
     * @param isPretty true,是否有空格展示；false,无
     * @return
     */
    public static String bytesToHexString(byte[] src, boolean isPretty) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
            if (isPretty) {
                stringBuilder.append(" ");
            }
        }
        return stringBuilder.toString().toUpperCase();
    }

    /**
     * Convert hex string to byte[]
     *
     * @param hexString the hex string
     * @return byte[]
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    /**
     * Convert char to byte
     *
     * @param c char
     * @return byte
     */
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }


    /**
     * 去除包尾部的为了补齐0x00不可见byte
     *
     * @param bb
     * @return
     */
    public static byte[] trimZeroByte(byte[] bb) {

        int length = 0;
        for (byte b : bb) {
            if (b != 0) {
                length++;
            }
        }
        byte[] ret = new byte[length];
        System.arraycopy(bb, 0, ret, 0, length);
        return ret;
    }

    /**
     * 根据start和end截取byte数组
     *
     * @param src
     * @param start
     * @param end
     * @return
     */
    public static byte[] subBytes(byte[] src, int start, int end) {

        if (src == null || start > end || start < 0 || end > src.length) {
            return src;
        }
        byte[] ret = new byte[end - start];
        System.arraycopy(src, start, ret, 0, end - start);
        return ret;
    }


    /**
     * 有符号长度数据转为无符号数据
     *
     * @param b
     * @return
     */
    public static int getLength(byte b) {

        if (b < 0) {
            return b + 256;
        }
        return b;
    }

    public static void main(String[] args) {
//        byte[] aa = {0x01, 0x02, 0x03, 0x04, 0x05, 0x06};
//        byte[] bytes = subBytes(aa, 1, 6);
//        byte aByte = getByte(49);

//        byte[] aByte = {0x42, 0x43, 0x43, 0x30, 0x31, 0x32, 0x00, 0x00, 0x00};
//        byte[] aByte1 = trimZeroByte(aByte);
//        String strFrombytes = getStrFrombytes(aByte, 1, 8, null);
//        System.out.println(strFrombytes);

//        System.out.println(getLength((byte) 0x0));
//        int length = getLength((byte) 0x80);
//        System.out.println(length);

//        short shortFromBytes = getShortFromBytes(new byte[]{0x00, 0x23}, 0);
//        System.out.println(shortFromBytes);

        aa("01010000000000000000000000000000");
    }

    public static void aa(String status) {

        //32
        int aa = 0;
        char[] chars = status.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            aa = (Byte.parseByte(chars[i] + "") << i) & 0xffffffff | aa;
        }
        byte[] intBytes = ByteUtils.getIntBytes(aa);
        System.out.println(ByteUtils.bytesToHexString(intBytes, false));

    }

}
