package com.cxd.cxd4android.global;

import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;

public class RSAUtils {

    private static PublicKey getPublicKey() throws Exception
    {
        String Modulus = "ALJ1h3sAB06/hTKd7QmNFJbx1lPO/XvJGE03xnkfWVN2Q3z62ffm56sq7YMDuV1LGkcfYdLCmu9Cu8vOfu/lKo2Q0pcEEBoY35s0MihKBh0pXrPcW6Gx5CBM195mIL17b+AqZ+34KqhO9MqpRH6vFpf+VL8BeotuzswhjEAOAh9d";
        String PublicExponent = "AQAB";
        byte[] modulusBytes = Base64.decode(Modulus, Base64.DEFAULT);
        byte[] exponentBytes = Base64.decode(PublicExponent, Base64.DEFAULT);
        BigInteger modulus = new BigInteger(1, modulusBytes);
        BigInteger exponent = new BigInteger(1, exponentBytes);

        RSAPublicKeySpec rsaPubKey = new RSAPublicKeySpec(modulus, exponent);
        KeyFactory fact = KeyFactory.getInstance("RSA");
        return fact.generatePublic(rsaPubKey);
    }

    public static String rsaEncrypt(String str)
    {

        try
        {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, getPublicKey());

            int buffSize = 117;
            byte[] data = str.getBytes("UTF-8");

            byte[] buffer = new byte[buffSize];

            ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            int readLen = inputStream.read(buffer, 0, buffSize);
            while (readLen > 0)
            {
                byte[] dataToEnc = new byte[readLen];
                System.arraycopy(buffer, 0, dataToEnc, 0, readLen);
                byte[] encData = cipher.doFinal(dataToEnc);
                outputStream.write(encData);
                buffer = new byte[buffSize];
                readLen = inputStream.read(buffer, 0, buffSize);
            }

            inputStream.close();
            byte[] result = outputStream.toByteArray();

            return Base64.encodeToString(result, Base64.DEFAULT);

        }
        catch (Exception e)
        {
            return null;
        }
    }

}
