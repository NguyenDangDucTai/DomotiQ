package com.example.devicesservice.dtos;

import com.example.devicesservice.utils.validators.EnumValidator;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
public class GetListRequest {

    @Min(value = 0, message = "page must be greater than or equal to 0")
    private int page = 0;

    @Min(value = 10, message = "size must be greater than or equal to 10")
    @Max(value = 100, message = "size must be less than or equal to 100")
    private int size = 10;

    private Sort sort;

    private Set<Query> queries = new HashSet<>();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @EqualsAndHashCode(onlyExplicitlyIncluded = true)
    public static class Sort {

        @EqualsAndHashCode.Include
        @NotBlank(message = "by must be not null or empty")
        private String by;

        @EnumValidator(enumClass = SortDirection.class, message = "direction must be one of these values: [ASC, DESC]", required = true)
        private SortDirection direction;

    }

    public enum SortDirection {
        ASC, DESC
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @EqualsAndHashCode(onlyExplicitlyIncluded = true)
    public static class Query {

        @EqualsAndHashCode.Include
        @NotBlank(message = "field must be not null or empty")
        private String field;

        @NotBlank(message = "value must be not null or empty")
        private Object value;

        @EnumValidator(enumClass = QueryOperator.class, message = "operator must be one of these values: [EQ, NE, GT, LT, GTE, LTE, LIKE]", required = true)
        private QueryOperator operator;

    }

    public enum QueryOperator {
        EQ, NE, GT, LT, GTE, LTE, LIKE
    }

}
