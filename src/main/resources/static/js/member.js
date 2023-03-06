let divMenu = document.querySelector(".ip-menu-div");
let netMenu = document.querySelector(".ip-menu-net");

function divIpClick(e) {
    document.querySelector(".devIp").value = e.target.innerText;
    document.querySelector(".devBtn").innerText = e.target.innerText;
}

function netIpClick(e) {
    document.querySelector(".netIp").value = e.target.innerText;
    document.querySelector(".netBtn").innerText = e.target.innerText;
}

divMenu.addEventListener("click",divIpClick)
netMenu.addEventListener("click",netIpClick)