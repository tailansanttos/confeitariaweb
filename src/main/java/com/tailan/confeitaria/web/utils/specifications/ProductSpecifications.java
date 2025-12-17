package com.tailan.confeitaria.web.utils.specifications;

import com.tailan.confeitaria.web.domain.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecifications {

    public static Specification<Product> productNameLikeIgnoreCase(String name) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.upper(root.get("name")), "%" +  name +  "%"));
    }

    public static Specification<Product> categoryNameEquals(String categoryName) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(criteriaBuilder.lower(root.get("category").get("name")), categoryName.toLowerCase()));
    }

    public static Specification<Product> isActive() {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.isTrue(root.get("active")));
    }
}
