package com.example.devicesservice.factories;

import com.example.devicesservice.dtos.GetListRequest;
import com.example.devicesservice.exceptions.ValidationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Map;

public class QueryFactory {

    public static Query createQuery(GetListRequest request) {
        Criteria criteria = new Criteria();

        if(request.getQueries() != null) {
            request.getQueries().forEach(query -> {
                switch (query.getOperator()) {
                    case EQ:
                        criteria.and(query.getField()).is(query.getValue());
                        break;
                    case NE:
                        criteria.and(query.getField()).ne(query.getValue());
                        break;
                    case GT:
                        criteria.and(query.getField()).gt(query.getValue());
                        break;
                    case GTE:
                        criteria.and(query.getField()).gte(query.getValue());
                        break;
                    case LT:
                        criteria.and(query.getField()).lt(query.getValue());
                        break;
                    case LTE:
                        criteria.and(query.getField()).lte(query.getValue());
                        break;
                    case LIKE:
                        criteria.and(query.getField()).regex((String) query.getValue(), "i");
                        break;
                    default:
                        throw new ValidationException(Map.of("operator", "operator not supported"));
                }
            });
        }

        Pageable pageable;
        if(request.getSort() != null) {
            Sort sort = Sort.by(Sort.Direction.valueOf(request.getSort().getDirection().name()), request.getSort().getBy());
            pageable = PageRequest.of(request.getPage(), request.getSize(), sort);
        } else {
            pageable = PageRequest.of(request.getPage(), request.getSize());
        }

        return new Query(criteria).with(pageable);
    }

}
