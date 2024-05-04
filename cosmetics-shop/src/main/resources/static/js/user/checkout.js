/*import { fetchProvince, fetchDistrict, fetchWard } from "../common/VietnamProvincesAPI.js";

// fetchProvince()
fetchDistrict(1)
fetchWard(1,1) */

const productIdElement = document.getElementById("product-buynow");
const productIdBuyNow = productIdElement.getAttribute("data-product-buynow");
const productQuantityBuyNowElement = document.getElementById("quantity-buynow");
const quantityBuyNow = productQuantityBuyNowElement.getAttribute("data-quantity-buynow");



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

let orderList;
const orderListElement = document.getElementById('order-list');
let html = '';
let productBuyNow = null;
let totalPrice = 0;
let totalQuantity = 0;
document.addEventListener("DOMContentLoaded", async () => {
	if (productIdBuyNow != null) {

		productBuyNow = await fetchProduct();
		totalPrice = productBuyNow.price * quantityBuyNow;
		totalQuantity = quantityBuyNow;
		const truncatedDescription = truncateDescription(productBuyNow.description);
		html += `
				    <li class="list-group-item d-flex justify-content-between lh-sm">
				        <div class="d-flex align-items-center">
				            <a href="/product/${productBuyNow.id}">
				                <img class="order-product-image" src="${productBuyNow.imageLink}">
				            </a>                                    
				            <div>
				                <a class="text-decoration-none link-primary p-0" 
				                    href="/product/${productBuyNow.id}">
				                    <h6 class="my-0">${productBuyNow.name}</h6>
				                </a>
				                <small class="text-body-secondary">${truncatedDescription}</small>
				            </div>
				        </div>
				        <span class="text-body-secondary align-self-center" style="justify-self: flex-end;">
				            ${totalQuantity} x $${productBuyNow.price} = $${totalPrice}
				        </span>
				    </li>
		   			<li class="list-group-item d-flex justify-content-between">				    
		              <span style="font-size:20px"><strong>Total (USD)</strong></span>
		              <strong style="font-size:20px">$${totalPrice}</strong>
		            </li>`

		orderListElement.innerHTML = html;
		document.getElementById('order-list-quantity').innerText = totalQuantity;
	}
	else {
		orderList = await fetchData();

		orderList.forEach(item => {
			const product = item.product;
			const quantity = item.quantity;

			totalPrice += item.quantity * item.product.price;
			totalQuantity += item.quantity;

			var thisProductPrice = item.quantity * item.product.price;

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
	}

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
		if (productBuyNow != null) {
			data.name = name.value;
			data.phone = phone.value;
			data.paymentMethodId = paymentMethodId;
			data.orderList = [{ product: productBuyNow, productId: productIdBuyNow, quantity: quantityBuyNow }];
			data.address = "";

			console.log(data);

			let jsonData = JSON.stringify(data);
			let encodedData = btoa(unescape(encodeURIComponent(jsonData)));
			
			if (data.paymentMethodId == 0)
				window.location.href = '/buynow/order?cost=' + totalPrice + '&data=' + encodedData;
			if (data.paymentMethodId == 1)
				window.location.href = '/vnpayment/create_payment?cost=' + totalPrice + '&data=' + encodedData +'&orderType=buynow';
		}
		else {
			data.name = name.value;
			data.phone = phone.value;
			data.paymentMethodId = paymentMethodId;
			data.orderList = orderList;
			data.address = "";

			let jsonData = JSON.stringify(data);
			let encodedData = btoa(unescape(encodeURIComponent(jsonData)));

			if (data.paymentMethodId == 0)
				window.location.href = '/order?cost=' + totalPrice + '&data=' + encodedData;
			if (data.paymentMethodId == 1)
				window.location.href = '/vnpayment/create_payment?cost=' + totalPrice + '&data=' + encodedData + '&orderType=cart';
		}
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

async function fetchProduct() {
	const response = await fetch(`/api/product/${productIdBuyNow}`);
	const data = await response.json();
	return data;
}