package com.liasplf.ncumall;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        NcumallApplication.class})
public class Test {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @org.junit.Test
    public void test(){
        redisTemplate.opsForValue().set("hello","redis");
    }
}
