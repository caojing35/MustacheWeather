package com.mustacheweather.android.util;

import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Handler;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Base64;
import android.util.Log;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;

/**
 * Created by caojing on 2017/10/18.
 */

public class AndroidKeyUtil {

    private final static String HEX = "0123456789ABCDEF";
    private  static final String CBC_PKCS5_PADDING = "AES/CBC/PKCS5Padding";//AES是加密方式 CBC是工作模式 PKCS5Padding是填充模式
    private  static final String AES = "AES";//AES 加密
    private  static final String  SHA1PRNG="SHA1PRNG";//// SHA1PRNG 强随机种子算法, 要区别4.2以上版本的调用方法

    private static final String TAG = "AndroidKeyUtil";

    private static byte[] mIV = null;

    public static SecretKey generateAndSaveKey() {


        try {
            KeyStore store = KeyStore.getInstance(KeyStore.getDefaultType());
//            KeyStore mKeyStore = KeyStore.getInstance("jks");
//            PBEKeySpec keySpec = new PBEKeySpec("TestContent".toCharArray());
//            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("AES/CBC/PKCS7Padding");
//            SecretKey key = keyFactory.generateSecret(keySpec);
//            KeyStore.SecretKeyEntry entry = new KeyStore.SecretKeyEntry(key);
            //存储key
            //mKeyStore.setEntry("weatherKey", entry, new KeyStore.PasswordProtection(psw.toCharArray()));

            final KeyGenerator generator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
            final int purpose = KeyProperties.PURPOSE_DECRYPT | KeyProperties.PURPOSE_ENCRYPT;
            store.load(null);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                final KeyGenParameterSpec.Builder builder = new KeyGenParameterSpec.Builder("weatherKey", purpose);
                builder.setBlockModes(KeyProperties.BLOCK_MODE_CBC);
                builder.setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7);
                builder.setKeySize(128);
                generator.init(builder.build());
                SecretKey secretKey = generator.generateKey();
                return secretKey;
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
//        } catch (InvalidKeySpecException e) {
//            e.printStackTrace();
//        } catch (KeyStoreException e) {
//            e.printStackTrace();
        } catch (RuntimeException re){
            re.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static SecretKey loadKey() {
        try {
            KeyStore mKeyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            mKeyStore.load(null);
            final SecretKey key = (SecretKey) mKeyStore.getKey("weatherKey", null);
            return key;
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return null;
    }

    public static byte[] encrypt(String content, SecretKey key){
        try {
            Cipher cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/"+ KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            final FingerprintManager.CryptoObject crypto;
            byte[] encrypted = cipher.doFinal(content.getBytes());
            mIV = cipher.getIV();
            return encrypted;
            //String result= Base64.encodeToString(encrypted, Base64.URL_SAFE);
            //Log.d(TAG, "result:" + result);

            //return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String decrypt(byte[] content, SecretKey key){
        final Cipher cipher;
        try {
            cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/"+ KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(mIV));
            byte[] decrypted = cipher.doFinal(content);
            String result = new String(decrypted);
            Log.d(TAG, "Decrypted result is:\n" + result + "\n");
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }

        return "";
    }

}
