document.write('<script src="/ajax/checkReviewContent.js"></script>');
document.write('<script src="/ajax/reviewStatFunction.js"></script>');
document.write('<script src="/ajax/reviewDeleteWarn.js"></script>');

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
    const $modalFooter = $(this).closest('.modal-footer');
    const reviewNumber = $modalFooter.find('.OnlyjQueryReviewNumber').val();
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
        const $modalFooter = $(this).closest('.modal-footer');
        const reviewNumber = $modalFooter.find('.OnlyjQueryReviewNumber').val();
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


$(document).ready(function() {
    $(".checkReviewer").click(function (event) {
        const $modalContent = $(this).closest('.modal-content');
        const reviewNumber = $modalContent.find('.OnlyjQueryReviewNumber').val();
        const reviewerId = $modalContent.find('.OnlyjQueryReviewerId').val();

        event.preventDefault();
        $.ajax({
            url: "/member/reviewer/check",
            data:{
              reviewerId: reviewerId
            },
            type: "POST",
            async: true,
            success: function (response) {
                reviewDeleteFunction(reviewNumber);
            },
            error: function (error) {
                alert("본인이 작성한 후기만 삭제 가능합니다.");
            }
        });
    });
});