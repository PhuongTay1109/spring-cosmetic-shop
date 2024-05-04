import { fetchProvince, fetchDistrict, fetchWard } from "../common/VietnamProvincesAPI.js";

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
	populateProvinceDropdown();

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
	var address = document.getElementById('address');
	const provinceDropdown = document.getElementById('province');
	const districtDropdown = document.getElementById('district');
	const wardDropdown = document.getElementById('ward');


	orderButton.addEventListener("click", (event) => {
		event.preventDefault();

		const selectedProvince = provinceDropdown.options[provinceDropdown.selectedIndex].text;
		const selectedDistrict = districtDropdown.options[districtDropdown.selectedIndex].text;
		const selectedWard = wardDropdown.options[wardDropdown.selectedIndex].text;

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
			data.address = `${address.value}, ${selectedWard}, ${selectedDistrict}, ${selectedProvince}`;

			console.log(data);

			let jsonData = JSON.stringify(data);
			let encodedData = btoa(unescape(encodeURIComponent(jsonData)));

			if (data.paymentMethodId == 0)
				window.location.href = '/buynow/order?cost=' + totalPrice + '&data=' + encodedData;
			if (data.paymentMethodId == 1)
				window.location.href = '/vnpayment/create_payment?cost=' + totalPrice + '&data=' + encodedData + '&orderType=buynow'; 
		}
		else {
			data.name = name.value;
			data.phone = phone.value;
			data.paymentMethodId = paymentMethodId;
			data.orderList = orderList;
			data.address = `${address.value}, ${selectedWard}, ${selectedDistrict}, ${selectedProvince}`;

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

// Function to populate dropdown with provinces
async function populateProvinceDropdown() {
	const provinceDropdown = document.getElementById('province');

	// Fetch provinces
	const provinces = await fetchProvince();

	// Add fetched provinces as options
	provinces.forEach(province => {
		const option = document.createElement('option');
		option.textContent = province.PROVINCE_NAME;
		option.value = province.PROVINCE_ID;
		provinceDropdown.appendChild(option);
	});
}

// Function to populate district dropdown based on selected province
async function populateDistrictDropdown(provinceId) {
	const districtDropdown = document.getElementById('district');

	// Fetch districts based on province ID
	const districts = await fetchDistrict(provinceId);

	districtDropdown.innerHTML = '';

	const defaultOption = document.createElement('option');
	defaultOption.value = '';
	defaultOption.textContent = 'Choose...';
	districtDropdown.appendChild(defaultOption);

	// Add fetched districts as options
	districts.forEach(district => {
		const option = document.createElement('option');
		option.value = district.DISTRICT_ID;
		option.textContent = district.DISTRICT_NAME;
		districtDropdown.appendChild(option);
	});
}

// Function to populate ward dropdown based on selected province and district
async function populateWardDropdown(districtId) {
	const wardDropdown = document.getElementById('ward');

	// Fetch wards based on province ID and district ID
	const wards = await fetchWard(districtId);

	wardDropdown.innerHTML = '';

	const defaultOption = document.createElement('option');
	defaultOption.value = '';
	defaultOption.textContent = 'Choose...';
	wardDropdown.appendChild(defaultOption);

	// Add fetched wards as options
	wards.forEach(ward => {
		const option = document.createElement('option');
		option.textContent = ward.WARDS_NAME;
		wardDropdown.appendChild(option);
	});
}

function handleProvinceChange() {
	const provinceDropdown = document.getElementById('province');
	const selectedProvinceId = provinceDropdown.value;

	populateDistrictDropdown(selectedProvinceId);
}

// Function to handle province or district selection change
function handleDistrictChange() {
	const districtDropdown = document.getElementById('district');
	const selectedDistrictId = districtDropdown.value;

	// Call populateWardDropdown with selected province ID and district ID
	populateWardDropdown(selectedDistrictId);
}

document.getElementById('province').addEventListener('change', handleProvinceChange);
document.getElementById('district').addEventListener('change', handleDistrictChange);
