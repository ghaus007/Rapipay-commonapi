package com.rapipay.commonapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.portal.common.utils.ResponseEncryption;

@SpringBootApplication
public class CommonapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommonapiApplication.class, args);
	}
	
	@Bean
    public ResponseEncryption enc() {
        return new ResponseEncryption();
    }

}
