package com.cognizant.socma.ms.tweet.services;

import java.time.Instant;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cognizant.socma.ms.tweet.dto.TweetDto;
import com.cognizant.socma.ms.tweet.entities.Hashtag;
import com.cognizant.socma.ms.tweet.entities.Tweet;
import com.cognizant.socma.ms.tweet.repositories.HashtagRepository;
import com.cognizant.socma.ms.tweet.repositories.TweetRepository;
import com.cognizant.socma.ms.tweet.utils.HashtagExtractor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TweetService {

  private final TweetRepository tweetRepository;

  private final HashtagRepository hashTagRepository;

  private final HashtagExtractor hashTagExtractor;

  @Transactional
  public void createTweet(final TweetDto tweetDto) {

    final Tweet tweet = new Tweet();
    tweet.setCreationDateTime(Instant.now());
    tweet.setMessage(tweetDto.getMessage());
    tweet.setUserId(tweetDto.getUserId());

    final Set<String> hashTagsStr = hashTagExtractor.extractHashtagsFrom(tweetDto.getMessage());

    log.info("Hashtags from message : {}", hashTagsStr);

    final Set<Hashtag> hashtags = new HashSet<>();
    for (final String ht : hashTagsStr) {

      final Optional<Hashtag> optionalHashtag = hashTagRepository.findByValue(ht);
      final Hashtag htEntity;
      if (optionalHashtag.isPresent()) {
        log.info("Hashtag {} exists", ht);
        htEntity = optionalHashtag.get();
      } else {
        log.info("Hashtag {} does not exist", ht);
        htEntity = new Hashtag();
        htEntity.setValue(ht);
      }
      // htEntity.getTweets().add(tweet);
      hashtags.add(htEntity);
    }
    tweet.setHashtags(hashtags);

    tweetRepository.save(tweet);

  }

  public List<Tweet> getTweetsWithHashtag(final String hashTag) {

    if (StringUtils.isBlank(hashTag)) {
      return Collections.emptyList();
    }
    log.info("Searching tweets with hashtag {}", hashTag);
    return tweetRepository.findByHashtagIgnoreCase(hashTag);
  }

  public List<Tweet> getTweetsFromUser(final long userId) {
    log.info("Searching tweets from user {}", userId);
    return tweetRepository.findByUserIdOrderByCreationDateTime(userId);
  }

}
