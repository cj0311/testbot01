package com.tradingview.testbot01.repository;


import com.tradingview.testbot01.domain.TradingViewAlert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertRepository extends JpaRepository<TradingViewAlert, Long> {
    List<TradingViewAlert> findTop10ByOrderByCreatedAtDesc();
}