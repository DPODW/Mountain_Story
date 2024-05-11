$(document).ready(function() {
    $("#reviewSuccessBtn").click(function (event) {
        const reviewTitle = $("#exampleFormControlInput1").val();
        const reviewContent = $("#exampleFormControlTextarea1").val();
        event.preventDefault();
        $.ajax({
            url: "/mountain/info/review/check/content",
            data:{
                reviewTitle:reviewTitle,
                reviewContent:reviewContent
            },
            type: "POST",
            async: true,
            success: function (response) {
                $("#reviewSave").submit();
            },
            error: function (error) {
                alert("작성 규칙 준수 요망");
            }
        });
    });
});
