const emailNotOkMsg = document.getElementById("emailNotOkMsg");
//중복 회원 검증
function checkEmail(){
    let email = document.getElementById("email").value;
    if(email){
        $.ajax({
            url: '/signup/checkDuplicateEmail',
            type: 'GET',
            data: { 'email': email },
            success: function(data) {
                if(data.result === true) {
                    console.log("중복된 아이디(이메일)")
                    emailNotOkMsg.style.display = "block";
                    document.getElementById("email").value = "";
                    document.getElementById("email").focus();
                }else {
                    console.log("사용가능한 아이디(이메일)")
                    emailNotOkMsg.style.display = "none";
                }
            }
        });
    }
}
//비밀번호 확인 검증

const passwordInput = document.getElementById("password");
const passwordInputError = document.getElementById("passwordError");
const passwordConfirmInput = document.getElementById("passwordConfirm");
const passwordConfirmError = document.getElementById("passwordConfirmError");

function validatePasswordConfirm() {
    if (passwordInput.value.length < 8 || passwordInput.value.length > 16) {
        passwordInputError.classList.add("is-invalid");
        passwordInputError.style.display = "block";
    } else {
        passwordInputError.classList.remove("is-invalid");
        passwordInputError.style.display = "none";
    }

    if (passwordInput.value !== passwordConfirmInput.value) {
        passwordConfirmInput.classList.add("is-invalid");
        passwordConfirmError.style.display = "block";
    } else {
        passwordConfirmInput.classList.remove("is-invalid");
        passwordConfirmError.style.display = "none";
    }
}

passwordInput.addEventListener("input", validatePasswordConfirm);
passwordConfirmInput.addEventListener("input", validatePasswordConfirm);


//
// function checkIpAddress(ipType){
//     let ipAddress = document.getElementById(ipType).value;
//     let ipNotOkMsg = document.getElementById(ipType+"NotOkMsg");
//     if(ipAddress){
//         console.log('ipType : '+ipType);
//         console.log('ipAddress : '+ipAddress);
//         $.ajax({
//             url: '/signup/checkDuplicateIpAddress',
//             type: 'GET',
//             data: { 'ipAddress': ipAddress },
//             success: function(data) {
//                 if(data.result === true) {
//                     console.log("이미 등록된 IP주소")
//                     ipNotOkMsg.style.display = "block";
//                     document.getElementById(ipType).value = "";
//                     document.getElementById(ipType).focus();
//                 }else {
//                     console.log("사용가능한 IP주소")
//                     ipNotOkMsg.style.display = "none";
//                 }
//             }
//         });
//
//         if (document.getElementById('devIp').value === document.getElementById('netIp').value){
//             document.getElementById("netIpNotOkMsg").style.display = "block";
//             document.getElementById('netIp').value = "";
//             document.getElementById('netIp').focus();
//         }
//     }
//
//
// }