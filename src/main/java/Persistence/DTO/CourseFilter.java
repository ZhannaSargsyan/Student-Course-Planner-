package Persistence.DTO;

import java.math.BigDecimal;
import java.util.List;

/**
 * A Data Transfer Object (DTO) that holds dynamic filtering criteria for querying Course entities.
 * Each non-null field represents a condition that will be turned into
 * an SQL WHERE clause.
 * The excludedCourseCodes list allows you to omit courses the student has already taken.
 */

public class CourseFilter {
    private String program;
    private String code;
    private String title;
    private BigDecimal credits;
    private String prerequisite;

    private List<String> excludedCourseCodes;

    public CourseFilter() {
    }

    public CourseFilter(String program,
                        String code,
                        String title,
                        BigDecimal credits,
                        String prerequisite,
                        List<String> excludedCourseCodes) {
        this.program = program;
        this.code = code;
        this.title = title;
        this.credits = credits;
        this.prerequisite = prerequisite;
        this.excludedCourseCodes = excludedCourseCodes;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getCredits() {
        return credits;
    }

    public void setCredits(BigDecimal credits) {
        this.credits = credits;
    }

    public String getPrerequisite() {
        return prerequisite;
    }

    public void setPrerequisite(String prerequisite) {
        this.prerequisite = prerequisite;
    }

    public List<String> getExcludedCourseCodes() {
        return excludedCourseCodes;
    }

    public void setExcludedCourseCodes(List<String> excludedCourseCodes) {
        this.excludedCourseCodes = excludedCourseCodes;
    }
}



