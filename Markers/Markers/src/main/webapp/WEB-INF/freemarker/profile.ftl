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
    <link rel="stylesheet" href="<@spring.url '/css/profile.css'/>">
    <title>Профиль</title>
</head>
<body class="bg-light">
<header><#include "header.ftl"></header>
<div class="container emp-profile">
    <form method="post">
        <div class="row">
            <div class="col-md-6">
                <div class="profile-head">
                    <#if message??><h2 class="h4 mb-4 font-weight-normal"><span style="color:green">${message}</span></h2></#if>
                    <h2>
                        ${user.firstName} ${user.lastName}
                    </h2>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-8">
                <div class="tab-content profile-tab" id="myTabContent">
                    <div class="tab-pane fade show active" id="home" role="tabpanel" aria-labelledby="home-tab">
                        <div class="row">
                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <label>Имя</label>
                            </div>
                            <div class="col-md-6">
                                <p>${user.firstName}</p>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <label>Фамилия</label>
                            </div>
                            <div class="col-md-6">
                                <p>${user.lastName}</p>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <label>E-mail</label>
                            </div>
                            <div class="col-md-6">
                                <p>${user.getEmail()}</p>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <label>Пол</label>
                            </div>
                            <div class="col-md-6">
                                <p>${user.getGender()}</p>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <label>Вы</label>
                            </div>
                            <div class="col-md-6">
                                <p>${user.getTypeOfStudent()}</p>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <label>Аудитории, которые вы отслеживаете</label>
                            </div>
                            <div class="col-md-6">
                                    <#if auditoriums?has_content>
                                <#list user.getAuditoriumList() as audit>
                                    <a href="<@spring.url '/auditorium/${audit.getName()}'/>"> ${audit.getName()}</a>,
                                </#list>
                            <#else> У вас нет отслеживаемых аудиторий
                                </#if><br> Изменить список аудиторий можно по <a href="<@spring.url '/profile/auditorium'/>">ссылке</a><br>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <input type="submit" class="profile-edit-btn" name="btnAddMore" value="Изменить профиль"/>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>
</body>
</html>