import { fetchProvince, fetchDistrict, fetchWard } from "../common/VietnamProvincesAPI.js";

// fetchProvince()
fetchDistrict(1)
fetchWard(1,1)

let orderList;

(() => {
	'use strict'

	// Fetch all the forms we want to apply custom Bootstrap validation styles to
	const forms = document.querySelectorAll('.needs-validation')

	// Loop over them and prevent submission
	Array.from(forms).forEach(form => {
		form.addEventListener('submit', event => {
			if (!form.checkValidity()) {
				event.preventDefault()
				event.stopPropagation()
			}

			form.classList.add('was-validated')
		}, false)
	})
})()

const orderListElement = document.getElementById('order-list');
let html = '';

document.addEventListener("DOMContentLoaded", async () => {
	orderList = await fetchData();

	var totalPrice = 0;
	var totalQuantity = 0;

	orderList.forEach(item => {
		const product = item.product;
		const quantity = item.quantity;

		totalPrice += item.quantity * item.product.price;
		totalQuantity += item.quantity;

		thisProductPrice = item.quantity * item.product.price;

		// Limit the length of description
		const truncatedDescription = truncateDescription(product.description);

		html += `
				    <li class="list-group-item d-flex justify-content-between lh-sm">
				        <div class="d-flex align-items-center">
				            <a href="/product/${item.product.id}">
				                <img class="order-product-image" src="${item.product.imageLink}">
				            </a>                                    
				            <div>
				                <a class="text-decoration-none link-primary p-0" 
				                    href="/product/${item.product.id}">
				                    <h6 class="my-0">${item.product.name}</h6>
				                </a>
				                <small class="text-body-secondary">${truncatedDescription}</small>
				            </div>
				        </div>
				        <span class="text-body-secondary align-self-center" style="justify-self: flex-end;">
				            ${quantity} x $${product.price} = $${thisProductPrice}
				        </span>
				    </li>`;

	});

	html += `<li class="list-group-item d-flex justify-content-between">
              <span style="font-size:20px"><strong>Total (USD)</strong></span>
              <strong style="font-size:20px">$${totalPrice}</strong>
            </li>`

	orderListElement.innerHTML = html;

	document.getElementById('order-list-quantity').innerText = totalQuantity;

	// ORDER BUTTON CLICK
	var orderButton = document.querySelector('button[type="submit"]');
	var paymentMethodRadio = document.querySelectorAll('input[name="paymentMethod"]');
	var name = document.getElementById('recipientName');
	var phone = document.getElementById('phoneNumber');


	orderButton.addEventListener("click", (event) => {
		event.preventDefault();

		let paymentMethodId;
		paymentMethodRadio.forEach((radio) => {
			if (radio.checked) {
				if (radio.id === 'cash') {
					paymentMethodId = 0;
				} else if (radio.id === 'bank') {
					paymentMethodId = 1;
				}
			}
		});


		let data = {};
		data.name = name.value;
		data.phone = phone.value;
		data.paymentMethodId = paymentMethodId;
		data.orderList = orderList;

		let jsonData = JSON.stringify(data); 
		
		console.log(jsonData);

		let encodedData = btoa(unescape(encodeURIComponent(jsonData))); 

		//window.location.href = '/order?cost=' + totalPrice + '&data=' + encodedData;
	});
});

function truncateDescription(description) {
	const maxLength = 25;
	if (description.length > maxLength) {
		return description.substring(0, maxLength) + '...';
	}
	return description;
}


async function fetchData() {
	const response = await fetch(`/api/cart`);
	const data = await response.json();
	return data;
}