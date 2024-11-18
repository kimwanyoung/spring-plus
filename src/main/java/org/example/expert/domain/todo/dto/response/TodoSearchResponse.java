package org.example.expert.domain.todo.dto.response;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TodoSearchResponse {

	private Long id;
	private String title;
	private Integer managerCount;
	private Integer commentCount;

	@Builder
	@QueryProjection
	public TodoSearchResponse(Long id, String title, Integer managerCount, Integer commentCount) {
		this.id = id;
		this.title = title;
		this.managerCount = managerCount;
		this.commentCount = commentCount;
	}
}
