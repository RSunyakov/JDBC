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
    <script
            src="https://code.jquery.com/jquery-3.4.1.min.js"
            integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
            crossorigin="anonymous"></script>
    <script>

        function start() {
            console.log('${token}');
            $.ajax({
                url: "/api/admin/support/users",
                headers: {
                    "Authorization" : "${token}"
                },
                method: "GET",
                dataType: "json",
                contentType: "application/json",
                success: function (response) {
                    $.each(response, function () {
                        $('#users').first().append('<li><span><a href="/support?userId=' + this.id + '" style="font-size: 12px">' + this.id + '. ' + this.email + '</a></span></li>');
                    });
                }
            })
        }

    </script>
</head>
<body onload="start()">
<#include "parts/header.ftl">
<!-- ##### Breadcumb Area Start ##### -->
<div class="breadcumb_area bg-img"
     style="background-image: url(https://sun9-21.userapi.com/c205620/v205620545/cb8bc/Fn_crZrrCh0.jpg); margin-top: 85px">
    <div class="container h-100">
        <div class="row h-100 align-items-center">
            <div class="col-12">
                <div class="page-title text-center">
                    <h2><@spring.message "adminSupport.page.panel"/></h2>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="order-details-confirmation" style="width: 1000px; margin-left: auto; margin-right: auto; min-height: 600px; margin-top: 40px">

    <div class="cart-page-heading">
        <h5><@spring.message "adminSupport.page.currentChat"/></h5>
    </div>

    <ul class="order-details-form mb-4" id="users">

    </ul>


</div>
</body>
</html>