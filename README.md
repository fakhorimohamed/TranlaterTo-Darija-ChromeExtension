# 🇲🇦 English to Moroccan Darija Translator (AI-Powered)

A full-stack application featuring a **Jakarta EE 10** backend and a **Chrome Extension** (Manifest V3) (or Php client app) that uses **Google Gemini AI** to provide natural, contextual translations from English to Moroccan Darija.

## 🚀 Features
* **Contextual AI Translation:** Uses Gemini 2.5 Flash .
* **Modern Extension UI:** A side-panel interface designed .
* **Secure API:** Implements Basic Authentication via Jakarta Security (Elytron). .

---

## 🛠️ Project Structure
* `/src`: Jakarta EE REST API (Maven project for WildFly).
* `/extension`: The Chrome Extension source files (HTML, CSS, JS,Json).
* `pom.xml`: Project dependencies and build configuration.

---

## ⚙️ Installation & Setup

### 1. Backend (Java API)
1.  **Prerequisites:** Java 17+, Maven 3.8+, and WildFly 30+.
2.  **Environment Variable:** Set your Gemini API key on your system:
    `GEMINI_API_KEY = "your_google_gemini_api_key"`

### 2. Frontend (Chrome Extension)
1.  Open Chrome and navigate to `chrome://extensions`.
2.  Enable **Developer Mode** (toggle in the top right).
3.  Click **Load unpacked**.
4.  Select the `/extension` folder from this repository.


## 📺 Demo Video
Click the link below to see the Darija Translator in action,  from selecting English text to receiving  Moroccan Darija translation:

[▶️ Watch the Demo Video Here](   --------------------------->>>>>LINK_HERE)

---

## 📖 How to Use
1.  **Login:** Use the default credentials (e.g., `admin` / `1234`) in the extension side panel.
2.  **Translate:** Type or paste English text into the source box.
3.  **Context Menu:** Highlight any text on a webpage, right-click, and select "Translate to Darija" to send it directly to the extension.

---

## 🔄 How It Works (User Flow)

This diagram explains the process from the moment you select text until you see the Darija translation.

```mermaid
sequenceDiagram
    participant U as User
    participant E as Chrome Extension
    participant S as WildFly Server
    participant AI as Gemini AI

    U->>E: 1. Select text & Open Sidepanel
    U->>E: 2. Enter Admin/Password
    E->>S: 3. Verify Credentials (POST)
    S-->>E: 4. Auth Success
    E->>S: 5. Send Text for Translation
    S->>AI: 6. Get Darija Translation
    AI-->>S: 7. Return Result
    S-->>E: 8. Return JSON Data
    E->>U: 9. Show Darija Text
