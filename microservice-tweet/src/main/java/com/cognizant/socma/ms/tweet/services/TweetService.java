package com.cognizant.socma.ms.tweet.services;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cognizant.socma.ms.tweet.dto.TweetDto;
import com.cognizant.socma.ms.tweet.entities.HashTag;
import com.cognizant.socma.ms.tweet.entities.Tweet;
import com.cognizant.socma.ms.tweet.repositories.TweetRepository;
import com.cognizant.socma.ms.tweet.utils.HashTagExtractor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TweetService {

  private final TweetRepository tweetRepository;

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
      final HashTag htEntity = new HashTag();
      htEntity.setValue(ht);
      htEntity.getTweets().add(tweet);
      hashtags.add(htEntity);
    }
    tweet.setHashtags(hashtags);

    // FIXME : it always insert new hashtag
    tweetRepository.save(tweet);

  }

}
