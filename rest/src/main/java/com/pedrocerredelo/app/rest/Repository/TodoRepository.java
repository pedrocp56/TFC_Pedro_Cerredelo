package com.pedrocerredelo.app.rest.Repository;

import com.pedrocerredelo.app.rest.Model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Task,Long> {
}
