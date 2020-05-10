<#ftl encoding="UTF-8"/>
<#import "/spring.ftl" as spring/>
<!doctype html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="<@spring.url '/css/bootstrap.css'/>">
    <script src="<@spring.url '/js/bootstrap.js'/>"></script>

    <title>Auditorium</title>
</head>
<body class="bg-light">
<header><#include "header.ftl"></header>
<div class="main">
    <#if message??><h1 class="h4 mb-4 font-weight-normal">
           <span style="color:green">${message}</span>
            </#if></h1>
    <h1 class="h3 mb-3 font-weight-normal">Аудитория: ${auditorium.getName()}</h1><br>
    В данной аудитории доступны следующие маркеры:<br>
    <table class="table table-sm table-striped">
        <thead>
        <tr>
            <th scope="col">Цвет маркера</th>
            <th scope="col">Остаток маркера</th>
        </tr>
        </thead>
        <tbody>
        <#list markers as marker>
            <tr>
                <td>${marker.color}</td>
                <td>${marker.condition}</td>
                <td>
                    <form action="<@spring.url '/auditorium/${auditorium.getName()}'/>" method="POST">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                        <button class="btn btn-secondary btn-sm" name="markerId" value="${marker.getId()}" type="submit">Провел пару с этим маркером!
                        </button>
                    </form>
                </td>
            </tr>
        </#list>
        <br>
        <br>
        </tbody>
    </table>
    <a href="<@spring.url '/auditorium/${auditorium.getName()}/addMarker'/>">
        <button class="btn btn-primary btn-lg" value="Добавить маркер">Добавить маркер</button>
    </a>
</div>
</body>
</html>