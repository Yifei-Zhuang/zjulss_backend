package com.example.zjulss.service.search;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.query.StringQuery;
import org.springframework.stereotype.Service;
import com.example.zjulss.entity.GoodForSale;
import java.net.InetAddress;
import java.net.UnknownHostException;

import lombok.extern.slf4j.Slf4j;
@Service
@Slf4j
public class GoodSearchService {
    private static final String GOOD_INDEX = "goodindex";

	private ElasticsearchOperations elasticsearchOperations;

    @Autowired
	public GoodSearchService(final ElasticsearchOperations elasticsearchOperations) {
		super();
		this.elasticsearchOperations = elasticsearchOperations;
	}

	public List<String> createProductIndexBulk(final List<GoodForSale> goods) {

		List<IndexQuery> queries = goods.stream()
				.map(good -> new IndexQueryBuilder().withId(good.getId().toString()).withObject(good).build())
				.collect(Collectors.toList());
		;

		return elasticsearchOperations.bulkIndex(queries, IndexCoordinates.of(GOOD_INDEX));

	}

	public String createProductIndex(GoodForSale good) {

		IndexQuery indexQuery = new IndexQueryBuilder().withId(good.getId().toString()).withObject(good).build();
		String documentId = elasticsearchOperations.index(indexQuery, IndexCoordinates.of(GOOD_INDEX));

		return documentId;
	}
    public void findByProductName(final String productName) {
		Query searchQuery = new StringQuery(
				"{\"match\":{\"name\":{\"query\":\""+ productName + "\"}}}\"");

		SearchHits<GoodForSale> products = elasticsearchOperations.search(searchQuery, GoodForSale.class,
				IndexCoordinates.of(GOOD_INDEX));
	}

       
    }
