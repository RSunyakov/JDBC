<#ftl encoding="UTF-8"/>
<#import "/spring.ftl" as spring/>
<#import "header.ftl" as header/>
<!doctype html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="<@spring.url '/css/bootstrap.css'/>">
    <link rel="stylesheet" href="<@spring.url '/css/signin.css'/>">
    <title><@spring.message code="signin"></@spring.message></title>
</head>
<body class="text-center">
<div>
    <form action="<@spring.url '/signin'/>" method="post" class="form-signin">
        <h1 class="h3 mb-3 font-weight-normal"><@spring.message code="signin"></@spring.message></h1>
        <input name="email" placeholder="Email" class="form-control" type="email" required="" autofocus="">
        <input type="password" name="password" placeholder=" <@spring.message code="password"></@spring.message>" class="form-control" required="">
        <label>
            <input type="checkbox" name="remember-me"> <@spring.message code="remember-me"></@spring.message>
        </label>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
        <button type="submit" class="btn btn-lg btn-primary btn-block" value="Войти"><@spring.message code="signinbutton"></@spring.message></button>
    </form>
    <p class="lead"><@spring.message code="dontsignup"></@spring.message> <a href="<@spring.url '/signup'/>"><@spring.message code="clickthis"></@spring.message></a><@spring.message code="forsignup"></@spring.message></p>
    <p class="lead"></p><@spring.message code="lang.choose"></@spring.message> <a href="<@spring.url '/signin?lang=ru'/>"> <@spring.message code="lang.ru"></@spring.message></a>, <a href="<@spring.url '/signin?lang=en'/>"><@spring.message code="lang.en"></@spring.message></a>
</div>
</body>
</html>