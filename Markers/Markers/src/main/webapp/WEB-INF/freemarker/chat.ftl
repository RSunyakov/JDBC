<#ftl encoding="UTF-8"/>
<#import "/spring.ftl" as spring/>
<!doctype html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Chat</title>
    <script
            src="https://code.jquery.com/jquery-3.4.1.min.js"
            integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
            crossorigin="anonymous"></script>
    <script src="<@spring.url '/js/chat.js'/>"></script>
    <link rel="stylesheet" href="<@spring.url '/css/bootstrap.css'/>">
</head>
<body onload="sendMessage('${userId}', 'Login')" class="bg-light">
<header><#include "header.ftl"></header>
<h1 class="h3 mb-3 font-weight-normal">Добро пожаловать в чат, ${userName}</h1>
    <input class="form-control" id="message" placeholder="Ваше сообщение">
    <button class="btn btn-secondary btn-sm" onclick="sendMessage('${userId}',
            $('#message').val())">Отправить</button>

    <ul class="list-group" id="messages">

    </ul>
</body>
</html>