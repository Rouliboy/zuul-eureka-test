package com.cognizant.socma.ms.tweet.repositories;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.cognizant.socma.ms.tweet.entities.Hashtag;

@Repository
public interface HashtagRepository extends CrudRepository<Hashtag, Long> {

  Optional<Hashtag> findByValue(final String value);
}
