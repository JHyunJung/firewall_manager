const member_contents = document.querySelector(".member_contents");

window.addEventListener('DOMContentLoaded', getMemberList);

async function getMemberList() {
    const url ="/firewall/get/member/all"
    const response = await fetch(url, {
        method: 'GET', // *GET, POST, PUT, DELETE, etc.
        mode: 'no-cors', // no-cors, *cors, same-origin
        cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
        credentials: 'same-origin', // include, *same-origin, omit
        headers: {
            'Content-Type': 'application/json' // 'Content-Type': 'application/x-www-form-urlencoded',
        },
        // body: JSON.stringify(data) // body data type must match "Content-Type" header
    })

    response.json().then(data => {
        for (let i = 0; i < data.length; i++) {
            let member = data[i];
            let id = member['id']
            let name = member['name']
            let email = member['email']
            let role = member['role']
            let devIp = member['devIp']
            let netIp = member['netIp']
            addHTML(id,name,email,role,devIp,netIp)
        }
    }).catch( err => {
        console.log(err)
    })
}

function addHTML(id,name,email,role,devIp,netIp) {
    let tempHtml=`<tr>
                      <th scope="col" style="width: 50px;">${id}</th>
                      <th scope="col">${name}</th>
                      <th scope="col">${email}</th>
                      <th scope="col">${role}</th>
                      <th scope="col">${devIp}</th>
                      <th scope="col">${netIp}</th>
                  </tr>`;
    member_contents.innerHTML += tempHtml;
}