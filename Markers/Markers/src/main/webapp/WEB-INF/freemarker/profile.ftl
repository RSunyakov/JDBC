<#ftl encoding="UTF-8"/>
<#import "/spring.ftl" as spring/>
<!doctype html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Профиль</title>
</head>
<body>
<#if message?? ><h1>${message}</h1></#if>
Добро пожаловать, ${user.firstName} ${user.lastName}!<br>
Ваши данные:<br>
Email:${user.getEmail()}<br>
Пол: ${user.getGender()}<br>
Аудитории, которые вы отслеживаете:
<#if user.getAuditoriumList()??>
    <#list user.getAuditoriumList() as audit>
        ${audit.getName()},
    </#list>
<#else> У вас нет отслеживаемых аудиторий
</#if><br> Изменить список аудиторий можно по <a href="<@spring.url '/profile/auditorium'/>">ссылке</a><br>
Вы: ${user.getTypeOfStudent()}<br>
</body>
</html>