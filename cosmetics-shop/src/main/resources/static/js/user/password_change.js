
const form = document.getElementById("change-password-form");

document.addEventListener("DOMContentLoaded", async function(event) {	
	await updateCartNumber();
    form.addEventListener('submit', async function (e) {
        e.preventDefault();
        let isValid = true
        console.log(isValid)
        document.querySelector('.currentPassword-error').innerHTML = ''
        document.querySelector('.newPassword-error').innerHTML = ''
        document.querySelector('.confirmPassword-error').innerHTML = ''
        const currentPassword = form.querySelector('input[name="currentPassword"]').value;
        const newPassword = form.querySelector('input[name="newPassword"]').value;
        const confirmPassword = form.querySelector('input[name="confirmPassword"]').value;
        try {
            const res = await fetch('/profile/password/change',{
                method: 'PUT',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({currentPassword, newPassword, confirmPassword})
            })
            const data= await res.json();
            console.log(data)
            if (!data.isValid) {
                document.querySelector('.currentPassword-error').innerHTML =  data.currentPassword ? data.currentPassword: '' 
				document.querySelector('.newPassword-error').innerHTML =  data.newPassword ? data.newPassword: '' 
				document.querySelector('.confirmPassword-error').innerHTML =  data.confirmPassword ? data.confirmPassword: ''  
            } else {
				location.assign('/profile/password/change?update_success');
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