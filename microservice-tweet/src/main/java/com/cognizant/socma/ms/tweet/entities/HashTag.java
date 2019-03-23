package com.cognizant.socma.ms.tweet.entities;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "HASHTAG")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"id", "tweets"})
public class HashTag {

  @Id
  @GeneratedValue
  private Long id;

  @Column(length = 256, nullable = false)
  private String value;

  @ManyToMany(fetch = FetchType.LAZY, mappedBy = "hashtags")
  Set<Tweet> tweets = new HashSet<>();

}

