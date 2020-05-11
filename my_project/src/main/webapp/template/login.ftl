<!doctype html>
<#import "spring.ftl" as spring />
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <link rel="stylesheet" href="https://vk.com/doc99916294_544558697?hash=c23b98998fd962260c&dl=826844dc49a2e1a0fd">
</head>
<body>
<#include "parts/header.ftl">

<!-- ##### Breadcumb Area Start ##### -->
<div class="breadcumb_area bg-img"
     style="background-image: url(https://sun9-21.userapi.com/c205620/v205620545/cb8bc/Fn_crZrrCh0.jpg);">
    <div class="container h-100">
        <div class="row h-100 align-items-center">
            <div class="col-12">
                <div class="page-title text-center">
                    <h2><@spring.message "login.page.login"/></h2>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- ##### Breadcumb Area End ##### -->

<!-- ##### Checkout Area Start ##### -->
<div class="checkout_area section-padding-80">
    <div class="container">
        <div class="row">
            <div class="col-md-6 offset-md-3">
                <form method="post" class="form-horizontal" id="myform">
                    <div class="order-details-confirmation" style="text-align: center;">

                        <div class="col-12 mb-4">
                            <label for="email_address"><@spring.message "signUp.page.email"/> <span>*</span></label>
                            <input type="email" class="form-control" id="email_address" value="" name="email">
                        </div>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                        <div class="col-12 mb-4">
                            <label for="password"><@spring.message "signUp.page.password"/> <span>*</span></label>
                            <input type="password" class="form-control" id="password" value="" name="password">
                        </div>
                        <div class="col-12 mb-4">
                        <div class="custom-control custom-checkbox d-block mb-2" style="text-align: left;">
                        <input type="checkbox" class="custom-control-input" id="customCheck1" name="remember-me">
                        <label class="custom-control-label" for="customCheck1"><@spring.message "login.page.rememberMe"/></label>
                        </div>
                        </div>

                        <button type="submit" class="btn essence-btn"><@spring.message "login.page.signIn"/></button>
                        </br></br>
                        <a href="/registration"></a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- ##### Checkout Area End ##### -->

</body>
</html>