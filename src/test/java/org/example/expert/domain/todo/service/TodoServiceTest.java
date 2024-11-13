package org.example.expert.domain.todo.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.example.expert.client.WeatherClient;
import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.todo.repository.TodoRepository;
import org.example.expert.domain.user.entity.User;
import org.example.expert.domain.user.enums.UserRole;
import org.example.expert.domain.user.repository.UserRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
class TodoServiceTest {

	@MockBean
	private WeatherClient weatherClient;

	@Autowired
	private TodoRepository todoRepository;

	@Autowired
	private UserRepository userRepository;
	@Test
	public void 계절과_날짜조건으로_할_일_목록_조회_테스트() {
		// given
		User user = new User("test", "test", UserRole.USER);
		User savedUser = userRepository.save(user);
		List<Todo> todos = List.of(
			new Todo("title", "contents", "sunny", savedUser),
			new Todo("title", "contents", "sunny", savedUser),
			new Todo("title", "contents", "sunny", savedUser),
			new Todo("title", "contents", "sunny", savedUser),
			new Todo("title", "contents", "rainy", savedUser),
			new Todo("title", "contents", "rainy", savedUser)
		);
		Pageable pageable = PageRequest.of(0, 10);
		todoRepository.saveAll(todos);

		// when
		long size = todoRepository.findByWeatherAndDateOrderByModifiedAtDesc(
			pageable,
			"sunny",
			LocalDateTime.of(2024, 10, 11, 12, 12),
			LocalDateTime.of(2024, 12, 11, 12, 12)
		).getTotalElements();

		// then
		assertThat(size).isEqualTo(4);
	}
}