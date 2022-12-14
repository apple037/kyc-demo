package com.wanda.kyc.utils.criteria;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;


public class MutiExpr implements Criterion {
	private Criterion[] criterion;
	private Operator operator;

	public MutiExpr(Criterion[] criterions, Operator operator) {
		this.criterion = criterions;
		this.operator = operator;
	}

	@Override
	public Predicate toPredicate(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		List<Predicate> predicates = new ArrayList<>();
		for (int i = 0; i < this.criterion.length; i++) {
			if (null != this.criterion[i]) {
				predicates.add(this.criterion[i].toPredicate(root, query, builder));
			}
		}
		switch (operator) {
			case OR:
				return builder.or(predicates.toArray(new Predicate[predicates.size()]));
			case AND:
				return builder.and(predicates.toArray(new Predicate[predicates.size()]));
			default:
				return null;
		}
	}
}
