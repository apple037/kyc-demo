package com.wanda.kyc.response;

import com.github.pagehelper.PageInfo;
import com.querydsl.core.QueryResults;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.Collections;
import java.util.List;


@Data
@AllArgsConstructor
@Builder
public class PageResult {

	private long total;
	private List<?> pageList;

	// @off

	public static PageResult empty() {
		return PageResult.builder()
				.total(0)
				.pageList(Collections.emptyList())
				.build();
	}

	public static PageResult transform(Page<?> jpaPageResult) {
		return PageResult.builder()
				.total(jpaPageResult.getTotalElements())
				.pageList(jpaPageResult.getContent())
				.build();
	}

	public static PageResult transform(QueryResults<?> queryDslPageResult) {
		return PageResult.builder()
				.total(queryDslPageResult.getTotal())
				.pageList(queryDslPageResult.getResults())
				.build();
	}

	public static PageResult transform(PageInfo<?> queryDslPageResult) {
		return PageResult.builder()
				.total(queryDslPageResult.getTotal())
				.pageList(queryDslPageResult.getList())
				.build();
	}

	// @on

}
