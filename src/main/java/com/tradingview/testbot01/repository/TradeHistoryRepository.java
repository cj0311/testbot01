package com.tradingview.testbot01.repository;

import com.tradingview.testbot01.domain.TradeHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradeHistoryRepository extends JpaRepository<TradeHistory, Long> {
    List<TradeHistory> findTop50ByOrderByTradeTimeDesc();
}