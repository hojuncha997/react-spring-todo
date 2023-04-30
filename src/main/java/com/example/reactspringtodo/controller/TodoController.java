package com.example.reactspringtodo.controller;

import com.example.reactspringtodo.dto.ResponseDTO;
import com.example.reactspringtodo.dto.TodoDTO;
import com.example.reactspringtodo.entity.TodoEntity;
import com.example.reactspringtodo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@RestController
@RequestMapping("/todo")
public class TodoController {

    private final TodoService todoService;
    //Todo메서드 작성: return ResponseEntity.ok().body(response);

    @Autowired
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/test")
    public ResponseEntity<?> testTodo(){
        List<String> list = new ArrayList<>();
        String str = todoService.testService();
        list.add(str);
        ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<?> createTodo(@RequestBody TodoDTO dto) {
        try {
            String temporaryUserId = "temporary-user"; //temporary user id;

            //(1) TodoEntity로 변환한다.
            TodoEntity entity = TodoDTO.toEntity(dto);

            //(2) id를 null로 초기화한다. 생성 당시에는 id가 없어야 하기 때문이다.
            entity.setId(null);

            //(3) 임시 유저아이디를 설정해준다. 이 부분은 인증과 인가 부분에서 수정할 예정.
            entity.setUserId(temporaryUserId);

            //(4) 서비스를 이용해 Todo엔티티를 생성한다.
            List<TodoEntity> entities =  todoService.create(entity);

            //(5) 자바 스트림을 사용하여 리턴된 엔티티를 리스트를 TodoDTO 리스트로 변환한다.
            List<TodoDTO> dtos =  entities.stream().map(TodoDTO::new).collect(Collectors.toList());

            //(6) 변환된 TodoDTO 리스트를 이용해 ResponseDTO를 초기화한다.
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

            //(7) ResponseDTO를 반환한다.
            return ResponseEntity.ok().body(response);

        }catch (Exception e) {
            //(8) 예외가 나는 경우 dto 대신 error 메시지를 넣어 리턴한다.
            String error = e.getMessage();
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }

    }

    @GetMapping
    public ResponseEntity<?> retrieveTodoList() { //원래는 String id값을 받아야겠지.

        String temporaryUserId = "temporary-user"; //temporary user id;

        //(1) 서비스 메서드의 retrieve 메서드를 사용해 Todo리스트를 가져온다
        List<TodoEntity> entities = todoService.retrieve(temporaryUserId);

        //(2) 자바 스트림을 사용하여 리턴된 엔티티를 리스트를 TodoDTO 리스트로 변환한다.
        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

        //(3) 변환된 TodoDTO 리스트를 이용해 ResponseDTO를 초기화한다.
        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

        //(4) ResponseDTO를 반환한다.
        return ResponseEntity.ok().body(response);
    }

    @PutMapping
    public ResponseEntity<?> updateTodo(@RequestBody TodoDTO dto) {

        String temporaryUserId = "temporary-user"; //temporary user id;

        //(1) dto를 TodoEntity로 변환한다.
        TodoEntity entity = TodoDTO.toEntity(dto);

        //(2) 임시 유저아이디를 설정해준다. 이 부분은 인증과 인가 부분에서 수정할 예정.
        entity.setUserId(temporaryUserId);

        //(3) 서비스를 이용해 Todo엔티티를 업데이트한다.
        List<TodoEntity> entities = todoService.update(entity);

        //(4) 자바 스트림을 이용해 리턴된 엔티티 리스트를 TodoDTO 리스트로 변환한다.
        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

        //(5) 변환된 TodoDTO 리스트를 이용해 ResponseDTO를 초기화한다.
        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

        //(6) ResponseDTO를 반환한다.
        return ResponseEntity.ok().body(response);

    }

    @DeleteMapping
    public ResponseEntity<?> delete(@RequestBody TodoDTO dto){
        try{
            String temporaryUserId = "temporary-user"; //temporary user id;

            //(1) dto를 TodoEntity로 변환한다.
            TodoEntity entity = TodoDTO.toEntity(dto);
            //(2) 임시 유저아이디를 설정해준다. 이 부분은 인증과 인가 부분에서 수정할 예정.
            entity.setUserId(temporaryUserId);
            //(3) 서비스를 이용해 Todo엔티티를 삭제한다.
            List<TodoEntity> entities = todoService.delete(entity);
            //(4) 자바 스트림을 이용해 리턴된 엔티티 리스트를 TodoDTO 리스트로 변환한다.
            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
            //(5) 변환된 TodoDTO 리스트를 이용해 ResponseDTO를 초기화한다.
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
            //(6) ResponseDTO를 반환한다.
            return ResponseEntity.ok().body(response);
        }catch (Exception e){
            //(7) 예외가 나는 경우 dto 대신 error 메시지를 넣어 리턴한다.
            String error = e.getMessage();
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }

    }

}
