// 게시글 데이터를 가져오는 함수
function fetchPosts() {
    fetch('/board/index') // 서버의 API 엔드포인트
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to fetch posts');
            }
            return response.json(); // JSON 형식으로 파싱된 응답 반환
        })
        .then(posts => {
            // 게시글 데이터를 받아와서 처리
            console.log('Received posts:', posts);

            // 게시글 데이터를 테이블에 추가하는 예시
            displayPosts(posts); // 게시글을 테이블에 표시하는 함수 호출
        })
        .catch(error => {
            console.error('Fetch error:', error);
            // 에러 처리
        });
}

// 게시글을 테이블에 표시하는 함수 예시 (실제로는 해당 함수를 구현해야 합니다)
function displayPosts(posts) {
    // 게시글 데이터를 테이블에 표시하는 로직을 구현
    const tableBody = document.getElementById('article-tablebody');

    // 게시글 데이터를 테이블에 삽입
    posts.forEach(post => {
        const row = tableBody.insertRow();
        row.innerHTML = `
            <tr class="post">
                <td><a href="/board/article">${post.title}</td>
                <td>${post.userName}</td>
                <td>${post.timeStamp}</td>
                <td>${post.viewCount}</td>
            </tr>
        `;//나중에 링크 뒤에 게시글번호 추가
    });
}

//페이지 정보 가져오기
function fetchPageNation(){
    fetch('/board/pageNation').then(response=>{
        if(!response.ok){
            throw new Error('failed to get pagination')
        }
        return response.json();
    }).then(data=>{
        console.log('Recieved Pagination');
        displayPaginagion(data.start, data.end);
    })
        .catch(error => {
            console.error('Fetch error');
        })
}
active=1;
function displayPaginagion(start, end){
    const pageNation = document.getElementById('pagination');

    for(i=start;i<=end;i++){
        const row = pageNation.insertRow();
        if(i===active)
            row.innerHTML='<a href="#" class="active">${i}</a>'
        else
            row.innerHTML='<a href="#">1</a>'
        //나중에 링크를 /board?p=${i}로 변경
    }

    const endrow=pageNation.insertRow();
    endrow.innerHTML=
        '            <a href="#">▶</a>\n' +
        '            <a href="#">▶▶</a>'
}

// 페이지 로드 후 게시글 데이터 가져오기
window.addEventListener('load', () => {
    fetchPosts(); // 페이지 로드 시 게시글 데이터 요청
    fetchPageNation();
});