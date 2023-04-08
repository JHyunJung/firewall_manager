const myipform = document.getElementById("myip-form");
myipform.addEventListener("submit", function(event) {
    event.preventDefault();
    const devIp = document.getElementById("devIp").value;
    const netIp = document.getElementById("netIp").value;
    if (devIp === netIp) {
        alert("개발망 IP주소와 인터넷망 IP주소가 동일합니다.");
        return;
    }
    if (myipform.checkValidity()) {
        const url = '/api/myinfo/ip';
        const data = {
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
                throw new Error("IP 변경 실패");
            }
        }).then(data => {
            alert(data.name +"님 IP 수정 성공");
            location.href = '/myinfo/ip';
        }).catch(error => {
            alert(error.message);
        });

    } else {
        event.stopPropagation();
        myipform.classList.add("was-validated");
    }
});