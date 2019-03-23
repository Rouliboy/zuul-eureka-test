package com.cognizant.socma.ms.gateway;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.Test;

public class ZuulProxyApplicationTests {

  @Test
  public void contextLoads() {

    final String line = "#food was testy. #drink lots of. #night was fab. #three #four";

    final Pattern pattern = Pattern.compile("#\\w+");

    final Matcher matcher = pattern.matcher(line);
    while (matcher.find()) {
      System.out.println(matcher.group().replaceAll("#", ""));
    }
  }

  // private Set<String> retrieveHashTags(final String message) {
  //
  // if (StringUtils.isBlank(message)) {
  // return Collections.emptySet();
  // }
  //
  // StringUtils.be
  // return null;
  // }

}
