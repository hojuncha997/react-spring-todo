package com.example.reactspringtodo.service;

import com.example.reactspringtodo.entity.TodoEntity;
import com.example.reactspringtodo.persistence.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TodoService {

    //TodoService 작성

//    @Autowired
    private TodoRepository repository;

    public TodoService(TodoRepository repository){
        this.repository = repository;
    }

    //create
    public List<TodoEntity> create(final TodoEntity entity) {
        //validations
        validate(entity);
        repository.save(entity);
        return repository.findByUserId(entity.getUserId());
    }

    //retrieve 조회하기
    public List<TodoEntity> retrieve(final String userId) {
        log.info(userId);
        log.info("TodoService : retrieve() ");
        return repository.findByUserId(userId);
    }

    //update
    public List<TodoEntity> update(final TodoEntity entity) {
        //(1) 저장할 엔티티의 유효성 확인
        validate(entity);

        //(2) 넘겨받은 엔티티 id를 이용해 TodoEntity를 가져온다. 존재하지 않는 엔티티는 업데이트 불가하기 때문이다.
        final Optional<TodoEntity> original = repository.findById(entity.getId());

        original.ifPresent(todo -> {
            //(3) 반환된 TodoEntity가 존재하면 값을 새 entity의 값으로 덮어 씌운다.
            todo.setTitle(entity.getTitle());
            todo.setDone(entity.getDone());

            //(4) 데이터베이스에 새 값을 저장한다.
            repository.save(todo);
        });
        /*
        위의 람다식과 같은 코드이다.
        if(original.ifPresent()){
            final TodoEntity todo = original.get();
            todo.setTitle(entity.getTitle());
            todo.setDone(entity.getDone());
            repository.save(todo);
        }
         */

        return retrieve(entity.getUserId());
    }

    //delete
    public List<TodoEntity> delete(final TodoEntity entity) {
        //(1) 저장할 엔티티의 유효성 확인
        validate(entity);

        try{
            //(2) 엔티티를 삭제한다
            repository.delete(entity);
        }catch (Exception e){
            //(3) exception 발생 시 id와 exception 을 로깅한다.
            log.error("error deleting entity : ", entity.getId(), e);

            //(4) 컨트롤러로 exception을 날린다. 데이터베이스 내부 로직을 캡슐화 하기 위해 e를 리턴하지 않고 새 excetpion 오브젝트를 반환한다.
            throw new RuntimeException("Error deleting entity : " + entity.getId());
        }

        //(5) 새 Todo 리스트를 가져와 반환한다.
        return retrieve(entity.getUserId());

    }













    //validate 메소드
    private void validate(final TodoEntity entity) {
        if(entity == null) {
            log.warn("Entity cannot be null");
            throw new RuntimeException("Entity cannot be null");
        }

        if(entity.getUserId()==null) {
            log.warn("Unknown user.");
            throw new RuntimeException("Unknown user.");
        }
    }

    public String testService() {
        //TodoEntity 생성
        TodoEntity entity = TodoEntity.builder().title("My first todo item").build();
        //TodoEntity  저장
        repository.save(entity);
        //TodoEntity  검색
        TodoEntity savedEntity = repository.findById(entity.getId()).get();

        return savedEntity.getTitle();
    }
}

