
package com.rapipay.commonapi.service.impl;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.nimbusds.jose.shaded.json.JSONObject;
import com.portal.common.constants.Constants;
import com.portal.common.models.RequestDto;
import com.portal.common.models.Response;
import com.portal.common.models.ResponseHandlingModel;
import com.portal.common.utils.ResponseEncryption;
import com.rapipay.commonapi.constants.HttpCall;
import com.rapipay.commonapi.dao.ManageUsersRepo;
import com.rapipay.commonapi.dao.MerchantProfileRepository;
import com.rapipay.commonapi.dao.ProfileRepository;
import com.rapipay.commonapi.dao.UsersListRepository;
import com.rapipay.commonapi.entity.UsersList;
import com.rapipay.commonapi.middleware.RequestHandling;
import com.rapipay.commonapi.middleware.SessionHandling;
import com.rapipay.commonapi.models.UserDetailsModel;
import com.rapipay.commonapi.redis.RedisOperations;
import com.rapipay.commonapi.repository.ManageUsersRepository;
import com.rapipay.commonapi.repository.TerminalDetailsRepository;
import com.rapipay.commonapi.repository.TerminalMasterRepository;
import com.rapipay.commonapi.repository.TerminalOnboardingForSwitchRepository;
import com.rapipay.commonapi.requestdto.CreateSubUserDto;
import com.rapipay.commonapi.requestdto.UpdateProfileDetailsDto;
import com.rapipay.commonapi.requestdto.UpdateUserDataDto;
import com.rapipay.commonapi.responsedto.KycEncodingDto;
import com.rapipay.commonapi.responsedto.UsersInformationDto;
import com.rapipay.commonapi.service.UserDetails;
import com.rapipay.commonapi.utils.RestCall;
import com.rapipay.commonapi.utils.StatusCode;
import com.rapipay.commonapi.utils.Utility;

@Service
public class UserDetailsImpl implements UserDetails {

	@Autowired
	SessionHandling sessionHandling;

	@Autowired
	ModelMapper mapper;

	@Autowired
	RequestHandling requestHandling;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	ProfileRepository profileDao;

	@Autowired
	TerminalDetailsRepository terminalDetailsRepository;

	@Autowired
	MerchantProfileRepository merchantProfileDao;

	@Autowired
	ResponseEncryption encResponse;

	@Autowired
	RestCall apiCall;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	ManageUsersRepository manageUsersRepository;

	@Autowired
	ManageUsersRepo manageUsersDao;

	@Autowired
	RedisOperations redisClient;

	@Autowired
	TerminalOnboardingForSwitchRepository terminalOnboardingForSwitchRepository;

	@Autowired
	UsersListRepository usersListRepository;
	@Autowired
	TerminalMasterRepository terminalMasterRepository;
	
	@Autowired
	Utility utility;

	@Override
	public Response updateProfileDetails(RequestDto request, HttpHeaders headers) {
		ResponseHandlingModel responseModel = new ResponseHandlingModel();
		Response response = new Response();
		try {
			if (!sessionHandling.checkSession(headers, responseModel).getResponseCode().equals("200")) {
				return mapper.map(responseModel, Response.class);
			}
			responseModel = requestHandling.handleRequest(request, responseModel);

			UpdateProfileDetailsDto updateProfileDetailsDto = objectMapper
					.readValue(responseModel.getResponseData().toString(), UpdateProfileDetailsDto.class);
			/*
			 * int count =
			 * merchantProfileRepository.updateMerchantInformation(updateProfileDetailsDto.
			 * getEmail(), updateProfileDetailsDto.getAddressLine(),
			 * updateProfileDetailsDto.getMerchantMobile());
			 */
			int count = 1;
			if (count == 1) {
				response.setResponseCode(StatusCode.SUCCESS_200.getCode());
				response.setResponseMessage(StatusCode.SUCCESS_200.getMessage());

			} else {
				response.setResponseCode(StatusCode.ERROR_401.getCode());
				response.setResponseMessage(StatusCode.ERROR_401.getMessage());
			}

		} catch (Exception e) {
			response.setResponseCode(StatusCode.EXCEPTION_501.getCode());
			response.setResponseMessage(StatusCode.EXCEPTION_501.getMessage());
			response.setResponseData(e.getMessage());

		}
		return response;

	}

