package au.com.jedisolution;

import java.io.IOException;
import java.nio.file.Paths;
import com.github.jarod.qqwry.IPZone;
import com.github.jarod.qqwry.QQWry;

public class IPUtil {
  static QQWry qqwry = null; // load qqwry.dat from java.nio.file.Path

  static {
    try {

      qqwry = new QQWry(Paths.get("/Users/yixjiang/IdeaProjects/Java/actitivy-log/ipdata/qqwry.dat"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  public static Location getLocationFromIP(String ip){
    if (qqwry!=null){
      IPZone ipzone = qqwry.findIP(ip);

      Location location = new Location();
      location.setCity(ipzone.getMainInfo());
      return location;
    }else{
      throw new RuntimeException("can't get qqwry file!");
    }
  }

}
