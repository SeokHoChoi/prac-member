# MEMBER Practice

### Info
Member 관련 기본 API 연습을 위한 Repository

- ROLE_USER, ROLE_ADMIN 권한 구분
- 간단한 게시판을 만들어 유저의 게시글 사용 테스트


### virsion
- SpringBoot 3.0.4
- java 17
- jsonwebtoken 0.11.2
- data-jpa 3.0.4
- security 3.0.4
- validation 3.0.4
- mysql 8.0.32

### Member API
* [회원가입] : POST /api/signup
* [로그인] : POST /api/authenticate
* [회원정보 수정] PUT /api/user/{username}
* [회원 탈퇴] DELETE /api/user
* [나의 회원정보 조회 - 모든 권한] GET /api/user
* [모든 사용자정보 조회 - ADMIN 권한] GET /api/user/{username}
* [회원아이디 찾기]
* [회원비밀번호 재설정]