package com.limaila.support.global.compress.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.BeanUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

@Slf4j
public class GzipUtil {

    public static final String compress(String src)  {
        byte[] bytes = compress(src.getBytes());
        return Base64.getEncoder().encodeToString(bytes);
    }


    public static final byte[] compress(byte[] bytes) {
        ByteArrayOutputStream aos = new ByteArrayOutputStream(1024);
        GZIPOutputStream gos = null;
        byte[] bs = null;
        try {
            log.debug("GzipUtil 压缩前大小：" + bytes.length);
            gos = new GZIPOutputStream(aos);
            gos.write(bytes);
            gos.flush();
            log.debug("GzipUtil 压缩后大小：" + aos.toByteArray().length);
            bs = aos.toByteArray();
        } catch (UnsupportedEncodingException e) {
            log.error("GzipUtil", e);
        } catch (IOException e) {
            log.error("GzipUtil", e);
        } finally {
            IOUtils.closeQuietly(gos, aos);
        }
        return bs;
    }

    public static final String uncompress(String src) {
        byte[] decode = Base64.getDecoder().decode(src);
        byte[] bytes = uncompress(decode);
        return new String(bytes);
    }


    public static final byte[] uncompress(byte[] bs) {
        ByteArrayInputStream bais = null;
        GZIPInputStream gis = null;
        byte bytes[] = null;
        try {
            bais = new ByteArrayInputStream(bs);
            gis = new GZIPInputStream(bais);
            bytes = IOUtils.toByteArray(gis);
        } catch (IOException e) {
            log.error("GzipUtil", e);
        } finally {
            IOUtils.closeQuietly(gis, bais);
        }
        return bytes;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        String str = "%5B%7B%22lastUpdateTime%22%3A%222011-10-28+9%3A39%3A41%22%2C%22smsList%22%3A%5B%7B%22liveState%22%3A%221";
        System.out.println("压缩前 ：" + str);
        String compress = compress(str);
        System.out.println("压缩后 ：" + compress);
        String uncompress = uncompress(compress);
        System.out.println("解压后 :" + uncompress);
    }
}
