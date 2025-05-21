package com.planner.business.services.implementation;

import com.planner.business.services.ICourseScraper;
import com.planner.data.entities.Course;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@Service
public class CourseScraperService implements ICourseScraper {
    private final String url;

    public CourseScraperService(@Value("${app.scraper.url}") String url) {
        this.url = url;
    }

    @Override
    public List<Course> scrapeCourses() {
        List<Course> scraped = new ArrayList<>();

        try {
            Document doc = Jsoup.connect(url).get();
            Element content = doc.selectFirst("div.entry-content");
            Elements elems = content.select("h3, p");

            String currentProgram = null;

            for (int i = 0; i < elems.size(); i++) {
                Element el = elems.get(i);
                String tag = el.tagName(), txt = el.text().trim();

                if ("h3".equals(tag)) continue;
                if (txt.matches("^[A-Z]{2,}(\\.[A-Z]+)*\\.?$")) {
                    currentProgram = txt;
                    continue;
                }
                if (txt.matches("^[A-Z]{2,}\\d{3,}$")) {
                    String code = txt;
                    String title = (++i < elems.size() ? elems.get(i).text().trim() : "");
                    StringBuilder desc = new StringBuilder();
                    // gather <p> until Credits:
                    while (i + 1 < elems.size() &&
                            elems.get(i + 1).tagName().equals("p") &&
                            !elems.get(i + 1).text().startsWith("Credits:")) {
                        desc.append(" ").append(elems.get(++i).text().trim());
                    }
                    // credits
                    String credits = "";
                    BigDecimal creditsValue = BigDecimal.ZERO;
                    if (i + 1 < elems.size() && elems.get(i + 1).text().startsWith("Credits:")) {
                        credits = elems.get(++i).text().replace("Credits:", "").trim();
                        if (!credits.isEmpty()) {
                            creditsValue = new BigDecimal(credits);
                        }
                    }
                    // prereq
                    String prereq = null;
                    if (i + 1 < elems.size() && elems.get(i + 1).text().startsWith("Prerequisite:")) {
                        String p = elems.get(++i).text().replace("Prerequisite:", "").trim();
                        if (!p.isEmpty()) prereq = p;
                    }

                    Course c = new Course();
                    c.setProgram(currentProgram);
                    c.setCode(code);
                    c.setTitle(title);
                    c.setDescription(desc.toString().trim());
                    c.setCredits(creditsValue);
                    c.setPrerequisite(prereq);
                    c.setLastUpdated(Timestamp.from(Instant.now()));
                    scraped.add(c);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to scrape courses", e);
        }
        return scraped;
    }
}
