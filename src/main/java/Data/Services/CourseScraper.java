// src/main/java/com/example/etl/service/CourseEtlService.java
package Data.Services;

import Data.Entities.Courses;
import Data.Repository.CourseRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class CourseScraper {

    private final CourseRepository repo;
    private final String url = "https://catalog.aua.am/course-descriptions-2024/";

    public CourseScraper(CourseRepository repo) {
        this.repo = repo;
    }

    @Scheduled(cron = "0 0 3 * * *") // daily at 03:00 AM
    public void scrapeAndUpsert() {
        try {
            Document doc = Jsoup.connect(url).get();
            Element content = doc.selectFirst("div.entry-content");
            Elements elems = content.select("h3, p");

            String currentProgram = null;
            List<Courses> scraped = new ArrayList<>();

            for (int i = 0; i < elems.size(); i++) {
                Element el = elems.get(i);
                String tag = el.tagName(), txt = el.text().trim();

                if ("h3".equals(tag)) continue;
                if (txt.matches("^[A-Z]{2,}$")) {
                    currentProgram = txt;
                    continue;
                }
                if (txt.matches("^[A-Z]{2,}\\d{3,}$")) {
                    String code = txt;
                    String title = (++i < elems.size() ? elems.get(i).text().trim() : "");
                    StringBuilder desc = new StringBuilder();
                    // gather <p> until Credits:
                    while (i + 1 < elems.size() &&
                            elems.get(i+1).tagName().equals("p") &&
                            !elems.get(i+1).text().startsWith("Credits:")) {
                        desc.append(" ").append(elems.get(++i).text().trim());
                    }
                    // credits
                    String credits = "";
                    BigDecimal creditsValue = BigDecimal.ZERO;
                    if (i + 1 < elems.size() && elems.get(i+1).text().startsWith("Credits:")) {
                        credits = elems.get(++i).text().replace("Credits:", "").trim();
                        creditsValue = new BigDecimal(credits);

                    }
                    // prereq
                    String prereq = null;
                    if (i + 1 < elems.size() && elems.get(i+1).text().startsWith("Prerequisite:")) {
                        String p = elems.get(++i).text().replace("Prerequisite:", "").trim();
                        if (!p.isEmpty()) prereq = p;
                    }

                    Courses c = new Courses();
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

            // upsert all
            for (Courses c : scraped) {
                repo.findByProgramAndCode(c.getProgram(), c.getCode())
                        .map(existing -> {
                            existing.setTitle(c.getTitle());
                            existing.setDescription(c.getDescription());
                            existing.setCredits(c.getCredits());
                            existing.setPrerequisite(c.getPrerequisite());
                            existing.setLastUpdated(Timestamp.from(Instant.now()));
                            return repo.save(existing);
                        })
                        .orElseGet(() -> repo.save(c));
            }

            System.out.println("ETL run complete: " + scraped.size() + " courses processed.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
