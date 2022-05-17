package com.rapipay.commonapi.utils;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

@Service
public class SessionManagement {
	
	public String aes256Encryption(String input, String initVector, String key) {

		try {
			IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");

			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

			byte[] cipherText;

			cipherText = cipher.doFinal(input.getBytes());
			return Base64.getEncoder().encodeToString(cipherText);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

}
