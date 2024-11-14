package org.example.expert.domain.todo.repository;

import static org.example.expert.domain.todo.entity.QTodo.*;
import static org.example.expert.domain.user.entity.QUser.*;

import org.example.expert.domain.todo.entity.Todo;
import org.springframework.stereotype.Repository;

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
}
