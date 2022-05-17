package com.rapipay.commonapi.dao;

import javax.persistence.EntityManager;

import com.portal.common.models.Response;
import com.rapipay.commonapi.requestdto.UpdateProfileDetailsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;


@Repository
@Transactional
public class ProfileRepository {
	
	@Autowired
	EntityManager entityManager;
	@Autowired
	ObjectMapper objectMapper;
	
	
	public Response updateProfileDetails(UpdateProfileDetailsDto updateProfileDetailsDto)
	{

		return null;
		
	}

}
