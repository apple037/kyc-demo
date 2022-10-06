package com.wanda.kyc.utils.criteria;

import java.util.Collection;


public class Restrict {

	private Restrict() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * 等于
	 */
	public static SingleExpr eq(String fieldName, Object value) {
		if (null == value) {
			return null;
		}
		return new SingleExpr(fieldName, value, Criterion.Operator.EQ);
	}

	/**
	 * 不等于
	 */
	public static SingleExpr ne(String fieldName, Object value) {
		if (null == value) {
			return null;
		}
		return new SingleExpr(fieldName, value, Criterion.Operator.NE);
	}

	/**
	 * 模糊匹配
	 */
	public static SingleExpr like(String fieldName, String value) {
		if (null == value) {
			return null;
		}
		return new SingleExpr(fieldName, value, Criterion.Operator.LIKE);
	}

	/**
	 * 模糊匹配 %xxx
	 */
	public static SingleExpr endWith(String fieldName, String value) {
		if (null == value) {
			return null;
		}
		return new SingleExpr(fieldName, value, Criterion.Operator.ENDWITH);
	}

	/**
	 * 大于
	 */
	public static SingleExpr gt(String fieldName, Object value) {
		if (null == value) {
			return null;
		}
		return new SingleExpr(fieldName, value, Criterion.Operator.GT);
	}

	/**
	 * 小于
	 */
	public static SingleExpr lt(String fieldName, Object value) {
		if (null == value) {
			return null;
		}
		return new SingleExpr(fieldName, value, Criterion.Operator.LT);
	}

	/**
	 * 小于等于
	 */
	public static SingleExpr lte(String fieldName, Object value) {
		if (null == value) {
			return null;
		}
		return new SingleExpr(fieldName, value, Criterion.Operator.LTE);
	}

	/**
	 * 大于等于
	 */
	public static SingleExpr gte(String fieldName, Object value) {
		if (null == value) {
			return null;
		}
		return new SingleExpr(fieldName, value, Criterion.Operator.GTE);
	}

	/**
	 * 并且
	 */
	public static MutiExpr and(Criterion... criterions) {
		return new MutiExpr(criterions, Criterion.Operator.AND);
	}

	/**
	 * 或者
	 */
	public static MutiExpr or(Criterion... criterions) {
		return new MutiExpr(criterions, Criterion.Operator.OR);
	}

	/**
	 * 在集合裡
	 */
	@SuppressWarnings({ "rawtypes" })
	public static MutiExpr in(String fieldName, Collection value) {
		if (value == null || value.isEmpty()) {
			return null;
		}
		SingleExpr[] s = new SingleExpr[value.size()];
		int i = 0;
		for (Object obj : value) {
			s[i] = new SingleExpr(fieldName, obj, Criterion.Operator.EQ);
			i++;
		}
		return new MutiExpr(s, Criterion.Operator.OR);
	}

}
