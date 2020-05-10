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
    <link rel="stylesheet" href="<@spring.url '/css/signup.css'/>">
    <title><@spring.message code="signup"></@spring.message></title>
</head>
<body class="text-center">
<div>
<@spring.bind "signUpDto"/>
<form action="<@spring.url '/signup'/>" method="POST" class="form-signup">
    <h1 class="h3 mb-3 font-weight-normal">Регистрация</h1>
    Email:<br>
    <@spring.formInput "signUpDto.email" "class='form-control'" />
    <@spring.showErrors ""/>
    <br>
    <@spring.message code="password"></@spring.message>:<br>
    <@spring.formPasswordInput "signUpDto.password" "class='form-control'"/>
    <@spring.showErrors ""/>
    <br>
    <@spring.message code="firstname"></@spring.message>:<br>
    <@spring.formInput "signUpDto.firstName" "class='form-control'"/>
    <@spring.showErrors ""/>
    <br>
    <@spring.message code="lastname"></@spring.message>:<br>
    <@spring.formInput "signUpDto.lastName" "class='form-control'"/>
    <@spring.showErrors ""/>
    <br>
    <@spring.message code="gender"></@spring.message>:<br>
    <@spring.formInput "signUpDto.gender" "class='form-control'"/>
    <@spring.showErrors ""/>
    <br>
    <@spring.message code="studentorprofesssor"></@spring.message>:<br>
    <@spring.formInput "signUpDto.typeOfStudent" "class='form-control'"/>
    <@spring.showErrors ""/>
    <br>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <button type="submit" class="btn btn-lg btn-primary btn-block" value="Зарегистрироваться"><@spring.message code="signupbutton"></@spring.message></button>
</form>
    <p class="lead"></p><@spring.message code="lang.choose"></@spring.message> <a href="<@spring.url '/signup?lang=ru'/>"> <@spring.message code="lang.ru"></@spring.message></a>, <a href="<@spring.url '/signup?lang=en'/>"><@spring.message code="lang.en"></@spring.message></a>
</div>
</body>
</html>