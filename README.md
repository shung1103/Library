# Library

<br>

## 주요 기능 소개

  1. 유저관리기능, 도서관리기능, 대출관리기능으로 각각의 CRUD를 간략하게 구현
  2. Redis를 활용한 Refresh token 운용 - JWT의 취약점 보완 및 로그인 상태 유지
  3. Redis를 활용한 Cache Memory 기능으로 가장 사용 빈도가 높은 도서 조회 속도를 향상
  4. Swagger 연동으로 테이블 명세를 대신하면서 프론트엔드 제작을 보조
  
<br>

## 개발 환경

     Spring Version : 3.2.0
     JDK Version : 17

<br>

## 🗓️ 프로젝트 기간

    2023년 12월 7일 ~ 2022년 12월 8일

<br>

## 🔧 사용한 Tool

<div style="display: flex; justify-content: center;">
  <img src="https://img.shields.io/badge/Java-007396?&style=flat&logo=Java&logoColor=white" style="margin-right: 10px;">
<img src="https://img.shields.io/badge/Spring-6DB33F?&style=flat&logo=spring&logoColor=white" style="margin-right: 10px;">
  <img src="https://img.shields.io/badge/MySQL-4479A1?style=flat&logo=mysql&logoColor=white" style="margin-right: 10px;"/>
  <img src="https://img.shields.io/badge/ApachetTomcat-F8DC75?style=flat&logo=apachetomcat&logoColor=white"/>
</div>

<div style="display: flex; justify-content: center;">
  <img src="https://img.shields.io/badge/Git-F05032?style=flat&logo=git&logoColor=white" style="margin-right: 10px;">
  <img src="https://img.shields.io/badge/Github-181717?style=flat&logo=github&logoColor=white" style="margin-right: 10px;">
  <img src="https://img.shields.io/badge/Intellijidea-000000?style=flat&logo=intellijidea&logoColor=white" style="margin-right: 10px;">
  <img src="https://img.shields.io/badge/Postman-FF6C37?style=flat&logo=postman&logoColor=white" style="margin-right: 10px;">
</div>

<div style="display: flex; justify-content: center;">
  <img src="https://img.shields.io/badge/Redis-DC382D?style=flat&logo=Redis&logoColor=white" style="margin-right: 10px;">
  <img src="https://img.shields.io/badge/Amazon S3-569A31?style=flat&logo=Amazon S3&logoColor=white" style="margin-right: 10px;">
</div>

<br>

## ERD


  
<br>

## 테이블 정의서

Swagger 연동으로 대체. 실행 후 http://localhost:8080/swagger-ui/index.html 로 들어가면 나옵니다.

![테이블 정의서 Swagger 스크린샷](https://github.com/shung1103/Library/assets/133616029/66a6561f-9031-47b0-b331-92aadf4ec36e)

<br>

<br>

## 💫 기능정의

`회원 가입 / 로그인`
- 리프레시 토큰으로 로그인 상태 유지가 가능하다.

`유저`
- 유저 페이지에서 정보 조회, 수정, 탈퇴, 로그아웃 가능하다.
- 자신의 도서 대출 내역을 볼 수 있다.
- 관리자는 모든 유저 목록과 함께 각 유저들의 도서 대출 내역을 볼 수 있다.

`도서`
- 회원 가입이나 로그인 하지 않아도 전체 도서 조회가 가능하다.
- 관리자는 도서 생성, 조회, 수정, 삭제가 가능하다.
- 유저는 도서 조회, 대출이 가능하다.
- 등록된 도서에 대한 대출이력을 확인할 수 있다.

`대출`
- 유저는 도서 대출, 반납이 가능하다.
- isBefore 함수를 사용하여 사용자가 연체가 되었을 경우 도서 대출이 제한된다.

<br>

## 시스템 구성도

<h2>⚙️서비스 아키텍처</h2>

![267603749-6d9e5d0d-3196-4ba6-8464-83a8caf078fa](https://github.com/shung1103/Library/assets/133616029/b072dd0f-3292-4491-8d81-d4aa21cb9cde)


<br>

