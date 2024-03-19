package com.example.reactspringtodo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity // 테이블마다 그에 상응하는 엔티티 클래스가 존재한다.하나의 엔티티 인스턴스는 DB 테이블의 한 행에 대응한다.
//자바 클래스를 엔티티로 정의할 때는 1. @NoArgsConstructor가 필요하다. 2. Getter/setter가 필요하다 3.PK를 지정해 줘야한다.
@Table(name = "Todo")
public class TodoEntity {
    @Id
    @GeneratedValue(generator="system-uuid") //ID를 자동으로 생성하겠다는 뜻. Id필드는 오브젝트를 DB에 저장할 때마다 생성할 수도 있지만 @GeneratedValue를 이용해 자동으로 생성할 수도 있다.
    //generator를 매개변수로 하여 ID를 생성하는 방식을 지정할 수 있다. system-uuid는 @Generic Generator에 정의된 generator의 이름이다.
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    //GenericGenerator는 hibernate에서 기본 제공하는 Generator가 아닌 다른 Generator를 사용하고 싶을 때 이용한다.
    // @GeneratedValue에 사용할 수 있는 generator를 정의한다. 기본 Generator로는 INCREMENTAL, SEQUENCE, TABLE, UUID, UUID2, IDENTITY, AUTO가 있다.
    //UUID를 이용하기 위해 strategy를 uuid로 지정하였다. 이렇게 uuid를 사용하는 system-uuid라는 이름의 GenericGenerator를 정의하였다.
    //이 Generator를 @GeneratedValue가 참조해 사용한다.

    private String id; //이 오브젝트의 아이디
    private String userId; //이 오브젝틀르 생성한 유저의 아이디
    private String title;// Todo타이틀 예) 운동하기
    private Boolean done; // true - todo를 완료한 경우 (checked), false - todo를 완료하지 않은 경우 (unchecked)


}
