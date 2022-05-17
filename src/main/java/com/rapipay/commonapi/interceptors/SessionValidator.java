package com.rapipay.commonapi.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.portal.common.models.ResponseHandlingModel;
import org.springframework.web.servlet.HandlerInterceptor;

import com.rapipay.commonapi.middleware.SessionHandling;

public class SessionValidator implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		SessionHandling sessionValidator = new SessionHandling();
		ResponseHandlingModel responseModel = new ResponseHandlingModel();
		sessionValidator.checkSession(null, responseModel);
		return true;

	}

}
