<#ftl encoding="UTF-8"/>
<#import "/spring.ftl" as spring/>
<!doctype html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Forbidden</title>
</head>
<body>
    <h1>Forbidden </h1>
    <h2>Адрес, на который вы пытались зайти: ${url}</h2>
    <h3>Причина: ${exception}</h3>
</body>
</html>