package com.shinhan.bullsandbears.report;

import com.shinhan.bullsandbears.domain.UserReportHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserReportHistoryRepository extends JpaRepository<UserReportHistory, Long> {
}
