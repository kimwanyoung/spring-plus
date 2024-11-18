package org.example.expert.domain.todo.repository;

import static org.example.expert.domain.comment.entity.QComment.*;
import static org.example.expert.domain.manager.entity.QManager.*;
import static org.example.expert.domain.todo.entity.QTodo.*;
import static org.example.expert.domain.user.entity.QUser.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.example.expert.domain.todo.dto.response.QTodoSearchResponse;
import org.example.expert.domain.todo.dto.response.TodoSearchResponse;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TodoCustomRepositoryImpl implements TodoCustomRepository {

	private final JPAQueryFactory queryFactory;

	public Todo findByIdWithUser(Long todoId) {
		return queryFactory
			.selectFrom(todo)
			.leftJoin(todo.user, user)
			.fetchJoin()
			.where(todo.id.eq(todoId))
			.fetchOne();
	}

	public Page<TodoSearchResponse> findAllBy(
		Pageable pageable,
		String title,
		String nickname,
		LocalDateTime start,
		LocalDateTime end
	){
		if (!StringUtils.hasText(title) && !StringUtils.hasText(nickname) && start == null && end == null) {
			return new PageImpl<>(Collections.emptyList(), pageable, 0);
		}
		
		List<TodoSearchResponse> content = queryFactory
			.select(new QTodoSearchResponse(todo.id ,todo.title, todo.managers.size(), todo.comments.size()))
			.from(todo)
			.leftJoin(todo.managers, manager)
			.leftJoin(manager.user, user)
			.leftJoin(todo.comments, comment)
			.where(
				eqTitle(title),
				eqNickname(nickname),
				betweenModifiedAt(start, end)
			)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		Long total = queryFactory
			.select(todo.count())
			.from(todo)
			.leftJoin(todo.managers, manager)
			.leftJoin(manager.user, user)
			.leftJoin(todo.comments, comment)
			.where(
				eqTitle(title),
				eqNickname(nickname),
				betweenModifiedAt(start, end)
			)
			.fetchOne();

		return new PageImpl<>(content, pageable, total == null ? 0 : total);
	}

	private BooleanExpression eqTitle(String title) {
		if(!StringUtils.hasText(title))  return null;
		return todo.title.eq(title);
	}

	private BooleanExpression eqNickname(String nickname) {
		if(!StringUtils.hasText(nickname))  return null;
		return manager.user.nickname.eq(nickname);
	}

	private BooleanExpression betweenModifiedAt(LocalDateTime start, LocalDateTime end) {
		if (Objects.nonNull(start) && Objects.nonNull(end)) {
			return todo.modifiedAt.between(start, end);
		} else if (Objects.nonNull(start)) {
			return todo.modifiedAt.goe(start);
		} else if (Objects.nonNull(end)) {
			return todo.modifiedAt.loe(end);
		} else {
			return null;
		}
	}
}
