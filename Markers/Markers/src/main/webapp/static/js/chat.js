function sendMessage(userId, text) {
    let body = {
        userId: userId,
        text: text
    };

    $.ajax({
        url: "/messages",
        method: "POST",
        data: JSON.stringify(body),
        contentType: "application/json",
        dataType: "json",
        complete: function () {
            if (text === 'Login') {
                receiveMessage(userId)
            }
        }
    });
}

// LONG POLLING
function receiveMessage(userId) {
    $.ajax({
        url: "/messages?userId=" + userId,
        method: "GET",
        dataType: "json",
        contentType: "application/json",
        success: function (response) {
            response.forEach(function (element) {
                $('#messages').first().after('<li class="list-group-item">' + element['text'] + '</li>');
            });
            receiveMessage(userId);
        }
    })
}