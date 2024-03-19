// /src/SignUp.js

import React from "react";
import { Container, Grid, Typography, TextField, Button } from "@mui/material";
import { signup } from "./service/ApiService";
import { Link } from "react-router-dom";
import { useNavigate } from "react-router-dom";

// 이 페이지를 작성하였으면 AppRouter.js의 라우터를 추가해주고, 그 다음엔 Login.js에 계정생성으로 이동하는 링크를 추가해준다.

function SignUp() {
  const handleSubmit = (event) => {
    event.preventDefault();
    // 오브젝트에서 form에 저장된 데이터를 맵의 형태로 바꿔준다.
    const data = new FormData(event.target);
    const username = data.get("username");
    const password = data.get("password");

    //백엔드 서버로 객체 전달
    signup({ username: username, password: password }).then((response) => {
      // 계정 생성 성공 시 login 페이지로 리다이렉트
      window.location.href = "/login";
    });
  };

  return (
    <Container component="main" maxWidth="xs" style={{ marginTop: "8%" }}>
      <Grid container spacing={2}>
        <Grid item xs={12}>
          <Typography component="h1" variant="h5">
            계정 생성
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
              계정 생성
            </Button>
          </Grid>
        </Grid>
        {/* 버튼 아래 계정 보유 여부 확인란 */}
        <Grid container justify="flex-end">
          <Grid item mt={2}>
            <Link to="/login" variant="body2">
              이미 계정이 있습니까? 로그인 하세요.
            </Link>
          </Grid>
        </Grid>
      </form>
    </Container>
  );
}

export default SignUp;
