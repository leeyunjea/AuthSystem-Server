package com.auth.redisTest;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest{
 
    @SuppressWarnings("rawtypes")
	@Autowired
    private RedisTemplate redisTemplate;
 
    @SuppressWarnings("unchecked")
	@Test
    public void testDataHandling() throws Exception {
 
        String key = "key:springboot";
        redisTemplate.opsForValue().set(key, "Hello");
        String value = (String)redisTemplate.opsForValue().get(key);
        
        System.out.println("key = " + key + " value = " + value);
 
        Assert.assertEquals("Hello", value);
    }
}

