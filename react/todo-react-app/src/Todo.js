// /src/Todo.js

import { CheckBox } from "@mui/icons-material";
import {
  InputBase,
  ListItem,
  ListItemText,
  Checkbox,
  Divider,
  ListItemSecondaryAction,
  IconButton,
} from "@mui/material";
// import { DeleteOutlined } from "@mui/icons-material/DeleteOutlined";
import { DeleteOutline } from "@mui/icons-material/";
import React from "react";
import { useState } from "react";

const Todo = (props) => {
  // function Todo(props) {
  const [item, setItem] = useState(props.item);
  const deleteItem = props.deleteItem;
  const editItem = props.editItem;

  const [readOnly, setReadOnly] = useState(true);

  // 아이템 삭제를 위한 함수 <IconButton> 에서 작동한다.
  const deleteEventHandler = () => {
    deleteItem(item);
  };

  /*
  타이틀 변경을 위해 인풋필드에서 사용자의 입력을 받아올 때, 
  editEventHandler()에서 item을 바로 넘겨버리면 글자를 입력할 때마다 HTTP 요청을 보내게 된다. X
  사용자가 수정을 완료한 시점에서 http 요청을 보내야 한다. O
  수정을 완료한 시점은 인풋필드가 수정 가능한 상태에서 수정이 불가능한 상태로 바뀌는 시점이다.
  
  따라서 editEventHandler에서는 프론트엔드의 item 값만 업데이트하고, editItem()은 생략하여 HTTP 요청은 보내지 않는다.
  이후 사용자가 엔터키를 누르는 순간 실행되는 turnOnReadOnly()에서 HTTP요청을 보내는 editItem()을 실행한다.

   */
  /*
  const editEventHandler = (event) => {
    //이벤트의 변화가 props.item.title을 변경시킨다.
    item.title = event.target.value;
    console.log("##event.item.title", item.title);
    //App.js의 editItem()를 실행한다. setItems([...items])를 통해 재 렌더링하게 하여 바뀐 item 내용을 표시하도록 한다.
    editItem(); //HTTP 요청을 보내지 않도록 주석처리. 이 함수를 입력이 끝나는 시점인 turnOnReadOnly 함수에서 실행한다.
  };
*/

  const editEventHandler = (event) => {
    setItem({ ...item, title: event.target.value });
  };

  //turnOffReadOnly 함수 작성
  const turnOffReadOnly = () => {
    setReadOnly(false);
  };

  const turnOnReadOnly = (event) => {
    if (event.key === "Enter") {
      setReadOnly(true);
      //editEventHandler가 setItem()을 사용하여 변경한 item을 매개변수로 담아서 실행한다.
      //App.js의 editItem()이 호출된다.
      editItem(item);
    }
  };

  const checkBoxEventHandler = (event) => {
    item.done = event.target.checked;
    console.log("##item.done", item.done);
    // editItem();
    editItem(item); //변경된 체크 정보가 적용된 item을 담아서 editItem() 실행
  };

  return (
    <ListItem>
      {/** 체크박스: Box가 대문자가 아님에 주의. item.done의 기본값은 false. 체크하면 true로 바뀜. */}
      <Checkbox checked={item.done} onChange={checkBoxEventHandler}></Checkbox>
      {/* 체크박스 옆 텍스트 영역 */}
      <ListItemText>
        <InputBase
          inputProps={{ "aria-label": "naked", readOnly: readOnly }}
          //클릭하면 ReadOnly가 false로 바뀌면서 커서 점멸
          onClick={turnOffReadOnly}
          //Enter키 누르면 turnOnReadOnly 실행: ReadOnly가 true로 바뀜
          onKeyDown={turnOnReadOnly}
          //여기서의 변화가 event에 editEventHandler 함수로 전달
          onChange={editEventHandler}
          type="text"
          id={item.id}
          name={item.id}
          value={item.title}
          multiline={true}
          fullWidth={true}
        ></InputBase>
      </ListItemText>

      {/* 삭제버튼을 위한 구분 */}
      <ListItemSecondaryAction>
        {/* 아이콘 버튼에 함수를 연결한다. */}
        <IconButton aria-label="Delete Todo" onClick={deleteEventHandler}>
          {/* 삭제 아이콘 버튼*/}
          <DeleteOutline></DeleteOutline>
        </IconButton>
      </ListItemSecondaryAction>
    </ListItem>
  );

  //   return (
  //     <div className="Todo">
  //       <input type="checkbox" id={item.id} name={item.id} checked={item.done} />
  //       <label id={item.id}>{item.title}</label>
  //     </div>
  //   );
};

export default Todo;
