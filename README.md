# Spring Boot&Thymeleaf 기반 게시판

## 📌 프로젝트 개요
- Spring Boot와 Thymeleaf를 기반으로하는 게시판 웹사이트
- CRUD 기능, AOP 기반 로깅, 예외 처리, 스케줄링 및 보안 기능을 포함

## 🚀 주요 기능

### ✅ Thymeleaf 기반 서버 사이드 렌더링
- 컨트롤러에서 Thymeleaf 템플릿을 반환하여 동적 데이터 렌더링 지원
- Model을 통해 데이터를 전달하여 UI 렌더링 최적화

### ✅ AOP 기반 로깅 시스템
- AOP(Aspect-Oriented Programming) 적용하여 컨트롤러 실행 전후 로그 기록
- JSON 형식으로 사용자 요청 데이터 및 요청 메서드 정보 저장

### ✅ Spring Security 기반 로그인 & 세션 관리
- 사용자 인증 및 권한 부여

### ✅ 예외 처리 공통화
- 예외 발생 시 JSON 형식의 응답 반환

### ✅ 스케줄링 기능 적용 (@Scheduled)
- 일정 시간마다 특정 조건에 맞는 게시글 상태 자동 업데이트

## 🛠️ Tech Stack
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-005F0F?style=for-the-badge&logo=thymeleaf&logoColor=white)
![Java Spring](https://img.shields.io/badge/Java%20Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
<img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white">
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![Redis](https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=redis&logoColor=white)
![JSON Web Tokens](https://img.shields.io/badge/JSON%20Web%20Tokens-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white)
