  function reviewGoodFunction(reviewNumber){
        const nowUrl = window.location.href;
        $.ajax({
            url: "/mountain/info/review/rating/"+reviewNumber+"/true",
            type: "POST",
            async: true,
            success: function(response) {
                alert("해당 리뷰를 추천했습니다. 새로고침이 이루어 집니다");
                window.location.href = nowUrl
            },
            error: function(error) {
                alert("이미 해당 게시글 평가를 완료했습니다.");
            }
        });
  }



    function reviewBadFunction(reviewNumber){
        const nowUrl = window.location.href;
        $.ajax({
            url: "/mountain/info/review/rating/"+reviewNumber+"/false",
            type: "POST",
            async: true,
            success: function(response) {
                alert("해당 리뷰를 비추천했습니다. 새로고침이 이루어 집니다");
                window.location.href = nowUrl
            },
            error: function(error) {
                alert("이미 해당 게시글 평가를 완료했습니다.");
            }
        });
    }

