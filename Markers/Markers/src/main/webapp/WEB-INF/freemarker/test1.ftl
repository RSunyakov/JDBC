<#ftl encoding="UTF-8"/>
<#import "/spring.ftl" as spring/>
<!doctype html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
<h1>Регистрация</h1>
<div>
    <@sping.bind "model"/>
    <form action="<@spring.url '/profile/auditorium'/>" method="POST">
        <input type ="text" name="test" id="test" value="${model.test}"/>
        <input type="submit"  value="Регистрация">
    </form>
</div>
${model.test}
</body>
</html>