<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
<form method="POST" enctype="multipart/form-data" action="/files">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    File to upload: <input type="file" name="file"><br/>
    <input type="submit" value="Press"> to upload the file!
</form>
</body>
</html>