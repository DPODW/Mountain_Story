$(document).ready(function() {
    $("#memberDeleteWarn").click(function (event) {
        event.preventDefault();
        if (confirm("회원 탈퇴시 작성한 후기가 모두 삭제됩니다. 계속하시겠습니까?")) {
            $.ajax({
                url: "/member/delete",
                type: "DELETE",
                async: true,
                success: function (response) {
                    alert("회원 탈퇴가 완료되었습니다.");
                    window.location.href = "/home";
                    //DELETE 요청으로 전송한 다음,  window.location.href = "/home"; 으로 재요청을 하면
                    //요청 메소드 DELETE 방식으로 재요청이된다. ( /home 요청이 delete 방식으로 가게됌)
                    //그래서 컨트롤러에서 반환 방식을 변경해주어야 한다.
                },
                error: function (error) {
                    alert("알 수 없는 에러 발생");
                }
            });
        }
    });
});