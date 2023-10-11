package com.shinhan.bullsandbears.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Report {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "report_id")
  private Long id;

  private Duration duration;
  private Integer amount;
  private LocalDate createdAt;

  @OneToMany(mappedBy = "report", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<UserReportHistory> userReportHistoryList = new ArrayList<>();

}
