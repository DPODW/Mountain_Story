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