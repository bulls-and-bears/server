//package com.shinhan.bullsandbears.domain.hmm;
//
//import lombok.AccessLevel;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//
//import javax.persistence.*;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//@Getter
//@NoArgsConstructor(access= AccessLevel.PROTECTED)
//public class User {
//
//  @Id
//  private String email;
//
//  private String name;
//  private LocalDateTime createdAt;
//
//  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//  private List<UserReportHistory> userReportHistoryList = new ArrayList<>();
//
//
//}