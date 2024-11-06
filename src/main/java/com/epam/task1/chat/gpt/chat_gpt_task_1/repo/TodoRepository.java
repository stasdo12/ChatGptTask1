package com.epam.task1.chat.gpt.chat_gpt_task_1.repo;

import com.epam.task1.chat.gpt.chat_gpt_task_1.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}
