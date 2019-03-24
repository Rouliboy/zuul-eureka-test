package com.cognizant.socma.ms.tweet.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "HASHTAG")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"id"})
public class Hashtag {

  @Id
  @GeneratedValue
  private Long id;

  @Column(unique = true, length = 256, nullable = false)
  private String value;

  @PrePersist
  public void prePersist() {
    if (null != value) {
      value = value.toLowerCase();
    }
  }

}
