package com.sep.paypal.repository;

import com.sep.paypal.model.JournalPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JournalPlanRepository extends JpaRepository<JournalPlan, Long> {
    JournalPlan findJournalPlanByJournal(String name);
    JournalPlan findJournalPlanById(Long id);
    JournalPlan findByPlanId(String planId);
}
