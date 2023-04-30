package com.example.reactspringtodo.persistence;

import com.example.reactspringtodo.entity.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, String> {

    //?1은 메서드의 매개변수의 순서 위치이다.
    //@Query("select * from TodoEntity t where t.userID =?1")
    List<TodoEntity> findByUserId(String userId);
}
