package com.example.reactspringtodo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponseDTO<T> {
    //TodoDTO뿐만 아니라 다른 모델도 ResponseDTO를 사용할 수 있도록 제네릭 처리
    private String error;
    //리스트를 반환하는 경우가 많으므로 리스트로 반환
    private List<T> data;

}