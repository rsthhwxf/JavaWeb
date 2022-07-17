package rsthh.wxf.mall.service.impl;

import com.alibaba.fastjson.JSON;
import oracle.jrockit.jfr.RecordingOptions;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rsthh.wxf.mall.config.ElasticSearchConfig;
import rsthh.wxf.mall.po.ES;
import rsthh.wxf.mall.po.Item;
import rsthh.wxf.mall.service.EsService;
import rsthh.wxf.mall.utils.EsConstant;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EsServiceImpl implements EsService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;



    public void agreement(Item item) throws IOException {
        BulkRequest bulkRequest = new BulkRequest();

        IndexRequest indexRequest = new
                IndexRequest(EsConstant.PRODUCT_INDEX);
        indexRequest.id(item.getId().toString());
        String jsonString = JSON.toJSONString(item);
        indexRequest.source(jsonString, XContentType.JSON);
        bulkRequest.add(indexRequest);
        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, ElasticSearchConfig.COMMON_OPTIONS);
        boolean b = bulk.hasFailures();
    }

    public void agreement2(String itemID) throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest(EsConstant.PRODUCT_INDEX,itemID);
        restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
    }
}