	@Override
	public Response getProfileDetails(RequestDto request, HttpHeaders headers) {
		ResponseHandlingModel responseModel = new ResponseHandlingModel();
		Response response = new Response();
		try {
			if (!sessionHandling.checkSession(headers, responseModel).getResponseCode().equals("200")) {
				return mapper.map(responseModel, Response.class);
			}
			responseModel = requestHandling.handleRequest(request, responseModel);

			JSONObject midDetails = objectMapper.readValue(responseModel.getResponseData().toString(),
					JSONObject.class);

			JSONObject jsonObject = merchantProfileDao.getMerchantProfileDetails((String) midDetails.get("mid"),
					response);
			if (!jsonObject.isEmpty()) {
				String encryptedPan = encResponse.encrypt((String) jsonObject.get("PAN_NO"));
				String encryptedAadhar = encResponse.encrypt((String) jsonObject.get("DOC_NO"));
				jsonObject.put("DOC_NO", encryptedAadhar);
				jsonObject.put("PAN_NO", encryptedPan);

				getKyc(jsonObject);
				response.setResponseData(jsonObject);

			}

		} catch (Exception e) {
			response.setResponseCode(StatusCode.EXCEPTION_501.getCode());
			response.setResponseMessage(StatusCode.EXCEPTION_501.getMessage());
			response.setResponseData(e.getMessage());

		}
		return response;

	}

