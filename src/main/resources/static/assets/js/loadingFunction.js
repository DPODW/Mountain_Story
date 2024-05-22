function handleSearchButtonClick() {
    document.getElementById("displayImage").style.display = "block";
}

// 입력 필드 입력 이벤트 핸들러 함수 정의
function handleInputChange() {
    var inputValue = document.getElementById("mountainNameInput").value;
    document.getElementById("displayText").innerText = '전국에 있는 모든 ' + inputValue + ' 검색 중 . . .';
}

// 검색 버튼 클릭 이벤트 핸들러 등록
document.getElementById("searchMountainBtn").addEventListener("click", handleSearchButtonClick);

// 입력 필드 입력 이벤트 핸들러 등록
document.getElementById("mountainNameInput").addEventListener("input", handleInputChange);



//산 개별 날씨 확인할시 로딩 화면 출력
document.addEventListener("DOMContentLoaded", function() {
    const elements = document.getElementsByClassName("btn btn-outline-success searchMountainWeather");
    for (let i = 0; i < elements.length; i++) {
        elements[i].addEventListener("click", function(event) {
            document.getElementById("displayImage").style.display = "block";
            document.getElementById("displayText").innerText = '날씨 로딩중. . .';
        });
    }
});