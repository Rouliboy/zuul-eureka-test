package com.cognizant.socma.ms.tweet.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.cognizant.socma.ms.tweet.entities.Tweet;

@Repository
public interface TweetRepository extends CrudRepository<Tweet, Long> {

}
