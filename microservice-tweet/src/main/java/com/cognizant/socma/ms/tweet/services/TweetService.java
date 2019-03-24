package com.cognizant.socma.ms.tweet.services;

import java.time.Instant;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cognizant.socma.ms.tweet.dto.TweetCreationDto;
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

  private final HashtagRepository hashtagRepository;

  private final HashtagExtractor hashtagExtractor;

  @Transactional
  public void createTweet(final TweetCreationDto tweetDto) {

    final Tweet tweet = new Tweet();
    tweet.setCreationDateTime(Instant.now());
    tweet.setMessage(tweetDto.getMessage());
    tweet.setUserId(tweetDto.getUserId());

    final Set<String> hashTagsStr = hashtagExtractor.extractHashtagsFrom(tweetDto.getMessage());

    log.info("Hashtags from message : {}", hashTagsStr);

    final Set<Hashtag> hashtags = new HashSet<>();
    for (final String ht : hashTagsStr) {

      final Optional<Hashtag> optionalHashtag = hashtagRepository.findByValueIgnoreCase(ht);
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
    return tweetRepository.findByHashtagIgnoreCase(hashTag);
  }

  public List<Tweet> getTweetsFromUsers(final List<Long> userIds) {
    return tweetRepository.findByUserIdsOrderByCreationDateTime(userIds);
  }

  public void deleteTweet(final long id) {
    try {
      tweetRepository.deleteById(id);
    } catch (final EmptyResultDataAccessException ex) {
      log.warn("Tweet with id {} does not exist or is already deleted", id);
    }
  }

}
