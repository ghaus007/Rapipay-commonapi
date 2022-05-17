package com.rapipay.commonapi.service;

import org.springframework.http.HttpHeaders;
import org.springframework.web.multipart.MultipartFile;

import com.portal.common.models.Response;

public interface StorageService {

	public Response uploadFile(MultipartFile file, HttpHeaders headers);

	public Response getFile(HttpHeaders headers);

}
