package com.scrcu.common.utils;

import java.security.Key;
import java.security.Security;

import javax.crypto.Cipher;

/**
 * 解密类，可打包成JAR提供给其它开发单位
 * <p>版权所有：重庆南华中天</P>
 *
 * @author cb
 */
public class CZenithDecrypt {
  private static final String strDefaultKey = "scrcuzenith";
  //  private static final long MILLIS_PER_DAY = 24 * 60 * 60 * 1000;  //一天的毫秒数
  private static Key key = null;

  /**
   *
   * 将表示16进制值的字符串转换为byte数组
   * 和public static String byteArr2HexStr(byte[] arrB)互为可逆的转换过程
   *
   * @param strIn 需要转换的字符串
   * @return 转换后的byte数组
   * @throws Exception
   *
   * 本方法不处理任何异常，所有异常全部抛出
   * @author
   */
  private static byte[] hexStr2ByteArr(String strIn) throws Exception {
    byte[] arrB = strIn.getBytes();
    int iLen = arrB.length;
    //两个字符表示一个字节，所以字节数组长度是字符串长度除以2
    byte[] arrOut = new byte[iLen / 2];
    for (int i = 0; i < iLen; i = i + 2) {
      String strTmp = new String(arrB, i, 2);
      arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
    }

    return arrOut;
  }

  /**
   *
   * 解密字节数组
   *
   * @param arrB 需解密的字节数组
   * @return 解密后的字节数组
   *
   * @throws Exception
   */
  private static byte[] decrypt(byte[] arrB) throws Exception {
    Security.addProvider(new com.sun.crypto.provider.SunJCE());
    Cipher decryptCipher = Cipher.getInstance("DES");
    decryptCipher.init(Cipher.DECRYPT_MODE, getKey());

    return decryptCipher.doFinal(arrB);
  }

  /**
   *
   * 解密字符串
   *
   * @param strIn 需解密的字符串
   * @return 解密后的字符串
   *
   * @throws Exception
   */
  public static String decrypt(String strIn) throws Exception {
    String strDecrypt = new String(decrypt(hexStr2ByteArr(strIn)));
    if (strDecrypt.length() < 14) {
      throw new Exception("加密数据长度太短了;为非法加密数据;");
    }
    /*long lngcurrentTimeMillis = System.currentTimeMillis();
    long lngTimeMillis = Long.parseLong(strDecrypt.substring(strDecrypt.length() - 13, strDecrypt.length()));
    //当相差大于一天时抛异常
    if (lngcurrentTimeMillis - lngTimeMillis > MILLIS_PER_DAY) {
      throw new Exception("当前加密数据过期，有效期一天;");
    }*/

    return getRandomString(strDecrypt.substring(0, strDecrypt.length() - 13));
  }

  /**
   * 每隔一字组成新的字符串
   *
   * @author cb
   * @param strA
   * @return
   */
  private static String getRandomString(String strA) {
    StringBuffer sbB = new StringBuffer();
    char[] charA = strA.toCharArray();
    int Alen = charA.length;
    for (int i = 0; i < Alen; i = i + 2) {
      sbB.append(charA[i]);
    }

    return sbB.toString();
  }

  /**
   *
   * 从指定字符串生成密钥，密钥所需的字节数组长度为8位 不足8位时后面补0，超出8位只取前8位
   *
   * @param arrBTmp 构成该字符串的字节数组
   * @return 生成的密钥
   *
   * @throws java.lang.Exception
   */
  private static Key getKey() throws Exception {
    if (key == null) {
      //创建一个空的8位字节数组（默认值为0）
      byte[] arrB = new byte[8];
      //将原始字节数组转换为8位
      byte[] arrBTmp = strDefaultKey.getBytes();
      if (arrBTmp.length > arrB.length) {
        System.arraycopy(arrBTmp, 0, arrB, 0, arrB.length);
      } else {
        System.arraycopy(arrBTmp, 0, arrB, 0, arrBTmp.length);
      }

      //生成密钥
      key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");
    }

    return key;
  }

}
