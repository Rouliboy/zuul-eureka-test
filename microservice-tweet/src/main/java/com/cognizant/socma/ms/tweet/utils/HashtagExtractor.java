package com.cognizant.socma.ms.tweet.utils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class HashtagExtractor {

  private static final Pattern HASHTAG_PATTERN = Pattern.compile("#\\w+");

  public Set<String> extractHashtagsFrom(final String message) {

    if (StringUtils.isBlank(message)) {
      return Collections.emptySet();
    }

    final Set<String> result = new HashSet<>();

    final Matcher matcher = HASHTAG_PATTERN.matcher(message);
    while (matcher.find()) {
      result.add(matcher.group().replaceAll("#", ""));
    }
    return result;
  }
}
