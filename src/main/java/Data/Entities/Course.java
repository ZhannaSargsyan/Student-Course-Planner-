package Data.Entities;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "courses")

public class Course {
    private String program;
    @Id
    private String code;
    private String title;
    private BigDecimal credits;
    private String prerequisite;
    @Column(columnDefinition = "text")
    private String description;
    private Timestamp timestamp;


    public Course() {}

    public Course(String program, String code, String title,
                  BigDecimal credits, String prerequisite,
                  String description, Timestamp timestamp) {
        this.program = program;
        this.code = code;
        this.title = title;
        this.credits = credits;
        this.prerequisite = prerequisite;
        this.description = description;
        this.timestamp = timestamp;
    }

    // getters & setters
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getProgram() { return program; }
    public void setProgram(String program) { this.program = program; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public BigDecimal getCredits() { return credits; }
    public void setCredits(BigDecimal credits) { this.credits = credits; }

    public String getPrerequisite() { return prerequisite; }
    public void setPrerequisite(String prerequisite) { this.prerequisite = prerequisite; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Timestamp getLastUpdated() { return timestamp; }
    public void setLastUpdated(Timestamp timestamp) { this.timestamp = timestamp; }

}
