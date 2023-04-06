//비밀번호 확인 검증
const passwordInput = document.getElementById("newPassword");
const passwordInputError = document.getElementById("passwordError");
const passwordConfirmInput = document.getElementById("newPasswordConfirm");
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

const myPasswordForm = document.getElementById("mypassword-form");
myPasswordForm.addEventListener("submit", function(event) {
    event.preventDefault();
    const password = document.getElementById("password").value;
    const newPassword = document.getElementById("newPassword").value;
    const newPasswordConfirm = document.getElementById("newPasswordConfirm").value;

    if(newPassword!=newPasswordConfirm){
        event.stopPropagation();
        myPasswordForm.classList.add("was-validated");
        return;
    }

    if (myPasswordForm.checkValidity()) {
        const url = '/api/myinfo/password';
        const data = {
            password: password,
            newPassword: newPassword
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
                throw new Error("비밀번호 변경 실패");
            }
        }).then(data => {
            console.log(data)
            alert(data.name +"님 비밀번호 변경 성공");
            location.href = '/';
        }).catch(error => {
            alert(error.message);
        });

    } else {
        event.stopPropagation();
        myPasswordForm.classList.add("was-validated");
    }
});