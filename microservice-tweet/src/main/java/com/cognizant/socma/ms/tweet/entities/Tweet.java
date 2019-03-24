package com.cognizant.socma.ms.tweet.entities;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TWEET")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"id", "hashtags"})
public class Tweet {

  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false)
  private Long userId;

  @Column(nullable = false)
  private Instant creationDateTime;

  @Column(name = "message", length = 256, nullable = false)
  private String message;

  @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
  @JoinTable(name = "tweets_hashtags", joinColumns = @JoinColumn(name = "tweet_id"),
      inverseJoinColumns = @JoinColumn(name = "hashtag_id"))
  Set<HashTag> hashtags = new HashSet<>();

}

