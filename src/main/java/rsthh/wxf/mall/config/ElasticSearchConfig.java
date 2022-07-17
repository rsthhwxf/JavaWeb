package rsthh.wxf.mall.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 1.导入依赖
 * 2.编写配置类 给容器注入一个RestHighLevelClient
 * 3.根据api进行操作es
 */
@Configuration
public class ElasticSearchConfig {

    public static final RequestOptions COMMON_OPTIONS;

    static {
        RequestOptions.Builder builder = RequestOptions.DEFAULT.toBuilder();

        COMMON_OPTIONS = builder.build();
    }

    @Bean
    public RestHighLevelClient client() {
        RestClientBuilder builder = RestClient.builder(new
                HttpHost("120.76.205.95", 9200, "http"));
        return new RestHighLevelClient(builder);
    }
}
