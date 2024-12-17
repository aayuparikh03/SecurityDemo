package com.example.specifications;

import com.example.entity.Student;
import org.springframework.data.jpa.domain.Specification;

public class StudentSpecifications {

    // Specification to find students by name
    public static Specification<Student> hasName(String name) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("name"), name);
    }

    // Specification to find students with IDs greater than a given value
    public static Specification<Student> hasIdGreaterThan(int id) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThan(root.get("id"), id);
    }
}
