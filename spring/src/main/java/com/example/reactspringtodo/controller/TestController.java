package com.example.reactspringtodo.controller;

import com.example.reactspringtodo.dto.ResponseDTO;
import com.example.reactspringtodo.dto.TestRequestBodyDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping
    public String testController() {
        return "Hello World!";
    }
    @GetMapping("/{id}")
    public String testControllerWithPathVariable(@PathVariable(required = false) int id) {
        return "Hello World! ID " + id;
    }

    @GetMapping("/testRequestParam")
    public String testControllerRequestParam(@RequestParam(required = false) int id) {
        return "Hello World! ID " + id;
    }

    /**
     * @author : hjcha
     * @param testRequestBodyDTO //int id, String message
     * @return Json 형태로 반환
     */
    @GetMapping("/testRequestBody")
    public TestRequestBodyDTO testControllerRequestBody(@RequestBody TestRequestBodyDTO testRequestBodyDTO) {
        //int id, String
        System.out.println("###############");
//        return "Hello World! ID : "
//                + testRequestBodyDTO.getId() +
//                " , Message : "
//                + testRequestBodyDTO.getMessage();
        return testRequestBodyDTO;
    }

    /**
     * @author : hjcha
     * @return Json 형태로 반환
     */
    @GetMapping("/testResponseBody")
    public ResponseDTO<String> testControllerResponseBody() {
        List<String> list = new ArrayList<>();
        list.add("Hello World! I'm ResponseDTO");

        ResponseDTO<String> response = ResponseDTO.<String>builder()
                .data(list)
                .build();

        return response;
    }

    @GetMapping("testResponseEntity")
    public ResponseEntity<?> testControllerResponseEntity() {
        List<String> list = new ArrayList<>();
        list.add("Hello World! I'm ResponseEntity. And you got 400!");
        ResponseDTO<String> response = ResponseDTO.<String>builder()
                .data(list)
                .build();
        //https status를 400으로 설정
//        return ResponseEntity.badRequest().body(response);
        return ResponseEntity.ok().body(response);
    }

}
