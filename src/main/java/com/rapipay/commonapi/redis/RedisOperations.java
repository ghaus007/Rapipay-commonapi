package com.rapipay.commonapi.redis;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisOperations {
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	public Object getValue(String key) {
		return redisTemplate.opsForValue().get(key);
	}

	public void setValue(String key, String value) {
		redisTemplate.opsForValue().set(key, value);
	}
	
	public Object getAndsetValue(String key, String value) {
		redisTemplate.opsForValue().set(key, value);
		return redisTemplate.opsForValue().get(key);
		
	}
	

	public void setValueExp(final String key, final String value) {
		redisTemplate.opsForValue().set(key, value);
		redisTemplate.expire(key, 1200, TimeUnit.SECONDS);
	}

	public void setValueExpTime(final String key, final String value, final long time) {
		redisTemplate.opsForValue().set(key, value);
		redisTemplate.expire(key, time, TimeUnit.SECONDS);
	}

	public void delKey(final String key) {
		redisTemplate.delete(key);

	}
}
