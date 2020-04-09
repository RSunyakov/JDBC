<#ftl encoding="UTF-8"/>
<#import "/spring.ftl" as spring/>
<!doctype html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Изменение</title>
</head>
<body>
<@spring.bind "auditorium"/>
<@spring.bind "auditoriums"/>
<form action="<@spring.url '/profile/auditorium'/>" method="POST">
    Выберите номер аудитории:<br>
    <@spring.formSingleSelect "auditorium.name", auditoriums/>
    <@spring.showErrors "<br>"/>
    <br><br>
    <input type="submit" value="Потдтвердить">
</form>
</body>
</html>