// 신고 데이터를 가져오는 함수
function fetchReports() {
    fetch('/admin/reports') // 서버의 API 엔드포인트
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to fetch reports');
            }
            return response.json(); // JSON 형식으로 파싱된 응답 반환
        })
        .then(reports => {
            console.log('Received reports:', reports);
            displayReports(reports); // 신고 데이터를 테이블에 표시하는 함수 호출
        })
        .catch(error => {
            console.error('Fetch error:', error);
            // 에러 처리 로직
        });
}

// 신고 데이터를 테이블에 표시하는 함수
function displayReports(reports) {
    const tableBody = document.getElementById('report-tablebody');
    tableBody.innerHTML = ''; // 기존 테이블 내용을 지움

    reports.forEach(report => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${report.id}</td>
            <td>${report.title}</td>
            <td>${report.reason}</td>
            <td>${report.username}</td>
            <td>${report.content}</td>
            <td>${report.date}</td>
            <td>
                <select data-id="${report.id}">
                    <option value="pending" ${report.status === 'pending' ? 'selected' : ''}>대기 중</option>
                    <option value="resolved" ${report.status === 'resolved' ? 'selected' : ''}>처리 완료</option>
                </select>
            </td>
        `;
        tableBody.appendChild(row);
    });

    // 상태 변경 이벤트 추가
    tableBody.querySelectorAll('select').forEach(select => {
        select.addEventListener('change', updateReportStatus);
    });
}

// 신고 상태를 업데이트하는 함수
function updateReportStatus(event) {
    const select = event.target;
    const reportId = select.dataset.id;
    const newStatus = select.value;

    fetch(`/admin/reports/${reportId}`, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json',
            'X-CSRF-Token': document.querySelector('input[name="_csrf"]').value // CSRF 토큰
        },
        body: JSON.stringify({ status: newStatus })
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Failed to update report status');
        }
        return response.json();
    })
    .then(data => {
        console.log('Report status updated:', data);
        alert('신고 상태가 업데이트되었습니다.');
    })
    .catch(error => {
        console.error('Update error:', error);
        alert('신고 상태 업데이트 중 오류가 발생했습니다.');
    });
}

// 페이지 로드 후 신고 데이터 가져오기
window.addEventListener('load', () => {
    fetchReports(); // 페이지 로드 시 신고 데이터 요청
});
