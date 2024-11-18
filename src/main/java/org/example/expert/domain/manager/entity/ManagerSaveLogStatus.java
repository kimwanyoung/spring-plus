package org.example.expert.domain.manager.entity;

public enum ManagerSaveLogStatus {
	SUCCESS("SUCCESS"),
	FAIL("FAIL");

	private final String status;

	ManagerSaveLogStatus(String status) {
		this.status = status;
	}
}
