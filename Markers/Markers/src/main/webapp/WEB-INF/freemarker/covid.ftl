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
    <title>Covid-19</title>
</head>
<body class="bg-light">
<header><#include "header.ftl"></header>
<h1 class="h3 mb-3 font-weight-normal">Статистика по коронавирусу в России</h1><br>
<table class="table table-sm table-striped">
    <thead>
    <tr>
        <th scope="col">Дата</th>
        <th scope="col">Количество зараженных</th>
        <th scope="col">Количество выздоровших</th>
        <th scope="col">Количество погибших</th>
    </tr>
    </thead>
    <tbody>
    <#list infos as info>
        <tr>
            <td>${info.date}</td>
            <td>${info.confirmed}</td>
            <td>${info.recovered}</td>
            <td>${info.deaths}</td>
        </tr>
    </#list>
    </tbody>
</table>
</body>
</html>