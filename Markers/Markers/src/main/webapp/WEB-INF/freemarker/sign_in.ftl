<#ftl encoding="UTF-8"/>
<#import "/spring.ftl" as spring/>
<!doctype html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>SignIn</title>
</head>
<body>
<div>
    <form action="<@spring.url '/signin'/>" method="post">
        <input name="email" placeholder="Email">
        <input type="password" name="password" placeholder="Пароль">
        <#--<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">-->
        <input type="submit" value="Войти">
    </form>
</div>
</body>
</html>