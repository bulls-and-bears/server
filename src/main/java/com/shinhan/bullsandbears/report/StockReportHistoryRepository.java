package com.shinhan.bullsandbears.report;

import com.shinhan.bullsandbears.domain.StockReportHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockReportHistoryRepository extends JpaRepository<StockReportHistory, Long> {

}
