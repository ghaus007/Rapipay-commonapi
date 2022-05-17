package com.rapipay.commonapi.interceptors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.portal.common.models.ResponseHandlingModel;
import org.springframework.web.servlet.HandlerInterceptor;
import com.google.gson.Gson;
import com.rapipay.commonapi.validations.HeadersValidation;

public class HeadersValidator implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		HeadersValidation headersValidator = new HeadersValidation();
		ResponseHandlingModel responseModel = headersValidator.validateHeaders(request);
		if (responseModel.getResponseCode().equals("200")) {
			return true;
		} else {
			response.setContentType("application/json");
			response.getWriter().write(new Gson().toJson(responseModel));
			response.setStatus(200);
			return false;
		}
		

	}

}
