package com.cognizant.socma.ms.tweet.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.cognizant.socma.ms.tweet.entities.Tweet;

@Repository
public interface TweetRepository extends CrudRepository<Tweet, Long> {

  @Query("SELECT t FROM Tweet t INNER JOIN t.hashtags h WHERE LOWER(h.value) = LOWER(:hashTag)")
  List<Tweet> findByHashTagIgnoreCase(final String hashTag);
}
