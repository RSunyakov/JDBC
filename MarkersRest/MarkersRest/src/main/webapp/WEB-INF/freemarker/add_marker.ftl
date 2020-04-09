<#ftl encoding="UTF-8"/>
<#import "/spring.ftl" as spring/>
<!doctype html>
<html lang="RU">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Добавление маркера</title>
</head>
<body>
<@spring.bind "marker"/>
<@spring.bind "colors"/>
<form action="<@spring.url '/auditorium/${auditorium.getName()}/addMarker'/>" method="POST">
    <@spring.formSingleSelect "marker.color", colors/>
    <@spring.showErrors "<br>"/>
    <br><br>
    <input type="submit" value="Потдтвердить">
</form>
</body>
</html>