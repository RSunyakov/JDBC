<#ftl encoding="UTF-8"/>
<#import "/spring.ftl" as spring/>
<!doctype html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Регистрация</title>
</head>
<body>
<#if message?? ><h1>${message}</h1></#if>
<@spring.bind "signUpDto"/>
<form action="<@spring.url '/signup'/>" method="POST">
    Email:<br>
    <@spring.formInput "signUpDto.email"/>
    <@spring.showErrors "<br>"/>
    <br><br>
    Пароль:<br>
    <@spring.formPasswordInput "signUpDto.password"/>
    <@spring.showErrors "<br>"/>
    <br><br>
    Ник:<br>
    <@spring.formInput "signUpDto.name"/>
    <@spring.showErrors "<br>"/>
    <br><br>
    <input type="submit" value="Регистрация">
</form>
</body>
</html>