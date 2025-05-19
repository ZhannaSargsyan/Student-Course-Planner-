package com.planner.data.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "degree_requirements")
public class DegreeRequirement {
        @Id
        private String program;
        @Column(columnDefinition = "text")
        private String summary;

        public DegreeRequirement() {}

        public DegreeRequirement(String program, String summary) {
        this.program = program;
        this.summary = summary;
        }

        // getters & setters
        public void setProgram(String program) {
                this.program = program;
        }
        public String getProgram() {
                return program;
        }

        public void setSummary(String summary) {
                this.summary = summary;
        }
        public String getSummary() {
                return summary;
        }
}
