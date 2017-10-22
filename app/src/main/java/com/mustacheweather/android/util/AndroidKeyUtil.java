package com.mustacheweather.android.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.fingerprint.FingerprintManager;
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

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

/**
 * Created by caojing on 2017/10/18.
 */

public class AndroidKeyUtil {

    private static final String TAG = "AndroidKeyUtil";

    private static String PERFKEY_KEY_IV = "PREF_KEYIV";

    public static byte[] mIV = null;

    public static SecretKey getKey(){
        SecretKey secretKey = loadKey();
        if (secretKey == null){
            Log.w(TAG, "getKey: need generate new key.");
            secretKey = generateAndSaveKey();
        }
        return secretKey;
    }

    public static SecretKey generateAndSaveKey() {


        try {
            KeyStore store = KeyStore.getInstance("AndroidKeyStore");
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
            KeyStore mKeyStore = KeyStore.getInstance("AndroidKeyStore");
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

    public static byte[] encrypt(byte[] plaintext, SecretKey key){
        try {
            Cipher cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/"+ KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);
            if (mIV != null){
                cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(mIV));
            } else {
                cipher.init(Cipher.ENCRYPT_MODE, key);
            }
            final FingerprintManager.CryptoObject crypto;
            byte[] ciphertext = cipher.doFinal(plaintext);
            if (mIV == null) {
                mIV = cipher.getIV();
            }
            return ciphertext;
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
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static byte[] decrypt(byte[] ciphertext, SecretKey key){
        final Cipher cipher;
        try {
            cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/"+ KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(mIV));
            byte[] plaintext = cipher.doFinal(ciphertext);
            return plaintext;
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

        return null;
    }

    public static void init(){
        SharedPreferences pref = Environment.getContext().getSharedPreferences("cipherInfo", Context.MODE_PRIVATE);
        String base64IV = pref.getString(PERFKEY_KEY_IV, null);
        if (base64IV != null){
            AndroidKeyUtil.mIV = Base64.decode(base64IV, Base64.DEFAULT);
        }
    }

    public static void storeIV(){
        SharedPreferences pref = Environment.getContext().getSharedPreferences("cipherInfo", Context.MODE_PRIVATE);
        String base64IV = Base64.encodeToString(AndroidKeyUtil.mIV, Base64.DEFAULT);
        pref.edit().putString(PERFKEY_KEY_IV, base64IV).apply();
    }
}
