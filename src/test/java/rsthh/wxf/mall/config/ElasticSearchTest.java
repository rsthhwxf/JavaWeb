package rsthh.wxf.mall.config;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import rsthh.wxf.mall.po.User;
import rsthh.wxf.mall.vo.Users;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ElasticSearchTest {


    @Autowired
    private RestHighLevelClient client;

    @Test
    public void contextLoads() {
        System.out.println(client);
    }


    @Test
    public void indexData() throws IOException {
//利用IndexRequest的source kv键值对
        IndexRequest indexRequest = new IndexRequest("users");
//数据id
        indexRequest.id("id");
//第一种
//indexRequest.source("userName","zhangsan","age",18,"gender","男");
//我们经常使用这个
        Users user = new Users();
        user.setAge(18);
        user.setGender("男");
        user.setName("张三");
        String jsonString = JSON.toJSONString(user);
        indexRequest.source(jsonString, XContentType.JSON);
//执行操作
        IndexResponse index = client.index(indexRequest,
               ElasticSearchConfig.COMMON_OPTIONS);
//提取有用的相应数据
        System.out.println(index);
    }
}
