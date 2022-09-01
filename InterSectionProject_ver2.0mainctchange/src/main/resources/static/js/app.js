// 웹소켓 변수
var ws;
var name;
// 시작/종료 버튼 활성화 비활성화
function setConnected(connected) {
   $("#connect").prop("disabled", connected); // 시작버튼
   $("#disconnect").prop("disabled", !connected); // 종료버튼
}
// 웹 소켓에 연결
function connect() {
   ws = new WebSocket('ws://localhost:8080/user');
   ws.onmessage = function(data) {
      console.log(data)
      respons(data.data);
   }
   setConnected(true);
}
// 연결 해제
function disconnect() {
   if (ws != null) {
      ws.close();
   }
   setConnected(false);
   console.log("Websocket is in disconnected state");
}
// 전송
function sendData() {
   var data = $("#user").val();
   if(data==null) data="손";
   var data = JSON.stringify({
      'user' : data
   })
   ws.send(data);
}

function respons(message) {
   $("#responsMessage").append("<tr><td> " + message + "</td></tr>");
}

$(function() {
   connect();
   $("form").on('submit', function(e) {
      e.preventDefault();
   });
   $("#connect").click(function() {
      connect();
   });
   $("#disconnect").click(function() {
      disconnect();
   });
   $("#send").click(function() {
      sendData();
   });
});