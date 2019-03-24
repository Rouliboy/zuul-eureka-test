package com.cognizant.socma.ms.tweet.repositories;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.cognizant.socma.ms.tweet.entities.HashTag;

@Repository
public interface HashTagRepository extends CrudRepository<HashTag, Long> {

  Optional<HashTag> findByValue(final String value);
}
