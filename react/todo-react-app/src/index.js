// /src/index.js

import React from "react";
// import ReactDOM from "react-dom/client";
import "./index.css";
import App from "./App";
import reportWebVitals from "./reportWebVitals";
import { createRoot } from "react-dom/client";

// 라우터 경로가 들어있는 RouterApp.js파일 추가하기
import AppRouter from "./AppRouter";
/*
//기존
//ReactDOM을 사용한다.

const root = ReactDOM.createRoot(document.getElementById("root"));

root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);
*/

const container = document.getElementById("root");
//React 18 이상 버전에서는 ReactDOM을 더이상 지원하지 않는다는 경고가 뜬다. 대신 createRoot를 사용해야 한다.
const root = createRoot(container);

root.render(<AppRouter tab="home"></AppRouter>);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
