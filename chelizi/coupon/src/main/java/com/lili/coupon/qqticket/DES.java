package com.lili.coupon.qqticket;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;


public class DES {
    private static final String QQCARD_DES_CBC_KEY = "$248karD";    // QQ卡券DES_CBC加密KEY

    private static byte[] toHH(int n) {
        byte[] b = new byte[4];
        b[3] = (byte) (n & 0xff);
        b[2] = (byte) (n >> 8 & 0xff);
        b[1] = (byte) (n >> 16 & 0xff);
        b[0] = (byte) (n >> 24 & 0xff);
        return b;
    }

    private static String parseByte2HexStr(byte[] buf) {
        StringBuilder sb = new StringBuilder();

        for (byte b : buf) {
            String hex = Integer.toHexString(b & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    private static String encryptDESCBCForQQCard(byte[] src, String key) throws Exception {
        // --生成key,同时制定是des还是DESede,两者的key长度要求不同
        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("utf-8"));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);

        // --加密向量
        byte bv[] = {0x12, 0x34, 0x56, 0x78, (byte) 0x90, (byte) 0xAB, (byte) 0xCD, (byte) 0xEF};
        IvParameterSpec iv = new IvParameterSpec(bv);

        // --通过Chipher执行加密得到的是一个byte的数组,Cipher.getInstance("DES")就是采用ECB模式,cipher.init(Cipher.ENCRYPT_MODE,secretKey)就可以了.
        Cipher cipher = Cipher.getInstance("DES/CBC/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);

        byte[] b = cipher.doFinal(src);
        return parseByte2HexStr(b).toLowerCase();
    }

    public static String getQQCardAccessToken(int appid) throws Exception {
        byte[] appidByte = toHH(appid);            // 200454989
        byte[] timestampByte = toHH((int) (System.currentTimeMillis() / 1000L));    // 1462258582

        byte[] data = {0x02, timestampByte[0], timestampByte[1], timestampByte[2], timestampByte[3],
                appidByte[0], appidByte[1], appidByte[2], appidByte[3], 0x03,
                0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
        return encryptDESCBCForQQCard(data, QQCARD_DES_CBC_KEY);
    }

    public static void main(String args[]) throws Exception {
        DES des = new DES();
        int appid = 1234567;
        System.out.println(getQQCardAccessToken(appid));
    }
}

