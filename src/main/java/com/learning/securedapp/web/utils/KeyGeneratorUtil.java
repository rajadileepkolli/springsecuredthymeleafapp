package com.learning.securedapp.web.utils;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import com.learning.securedapp.exception.SecuredAppException;
import com.learning.securedapp.exception.SecuredAppExceptionBean;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KeyGeneratorUtil {
    
    private static final String KEY_GENERATOR = "ba189daec8f83047";

    public static String encrypt(String plainText) throws SecuredAppException {
        Key aesKey = new SecretKeySpec(KEY_GENERATOR.getBytes(), "AES");
        Cipher cipher;
        byte[] encoder = null;
        try {
            cipher = Cipher.getInstance("AES");
            byte[] plainTextByte = plainText.getBytes();
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
            byte[] encryptedByte = cipher.doFinal(plainTextByte);
            encoder = Base64.encodeBase64(encryptedByte);
        } catch (NoSuchAlgorithmException e) {
            log.error("Exception ::{}", e.getMessage(), e);
            SecuredAppExceptionBean bean = new SecuredAppExceptionBean();
            bean.setFaultCode("1102");
            bean.setFaultString("NoSuchAlgorithmException");
            throw new SecuredAppException(bean);
        } catch (NoSuchPaddingException e) {
            log.error("Exception ::{}", e.getMessage(), e);
            SecuredAppExceptionBean bean = new SecuredAppExceptionBean();
            bean.setFaultCode("1103");
            bean.setFaultString("NoSuchPaddingException");
            throw new SecuredAppException(bean);
        } catch (InvalidKeyException e) {
            log.error("Exception ::{}", e.getMessage(), e);
            SecuredAppExceptionBean bean = new SecuredAppExceptionBean();
            bean.setFaultCode("1103");
            bean.setFaultString("InvalidKeyException");
            throw new SecuredAppException(bean);
        } catch (IllegalBlockSizeException e) {
            log.error("Exception ::{}", e.getMessage(), e);
            SecuredAppExceptionBean bean = new SecuredAppExceptionBean();
            bean.setFaultCode("1105");
            bean.setFaultString("IllegalBlockSizeException");
            throw new SecuredAppException(bean);
        } catch (BadPaddingException e) {
            log.error("Exception ::{}", e.getMessage(), e);
            SecuredAppExceptionBean bean = new SecuredAppExceptionBean();
            bean.setFaultCode("1106");
            bean.setFaultString("BadPaddingException");
            throw new SecuredAppException(bean);
        }
        return new String(encoder);
    }

}
