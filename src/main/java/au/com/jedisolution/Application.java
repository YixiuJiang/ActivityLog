package au.com.jedisolution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class Application implements CommandLineRunner {
  private Integer threadSum = 99;

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
    jdbcTemplate.query(
        "SELECT distinct(ip) FROM LoginLog_copy1",
        (rs, rowNum) ->
            allRecords
                .add(new ActivityLog(rs.getString("ip")))
    );
    List<List<ActivityLog>> groups = splitActivityLogs(allRecords);
    for (int i=0;i<groups.size();i++){
      List<ActivityLog> activityLogs = groups.get(i);
      final int groupId = i;
      Runnable task = () -> {
        log.info("Hello group " + groupId);
        activityLogs.forEach(activityLog -> {
          int  ipcount = jdbcTemplate.queryForObject("select count(1) as c from DONE_IP where IP= '"+activityLog.getIp()+"'",Integer.class);
          if (ipcount == 0) {
            String city = IPUtil.getLocationFromIP(activityLog.getIp()).getCity();
            jdbcTemplate.batchUpdate("update LoginLog_copy1 set city = '"+city+"', province='"+city.substring(0,2)+"' where ip='"+activityLog.getIp()+"' ");
            jdbcTemplate.batchUpdate("insert into DONE_IP (IP) VALUES ('"+activityLog.getIp()+"' )");

          }else{
            log.info("skip ip " + activityLog.getIp());

          }
       });
      };

      Thread thread = new Thread(task);
      thread.start();
    }

  }

  private List<List<ActivityLog>>  splitActivityLogs(List<ActivityLog> allRecords ){
    List<List<ActivityLog>> recordList = new ArrayList<>();
    Integer allRecord = allRecords.size();
    Integer step = allRecord/threadSum;
    if (allRecord % threadSum>0){
      threadSum++;
    }
    for (int i=0;i<threadSum-1;i++){
      Integer fromIndex = i*step;
      Integer toIndex = (i+1)*step;
      if (i==threadSum-1){
        toIndex = allRecord;
      }
      recordList.add(allRecords.subList(fromIndex,toIndex));
    }
    return recordList;
  }
}
