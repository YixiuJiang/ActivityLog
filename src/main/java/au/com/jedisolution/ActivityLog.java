package au.com.jedisolution;

public class ActivityLog {
  private String ip;
  private String city;
  private long id;

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public ActivityLog(String ip) {
    this.ip = ip;
  }
}
