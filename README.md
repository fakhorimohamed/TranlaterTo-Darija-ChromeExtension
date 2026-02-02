# 🇲🇦 English to Moroccan Darija Translator (AI-Powered)

A professional full-stack application featuring a **Jakarta EE 10** backend and a **Chrome Extension** (Manifest V3) that leverages **Google Gemini AI** to provide natural, contextual translations from English to Moroccan Darija.

## 🚀 Features
* **Contextual AI Translation**: Powered by **Gemini 2.5 Flash**, the first Flash model featuring integrated thinking capabilities for nuanced dialect translation.
* **Thinking Capabilities**: The model performs a reasoning process to ensure translations respect Moroccan cultural context and linguistic nuances.
* **Modern Extension UI**: A sleek side-panel interface designed for seamless user interaction and rapid translation access.
* **Secure API Gateway**: Implements enterprise-grade Basic Authentication via **Jakarta Security (Elytron)**.
* **Cross-Origin Resource Sharing**: Custom **CORS Filter** ensures secure communication between the browser extension and the WildFly server.

---

## 🛠️ Integrated Technology Stack

This project demonstrates a modular, multi-client architecture where the backend acts as a secure gateway to AI services.

```mermaid
graph TD
    subgraph Clients
        A[Chrome Extension]
        B[PHP Web App]
        C[Postman / CLI]
    end

    subgraph "Jakarta EE Backend (WildFly)"
        D[REST API - JAX-RS]
        E[Jakarta Security - Elytron]
        F[GeminiService - AI Logic]
    end

    subgraph "AI Provider"
        G[Gemini 2.5 Flash]
    end

    A & B & C --> D
    D --> E
    E --> F
    F --> G
