package org.example.expert.domain.manager.service;

import org.example.expert.domain.manager.entity.ManagerSaveLog;
import org.example.expert.domain.manager.repository.ManagerSaveLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ManagerLogService {

	private final ManagerSaveLogRepository managerSaveLogRepository;

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void saveLog(ManagerSaveLog log) {
		managerSaveLogRepository.save(log);
	}
}
