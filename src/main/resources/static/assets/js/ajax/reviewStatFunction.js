$(document).ready(function() {
    $(".reviewGoodBtn").click(function(event) {
        const reviewNumber = $(this).siblings(".OnlyjQueryReviewNumber").val();
        const mountainIndex = $("#mountainIndex").val();
        console.log(reviewNumber)
        event.preventDefault();
        $.ajax({
            url: "/mountain/info/review/rating/"+reviewNumber+"/good",
            type: "POST",
            async: true,
            success: function(response) {
                 if(mountainIndex !=null){
                     alert("해당 리뷰를 추천하였습니다. 새로고침이 이루어집니다.");
                     window.location.href = "/mountain/info/search/one/"+mountainIndex
                 }else if(mountainIndex==null){
                     alert("해당 리뷰를 추천하였습니다. 새로고침이 이루어집니다.");
                     window.location.href = "/member/review/history"
                 }

            },
            error: function(error) {
                alert("이미 해당 게시글 평가를 완료했습니다.");

            }
        });
    });


    $(".reviewBadBtn").click(function(event) {
        const reviewNumber = $(this).siblings(".OnlyjQueryReviewNumber").val();
        const mountainIndex = $("#mountainIndex").val();
        console.log(reviewNumber);
        event.preventDefault();
        $.ajax({
            url: "/mountain/info/review/rating/"+reviewNumber+"/bad",
            type: "POST",
            async: true,
            success: function(response) {
                if(mountainIndex !=null){
                    alert("해당 리뷰를 추천하였습니다. 새로고침이 이루어집니다.");
                    window.location.href = "/mountain/info/search/one/"+mountainIndex
                }else if(mountainIndex==null){
                    alert("해당 리뷰를 추천하였습니다. 새로고침이 이루어집니다.");
                    window.location.href = "/member/review/history"
                }
            },
            error: function(error) {
                alert("이미 해당 게시글 평가를 완료했습니다.");
            }
        });
    });

});
