$(document).ready(function() {
    $("#memberDeleteWarn").click(function (event) {
        event.preventDefault();
        if (confirm("회원 탈퇴시 작성한 후기가 모두 삭제됩니다. 계속하시겠습니까?")) {
            $.ajax({
                url: "/member/delete",
                type: "POST",
                async: true,
                success: function (response) {
                    alert("회원 탈퇴가 완료되었습니다.");
                    window.location.href = "/home";
                },
                error: function (error) {
                    alert("알 수 없는 에러 발생");
                }
            });
        }
    });
});