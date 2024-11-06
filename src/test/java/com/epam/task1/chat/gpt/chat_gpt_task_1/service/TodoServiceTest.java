package com.epam.task1.chat.gpt.chat_gpt_task_1.service;

import com.epam.task1.chat.gpt.chat_gpt_task_1.model.Todo;
import com.epam.task1.chat.gpt.chat_gpt_task_1.repo.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoService todoService;

    private Todo todo;

    @BeforeEach
    public void setUp() {
        todo = new Todo();
        todo.setId(1L);
        todo.setTitle("Test Todo");
        todo.setDescription("Test Description");
    }

    @Test
     void testGetAllTodos() {
        Mockito.when(todoRepository.findAll()).thenReturn(Arrays.asList(todo));

        List<Todo> todos = todoService.getAllTodos();

        assertNotNull(todos);
        assertEquals(1, todos.size());
        assertEquals("Test Todo", todos.get(0).getTitle());
    }

    @Test
     void testGetTodoById() {
        Mockito.when(todoRepository.findById(1L)).thenReturn(Optional.of(todo));

        Optional<Todo> fetchedTodo = todoService.getTodoById(1L);

        assertTrue(fetchedTodo.isPresent());
        assertEquals("Test Todo", fetchedTodo.get().getTitle());
    }

    @Test
     void testCreateTodo() {
        Mockito.when(todoRepository.save(todo)).thenReturn(todo);
        Todo createdTodo = todoService.createTodo(todo);

        assertNotNull(createdTodo);
        assertEquals("Test Todo", createdTodo.getTitle());
    }

    @Test
     void testUpdateTodo() {
        Todo updatedTodo = new Todo();
        updatedTodo.setId(1L);
        updatedTodo.setTitle("Updated Todo");
        updatedTodo.setDescription("Updated Description");
        Mockito.when(todoRepository.findById(1L)).thenReturn(Optional.of(todo));
        Mockito.when(todoRepository.save(todo)).thenReturn(updatedTodo);
        Todo result = todoService.updateTodo(1L, updatedTodo);
        assertNotNull(result);
        assertEquals("Updated Todo", result.getTitle());
        assertEquals("Updated Description", result.getDescription());
    }

    @Test
     void testDeleteTodo() {
        Mockito.doNothing().when(todoRepository).deleteById(1L);
        todoService.deleteTodo(1L);
        Mockito.verify(todoRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
     void testUpdateTodoNotFound() {
        Mockito.when(todoRepository.findById(1L)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            todoService.updateTodo(1L, todo);
        });

        assertEquals("Todo не найден", exception.getMessage());
    }
}
