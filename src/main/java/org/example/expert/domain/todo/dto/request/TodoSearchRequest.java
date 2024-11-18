package org.example.expert.domain.todo.dto.request;

import java.time.LocalDateTime;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TodoSearchRequest {

	private String title;
	private String nickname;
	private LocalDateTime start;
	private LocalDateTime end;
}
