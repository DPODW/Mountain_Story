document.write('<script src="/ajax/checkReviewContent.js"></script>');
document.write('<script src="/ajax/reviewStatFunction.js"></script>');

$("#reviewAccessCheck").click(function (event) {
        event.preventDefault();
        $.ajax({
            url: "/member/access/check",
            type: "POST",
            async: true,
            success: function (response) {
                reviewContentCheck();
            },
            error: function (error) {
                alert("로그인 한 회원만 후기 작성이 가능합니다.");
            }
        });
    });

$(document).ready(function() {
$(".rateGoodAccessCheck").click(function (event) {
    const reviewNumber = $(this).siblings(".OnlyjQueryReviewNumber").val();
    event.preventDefault();
    $.ajax({
        url: "/member/access/check",
        type: "POST",
        async: true,
        success: function (response) {
            reviewGoodFunction(reviewNumber);
        },
        error: function (error) {
            alert("로그인 한 회원만 후기 작성이 가능합니다.");
        }
    });
  });
});

$(document).ready(function() {
    $(".rateBadAccessCheck").click(function (event) {
        const reviewNumber = $(this).siblings(".OnlyjQueryReviewNumber").val();
        event.preventDefault();
        $.ajax({
            url: "/member/access/check",
            type: "POST",
            async: true,
            success: function (response) {
                reviewBadFunction(reviewNumber);
            },
            error: function (error) {
                alert("로그인 한 회원만 후기 작성이 가능합니다.");
            }
        });
    });
});