//서비스가 요청을 처리하고 클라이언트로 반환할 때 Model그 자체를 반환하는 경우는 거의 없음
//보통 모델을 DTO로 변환하여 반환한다.
//1. 로직을 캡슐화 할 수 있음. 내부 로직, DB와 비슷한 구조를 가진 모델을 그대로 반환하는 것은 위험함.
//2. 클라이언트가 필요한 정보를 모델이 전부 포함하지 않는 경우가 많기 때문. 예)에러

package com.example.reactspringtodo.dto;

import com.example.reactspringtodo.entity.TodoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TodoDTO {
    //HTTP 응답을 반환하거나 비즈니스 로직을 캡슐화하거나, 추가적인 정보를 함께 반환하기 위해 DTO를 사용한다.
    private String id; //이 오브젝트의 아이디
    private String userId; //이 오브젝틀르 생성한 유저의 아이디
    private String title;// Todo타이틀 예) 운동하기
    private Boolean done; // true - todo를 완료한 경우 (checked), false - todo를 완료하지 않은 경우 (unchecked)

    public TodoDTO(final TodoEntity entity) {
        this.id = entity.getId();
        this.userId = entity.getUserId();
        this.title = entity.getTitle();
        this.done = entity.getDone();
    }

    public static TodoEntity toEntity(final TodoDTO dto){
        return TodoEntity.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .done(dto.getDone())
                .build();
    }
}
