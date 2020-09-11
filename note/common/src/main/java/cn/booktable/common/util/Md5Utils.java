package cn.booktable.common.util;


import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Md5工具类
 * @author ljc
 * @version 1.0
 */
public class Md5Utils {

    private static final String sv = "Uywe012$512*Iowe101ld752JHwnsf-1299OOw0";





    /**
     * MD5 摘要，使用系统缺省字符集编码
     * @param input
     * @return
     */
    public static String MD5(String input) {
        return MD5(input, Charset.defaultCharset());
    }

    /**
     * MD5 摘要
     * @param input
     * @param charset
     * @return
     */
    public static String MD5(String input, String charset) {
        return MD5(input, Charset.forName(charset));
    }

    /**
     * MD5 摘要
     * @param input
     * @param charset
     * @return
     */
    public static String MD5(String input, Charset charset) {
        MessageDigest md = null;

        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        md.update(input.getBytes(charset));

        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f' };
        byte tmp[] = md.digest();
        char str[] = new char[16 * 2];

        int k = 0;
        for (int i = 0; i < 16; i++) {
            byte byte0 = tmp[i];
            str[k++] = hexDigits[byte0 >>> 4 & 0xf];
            str[k++] = hexDigits[byte0 & 0xf];
        }

        String result = new String(str);

        return result;
    }



    /**
     * 用MD5算法进行加密
     * @param pStrPW 待被加密的字符串
     * @return 加密后的字符串
     */
    public static String crypt(String pStrPW) {
        String strCrypt = hash(pStrPW);
        if (strCrypt.length() > 0) {
            strCrypt += sv;
            strCrypt = hash(strCrypt);
        }

        return strCrypt;
    }

    /**
     * MD5算法进行散列
     * @param str 待散列的字符串
     * @return 散列后的字符串
     */
    public static String hash(String str) {
        String result = "";
        if (str == null) { // 如果传入参数为空，则返回空字符串
            return result;
        }
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] data = str.getBytes();
            int l = data.length;
            for (int i = 0; i < l; i++) {
                md.update(data[i]);
            }
            byte[] digest = md.digest();

            result = ByteUtils.byteArrayToHexString(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        return result;
    }

    /**
     * 验证MD5加签， 验证加签是否正确
     * @param sign 传入的加签字符
     * @param secret   密匙
     * @param signString  被加签字符串
     * @return
     */
    public static boolean checkMD5(String sign, String secret, String signString) {
        String md5Sign = Md5Utils.MD5(signString + secret, "UTF-8");
        if (md5Sign.equals(sign)) {
            return true;
        } else {
            return false;
        }
    }

}