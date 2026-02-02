const inputText = document.getElementById("myText");
const outputText = document.getElementById("res");
const translateBtn = document.getElementById("btn");
const loginBtn = document.getElementById("login-btn");
const logoutBtn = document.getElementById("logout-btn");
const authSection = document.getElementById("auth-section");
const mainSection = document.getElementById("main-section");


const BASE_URL = "http://127.0.0.1:8080/TranlaterTo-Darija-ChromeExtension/api";



async function checkAuth() {
    const data = await chrome.storage.local.get("authToken");
    if (data.authToken) {
        authSection.style.display = "none";
        mainSection.style.display = "flex"; 
    } else {
        authSection.style.display = "flex";
        mainSection.style.display = "none";
    }
}


loginBtn.addEventListener("click", async () => {
    const user = document.getElementById("user").value.trim();
    const pass = document.getElementById("pass").value.trim();

    if (!user || !pass) {
        alert("Enter your credentials");
        return;
    }

   
    loginBtn.innerText = "Authenticating...";
    loginBtn.disabled = true;

    const authHeader = "Basic " + btoa(`${user}:${pass}`);

    try {
        const res = await fetch(`${BASE_URL}/translate`, {
            method: "POST",
            headers: { 
                "Content-Type": "application/json",
                "Authorization": authHeader 
            },
            body: JSON.stringify({ text: "auth_check" }),
            credentials: 'include' 
        });

        if (res.ok) {
            await chrome.storage.local.set({ authToken: authHeader });
            checkAuth();
        } else if (res.status === 401) {
            alert("Invalid Credentials");
        } else {
            alert("Error: " + res.status);
        }
    } catch (err) {
        console.error("Login error:", err);
        alert("Server unreachable. Ensure WildFly is running.");
    } finally {
      
        loginBtn.innerText = "Sign In";
        loginBtn.disabled = false;
    }
});


logoutBtn.addEventListener("click", async () => {
    await chrome.storage.local.remove("authToken");
    checkAuth();
});


translateBtn.addEventListener("click", async () => {
    const textValue = inputText.value.trim();
    if (!textValue) return;

    const { authToken } = await chrome.storage.local.get("authToken");
    if (!authToken) {
        checkAuth();
        return;
    }

    translateBtn.innerText = "Processing...";
    translateBtn.style.opacity = "0.7";
    translateBtn.disabled = true;
    
    try {
        const response = await fetch(`${BASE_URL}/translate`, {
            method: "POST",
            headers: { 
                "Content-Type": "application/json",
                "Authorization": authToken
            },
            body: JSON.stringify({ text: textValue }),
            credentials: 'include'
        });

        if (response.status === 401) {
            alert("Session expired.");
            await chrome.storage.local.remove("authToken");
            checkAuth();
            return;
        }

        const data = await response.json();
      

        outputText.value = data.translatedText || "Translation empty.";
    } catch (err) {
        console.error("Translation error:", err);
        outputText.value = "Translation Error: " + err.message;
    } finally {
        translateBtn.innerText = "Translate";
        translateBtn.style.opacity = "1";
        translateBtn.disabled = false;
    }
});



chrome.runtime.onMessage.addListener((msg) => {
  if (msg.action === "updateText" && msg.text) {
    inputText.value = msg.text;
    outputText.value = "";
    
    translateBtn.click();
  }
});


checkAuth();