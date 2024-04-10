form = document.querySelector("#register-form")
 
document.addEventListener("DOMContentLoaded", async function() {
    console.log(form)
    form.addEventListener('submit', async function (e) {
        e.preventDefault();
        let isValid = true
        document.querySelector('.firstName-error').innerHTML = ''
        document.querySelector('.lastName-error').innerHTML = ''
        document.querySelector('.password-error').innerHTML = ''
        document.querySelector('.username-error').innerHTML = ''
        const firstName = form.querySelector('input[name="firstName"]').value;
        const lastName = form.querySelector('input[name="lastName"]').value;
        const password = form.querySelector('input[name="password"]').value;
        const email = form.querySelector('input[name="email"]').value;
        const phone = form.querySelector('input[name="phone"]').value;
        const address = form.querySelector('input[name="address"]').value;
        const username = form.querySelector('input[name="username"]').value;
        
        try {
			const res = await fetch('auth/register', {
				method: 'POST',
				body: JSON.stringify({firstName, lastName, email, phone, address, username, password}),
				headers: {'Content-Type': 'application/json'}
			})
			
			const data= await res.json();
			console.log(data);
			
			if (!data.isValid) {
				document.querySelector('.firstName-error').innerHTML =  data.firstName ? data.firstName : '';
				document.querySelector('.lastName-error').innerHTML =  data.lastName ? data.lastName : '';
				document.querySelector('.password-error').innerHTML =  data.password ? data.password : '';
				document.querySelector('.username-error').innerHTML =  data.username ? data.username : '';
				document.querySelector('.phone-error').innerHTML =  data.phone ? data.phone : '';
				document.querySelector('.email-error').innerHTML =  data.email ? data.email : '';
			} else {
				location.assign('/login?registered');
			}
				
		} catch(e) {
			throw e;
		}
		
        
        
        console.log(firstName);
        

        // Placeholder for the missing code block
    })
    
 
})

 function hideAndShowPass() {
        var passwordField = document.getElementById("password");
        var eyeIcon = document.querySelector(".eye-icon");

        if (passwordField.type === "password") {
            passwordField.type = "text";
            eyeIcon.classList.remove("fa-eye-slash");
            eyeIcon.classList.add("fa-eye");
        } else {
            passwordField.type = "password";
            eyeIcon.classList.remove("fa-eye");
            eyeIcon.classList.add("fa-eye-slash");
        }
 }