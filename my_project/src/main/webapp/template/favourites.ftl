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
    <#--    <script type='text/javascript'>-->
    <#--        function submitForm(){-->
    <#--            document.getElementById('myform').submit();-->
    <#--        }-->
    <#--    </script>-->
    <script
            src="https://code.jquery.com/jquery-3.4.1.min.js"
            integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo=" crossorigin="anonymous"></script>
    <script>

        function receiveProducts() {
                $.ajax({
                    url: "/api/products-management/products/favourite",
                    headers: {
                        "Authorization" : "${token}"
                    },
                    method: "GET",
                    dataType: "json",
                    contentType: "application/json",
                    success: function (response) {
                        createView(response)
                    }
                });
        }


        function createView(response) {
            document.getElementById('products').innerHTML = "";
            document.getElementById('size').innerHTML = "";
            $('#size').first().append(response['data'].length);
            $.each(response['data'], function () {
                $('#products').first().append('                            <div class="col-12 col-sm-3">\n' +
                    '                                <div class="single-product-wrapper">\n' +
                    '                                    <div class="product-img">\n' +
                    '                                        <img src=' + this.imgUrl + ' alt="">\n' +
                    '                                        <div class="product-badge">\n' +
                    '                                           <span>' + this.who + '</span>\n' +
                    '                                        </div>' +
                    '                                        <div class="product-favourite">\n' +
                    '                                            <a onclick="deleteProduct('+this.id+')"><img\n' +
                    '                                                        src="https://sun9-8.userapi.com/c855624/v855624055/2151ba/Z05N54xeZ5Q.jpg"\n' +
                    '                                                        alt="Удалить товар" style="height: 60%; width: auto;"></a>\n' +
                    '                                        </div>\n' +
                    '                                    </div>\n' +
                    '                                    <div class="product-description">\n' +
                    '                                        <span>' + this.site.store_name + '</span>\n' +
                    '                                        <a href=' + this.productUrl + '>\n' +
                    '                                            <h6>' + this.productName + '</h6>\n' +
                    '                                        </a>\n' +
                    '                                        <p class="product-price"><span\n' +
                    '                                                    class="old-price">' + this.oldPrice + '</span>' + this.newPrice + '</p>' +
                    '                                    </div>\n' +
                    '                                </div>\n' +
                    '                            </div>');
            });
        }


        function deleteProduct(productId) {
            $.ajax({
                url: "/api/products-management/products/favourite?product=" + productId,
                headers: {
                    "Authorization" : "${token}"
                },
                method: "DELETE",
                dataType: "json",
                contentType: "application/json",
                error: function (response) {
                    alert(response.responseText);
                },
                complete: function () {
                    receiveProducts();
                }
            })
        }
    </script>
</head>
<body onload="receiveProducts()">
<#include "parts/header.ftl">

<!-- ##### Breadcumb Area Start ##### -->
<div class="breadcumb_area bg-img"
     style="background-image: url(https://sun9-21.userapi.com/c205620/v205620545/cb8bc/Fn_crZrrCh0.jpg);">
    <div class="container h-100">
        <div class="row h-100 align-items-center">
            <div class="col-12">
                <div class="page-title text-center">
                    <h2><@spring.message "favourites.page.favourites"/></h2>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- ##### Shop Grid Area Start ##### -->
<section class="shop_grid_area section-padding-80">
    <div class="container">
        <div class="row">

            <div class="col-12 ">
                <div class="shop_grid_product_area">
                    <div class="row">
                        <div class="col-12">
                            <div class="product-topbar d-flex align-items-center justify-content-between">
                                <!-- Total Products -->
                                <div class="total-products">
                                    <p><@spring.message "products.page.found"/> <span id="size">0</span> <@spring.message "products.page.products"/></p>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="row" id="products">


                    </div>
                </div>
            </div>
        </div>
    </div>
</section>


</body>
</html>