$(document).ready(function() {
    $("#reviewGoodBtn").click(function(event) {
        const reviewNumber = $("#reviewNumber").val();
        console.log(reviewNumber)
        event.preventDefault();
        $.ajax({
            url: "/mountain/info/review/good/"+reviewNumber,
            type: "POST",
            async: true,
            success: function(response) {
                alert("테스트 작동 성공");

            },
            error: function(error) {
                alert("테스트 작동 실패");

            }
        });
    });
});
