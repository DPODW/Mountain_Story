document.write('<script src="/js/ajax/checkReviewContent.js"></script>');

$(document).ready(function() {
    $("#memberAccessCheck").click(function (event) {
        event.preventDefault();
        $.ajax({
            url: "/member/access/check",
            type: "POST",
            async: true,
            success: function (response) {
                reviewContentCheck();
            },
            error: function (error) {
                alert("회원이 아닙니다!");
            }
        });
    });
});