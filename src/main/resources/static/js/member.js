let id = memberId;
const form = document.getElementById("edit-form");
form.addEventListener("submit", (event) => {
    event.preventDefault();
    const role = document.getElementById("role");
    const devIp = document.getElementById("devIp").value;
    const netIp = document.getElementById("netIp").value;
    if (devIp === netIp) {
        alert("개발망 IP주소와 인터넷망 IP주소가 동일합니다.");
        return;
    }
    if (form.checkValidity()) {
        const url = '/api/member/' + id;
        const data = {
            role: role.value,
            devIp: devIp,
            netIp: netIp
        };

        fetch(url, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json; charset=utf-8"
            },
            body: JSON.stringify(data)
        }).then(response => {
            if (response.ok) {
                return response.json();
            } else {
                alert("회원 수정 실패")
            }
        }).then(data => {
                alert(data.name +" 회원 수정 성공");
                location.href = '/members';
            }).catch(error => {
            alert(error);
        });
        
    } else {
        event.stopPropagation();
        form.classList.add("was-validated");
    }
});