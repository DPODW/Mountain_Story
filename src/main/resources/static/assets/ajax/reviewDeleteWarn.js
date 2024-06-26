function reviewDeleteFunction(reviewNumber) {
        const nowUrl = window.location.href;
        if (confirm("후기 삭제시 복구가 불가능 합니다. 계속하시겠습니까?")) {
            $.ajax({
                url: "/mountain/info/review/delete/" + reviewNumber,
                type: "DELETE",
                async: true,
                success: function (response) {
                    alert("삭제되었습니다. 새로고침이 이루어집니다.");
                    window.location.href = nowUrl;
                },
                error: function (error) {
                    alert("알수없는 에러 발생");
                }
            });
        }
}