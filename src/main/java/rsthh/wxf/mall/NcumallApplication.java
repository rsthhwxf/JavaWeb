package rsthh.wxf.mall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("rsthh.wxf.mall.dao")
public class NcumallApplication {
    public static void main(String[] args) {
        SpringApplication.run(NcumallApplication.class, args);
    }
}
