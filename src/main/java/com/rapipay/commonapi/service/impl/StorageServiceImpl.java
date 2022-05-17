package com.rapipay.commonapi.service.impl;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.Objects;

import com.portal.common.models.Response;
import com.portal.common.models.ResponseHandlingModel;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.rapipay.commonapi.middleware.RequestHandling;
import com.rapipay.commonapi.middleware.SessionHandling;
import com.rapipay.commonapi.models.UserDetailsModel;
import com.rapipay.commonapi.redis.RedisOperations;
import com.rapipay.commonapi.service.StorageService;
import com.rapipay.commonapi.validations.CommonRequestValidations;
import com.rapipay.commonapi.validations.HeadersValidation;

@Service
public class StorageServiceImpl implements StorageService {
	private final Path rootLocation = Paths.get("./uploads");

	@Autowired
	CommonRequestValidations validations;
	@Autowired
	SessionHandling sessionHandling;
	@Autowired
	ModelMapper modelMapper;
	@Autowired
	ObjectMapper mapper;
	@Autowired
	RedisOperations redisClient;
	@Autowired
	HeadersValidation headersValidation;

	Logger log = LoggerFactory.getLogger(StorageServiceImpl.class);

	@Override
	public Response uploadFile(MultipartFile file, HttpHeaders headers) {
		ResponseHandlingModel responseHandle = new ResponseHandlingModel();
		try {
			String userId = null;
			if (validations.uploadProfileImageValidations(file, responseHandle).getResponseCode().equals("301")
					|| headersValidation.validateHeaders(headers, responseHandle).getResponseCode().equals("301")
					|| !sessionHandling.checkSession(headers, responseHandle).getResponseCode()
							.equals("200")) {
				return modelMapper.map(responseHandle, Response.class);

			}
			if (Objects.nonNull(headers.getFirst("requestFrom")) && headers.getFirst("requestFrom").equals("mobile")) {
				userId = headers.getFirst("userId");

			} else {

				responseHandle = sessionHandling.getUserIdFromSession(headers.getFirst("sessionRefNo"), responseHandle);
				if (!responseHandle.getResponseCode().equals("200")) {
					return modelMapper.map(responseHandle, Response.class);
				}

				userId = (String) responseHandle.getResponseData();
			}
			responseHandle = saveFileToLocation(responseHandle, file, userId);

		} catch (Exception e) {
			responseHandle.setResponseCode("501");
			responseHandle.setResponseMessage("Exception Occurred.");
			responseHandle.setResponseData(e.getMessage());
		}

		return modelMapper.map(responseHandle, Response.class);

	}

	@Override
	public Response getFile(HttpHeaders headers) {
		ResponseHandlingModel responseHandle = new ResponseHandlingModel();
		try {
			if (headersValidation.validateHeaders(headers, responseHandle).getResponseCode().equals("301")
					|| !sessionHandling.checkSession(headers, responseHandle).getResponseCode()
							.equals("200")) {
				return modelMapper.map(responseHandle, Response.class);
			}
			String userId = null;
			if (Objects.nonNull(headers.getFirst("requestFrom")) && headers.getFirst("requestFrom").equals("mobile")) {
				userId = headers.getFirst("userId");

			} else {
				userId = (String) sessionHandling.getUserIdFromSession(headers.getFirst("sessionRefNo"), responseHandle)
						.getResponseData();
			}

			responseHandle = getImageNameFromRedis(userId, responseHandle);
			if (!responseHandle.getResponseCode().equals("200")) {
				return modelMapper.map(responseHandle, Response.class);
			}

			String imageName = (String) responseHandle.getResponseData();
			String pathString = "./uploads/" + imageName;
			Path p = Paths.get(pathString);
			byte[] data = Files.readAllBytes(p);
			String encodedString = Base64.getEncoder().encodeToString(data);
			responseHandle.setResponseCode("200");
			responseHandle.setResponseData(encodedString);

		} catch (

		Exception e) {
			responseHandle.setResponseCode("501");
			responseHandle.setResponseMessage("Exception Occurred.");
			responseHandle.setResponseData(e.getMessage());
		}
		return modelMapper.map(responseHandle, Response.class);

	}

	private ResponseHandlingModel saveFile(MultipartFile file, String userId) {

		ResponseHandlingModel response = new ResponseHandlingModel();
		String extension = FilenameUtils.getExtension(file.getOriginalFilename());
		String newFileName = userId + "." + extension;
		Path destinationFile = this.rootLocation.resolve(Paths.get(newFileName)).normalize().toAbsolutePath();

		// String newFileName = file.getOriginalFilename().split(".")
		if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
			response.setResponseCode("401");
			response.setResponseMessage("Invalid destination for image.");
		}
		try (InputStream inputStream = file.getInputStream()) {
			Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
			log.info("image saved on location {}", destinationFile);
			response.setResponseCode("200");
			response.setResponseMessage("Request Processed Successfully.");
			response.setResponseData(newFileName);
		} catch (Exception e) {
			response.setResponseCode("501");
			response.setResponseMessage("Exception Occurred.");
			response.setResponseData(e.getMessage());
		}
		return response;
	}

	private ResponseHandlingModel saveImageNameInRedis(String imageName, String userId, ResponseHandlingModel response) {
		try {
			String redisKey = "POS_USER_DETAILS_" + userId;
			Object redisData = redisClient.getValue(redisKey);
			UserDetailsModel userData = mapper.readValue(redisData.toString(), UserDetailsModel.class);
			userData.setUserImageName(imageName);
			redisClient.setValue(redisKey, new Gson().toJson(userData).toString());
			response.setResponseCode("200");
			response.setResponseMessage("Request Processed Successfully.");
			response.setResponseData(null);

		} catch (Exception e) {
			response.setResponseCode("501");
			response.setResponseMessage("Exception Occurred.");
			response.setResponseData(e.getMessage());
		}
		return response;

	}

	private ResponseHandlingModel getImageNameFromRedis(String userId, ResponseHandlingModel response) {
		try {
			String redisKey = "POS_USER_DETAILS_" + userId;
			Object redisData = redisClient.getValue(redisKey);
			UserDetailsModel userData = mapper.readValue(redisData.toString(), UserDetailsModel.class);
			String imageName = userData.getUserImageName();
			if (Objects.isNull(imageName)) {
				response.setResponseCode("401");
				response.setResponseMessage("Error finding image name for user.");
			} else {
				response.setResponseCode("200");
				response.setResponseMessage("Request Processed Successfully.");
				response.setResponseData(imageName);
			}

		} catch (Exception e) {
			response.setResponseCode("501");
			response.setResponseMessage("Exception Occurred.");
			response.setResponseData(e.getMessage());
		}
		return response;

	}

	private ResponseHandlingModel saveFileToLocation(ResponseHandlingModel responseHandle, MultipartFile file,
			String userId) {
		responseHandle = saveFile(file, userId);
		if (responseHandle.getResponseCode().equals("200")) {
			responseHandle = saveImageNameInRedis(responseHandle.getResponseData().toString(), userId, responseHandle);
		}
		return responseHandle;
	}

}
