package com.qinzhi.jpa;

import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.Collection;
import java.util.List;

/**
 * 动态条件
 */
public class DynamicSpecifications {

    public static <T> Specification<T> bySearchFilter(final Collection<SearchFilter> filters, final Class<T> clazz)
    {
        return new Specification<T>()
        {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder)
            {
                if (CollectionUtils.isNotEmpty(filters))
                {
                    // 原始条件
                    List<Predicate> andPredicates = Lists.newArrayList();
                    // 数据权限条件
                    List<Predicate> orPredicates = Lists.newArrayList();
                    for (SearchFilter filter : filters)
                    {
                        if (filter.conjunctionType.equals(SearchFilter.ConjunctionType.OR))
                        {
                            build(orPredicates, root, builder, filter);
                        }
                        else
                        {
                            build(andPredicates, root, builder, filter);
                        }
                    }
                    Predicate addPredicate = null;
                    Predicate orPredicate = null;
                    // 将所有条件用 and 联合起来
                    if (!andPredicates.isEmpty())
                    {
                        addPredicate = builder.and(andPredicates.toArray(new Predicate[andPredicates.size()]));
                    }
                    if (!orPredicates.isEmpty())
                    {
                        orPredicate = builder.or(orPredicates.toArray(new Predicate[orPredicates.size()]));
                    }
                    if (null != addPredicate && null != orPredicate)
                    {
                        return builder.and(addPredicate, orPredicate);
                    }
                    else if (null != addPredicate)
                    {
                        return addPredicate;
                    }
                    else if (null != orPredicate)
                    {
                        return orPredicate;
                    }

                }
                return builder.conjunction();
            }
        };
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static void build(List<Predicate> predicates, Root root, CriteriaBuilder builder, SearchFilter filter)
    {

        // nested path translate, 如Task的名为"user.name"的filedName,
        // 转换为Task.user.name属性
        String[] names = StringUtils.split(filter.fieldName, ".");
        Path expression = root.get(names[0]);
        for (int i = 1; i < names.length; i++)
        {
            expression = expression.get(names[i]);
        }
        // logic operator
        switch (filter.operator)
        {
            case EQ:
                predicates.add(builder.equal(expression, filter.value));
                break;
            case NE:
                predicates.add(builder.notEqual(expression, filter.value));
                break;
            case LIKE:
                predicates.add(builder.like(expression, "%" + filter.value + "%"));
                break;
            case NOTLIKE:
                predicates.add(builder.notLike(expression, "%" + filter.value + "%"));
                break;
            case GT:
                predicates.add(builder.greaterThan(expression, (Comparable)filter.value));
                break;
            case LT:
                predicates.add(builder.lessThan(expression, (Comparable)filter.value));
                break;
            case GTE:
                predicates.add(builder.greaterThanOrEqualTo(expression, (Comparable)filter.value));
                break;
            case LTE:
                predicates.add(builder.lessThanOrEqualTo(expression, (Comparable)filter.value));
                break;
            case IN:
                String[] values = filter.value.toString().split(",");
                predicates.add(expression.in(values));
                break;
            case NOTIN:
                String[] values1 = filter.value.toString().split(",");
                predicates.add(builder.not(expression.in(values1)));
                break;
            case ISNULL:
                predicates.add(builder.isNull(expression));
                break;
            case ISNOTNULL:
                predicates.add(builder.isNotNull(expression));
                break;
        }
    }
}
