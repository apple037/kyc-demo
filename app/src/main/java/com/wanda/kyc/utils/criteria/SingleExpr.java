package com.wanda.kyc.utils.criteria;

import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class SingleExpr implements Criterion {
	private String fieldName;
	private Object value;
	private Operator operator;

	protected SingleExpr(String fieldName, Object value, Operator operator) {
		this.fieldName = fieldName;
		this.value = value;
		this.operator = operator;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Predicate toPredicate(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		Path expression;
		if (fieldName.contains(".")) {
			String[] names = StringUtils.split(fieldName, ".");
			expression = root.get(names[0]);
			Class clazz = expression.getJavaType();
			if (clazz.equals(Set.class)) {
				SetJoin setJoin = root.joinSet(names[0]);
				expression = setJoin.get(names[1]);
			} else if (clazz.equals(List.class)) {
				ListJoin listJoin = root.joinList(names[0]);
				expression = listJoin.get(names[1]);
			} else if (clazz.equals(Map.class)) {
				MapJoin mapJoin = root.joinMap(names[0]);
				expression = mapJoin.get(names[1]);
			} else {
				expression = expression.get(names[1]);
			}
		} else {
			expression = root.get(fieldName);
		}
		switch (operator) {
			case EQ:
				return builder.equal(expression, value);
			case NE:
				return builder.notEqual(expression, value);
			case LIKE:
				return builder.like((Expression<String>) expression, "%" + value + "%");
			case LT:
				return builder.lessThan(expression, (Comparable) value);
			case GT:
				return builder.greaterThan(expression, (Comparable) value);
			case LTE:
				return builder.lessThanOrEqualTo(expression, (Comparable) value);
			case GTE:
				return builder.greaterThanOrEqualTo(expression, (Comparable) value);
			case ENDWITH:
				return builder.like((Expression<String>) expression, "%" + value);
			default:
				return null;
		}
	}

}
