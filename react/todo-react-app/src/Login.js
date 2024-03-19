// /src/Login.js : 로그인을 위한 폼이 존재하는 페이지
import React from "react";
import { signin } from "./service/ApiService";
import { TextField, Typography, Container, Grid, Button } from "@mui/material";
import { Link } from "react-router-dom";

function Login() {
  //폼의 submit 컨트롤
  const handleSubmit = (event) => {
    event.preventDefault();
    const data = new FormData(event.target);
    const username = data.get("username");
    const password = data.get("password");

    // /src/ApiService.js의 signin() 메서드를 사용해 로그인
    signin({ username: username, password: password });
  };

  return (
    <Container component="main" maxWidth="xs" style={{ marginTop: "8%" }}>
      <Grid container spacing={2}>
        <Grid item xs={12}>
          <Typography component="h1" variant="h5">
            로그인
          </Typography>
        </Grid>
      </Grid>
      <form noValidate onSubmit={handleSubmit}>
        {" "}
        {/* submit 버튼을 누르면 handleSubmit이 실행된다 */}
        <Grid container spacing={2}>
          {/* 아이디 입력창 */}
          <Grid item xs={12}>
            <TextField
              variant="outlined"
              required
              fullWidth
              id="username"
              label="아이디"
              name="username"
              autoComplete="username"
            ></TextField>
          </Grid>

          {/* 비밀번호 입력창 */}
          <Grid item xs={12}>
            <TextField
              variant="outlined"
              required
              fullWidth
              id="password"
              label="패스워드"
              name="password"
              autoComplete="current-password"
            ></TextField>
          </Grid>
          <Grid item xs={12}>
            <Button type="submit" fullWidth variant="contained" color="primary">
              로그인
            </Button>
          </Grid>
        </Grid>
        {/* 계정 생성 여부 확인란 */}
        <Grid item mt={2}>
          <Link to="/signup" variant="body2">
            계정이 없습니까? 여기서 확인하세요
          </Link>
        </Grid>
      </form>
    </Container>
  );
}

export default Login;
