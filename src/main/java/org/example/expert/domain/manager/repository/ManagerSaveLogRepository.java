package org.example.expert.domain.manager.repository;

import org.example.expert.domain.manager.entity.ManagerSaveLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerSaveLogRepository extends JpaRepository<ManagerSaveLog, Long> {
}
