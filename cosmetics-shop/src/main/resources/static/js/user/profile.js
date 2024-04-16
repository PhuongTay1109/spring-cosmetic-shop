document.addEventListener("DOMContentLoaded", async function () {
	form = document.querySelector("#profile-form")
	const imageInput = document.querySelector('#image-input');
	const imagePreview = document.querySelector('#image-preview');
	// Function to reset image preview to original avatar

	// Add event listener to clear file input when modal is closed
	$('#edit-image').on('hidden.bs.modal', function () {
		$('#image-input').val('');
		imagePreview.src = originalAvatar;
	});

	imageInput.addEventListener('change', function () {
		const file = this.files[0];
		console.log(file)
		if (file) {
			const reader = new FileReader();
			reader.onload = function (e) {
				// const img = new Image();
				imagePreview.src = e.target.result;
				console.log(e.target);
				imagePreview.classList.add('rounded-circle', 'mt-2'); // Optional: Add Bootstrap img-fluid class for responsive images
				imagePreview.style.width = '150px'
				// imagePreview.innerHTML = ''; // Clear previous preview
				// imagePreview.appendChild(img);
			}
			reader.readAsDataURL(file);
		}
	});

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
			const res = await fetch('/profile', {
				method: 'PUT',
				body: JSON.stringify({ userId, firstName, lastName, phone, address }),
				headers: { 'Content-Type': 'application/json' }
			})

			const data = await res.json();
			console.log(data);

			if (!data.isValid) {
				document.querySelector('.firstName-error').innerHTML = data.firstName ? data.firstName : '';
				document.querySelector('.lastName-error').innerHTML = data.lastName ? data.lastName : '';
				document.querySelector('.phone-error').innerHTML = data.phone ? data.phone : '';
			} else {
				location.assign('/profile?update_success');
			}

		} catch (e) {
			throw e;
		}

	})


})