	private void getKyc(JSONObject jsonObject) throws JsonProcessingException {
		MultiValueMap<String, UpdateProfileDetailsDto.GetKycImageDto> body = new LinkedMultiValueMap<String, UpdateProfileDetailsDto.GetKycImageDto>();
		List<String> documentNames = Arrays.asList("ADD_BACK", "PAN_Image", "ADD_FRONT");
		for (String i : documentNames) {
			UpdateProfileDetailsDto.GetKycImageDto getKyc = new UpdateProfileDetailsDto.GetKycImageDto();
			getKyc.setDocumentPath((String) jsonObject.get(i));
			getKyc.setDocumentId(i);
			body.add("kycDocEncodingDto", getKyc);
			jsonObject.put(i, "");
		}

		ResponseEntity responseEntity = apiCall.postCall(HttpCall.GET_KYC, body, HttpMethod.POST);
		if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
			if (responseEntity.hasBody()) {
				Response kycResponse = objectMapper
						.readValue(Objects.requireNonNull(responseEntity.getBody()).toString(), Response.class);
				if (kycResponse.getResponseCode().equals(StatusCode.SUCCESS_200.getCode())) {
					KycEncodingDto kycEncodingData = objectMapper
							.readValue(new Gson().toJson(kycResponse.getResponseData()), KycEncodingDto.class);
					for (UpdateProfileDetailsDto.GetKycImageDto kycData : kycEncodingData.getKycDocEncodingDto()) {

						jsonObject.put(kycData.getDocumentId(), kycData.getEncodedDocumentContent());
					}

				}
			}
		}

	}

	@Override
	public Response getUserList(String mid, String tid, String mobileNo, HttpHeaders headers) {
		Response response = new Response();
		ResponseHandlingModel responseHandle = new ResponseHandlingModel();
		/*
		 * if (!sessionHandling.checkSession(headers,
		 * responseHandle).getResponseCode().equals("200")) { return
		 * modelMapper.map(responseHandle, Response.class);
		 *
		 * }
		 */
		List<UsersList> usersList = null;
		response.setResponseCode(StatusCode.SUCCESS_200.getCode());
		response.setResponseMessage(StatusCode.SUCCESS_200.getMessage());
		if (Objects.nonNull(tid) && (!tid.isBlank()) && Objects.nonNull(mid)) {
			usersList = manageUsersRepository.findByMidAndTids(mid, tid);

		} else if (Objects.nonNull(mid)) {
			usersList = manageUsersRepository.findByMids(mid);

		} else if (Objects.nonNull(mobileNo)) {
			usersListRepository.getUsersByMobileNo(mobileNo, response);
		} else {
			response.setResponseCode(StatusCode.ERROR_401.getCode());
			response.setResponseMessage(StatusCode.ERROR_401.getMessage());
		}

		response.setResponseData(usersList);

		return response;
	}

	@Override
	public Response getUserInformation(String userId, HttpHeaders headers) throws JsonProcessingException {
		Response response = new Response();
		ResponseHandlingModel responseHandle = new ResponseHandlingModel();

		UsersInformationDto usersInformation = objectMapper
				.readValue(redisClient.getValue(Constants.userInfoKey + userId).toString(), UsersInformationDto.class); // UsersInformationDto
		// usersInformation = manageUsersRepository.getUsersInformation(userId);
		response.setResponseCode(StatusCode.SUCCESS_200.getCode());
		response.setResponseMessage(StatusCode.SUCCESS_200.getMessage());
		response.setResponseData(usersInformation);

		return response;
	}

	@Override
	public Response updateUserInformation(UpdateUserDataDto updateUserDataDto, HttpHeaders headers)
			throws JsonProcessingException {
		Response response = new Response();
		ResponseHandlingModel responseHandle = new ResponseHandlingModel();

		try {

			UserDetailsModel userData = objectMapper.readValue(
					redisClient.getValue(Constants.userInfoKey + updateUserDataDto.getUserId()).toString(),
					UserDetailsModel.class);

			if (updateUserDataDto.getServiceType().equals("UPDATE_STATUS")) {

				if (!updateUserDataDto.getUserStatus().equals("1")
						&& !Objects.equals(updateUserDataDto.getUserStatus(), "0")) {
					response.setResponseCode(StatusCode.ERROR_301_VALIDATION.getCode());
					response.setResponseMessage(StatusCode.ERROR_301_VALIDATION.getMessage());
					return response;
				}
				int recordCount = manageUsersRepository.updateUserData(updateUserDataDto.getUserStatus(),
						updateUserDataDto.getUserId());
				if (recordCount == 1) {
					userData.setUserStatus(updateUserDataDto.getUserStatus());

					// response.setResponseData(usersInformation); } else {

				}
			} else {
				if (Objects.nonNull(updateUserDataDto.getAuthPin())) {
					userData.setAuthPin(updateUserDataDto.getAuthPin());
				} else {
					updateUserDataDto.setAuthPin(userData.getAuthPin());
				}

				userData.setUserAccess(updateUserDataDto.getUserAccess());
				userData.setFullName(updateUserDataDto.getUserName());
				userData.setMobileNo(updateUserDataDto.getMobileNo());
				manageUsersDao.updateUserInformation(updateUserDataDto, response);

				redisClient.setValue(Constants.userInfoKey + updateUserDataDto.getUserId(),
						new Gson().toJson(userData));
			}

			response.setResponseCode(StatusCode.SUCCESS_200.getCode());

			response.setResponseMessage(StatusCode.SUCCESS_200.getMessage());
			return response;

		} catch (Exception e) { // TODO Auto-generated catch block
			response.setResponseCode(StatusCode.ERROR_501.getCode());

			response.setResponseMessage(StatusCode.ERROR_501.getMessage());
			response.setResponseData(e.getMessage());

			return response;

		}
	}

	@SuppressWarnings("unused")
	@Override
	public Response createSubUserViaProc(CreateSubUserDto createSubUserDto, HttpHeaders headers)
			throws JsonProcessingException {
		UserDetailsModel userDetailsModel = new UserDetailsModel();

		Response response = new Response();
		ResponseHandlingModel responseHandle = new ResponseHandlingModel();

		
		/*
		 * if (!sessionHandling.checkSession(headers,
		 * responseHandle).getResponseCode().equals("200")) { return
		 * modelMapper.map(responseHandle, Response.class);
		 * 
		 * }
		 */
		try {
			Object o = redisClient.getValue(Constants.userInfoKey + createSubUserDto.getUserId());
			if (!(o == null)) {
				// TODO Auto-generated catch block
				response.setResponseCode(StatusCode.ERROR_401_AUTHENTICATION_FAILURE.getCode());
				response.setResponseMessage("User already exists");
				return response;
			}

			JSONObject responseData = new JSONObject();
			manageUsersDao.getRevRkiStatus(createSubUserDto, response, responseData);
			if (responseData == null) {
				throw new SQLException("Invalid Rki/REV Status.Please contact support");
			}
			
			if (responseData.get("isRev").equals("1")) {
				
				String revStatus[]= {"111111"};
				utility.prepareRevRkiData(responseData, userDetailsModel, revStatus);
				
			}
			
			

			else {
				manageUsersDao.getRevStatusFlag(createSubUserDto, response, responseData);
				if (responseData == null) {
					throw new SQLException("Invalid Rki/REV Status.Please contact support");
				}
				
				String revStatus[]= {String.valueOf(responseData.get("revStatus"))};

				

			}
			
			utility.prepareDataToCreateTerminalSubUSer(createSubUserDto, userDetailsModel);
			manageUsersDao.createSubUserviaProc(createSubUserDto, response);
			if (response.getResponseCode().toString().equals("200")) {
				redisClient.setValue(Constants.userInfoKey + createSubUserDto.getUserId(),
						new Gson().toJson(userDetailsModel));
				// call Insert_NEW_TERMINAL_User_Details proc here
				response.setResponseCode(StatusCode.SUCCESS_200.getCode());
				response.setResponseMessage(StatusCode.SUCCESS_200.getMessage());
			}

			return response;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			response.setResponseCode(StatusCode.EXCEPTION_501.getCode());
			response.setResponseMessage(StatusCode.EXCEPTION_501.getMessage());
			response.setResponseData(e.getMessage());
			return response;
		}
		
	}

	/*
	 * @SuppressWarnings("unused")
	 * 
	 * @Override public Response createSubUserViaProc(CreateSubUserDto
	 * createSubUserDto, HttpHeaders headers) throws JsonProcessingException {
	 * UserDetailsModel userDetailsModel = new UserDetailsModel();
	 * 
	 * Response response = new Response(); ResponseHandlingModel responseHandle =
	 * new ResponseHandlingModel();
	 * 
	 * 
	 * if (!sessionHandling.checkSession(headers,
	 * responseHandle).getResponseCode().equals("200")) { return
	 * modelMapper.map(responseHandle, Response.class);
	 *
	 * }
	 * 
	 * 
	 * try { Object userInfoRedis = redisClient.getValue(Constants.userInfoKey +
	 * createSubUserDto.getUserId()); if (!(userInfoRedis == null)) { // TODO
	 * Auto-generated catch block
	 * response.setResponseCode(StatusCode.ERROR_401_AUTHENTICATION_FAILURE.getCode(
	 * )); response.setResponseMessage("User already exists"); return response; }
	 * 
	 * 
	 * TerminalOnBoardingForSwitch tofs =
	 * terminalOnboardingForSwitchRepository.getRkiRevStatus(createSubUserDto.getMid
	 * (), createSubUserDto.getTid()); if (tofs == null) { throw new
	 * SQLException("Invalid Rki/REV Status.Please contact support"); } else {
	 * 
	 * userDetailsModel.setIsRev(tofs.isRev() == true ? "Y" : "N");
	 * userDetailsModel.setIsRki(tofs.isRev() == true ? "Y" : "N");
	 * userDetailsModel.setRevStatus(tofs.getRevStatus()); }
	 * 
	 * if (manageUsersRepository.findByUserId(createSubUserDto.getUserId()) == null)
	 * {
	 * 
	 * if (manageUsersRepository.findByTid(createSubUserDto.getTid()).size() < 4) {
	 * 
	 * if (!(terminalMasterRepository.findByTerminalSerialNo(createSubUserDto.
	 * getDeviceSerialNo()) == null)) {
	 * 
	 * 
	 * }
	 * 
	 * 
	 * userDetailsModel.setUserStatus("1");
	 * userDetailsModel.setUserId(createSubUserDto.getUserId());
	 * userDetailsModel.setUserAccess(createSubUserDto.getUserAccess());
	 * userDetailsModel.setMobileNo(createSubUserDto.getMobileNo());
	 * userDetailsModel.setAuthPin(createSubUserDto.getPassword());
	 * userDetailsModel.setMid(createSubUserDto.getMid());
	 * userDetailsModel.setTid(createSubUserDto.getTid());
	 * userDetailsModel.setDeviceSerialNo(createSubUserDto.getDeviceSerialNo());
	 * userDetailsModel.setFullName(createSubUserDto.getUserName()); //
	 * userAccess//inital//orTime//isRev//isRki//revStatus//deviceName//deviceType//
	 * deviceType//appVersion//lat//lon//clientIP//userImageName
	 * userDetailsModel.setInitial(String.valueOf(createSubUserDto.getUserName().
	 * charAt(0))); userDetailsModel.setOneTime("true");
	 * userDetailsModel.setDeviceName(""); userDetailsModel.setDeviceType("");
	 * userDetailsModel.setAppVersion(""); userDetailsModel.setLat("");
	 * userDetailsModel.setLon(""); userDetailsModel.setClientIP("");
	 * userDetailsModel.setUserImageName("");
	 * 
	 * manageUsersDao.createSubUser(createSubUserDto, response); if
	 * (response.getResponseCode().toString().equals("200")) {
	 * redisClient.setValue(Constants.userInfoKey + createSubUserDto.getUserId(),
	 * new Gson().toJson(userDetailsModel)); // call
	 * Insert_NEW_TERMINAL_User_Details proc here
	 * response.setResponseCode(StatusCode.SUCCESS_200.getCode());
	 * response.setResponseMessage(StatusCode.SUCCESS_200.getMessage()); } } else {
	 * response.setResponseCode(StatusCode.LIMIT_CONSUMED.getCode());
	 * response.setResponseMessage(StatusCode.LIMIT_CONSUMED.getMessage()); }
	 * 
	 * 
	 * } else { response.setResponseCode(StatusCode.RECORD_NOT_INSERTED.getCode());
	 * response.setResponseMessage(StatusCode.RECORD_NOT_INSERTED.getMessage()); }
	 * 
	 * return response; } catch (Exception e) { // TODO Auto-generated catch block
	 * response.setResponseCode(StatusCode.EXCEPTION_501.getCode());
	 * response.setResponseMessage(StatusCode.EXCEPTION_501.getMessage());
	 * response.setResponseData(e.getMessage()); return response; } }
	 */
}
