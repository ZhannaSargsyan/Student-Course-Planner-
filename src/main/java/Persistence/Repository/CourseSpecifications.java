package Persistence.Repository;

import Persistence.DTO.CourseFilter;
import Data.Entities.Course;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * JPA Specification instances to filter courses based on provided fields.
 * For every non-null field in the filter, a corresponding Predicate
 * is added (ANDed together). Also supports excluding a list of course by code.
 */

public class CourseSpecifications {

    public static Specification<Course> withFilter(CourseFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Filter by exact program
            if (filter.getProgram() != null) {
                predicates.add(cb.equal(root.get("program"), filter.getProgram()));
            }

            // Filter by exact course code
            if (filter.getCode() != null) {
                predicates.add(cb.equal(root.get("code"), filter.getCode()));
            }

            // Filter by exact title
            if (filter.getTitle() != null) {
                predicates.add(cb.equal(root.get("title"), filter.getTitle()));
            }

            // Filter by exact credits
            if (filter.getCredits() != null) {
                predicates.add(cb.equal(root.get("credits"), filter.getCredits()));
            }

            // Filter by exact prerequisite
            if (filter.getPrerequisite() != null) {
                predicates.add(cb.equal(root.get("prerequisite"), filter.getPrerequisite()));
            }

            // Exclude courses the student has already taken
            if (filter.getExcludedCourseCodes() != null && !filter.getExcludedCourseCodes().isEmpty()) {
                predicates.add(cb.not(root.get("code").in(filter.getExcludedCourseCodes())));
            }

            // Combine all predicates with AND
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
