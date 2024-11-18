package org.example.expert.domain.manager.entity;

import java.time.LocalDateTime;

import org.example.expert.domain.common.entity.Timestamped;
import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "log")
@NoArgsConstructor
public class ManagerSaveLog {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long requestUserId;

	@Enumerated(EnumType.STRING)
	private ManagerSaveLogStatus status;

	@CreatedDate
	@Column(updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime createdAt;

	@Builder
	public ManagerSaveLog(Long requestUserId, ManagerSaveLogStatus status, LocalDateTime createdAt) {
		this.requestUserId = requestUserId;
		this.status = status;
		this.createdAt = createdAt;
	}
}
