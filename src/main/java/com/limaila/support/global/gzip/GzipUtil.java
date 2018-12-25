package com.limaila.support.global.gzip;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

@Slf4j
public class GzipUtil {

    public static final byte[] compress(byte[] bytes) {
        ByteArrayOutputStream aos = new ByteArrayOutputStream(1024);
        GZIPOutputStream gos = null;
        byte[] bs = null;
        try {
            log.debug("BodyFilter 压缩前大小：" + bytes.length);
//            log.debug("BodyFilter 压缩前数据：" + new String(bytes, "utf-8"));
//            GZIP压缩
            gos = new GZIPOutputStream(aos);
            gos.write(bytes);
            gos.flush();
            log.debug("BodyFilter 压缩后大小：" + aos.toByteArray().length);
            bs = aos.toByteArray();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(gos, aos);
        }
        return bs;
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
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(gis, bais);
        }
        return bytes;
    }
}
