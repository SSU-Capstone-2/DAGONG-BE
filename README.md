# 🛒 DAGONG – 지역 기반 AI 공동구매 플랫폼

> 위치 인증과 실시간 채팅 기능을 결합해 **지역 내 사용자 간 공동구매**를 돕는  
> AI 기반 온디맨드 플랫폼입니다.  
> 단순한 거래를 넘어 지역 커뮤니티의 소비 문제를 해결하는 것을 목표로 합니다.  

---

## 📆 Project Info

- **기간:** 2025.03 ~ 
- **인원:** 4명 (프론트엔드 2 · 백엔드 2)  
- **담당 역할:** 백엔드 개발 (기여도 약 30%)  

---

## 🚀 Overview

DAGONG은 지역 인증을 기반으로 한 **AI 공동구매 플랫폼**으로,  
사용자는 위치 기반으로 공동구매 방을 개설하거나 참여할 수 있습니다.  
거래 상황은 WebSocket을 통해 실시간으로 공유되며,  
AI 서버는 거래 성공 확률과 수요 예측을 제공합니다.

---

## 🧠 Main Features

### 🏷️ 공동구매 관리
- 공동구매 방 개설 / 참여 / 종료 기능
- 상태 전환 (OPEN → SUCCESS / FAIL)
- 정원 초과, 마감시간 등 조건 검증 로직 포함

### 💬 실시간 채팅
- WebSocket 기반 사용자 간 실시간 메시지 송수신  
- 구매 상태, 참여자 변경 이벤트 실시간 브로드캐스팅  

### 🤖 AI 수요 예측 및 감정 분석
- **FastAPI** 서버와 비동기 통신  
- LangChain + OpenAI API로 리뷰 감정 분석 및 거래 성공률 예측  
- REST 통신 기반으로 Spring ↔ FastAPI 연동

### 📊 데이터 통계 및 대시보드
- DAU / NRU / 재참여율 69% 시각화
- SQL 기반 통계 쿼리 및 API 구성
- React 기반 대시보드 연동 예정

---

## 🛠️ Tech Stack

| Category | Stack |
|-----------|--------|
| **Backend** | Spring Boot, JPA, MySQL |
| **AI Server** | FastAPI, LangChain, OpenAI API |
| **Infra** | AWS EC2 · RDS, Docker, Nginx |
| **Communication** | WebSocket, RESTful API |
| **Tools** | Notion, GitHub Projects, Figma |

---

## 📦 Architecture

```plaintext
[User]
   ↓ WebSocket / REST API
[Spring Boot Server]
   ├── GroupPurchase Module
   ├── Chat Module
   ├── Statistics Module
   └── AI Communication (RestTemplate)
         ↓
     [FastAPI Server]
         └── AI Prediction & Sentiment Analysis
   ↑
[AWS RDS / MySQL]
