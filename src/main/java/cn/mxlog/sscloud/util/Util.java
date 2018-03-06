package cn.mxlog.sscloud.util;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigInteger;
import java.net.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * Created by F.Du on 2017/9/11.
 */
public class Util {


    public static void main(String agrs[]) {

    }

    protected final Logger logger = LoggerFactory.getLogger(this
            .getClass());

    public static String genOrderNo() {
        Random random = new Random();
        return System.currentTimeMillis() + ""+ (random.nextInt(99999) + 10000);

    }

    public static Date getCurrentDate() {
        return DateUtils.truncate(getCurrentDateTime(),
                Calendar.DAY_OF_MONTH);
    }

    public static Date getCurrentDateTime() {
        return new Date();
    }

    public static String getGUIDCode() {
        return UUID.randomUUID().toString();
    }

    public static String getUniqueFileName() {
        return getGUIDCode();
    }

    public static String getHashCode(String data, String algorithm) {
        MessageDigest digest;
        try {
            digest = java.security.MessageDigest
                    .getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            return "";
        }
        digest.update(data.getBytes());
        byte[] hash = digest.digest();
        return toHex(hash);
    }

    public long getHashNumber(String data) {
        if (StringUtils.isBlank(data))
            return 0;
        return data.hashCode();
    }

    public static String getMD5Code(String data) {
        return getHashCode(data, "MD5");
    }

    public static String getSHA1Code(String data) {
        return getHashCode(data, "sha1");
    }

    public static String toHex(byte[] bytes) {
        BigInteger bi = new BigInteger(1, bytes);
        return String.format("%0" + (bytes.length << 1) + "X",
                bi);
    }

    public static int getCurrentTime() {
        Date dateTime = getCurrentDateTime();
        Calendar current = Calendar.getInstance();
        current.setTime(dateTime);
        int hour = current.get(Calendar.HOUR_OF_DAY);
        int minute = current.get(Calendar.MINUTE);
        int second = current.get(Calendar.SECOND);
        int secondID = hour * 10000 + minute * 100 + second;
        return secondID;
    }

    public static Date getDateFromDateTime(Date dateTime) {
        return DateUtils.truncate(dateTime,
                Calendar.DAY_OF_MONTH);
    }

    public static String getBase64(String data) {
        if (data == null) {
            return null;
        }
        return new String(Base64.encode(data.getBytes()));
    }

    public static String getRemoteIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            return ip;
        }
        return request.getRemoteAddr();
    }


    /**
     * udp 请求
     *
     * @param host
     * @param port
     * @param params
     * @return
     */
    public static String udp(String host, String port, String params) {
        DatagramSocket client = null;
        try {
            client = new DatagramSocket();
            client.setSoTimeout(30 * 1000); // 30秒超时

            //将发送的内容转换为byte数组
            byte[] sendData = params.getBytes();
            //将服务器IP转换为InetAddress对象
            InetAddress server = InetAddress.getByName(host);

            //构造发送的数据包对象
            DatagramPacket sendDp = new DatagramPacket(sendData, sendData.length, server, Integer.parseInt(port));

            //接收数据
            client.send(sendDp);
            byte[] receiveData = new byte[1024 * 10];
            DatagramPacket receiveDp = new DatagramPacket(receiveData, receiveData.length);

            //接收
            client.receive(receiveDp);
            client.close();
            return new String(receiveDp.getData(), 0, receiveDp.getLength());
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void udpNoResult(String host, String port, String params) {
        DatagramSocket client = null;
        try {
            client = new DatagramSocket();
            client.setSoTimeout(30 * 1000); // 30秒超时

            //将发送的内容转换为byte数组
            byte[] sendData = params.getBytes();
            //将服务器IP转换为InetAddress对象
            InetAddress server = InetAddress.getByName(host);

            //构造发送的数据包对象
            DatagramPacket sendDp = new DatagramPacket(sendData, sendData.length, server, Integer.parseInt(port));

            //发送数据
            client.send(sendDp);

            client.close();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
