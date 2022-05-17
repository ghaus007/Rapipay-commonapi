package com.rapipay.commonapi.interceptors;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.portal.common.models.ResponseHandlingModel;
import org.springframework.web.servlet.HandlerInterceptor;


public class RequestValidator implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String request1 = (String) request.getAttribute("request");
		if (Objects.nonNull(request.getAttribute("request"))) {

			return true;
		} else {
			ResponseHandlingModel responseModel = new ResponseHandlingModel();
			responseModel.setResponseCode("301");
			responseModel.setResponseMessage("Please provide valid request.");
//			response.setContentType("application/json");
//			response.getWriter().write(new Gson().toJson(responseModel));
//			response.setStatus(200);
			return true;
		}

	}
}
