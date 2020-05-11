<!doctype html>
<html lang="en">
<#import "spring.ftl" as spring />
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <link rel="stylesheet" href="https://vk.com/doc99916294_544558697?hash=c23b98998fd962260c&dl=826844dc49a2e1a0fd">
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script
            src="https://code.jquery.com/jquery-3.4.1.min.js"
            integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
            crossorigin="anonymous"></script>
    <script>


        function sendMessage(userId, text) {
            let body = {
                userId: userId,
                receiverId: 0,
                text: text
            };

            $.ajax({
                url: "/api/messages",
                headers: {
                    "Authorization" : "${token}"
                },
                method: "POST",
                data: JSON.stringify(body),
                contentType: "application/json",
                dataType: "json",
                complete: function () {
                    $('#messages').first().append('<div class="msg-right" >' + text + '</div >');
                }
            });
            document.getElementById('message').value = "";
        }

        function start(userId) {
            $.ajax({
                url: "/api/messages/start?userId=" + userId,
                headers: {
                    "Authorization" : "${token}"
                },
                method: "GET",
                dataType: "json",
                contentType: "application/json",
                success: function (response) {
                    $.each(response, function () {
                        if (this.userId == 0) {
                            $('#messages').first().append('<div class="msg-left" >' + this.text + '</div>');
                        } else {
                            $('#messages').first().append('<div class="msg-right" >' + this.text + '</div>');
                        }
                    });
                    receiveMessage(userId);
                }
            })
            // var objDiv = document.getElementById("messages");
            // objDiv.scrollTo(0, objDiv.scrollHeight);
        }

        function check(text) {
            $.ajax({
                url: "/check?text="+text,
                method: "GET",
                dataType: "json",
                contentType: "application/json",
                complete: function () {

                }
            })
        }


        // LONG POLLING
        function receiveMessage(userId) {
            $.ajax({
                url: "/api/messages?userId=" + userId,
                headers: {
                    "Authorization" : "${token}"
                },
                method: "GET",
                dataType: "json",
                contentType: "application/json",
                success: function (response) {
                    $.each(response, function () {
                        $('#messages').first().append('<div class="msg-left" >' + this.text + '</div>');
                    });
                    receiveMessage(userId);
                }
            })

        }
    </script>
    <style>
        body {
            background: #f5f6f7;
        }

        #messages {
            overflow-y: scroll;
            height: 595px;
            margin: 5px 200px;
            background: #fff;
        }

        .msg-right {
            background: #000;
            padding: 10px;
            text-align: right;
            color: #fff;
            margin: 5px;
            width: 70%;
            float: right;
            margin-right: 30px;
        }

        .msg-left {
            background: #ddd;
            padding: 10px;
            margin: 5px;
            width: 70%;
            float: left;
            margin-left: 30px;
        }

        .msg-left:before {
            width: 0;
            height: 0;
            content: "";
            top: 9px;
            left: -28px;
            position: relative;
            border-style: solid;
            border-width: 20px 0px 0px 20px;
            border-color: #ddd transparent transparent transparent;

        }

        .msg-right:after {
            width: 0;
            height: 0;
            content: "";
            top: 9px;
            left: 29px;
            position: relative;
            border-style: solid;
            border-width: 20px 20px 00px 0px;
            border-color: #000 transparent transparent transparent;


        }
    </style>
</head>
<body onload="start(${userId})" >
<#include "parts/header.ftl">
<!-- ##### Breadcumb Area Start ##### -->
<div class="breadcumb_area bg-img"
     style="background-image: url(https://sun9-21.userapi.com/c205620/v205620545/cb8bc/Fn_crZrrCh0.jpg); margin-top: 85px">
    <div class="container h-100">
        <div class="row h-100 align-items-center">
            <div class="col-12">
                <div class="page-title text-center">
                    <h2><@spring.message "header.page.support"/></h2>
                </div>
            </div>
        </div>
    </div>
</div>
<div>


    <div class="container">
        <div id="messages">
        </div>
    </div>
    <script>$('#messages').scrollTop($('#messages').scrollHeight);</script>

</div>
<div style="text-align: center">
    <div style="width: 700px; margin-left: auto; margin-right: auto;">
        <input id="message" placeholder="<@spring.message "support.page.message"/>" class="form-control" style="float: left; width: 64%; height: 50px">
        <button class="btn essence-btn" style="float:right; width: 34%;" onclick="sendMessage('${userId}',
                $('#message').val())"><@spring.message "support.page.send"/>
        </button>
    </div>
</div>
</body>
</html>