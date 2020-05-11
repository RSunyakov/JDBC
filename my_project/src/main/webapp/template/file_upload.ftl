<!doctype html>
<#import "spring.ftl" as spring />
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <script
            src="https://code.jquery.com/jquery-3.4.1.js"
            integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
            crossorigin="anonymous"></script>
    <title>Document</title>
    <script>


        function sendFile() {
            var form = $("#post_form")[0];
            var data = new FormData(form);
            $.ajax({
                type: "POST",
                enctype: 'multipart/form-data',
                url: "/files",
                data: data,
                processData: false,
                contentType: false,
                cache: false,
                timeout: 600000
            })
                .done(function (response) {
                    alert(response)
                })
                .fail(function () {
                    alert('Error')
                });
        }
    </script>
</head>
<body>
<div>
    <form name="post_form" id="post_form" enctype="multipart/form-data">
        <div id="namer">
            <div id="namer-input">
                <input type="file" id="file" name="file" placeholder="Choose your file"/>
            </div>
        </div>
        <button type="button" class="btn btn-danger btn-lg" id="test_ajax" onclick="sendFile()" value="Load file">Load file
        </button>
    </form>
</div>
</body>
</html>