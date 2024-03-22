function showReviewForm() {
    document.getElementById("showReviewForm").style.display = "block";
}

function closeReviewForm() {
    document.getElementById("showReviewForm").style.display = "none";
}

document.getElementById("showReviewFormBtn").addEventListener("click", showReviewForm);

document.getElementById("closeReviewFormBtn").addEventListener("click", closeReviewForm);