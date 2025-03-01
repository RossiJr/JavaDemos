let socket = new SockJS("/api/v1/chat");
let stompClient = Stomp.over(socket);

stompClient.connect({}, function () {
    console.log("Connected to WebSocket");
    stompClient.subscribe("/api/v1/socket/topic/messages", function (msg) {
        let chatMessage = JSON.parse(msg.body);
        displayMessage(chatMessage.sender, chatMessage.message);
    });
});

function sendMessage() {
    let username = document.getElementById("usernameInput").value.trim();
    let message = document.getElementById("messageInput").value.trim();

    if (!username) {
        alert("Please enter your name before sending a message.");
        return;
    }
    if (!message) {
        alert("Please enter a message.");
        return;
    }

    fetch(`/api/v1/chat/send?sender=${encodeURIComponent(username)}&message=${encodeURIComponent(message)}`, {
        method: "POST"
    });

    document.getElementById("messageInput").value = ""; // Clear input field
}

function displayMessage(sender, message) {
    let li = document.createElement("li");
    li.innerHTML = `<strong>${sender}:</strong> ${message}`;
    document.getElementById("messages").appendChild(li);
}
