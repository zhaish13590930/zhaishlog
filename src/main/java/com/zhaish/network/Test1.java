package com.zhaish.network;

import com.baidubce.util.StringFormatUtils;
import com.zhaish.network.bytes.ByteUtils;
import org.apache.commons.lang3.math.NumberUtils;

public class Test1 {
    public static void main(String[] args) {
        String a = "0xF";
        System.out.println(a);
        boolean r = isNumeric(a);
        System.out.println(r);
        r = NumberUtils.isDigits(a);
        System.out.println(r);
        r = NumberUtils.isCreatable(a);
        System.out.println(r);
        Integer num = NumberUtils.createInteger(a);
        System.out.println(num);
        System.out.println(Integer.toHexString(num));
        byte[] arr = ByteUtils.getIntBytes(num);
        String str = ByteUtils.bytesToHexString(arr,false);
        System.out.println(str);
    }

    public static boolean isNumeric(final CharSequence cs) {
        if (isEmpty(cs)) {
            return false;
        }
        final int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isDigit(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }


    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }
}
