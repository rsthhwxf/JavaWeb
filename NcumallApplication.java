package com.liasplf.ncumall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.liasplf.ncumall.dao")
public class NcumallApplication {
    public static void main(String[] args) {
        SpringApplication.run(NcumallApplication.class, args);
    }
}
