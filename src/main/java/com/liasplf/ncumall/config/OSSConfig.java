package com.liasplf.ncumall.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OSSConfig {

    @Bean
    public OSS ossClient(){
        String endpoint = "oss-cn-shenzhen.aliyuncs.com";
        String accessKeyId = "LTAI5tH3tk7MZ2gmejiodLfu";
        String accessKeySecret = "iwkT5uGd4R8xodwulxO5keDdDOIwHt";

        OSS ossClient = new OSSClientBuilder().build(endpoint,accessKeyId,accessKeySecret);
        return ossClient;
    }
}
