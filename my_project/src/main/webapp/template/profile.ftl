<!doctype html>
<#import "spring.ftl" as spring />
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <link rel="stylesheet" href="https://vk.com/doc99916294_544558697?hash=c23b98998fd962260c&dl=826844dc49a2e1a0fd">
</head>
<body>
<#include "parts/header.ftl">
<div style="text-align: center; margin-top: 50px">
<a class="btn essence-btn" href="/logout" onclick="document.getElementById('logout').submit();"><@spring.message "profile.page.logout"/></a>
</div>
</body>
</html>