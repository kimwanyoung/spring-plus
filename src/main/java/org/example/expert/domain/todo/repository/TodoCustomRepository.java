package org.example.expert.domain.todo.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.example.expert.domain.todo.dto.response.TodoSearchResponse;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TodoCustomRepository {

	Todo findByIdWithUser(Long todoId);

	Page<TodoSearchResponse> findAllBy(
		Pageable pageable,
		String title,
		String nickname,
		LocalDateTime start,
		LocalDateTime end
	);
}
