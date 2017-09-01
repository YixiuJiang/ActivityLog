package au.com.jedisolution;

public class IPTest {

  public static void main(String[] args) {

    String myIP = "202.96.108.10";
    System.out.println(IPUtil.getLocationFromIP(myIP).getCity());
  }


}

