package au.com.jedisolution;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class Application implements CommandLineRunner {

  private static final Logger log = LoggerFactory.getLogger(Application.class);

  public static void main(String args[]) {
    SpringApplication.run(Application.class, args);
  }

  @Autowired
  JdbcTemplate jdbcTemplate;

  @Override
  public void run(String... strings) throws Exception {

    // Uses JdbcTemplate's batchUpdate operation to bulk load data
//        jdbcTemplate.batchUpdate("INSERT INTO customers(first_name, last_name) VALUES (?,?)", splitUpNames);

    List<ActivityLog> allRecords = new ArrayList<>();
    Integer count = jdbcTemplate
        .queryForObject("select count(1) from LoginLog_copy1", Integer.class);
    log.info("all records " + count);
    jdbcTemplate.query(
        "SELECT id, ip FROM LoginLog_copy1",
        (rs, rowNum) ->
            allRecords
                .add(new ActivityLog(rs.getString("ip"), rs.getString("city"), rs.getLong("id")))
    ).forEach(customer -> log.info(customer.toString()));
    log.info("all records " + allRecords.size());
  }
}