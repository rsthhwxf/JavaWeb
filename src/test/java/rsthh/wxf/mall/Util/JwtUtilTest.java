package rsthh.wxf.mall.Util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import rsthh.wxf.mall.utils.JwtUtil;

import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class JwtUtilTest {
    @Test
    public void getTimeByToken(){
        JwtUtil.getTimeByToken(getToken());
    }

    @Test
    public String getToken(){
        return JwtUtil.getToken(1);
    }
}
