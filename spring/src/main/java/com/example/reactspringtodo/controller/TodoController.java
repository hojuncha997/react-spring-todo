package com.example.reactspringtodo.controller;

import com.example.reactspringtodo.dto.ResponseDTO;
import com.example.reactspringtodo.dto.TodoDTO;
import com.example.reactspringtodo.entity.TodoEntity;
import com.example.reactspringtodo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    /*
    @PostMapping
    public ResponseEntity<?> createTodo(@RequestBody TodoDTO dto) { 인증/인가 부분 이후 아래처럼 변경됨.
    ==> 스프링이 @AuthenticationPrincipal을 이용하여 userID를 찾아 넘겨준다.
    이것은 JwtAuthenticationFilter 클래스에서 찾을 수 있다.
    ...
    AbstractAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userId, null,  AuthorityUtils.NO_AUTHORITIES);
    ...

    이 때 생성자의 첫 매개변수로 넣은 String 타입의 userID가 AuthenicationPrincipal이 된다.
    이 오브젝트를 SecurityContext에 등록했다. 따라서 스프링은 컨트롤러 메서드를 부를 대 @AuthenticationPrincipal 어노테이션이 존재한다는 것을 인지한다.
    그래서 SecurityContextHolder에서 SecurityContext::Authentication, 즉 UsernamePasswordAuthenticationToken 오브젝트를 가져온다.
    이 오브젝트에서 다시 AuthenticationPrincipal을 가져와 컨트롤러 메서드에 넘긴다.

    즉, JwtAuthenticationFilter 클래스에서 AuthenticationPrincipal을 String 타입의 오브젝트로 지정했기 때문에
    @AuthenticationPrincipal의 타입으로 String을 사용해야 한다는 것을 미리 안 것이다.

    아래가 적용한 코드이다.
     */
    @PostMapping
    public ResponseEntity<?> createTodo(@AuthenticationPrincipal String userId, @RequestBody TodoDTO dto) {
        try {
            //String temporaryUserId = "temporary-user"; //temporary user id;
            //@AuthenticationalPrincipal String userId를 쓰기 전에는 무조건 temporary-user를 사용하도록 하드코딩 돼 있었다. 이제 쓰지 않는다.

            //(1) TodoEntity로 변환한다.
            TodoEntity entity = TodoDTO.toEntity(dto);

            //(2) id를 null로 초기화한다. 생성 당시에는 id가 없어야 하기 때문이다.
            entity.setId(null);

            //(3) 임시 유저아이디를 설정해준다. 이 부분은 인증과 인가 부분에서 수정할 예정.
            /* (3) Authentication Bearer Token을 통해 받은 userId를 넘긴다*/
            //entity.setUserId(temporaryUserId);
            entity.setUserId(userId);

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
    public ResponseEntity<?> retrieveTodoList(@AuthenticationPrincipal String userId) { //원래는 String id값을 받아야겠지.
        System.out.println("/todo GetMapping 들어옴");

        //String temporaryUserId = "temporary-user"; //temporary user id;
        //@AuthenticationalPrincipal String userId를 쓰기 전에는 무조건 temporary-user를 사용하도록 하드코딩 돼 있었다. 이제 쓰지 않는다.


        //(1) 서비스 메서드의 retrieve 메서드를 사용해 Todo리스트를 가져온다
        //@AuthenticationalPrincipal String userId를 쓰기 전에는 무조건 temporary-user를 사용하도록 하드코딩 돼 있었다. 이제 쓰지 않는다.
        //List<TodoEntity> entities = todoService.retrieve(temporaryUserId);
        List<TodoEntity> entities = todoService.retrieve(userId);

        //(2) 자바 스트림을 사용하여 리턴된 엔티티를 리스트를 TodoDTO 리스트로 변환한다.
        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

        //(3) 변환된 TodoDTO 리스트를 이용해 ResponseDTO를 초기화한다.
        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

        //(4) ResponseDTO를 반환한다.
        return ResponseEntity.ok().body(response);
    }




    @PutMapping
    public ResponseEntity<?> updateTodo(@AuthenticationPrincipal String userId, @RequestBody TodoDTO dto) {

        //String temporaryUserId = "temporary-user"; //temporary user id;
        //@AuthenticationalPrincipal String userId를 쓰기 전에는 무조건 temporary-user를 사용하도록 하드코딩 돼 있었다. 이제 쓰지 않는다.


        //(1) dto를 TodoEntity로 변환한다.
        TodoEntity entity = TodoDTO.toEntity(dto);

        //(2) 임시 유저아이디를 설정해준다. 이 부분은 인증과 인가 부분에서 수정할 예정.
        //@AuthenticationalPrincipal String userId를 쓰기 전에는 무조건 temporary-user를 사용하도록 하드코딩 돼 있었다. 이제 쓰지 않는다.
        //entity.setUserId(temporaryUserId);
        entity.setUserId(userId);

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
    public ResponseEntity<?> delete(@AuthenticationPrincipal String userId, @RequestBody TodoDTO dto){
        try{
            //String temporaryUserId = "temporary-user"; //temporary user id;
            //@AuthenticationalPrincipal String userId를 쓰기 전에는 무조건 temporary-user를 사용하도록 하드코딩 돼 있었다. 이제 쓰지 않는다.

            //(1) dto를 TodoEntity로 변환한다.
            TodoEntity entity = TodoDTO.toEntity(dto);
            //(2) 임시 유저아이디를 설정해준다. 이 부분은 인증과 인가 부분에서 수정할 예정.
            //entity.setUserId(temporaryUserId);
            entity.setUserId(userId);

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
