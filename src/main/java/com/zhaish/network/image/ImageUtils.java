package com.zhaish.network.image;

import com.baidubce.services.bcc.model.ImageModel;
import com.zhaish.network.bytes.HexStringUtils;
import org.apache.commons.io.IOUtils;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.io.*;
import java.util.Arrays;
import java.util.Iterator;

/**
 * @datetime:2020/7/21 17:46
 * @author: zhaish
 * @desc:
 **/
public class ImageUtils {
    public static void main(String[] args) {
        File f = new File("D:\\image\\vn.jfif");
        InputStream fis = null;
        try {
            fis = new FileInputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ImageInputStream iis = null;
        try {
            iis =ImageIO.createImageInputStream(fis);
            //ImageIO.get
            Iterator<ImageReader> iterable = ImageIO.getImageReaders(iis);
            iterable.forEachRemaining(r -> {
                System.out.println(r.getClass().getName());
            });
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (iis != null){
                try {
                    iis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        int len = (int) f.length();
        byte[] bImage = new byte[len];
        try {
            fis.read(bImage);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    /*    for (byte b : bImage) {

            System.out.println((char) b);
        }
*/
      /*  String hex = HexStringUtils.toHexString(bImage);
        System.out.println(hex);
        String imageStr = new String(bImage);
        System.out.println(imageStr);
*/
    }
}
