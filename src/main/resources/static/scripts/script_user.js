//사용자 페이지 공용 스크립트
var username = document.querySelector('meta[name="meta-username"]').getAttribute("content");
var role = document.querySelector('meta[name="meta-user-role"]').getAttribute("content");
console.log('username : ' + username);
var nav_prof_board = document.getElementById("nav-professionalboard");
if(username){
    var nav_status = document.getElementById("nav-loginstatus");
    nav_status.textContent = username;
    nav_status.href = "/mypage";

    if(role==="ROLE_PROFESSIONAL" || role==="ROLE_ADMIN")
        nav_prof_board.style.display = "block";
}