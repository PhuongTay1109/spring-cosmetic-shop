form = document.querySelector("#profile-form")


 
document.addEventListener("DOMContentLoaded", async function() {
    form.addEventListener('submit', async function (e) {
        e.preventDefault();
        let isValid = true
        document.querySelector('.firstName-error').innerHTML = ''
        document.querySelector('.lastName-error').innerHTML = ''
        document.querySelector('.phone-error').innerHTML = ''
        const userId = form.querySelector('input[name="userId"]').value;
        const firstName = form.querySelector('input[name="firstName"]').value;
        const lastName = form.querySelector('input[name="lastName"]').value;
        const phone = form.querySelector('input[name="phone"]').value;        
        const address = form.querySelector('input[name="address"]').value;        
        try {
			const res = await fetch('/admin/profile', {
				method: 'PUT',
				body: JSON.stringify({userId, firstName, lastName, phone, address}),
				headers: {'Content-Type': 'application/json'}
			})
			
			const data= await res.json();
			console.log(data);
			
			if (!data.isValid) {
				document.querySelector('.firstName-error').innerHTML =  data.firstName ? data.firstName : '';
				document.querySelector('.lastName-error').innerHTML =  data.lastName ? data.lastName : '';
				document.querySelector('.phone-error').innerHTML =  data.phone ? data.phone : '';
			} else {
				location.assign('/admin/profile?update_success');
			}
				
		} catch(e) {
			throw e;
		}
        
    })
    
 
})
