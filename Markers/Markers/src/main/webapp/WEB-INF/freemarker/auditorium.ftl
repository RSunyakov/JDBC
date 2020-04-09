<#ftl encoding="UTF-8"/>
<#import "/spring.ftl" as spring/>
<!doctype html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Auditorium</title>
</head>
<body>
<h1><#if message?? ><h1>${message}</h1></#if></h1>
<h1>Аудитория: ${auditorium.getName()}</h1><br>
В данной аудитории доступны следующие маркеры:<br>
<table>
    <tr>
        <th>Цвет маркера</th>
        <th>Остаток маркера</th>
    </tr>
<#list markers as marker>
    <tr>
        <td>${marker.color}</td>
        <td>${marker.condition}</td>
        <td>
            <form action="<@spring.url '/auditorium/${auditorium.getName()}'/>" method="POST">
                <button name="markerId" value="${marker.getId()}" type="submit">Провел пару с этим маркером!</button>
            </form>
            </td>
    </tr>
</#list>
    <br>
    <br>
</table>
<a href="<@spring.url '/auditorium/${auditorium.getName()}/addMarker'/>"> <button value="Добавить маркер">Добавить маркер</button></a>
</body>
</html>