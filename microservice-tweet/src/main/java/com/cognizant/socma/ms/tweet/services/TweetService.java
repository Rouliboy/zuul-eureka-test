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
import com.cognizant.socma.ms.tweet.entities.HashTag;
import com.cognizant.socma.ms.tweet.entities.Tweet;
import com.cognizant.socma.ms.tweet.repositories.HashTagRepository;
import com.cognizant.socma.ms.tweet.repositories.TweetRepository;
import com.cognizant.socma.ms.tweet.utils.HashTagExtractor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TweetService {

  private final TweetRepository tweetRepository;

  private final HashTagRepository hashTagRepository;

  private final HashTagExtractor hashTagExtractor;

  @Transactional
  public void createTweet(final TweetDto tweetDto) {

    final Tweet tweet = new Tweet();
    tweet.setCreationDateTime(Instant.now());
    tweet.setMessage(tweetDto.getMessage());
    tweet.setUserId(tweetDto.getUserId());

    final Set<String> hashTagsStr = hashTagExtractor.extractHashTagsFrom(tweetDto.getMessage());

    log.info("Hashtags from message : {}", hashTagsStr);

    final Set<HashTag> hashtags = new HashSet<>();
    for (final String ht : hashTagsStr) {

      final Optional<HashTag> optionalHashTag = hashTagRepository.findByValue(ht);
      final HashTag htEntity;
      if (optionalHashTag.isPresent()) {
        log.info("Hashtag {} exists", ht);
        htEntity = optionalHashTag.get();
      } else {
        log.info("Hashtag {} does not exist", ht);
        htEntity = new HashTag();
        htEntity.setValue(ht);
      }
      htEntity.getTweets().add(tweet);
      hashtags.add(htEntity);
    }
    tweet.setHashtags(hashtags);

    // FIXME : it always insert new hashtag
    tweetRepository.save(tweet);

  }

  public List<Tweet> getTweetsWithHashTag(final String hashTag) {

    if (StringUtils.isBlank(hashTag)) {
      return Collections.emptyList();
    }
    log.info("Searching tweets with hashtag {}", hashTag);
    return tweetRepository.findByHashTagIgnoreCase(hashTag);
  }

}
