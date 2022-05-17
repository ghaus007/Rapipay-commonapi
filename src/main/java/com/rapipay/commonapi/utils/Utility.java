package com.rapipay.commonapi.utils;

import java.time.Instant;
import java.util.Base64;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

import com.nimbusds.jose.shaded.json.JSONObject;
import com.rapipay.commonapi.models.UserDetailsModel;
import com.rapipay.commonapi.requestdto.CreateSubUserDto;

@Service
public class Utility {
	public String generateRandomNumber() {
		int leftLimit = 48; // numeral '0'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 10;
		Random random = new Random();

		String generatedString = random.ints(leftLimit, rightLimit + 1)
				.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(targetStringLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

		System.out.println(generatedString);
		return generatedString;
	}

	public long getCurrentTimeStamp() {
		Instant instant = Instant.now();
		long timeStampSeconds = instant.getEpochSecond();
		return timeStampSeconds;

	}

	public String getTimeStamp() {
		Instant instant = Instant.now();
		long timeStampSeconds = instant.getEpochSecond();
		return String.valueOf(timeStampSeconds);

	}

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
	
	public String prepareDataToCreateParentSubUSer() {
		return null;
		
	}
	
	public void prepareRevRkiData(JSONObject responseData,UserDetailsModel userDetailsModel,String revStatus[]) {
		String isRev[]= {String.valueOf(responseData.get("isRev"))};
		String isRki[]= {String.valueOf(responseData.get("isRki"))};

		userDetailsModel.setRevStatus(revStatus);
		userDetailsModel.setIsRev(isRev);
		userDetailsModel.setIsRki(isRki);
		
	}
	
	
	public void prepareFinalData(JSONObject responseData,UserDetailsModel userDetailsModel,String revStatus[]) {
		String isRev[]= {String.valueOf(responseData.get("isRev").equals("1")?"Y":"N")};
		String isRki[]= {String.valueOf(responseData.get("isRki").equals("1")?"Y":"N")};

		userDetailsModel.setRevStatus(revStatus);
		userDetailsModel.setIsRev(isRev);
		userDetailsModel.setIsRki(isRki);
		
	}
	
	
public void prepareDataToCreateTerminalSubUSer(CreateSubUserDto createSubUserDto ,UserDetailsModel userDetailsModel ) {
	
	String [] deviceSerialNo= {createSubUserDto.getDeviceSerialNo()};
	String [] tid= {createSubUserDto.getTid()};

	userDetailsModel.setUserStatus("1");
	userDetailsModel.setUserId(createSubUserDto.getUserId());
	userDetailsModel.setUserAccess(createSubUserDto.getUserAccess());
	userDetailsModel.setMobileNo(createSubUserDto.getMobileNo());
	userDetailsModel.setAuthPin(createSubUserDto.getPassword());
	userDetailsModel.setMid(createSubUserDto.getMid());
	userDetailsModel.setTid(tid);
	userDetailsModel.setDeviceSerialNo(deviceSerialNo);
	userDetailsModel.setFullName(createSubUserDto.getUserName());
	// userAccess//inital//orTime//isRev//isRki//revStatus//deviceName//deviceType//deviceType//appVersion//lat//lon//clientIP//userImageName
	userDetailsModel.setInitial(String.valueOf(createSubUserDto.getUserName().charAt(0)));
	userDetailsModel.setOneTime("true");
	userDetailsModel.setDeviceName("");
	userDetailsModel.setDeviceType("");
	userDetailsModel.setAppVersion("");
	userDetailsModel.setLat("");
	userDetailsModel.setLon("");
	userDetailsModel.setClientIP("");
	userDetailsModel.setUserImageName("");
		
	}
	
	
}
