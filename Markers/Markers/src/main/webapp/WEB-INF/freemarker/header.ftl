<#assign  security=JspTaglibs["http://www.springframework.org/security/tags"] />
<#import "/spring.ftl" as spring/>
    <nav class="navbar navbar-expand-md navbar-dark bg-dark mb-4">
        <a class="navbar-brand mx-auto" href="index">
            <img class="" src="image/logo.jpg" alt="">
        </a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <a class="nav-link" href="<@spring.url '/chat'/>">Чат</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="<@spring.url '/covid'/>">COVID-19</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="<@spring.url '/covid_vk'/>">COVID-19(VK)</a>
                </li>
            </ul>
            <ul class="navbar-nav justify-content-end">
                <@security.authorize access="! isAuthenticated()">
                    <li class="nav-item" style="color:rgba(255,255,255,0.5);"><a class="nav-link" href="<@spring.url '/signin'/>">Войти</a></li>
                    <li class="nav-item" style="color:rgba(255,255,255,0.5);"><a class="nav-link" href="<@spring.url '/signup'/>">Зарегистрироваться</a></li>
                </@security.authorize>
                <@security.authorize access="isAuthenticated()">
                    <li class="nav-item" style="color:rgba(255,255,255,0.5);"><a class="nav-link" href="<@spring.url '/profile'/>">Профиль</a></li>
                    <li class="nav-item" style="color:rgba(255,255,255,0.5);"><a class="nav-link" href="<@spring.url '/signin?logout'/>">Выйти</a></li>
                </@security.authorize>
            </ul>
        </div>
    </nav>