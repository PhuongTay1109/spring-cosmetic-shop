
const form = document.getElementById("reset-password-form");

document.addEventListener("DOMContentLoaded", async function(event) {	
    form.addEventListener('submit', async function (e) {
        e.preventDefault();
        let isValid = true
        console.log(isValid)
        document.querySelector('.newPassword-error').innerHTML = ''
        document.querySelector('.confirmPassword-error').innerHTML = ''
        const newPassword = form.querySelector('input[name="newPassword"]').value;
        const userId = form.querySelector('input[name="userId"]').value;
        const confirmPassword = form.querySelector('input[name="confirmPassword"]').value;
        try {
            const res = await fetch('/reset_password',{
                method: 'PUT',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({userId, newPassword, confirmPassword})
            })
            const data= await res.json();
            console.log(data)
            if (!data.isValid) {
				document.querySelector('.newPassword-error').innerHTML =  data.newPassword ? data.newPassword: '' 
				document.querySelector('.confirmPassword-error').innerHTML =  data.confirmPassword ? data.confirmPassword: ''  
            } else {
				location.assign('/reset_password?success');
			} 
        } catch(e) {
            throw e;
        }
    })
})

function hideAndShowPass(icon) {
    var passwordField = icon.parentNode.querySelector("input");
    if (passwordField.type === "password") {
        passwordField.type = "text";
        icon.classList.remove("fa-eye-slash");
        icon.classList.add("fa-eye");
    } else {
        passwordField.type = "password";
        icon.classList.remove("fa-eye");
        icon.classList.add("fa-eye-slash");
    }
}