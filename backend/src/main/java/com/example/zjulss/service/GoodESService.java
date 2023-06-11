package com.example.zjulss.service;

import com.example.zjulss.entity.GoodForSale;
import com.example.zjulss.repository.GoodForSaleRepository;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class GoodESService {
    @Autowired
    GoodForSaleRepository goodForSaleRepository;

    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    public void insertGoodForSale(GoodForSale goodForSale){
        goodForSaleRepository.save(goodForSale);
    }

    public void deleteGoodForSale(GoodForSale goodForSale){
        goodForSaleRepository.deleteById(goodForSale.getId());
    }
    public void deleteGoodForSaleById(Integer id){
        goodForSaleRepository.deleteById(id);
    }

    public void updateGoodForSale(GoodForSale goodForSale){
        goodForSaleRepository.save(goodForSale);
    }
    public List<GoodForSale> searchGoodForSale(String keyWord,int pageNum,boolean isSortByPrice,boolean isSortBySort,int sort){
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.multiMatchQuery(keyWord, "name"))
//                .withSort(SortBuilders.fieldSort("id").order(SortOrder.ASC))
                .withPageable(PageRequest.of(pageNum,  10))
                .withHighlightFields(
//                        new HighlightBuilder.Field("name").preTags("<em>").postTags("</em>")
                );
        if(isSortByPrice){
            nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("price").order(SortOrder.ASC));
        }
        if(isSortBySort) {
            nativeSearchQueryBuilder.withFilter(new RangeQueryBuilder("sort").from(sort).to(sort));
        }
        return searchHelper(nativeSearchQueryBuilder.build());
    }
    private List<GoodForSale> searchHelper(SearchQuery searchQuery){
        Page<GoodForSale> page = elasticsearchTemplate.queryForPage(searchQuery, GoodForSale.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> aClass, Pageable pageable) {
                SearchHits hits = response.getHits();
                if (hits.getTotalHits() <= 0) {
                    return null;
                }

                List<GoodForSale> list = new ArrayList<>();
                for (SearchHit hit : hits) {
                    GoodForSale good = new GoodForSale();

                    String id = hit.getSourceAsMap().get("id").toString();
                    good.setId(Integer.valueOf(id));
//                    String modify = hit.getSourceAsMap().get("modify").toString();
//                    good.setModify(LocalDateTime.from((new Date(Long.valueOf(modify))).toInstant()));
                    String name = hit.getSourceAsMap().get("name").toString();
                    good.setName(name);
                    String remark = hit.getSourceAsMap().get("remark").toString();
                    good.setRemark(remark);
                    String image = hit.getSourceAsMap().get("image").toString();
                    good.setImage(image);


                    String level = hit.getSourceAsMap().get("level").toString();
                    good.setLevel(Integer.valueOf(level));
                    String sort = hit.getSourceAsMap().get("sort").toString();
                    good.setSort(Integer.valueOf(sort));
                    String count = hit.getSourceAsMap().get("count").toString();
                    good.setCount(Integer.valueOf(count));
                    String display = hit.getSourceAsMap().get("display").toString();
                    good.setDisplay(Integer.valueOf(display));
                    String transaction = hit.getSourceAsMap().get("transaction").toString();
                    good.setTransaction(Integer.valueOf(transaction));
                    String sales = hit.getSourceAsMap().get("sales").toString();
                    good.setSales(Integer.valueOf(sales));
                    String uid = hit.getSourceAsMap().get("uid").toString();
                    good.setUid(Integer.valueOf(uid));

                    String price = hit.getSourceAsMap().get("price").toString();
                    good.setPrice(Double.valueOf(price));


                    // 处理高亮显示的结果
                    HighlightField nameField = hit.getHighlightFields().get("name");
                    if (nameField != null) {
                        good.setName(nameField.getFragments()[0].toString());
                    }

                    HighlightField remarkField = hit.getHighlightFields().get("remark");
                    if (remarkField != null) {
                        good.setRemark(remarkField.getFragments()[0].toString());
                    }

                    list.add(good);
                }

                return new AggregatedPageImpl(list, pageable,
                        hits.getTotalHits(), response.getAggregations(), response.getScrollId(), hits.getMaxScore());
            }
        });

        List<GoodForSale> list = new ArrayList<>();
        if(page != null) {
            for (GoodForSale good : page) {
                list.add(good);
            }
        }
        return list;
    }
}
