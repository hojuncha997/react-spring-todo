// /service/ApiService.js

import { API_BASE_URL } from "../api-config";

export function call(api, method, request) {
  let headers = new Headers({
    "Content-type": "application/json",
  });
  //   alert(headers);

  //로컬 스토리지에서 ACCESS TOKEN 가져오기
  const accessToken = localStorage.getItem("ACCESS_TOKEN");
  if (accessToken && accessToken != null) {
    headers.append("Authorization", "Bearer " + accessToken);
  }

  let options = {
    headers: headers,
    // http://localhost:8080 + api
    url: API_BASE_URL + api,
    method: method,
  };

  //   let options = {
  //     headers: new Headers({
  //       "Content-type": "application/json",
  //     }),
  //     url: API_BASE_URL + api,
  //     method: method,
  //   };

  if (request) {
    //GET method
    options.body = JSON.stringify(request);
  }

  //   return fetch(options.url, options)
  //     .then((response) => {
  //       if (response.status === 200) {
  //         return response.json();
  //       }
  //     })
  //     .catch((error) => {
  //       console.log("http error");
  //       console.log(error);
  //     });
  // 로그인 하지 않았을 경우 로그인 페이지로 리다이렉트 해주는 로직을 넣어줘야 한다.

  return fetch(options.url, options)
    .then((response) => {
      if (response.status === 200) {
        return response.json();
      } else if (response.status === 403) {
        window.location.href = "/login"; //로그인 페이지로 redirect
      } else {
        Promise.reject(response);
        throw Error(response);
      }
    })
    .catch((error) => {
      console.log("http error");
      console.log(error);
    });
}

//로그인 시 사용하는 함수
export function signin(userDTO) {
  return call("/auth/signin", "POST", userDTO).then((response) => {
    console.log("response: ", response);
    if (response.token) {
      alert("로그인 토큰: " + response.token);
      //로컬 스토리지에 토큰 저장
      localStorage.setItem("ACCESS_TOKEN", response.token);
      //토큰이 존재하는 경우 "/"로 리다이렉트
      window.location.href = "/";
    }
  });
}

//로그아웃 시 사용하는 함수
export function signout() {
  localStorage.setItem("ACCESS_TOKEN", null);
  window.location.href = "/login";

  //로그아웃 버튼은 App.js 컴포넌트에 네비게이션 바를 추가하고 거기에 링크를 추가한다.
}

//계정 생성 시 사용하는 함수
export function signup(userDTO) {
  return call("/auth/signup", "POST", userDTO);
}
