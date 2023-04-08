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

function resetPassword(){
    // var password = prompt("\'"+memberName+"\' 초기화 비밀번호 입력 (4자 이상)");
    // if(password === null) {
    //     alert("취소하셨습니다.");
    //     return;
    // }else if(password.length < 4){
    //     alert("초기화 비밀번호는 4자 이상 입력해주세요.")
    //     return;
    // }

    if(!confirm("비밀번호 초기화 진행하시겠습니까?")){
        alert("취소하셨습니다.");
        return;
    }

    const url = '/api/member/resetPassword/'+id;

    fetch(url, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json; charset=utf-8"
        }
    }).then(response => {
        if (response.ok) {
            return response.json();
        } else {
            throw new Error("비밀번호 초기화 실패");
        }
    }).then(data => {
        console.log(data)
        alert(data.name +"님 비밀번호 초기화 성공 (*********) ");
        location.href = '/members';
    }).catch(error => {
        alert(error.message);
    });
}