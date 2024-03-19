// /src/AppRouter.js : 페이지 라우팅 정보를 적는 페이지
import React from "react";
import "./index.css";
import App from "./App";
import Login from "./Login";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import { Typography, Box } from "@mui/material";
import SignUp from "./SignUp";

function Copyright() {
  return (
    <Typography variant="body2" color="textSecondary" align="center">
      {"Copyright © "}
      fsoftwareengineer, {new Date().getFullYear()}
      {"."}
    </Typography>
  );
}

function AppRouter() {
  return (
    <div>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<App />}></Route>
          <Route path="login" element={<Login />}></Route>
          {/* 계정생성 라우터 추가. 이후 Login.js 컴포넌트에 SignUp.js로 가능 링크를 추가해준다. */}
          <Route path="signup" element={<SignUp />}></Route>
        </Routes>
      </BrowserRouter>
      <Box mt={5}>
        <Copyright />
      </Box>
    </div>
  );
}

export default AppRouter;
