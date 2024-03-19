// /src/AddTodo.js

import React, { useState } from "react";
import { Button, Grid, TextField } from "@mui/material";

// function AddTodo(props) {
const AddTodo = (props) => {
  //사용자의 입력을 저장할 오브젝트
  const [item, setItem] = useState({ title: "" });
  const addItem = props.addItem;

  //onInputChange 함수 작성
  const onInputChange = (event) => {
    setItem({ title: event.target.value });
    console.log("##AddTodo-item", item);
  };

  const onButtonClick = () => {
    console.log("##AddTodo: onButtonClick 발동");
    addItem(item);
    // item을 다시 초기화 해준다. 이렇게 해주지 않으면 방금 추가된 내용이 item에 남기 때문이다.
    setItem({ title: "" });
  };

  //enterKeyEventHandler 함수: Enter키를 누르면 발동됨다
  const enterKeyEventhandler = (event) => {
    if (event.key === "Enter") {
      onButtonClick(); //괄호를 빼먹지 않도록 주의
    }
  };

  return (
    <Grid container style={{ marginTop: 20 }}>
      <Grid xs={11} md={11} item style={{ paddingRight: 16 }}>
        <TextField
          placeholder="Add Todo here"
          fullWidth
          onChange={onInputChange}
          //엔터키 입력 시 onKeyPress 발동
          onKeyPress={enterKeyEventhandler}
          value={item.title}
        ></TextField>
      </Grid>
      <Grid xs={1} md={1} item>
        <Button
          fullWidth
          style={{ height: "100%" }}
          color="secondary"
          variant="outlined"
          // 버튼클릭 시 onClick 발동 App.js addItem() 실행
          onClick={onButtonClick}
        >
          +
        </Button>
      </Grid>
    </Grid>
  );
};

export default AddTodo;
