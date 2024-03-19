// /src/App.js

import "./App.css";
import Todo from "./Todo";
import AddTodo from "./AddTodo";
import React, { useEffect, useState } from "react";
import {
  Paper,
  List,
  Divider,
  Container,
  Toolbar,
  Typography,
  AppBar,
  Button,
  Grid,
} from "@mui/material";
import { call, signout } from "./service/ApiService";
import Dashboard from "./dashboard/Dashboard";
import TemporaryDrawer from "./dashboard/TemporaryDrawer";
import ResponsiveDrawer from "./dashboard/ResponsiveDrawer";

function App() {
  //기본 스테이트를 객체로 설정하였다.
  const [items, setItems] = useState([]);
  // 로딩에 사용할 스테이트 선언
  const [loading, setLoading] = useState(true);

  // //백엔드에 보낼 요청 옵션
  // const requestOptions = {
  //   method: "GET",
  //   headers: { "Content-Type": "application/json" },
  // };

  /*
  useEffect(콜백함수, 디펜던시배열) 
  :
  첫 렌더링(마운팅)이 일어났을 때, 그 이후에는 배열 안의 오브젝트 값이 변할 때마다 콜백함수를 부른다.
  따라서 렌더링 이후에 발생하는 효과인 것이다.

  빈 배열을 인자로 넘긴 이유:

  디펜던시 배열에 items를 넣었다고 가정하자. 다시 무한 루프에 빠진다.
   첫 렌더링 이후 items 내용이 바뀌고, 그 때문에 다시 렌더링이 되고 다시 useEffect가 실행되기 때문이다.
  이를 방지하기 위해 빈 배열을 넘긴 것이다.
  */
  useEffect(() => {
    //fetch를 통해 비동기 요청 발송: useEffect를 사용하지 않으면 응답이 도착한 후 setItems 때문에 무한 렌더링에 빠진다.
    console.log("App.js - useEffect-GET 아이템 리스트 가져오기");
    call("/todo", "GET", null).then((response) => {
      setItems(response.data);
      setLoading(false); //로딩 중이 아니면, 즉 값을 다 가져왔으면 loading을 false로 설정해 준다.
    });
  }, []);

  const addItem = (item) => {
    console.log("App.js - useEffect-POST 아이템 추가하기");
    call("/todo", "POST", item).then((response) => setItems(response.data));
  };

  const deleteItem = (item) => {
    console.log("App.js - useEffect-DELETE 아이템 지우기");
    call("/todo", "DELETE", item).then((response) => setItems(response.data));
  };

  /*
  const addItem = (item) => {
    item.id = "ID-" + items.length; //key를 위한 id
    item.done = false; //done 초기화. 아이템을 추가하면 기본적으로 체크박스가 체크되지 않은 상태로 추가한다.
    //업데이트는 반드시 setItems로 하고 새 배열을 만들어야 한다.
    setItems([...items, item]);
    console.log("items: ", items);
  };

  //Todo에서 쓰레기통 아이콘을 클릭하면 삭제된다. 이걸 <Todo>를 호출할 때 넣어준다. 위를 봐라.
  const deleteItem = (item) => {
    // 삭제할 item을 제외한 아이템을 다시 배열에 저장한다.
    const newItems = items.filter((e) => e.id !== item.id);
    console.log("##newItems", newItems);
    setItems([...newItems]);

    //여기가 문제네
    // const newItems = [];
    // for (let i = 0; i < items.length; i++) {
    //   if (items.id !== items[i]) {
    //     newItems.push(items[i]);
    //     // break;
    //   }
    // }
    // console.log("##newItems!!!", newItems);
    // setItems([...newItems]);
  };
*/

  /*
  //백엔드와의 통합 이후에 사용하는 아이템 수정 코드

  기존에는 Todo.js에서 값을 변경한 후 App.js에서 리스트를 재 렌더링 함으로써 리스트를 수정하였다.
  그러나 백엔드 서버와 통합하면 API를 이용하는 경우에는, 
  1. 서버 데이터를 업데이트 한 후 
  2. 변경된 내용을 화면에 다시 출력하는 작업이 필요하다.
  */
  const editItem = (item) => {
    //백엔드와 통합하기 전의 editItem 함수는 매개변수를 받지 않았다.그러나 이제부터는 수정할 item을 받아야 한다
    // 따라서 Todo.js에서 editItem()을 사용할 때 item을 넘겨줘야 한다.
    console.log("App.js : editItem() 발동");
    call("/todo", "PUT", item).then((response) => setItems(response.data));
  };

  /*
  //백엔드와의 통합 이전에 사용하던 아이템 수정 코드
  const editItem = () => {
    // items 내부의 값을 변경했기 때문에 새 배열로 초기화해 화면을 다시 렌더링한다.
    setItems([...items]);
  };
  */

  // 실제 페이지에 나타나는 게시글 영역. paper영역에 목록이 나타난다. 여기서 쓰는 props는 이 함수 이전에 선언되어야 한다.
  let todoItems = items.length > 0 && (
    <Paper style={{ margin: 16 }}>
      <List>
        {items.map((item) => (
          <Todo
            item={item}
            key={item.id}
            //삭제
            deleteItem={deleteItem}
            //수정
            editItem={editItem}
          ></Todo>
        ))}
      </List>
    </Paper>
  );

  // navigation bar(네비게이션바) 추가
  let navigationBar = (
    <AppBar position="static">
      {/* position의 역할이 무엇일까 */}
      <Toolbar>
        <Grid justifyContent="space-between" container>
          <Grid item>
            <Typography variant="h6">Test List</Typography>
          </Grid>
          <Grid item>
            <Button color="inherit" raised onClick={signout}>
              로그아웃
            </Button>
          </Grid>
        </Grid>
      </Toolbar>
    </AppBar>
  );

  // 로딩 중이 아닐 때 렌더링 할 부분
  let todoListPage = (
    <div>
      {/* <TemporaryDrawer></TemporaryDrawer> */}
      {/* <Dashboard></Dashboard> */}
      {/* <ResponsiveDrawer></ResponsiveDrawer> */}
      {/* 네비게이션바 + 로그아웃 */}
      {navigationBar}
      <Container maxWidth="md">
        {/* AddTodo: Todo 추가, addItem도 props로 넘긴다. */}
        <AddTodo addItem={addItem}></AddTodo>
        {/* Todo 목록 표시 */}
        {todoItems}
      </Container>
    </div>
  );

  // 로딩 중일 때 렌더링 할 부분
  let loadingPage = <h1> 로딩 중... </h1>;
  let content = loadingPage;

  if (!loading) {
    // loading 스테이트가 false라면, 즉 로딩 중이 아니라 이미 값을 가져온 상태라면 todoList를 선택
    content = todoListPage;
  }

  // 선택한 content 렌더링
  return (
    <div className="App">
      <div>{content}</div>
      {/* <div>
        <Dashboard />
      </div> */}
    </div>
  ); //기본적으로는 로딩중인 화면을 반환

  // return (
  //   <div className="App">
  //     {/* 네비게이션바 + 로그아웃 */}
  //     {navigationBar}
  //     <Container maxWidth="md">
  //       {/* AddTodo: Todo 추가, addItem도 props로 넘긴다. */}
  //       <AddTodo addItem={addItem}></AddTodo>
  //       {/* Todo 목록 표시 */}
  //       {todoItems}
  //     </Container>
  //   </div>
  // );
}

export default App;
