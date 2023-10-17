package com.shinhan.bullsandbears.domain;

import com.shinhan.bullsandbears.report.Report;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserReportHistory {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="user_report_history_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name="user_id")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  @JoinColumn(name="report_id")
  private Report report;

  @Builder
  public UserReportHistory(User user, Report report){
    this.user = user;
    this.report = report;
  }
}
