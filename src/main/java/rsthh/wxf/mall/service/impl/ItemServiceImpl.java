package rsthh.wxf.mall.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.NestedQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.nested.NestedAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.nested.ParsedNested;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedLongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import rsthh.wxf.mall.config.ElasticSearchConfig;
import rsthh.wxf.mall.dao.ItemDao;
import rsthh.wxf.mall.po.Item;
import rsthh.wxf.mall.po.Orders;
import rsthh.wxf.mall.service.ItemService;
import org.springframework.stereotype.Service;
import rsthh.wxf.mall.utils.EsConstant;
import rsthh.wxf.mall.vo.SearchParam;
import rsthh.wxf.mall.vo.SearchResult;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl extends ServiceImpl<ItemDao, Item> implements ItemService {

    @Resource
    private RestHighLevelClient esRestClient;

    @Override
    public List<Item> getItemList(int pageNum, int pageSize){
        PageHelper.startPage(pageNum,pageSize);

        List<Item> list =  this.baseMapper.selectList(new QueryWrapper<Item>());

        PageInfo<Item> pageInfo = new PageInfo<>(list);
        return pageInfo.getList();

    }

    @Override
    public SearchResult searchItem(SearchParam searchParam){


        //1、动态构建出查询需要的DSL语句
        SearchResult result = null;

        //1、准备检索请求
        SearchRequest searchRequest = buildSearchRequest(searchParam);

        try {
            //2、执行检索请求
            SearchResponse response = esRestClient.search(searchRequest, ElasticSearchConfig.COMMON_OPTIONS);

            //3、分析响应数据，封装成我们需要的格式
            result = buildSearchResult(response,searchParam);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    private SearchResult buildSearchResult(SearchResponse response,SearchParam param) {

        SearchResult result = new SearchResult();

        //1、返回的所有查询到的商品
        SearchHits hits = response.getHits();

        List<Item> esModels = new ArrayList<>();
        //遍历所有商品信息
        if (hits.getHits() != null && hits.getHits().length > 0) {
            for (SearchHit hit : hits.getHits()) {
                String sourceAsString = hit.getSourceAsString();
                Item esModel = JSON.parseObject(sourceAsString, Item.class);
                esModels.add(esModel);
            }
        }
        result.setProducts(esModels);
        //5、分页信息-页码
        result.setPageNum(param.getPageNum());
        //5、1分页信息、总记录数
        long total = hits.getTotalHits().value;
        result.setTotal(total);

        //5、2分页信息-总页码-计算
        int totalPages = (int)total % EsConstant.PRODUCT_PAGESIZE == 0 ?
                (int)total / EsConstant.PRODUCT_PAGESIZE : ((int)total / EsConstant.PRODUCT_PAGESIZE + 1);
        result.setTotalPages(totalPages);

        List<Integer> pageNavs = new ArrayList<>();
        for (int i = 1; i <= totalPages; i++) {
            pageNavs.add(i);
        }
        result.setPageNavs(pageNavs);

        return result;
    }


    /**
     * 准备检索请求
     * 模糊匹配，过滤（按照属性，分类，品牌，价格区间，库存），排序，分页，高亮，聚合分析
     * @return
     */
    private SearchRequest buildSearchRequest(SearchParam param) {

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        /**
         * 模糊匹配，过滤（按照属性，分类，品牌，价格区间，库存）
         */
        //1. 构建bool-query
        BoolQueryBuilder boolQueryBuilder=new BoolQueryBuilder();

        //1.1 bool-must
        if(!StringUtils.isEmpty(param.getKeyword())){
            boolQueryBuilder.must(QueryBuilders.matchQuery("itemName",param.getKeyword()));
        }

        //1.2 bool-fiter
        //skuPrice
        if(!StringUtils.isEmpty(param.getSkuPrice())){
            //skuPrice形式为：1_500或_500或500_
            RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("skuPrice");
            String[] price = param.getSkuPrice().split("_");
            if(price.length==2){
                rangeQueryBuilder.gte(price[0]).lte(price[1]);
            }else if(price.length == 1){
                if(param.getSkuPrice().startsWith("_")){
                    rangeQueryBuilder.lte(price[1]);
                }
                if(param.getSkuPrice().endsWith("_")){
                    rangeQueryBuilder.gte(price[0]);
                }
            }
            boolQueryBuilder.filter(rangeQueryBuilder);
        }

        //封装所有的查询条件
        searchSourceBuilder.query(boolQueryBuilder);


        /**
         * 排序，分页，高亮
         */

        //排序
        //形式为sort=asc/desc
        if(!StringUtils.isEmpty(param.getSort())){
            String sort = param.getSort();
            SortOrder sortOrder="asc".equalsIgnoreCase(sort)?SortOrder.ASC:SortOrder.DESC;
            searchSourceBuilder.sort("price",sortOrder);
        }

        //分页
        searchSourceBuilder.from((param.getPageNum()-1)* EsConstant.PRODUCT_PAGESIZE);
        searchSourceBuilder.size(EsConstant.PRODUCT_PAGESIZE);

        SearchRequest searchRequest = new SearchRequest(new String[]{EsConstant.PRODUCT_INDEX},searchSourceBuilder);

        return searchRequest;
    }
}

