let selectedText = "";


chrome.runtime.onInstalled.addListener(() => {
  chrome.contextMenus.create({
    id: "translate",
    title: "Translate to Darija",
    contexts: ["selection"]
  });

  
  chrome.sidePanel.setPanelBehavior({ openPanelOnActionClick: true });
});


chrome.contextMenus.onClicked.addListener((info, tab) => {
  if (info.menuItemId === "translate") {
    selectedText = info.selectionText;

   
    chrome.sidePanel.open({ tabId: tab.id });

    
    setTimeout(() => {
      chrome.runtime.sendMessage({
        action: "updateText",
        text: selectedText
      }).catch(err => console.log("Panel loading... message pending."));
    }, 400);
  }
});


chrome.runtime.onMessage.addListener((msg, sender, sendResponse) => {
  if (msg.action === "getSelection") {
    sendResponse({ text: selectedText });
  }
  return true;
});