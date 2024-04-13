/**
 * 
 */

let fetchedData;

document.addEventListener("DOMContentLoaded", async function() {
	//await updateCartNumber();
	
	fetchedData = await fetchData();
	
	console.log(fetchedData);
});

async function fetchData() {
	const response = await fetch(`/api/cart`);
	const data = await response.json();
	return data;
}

function changeQuantity(btn) {
	var input = document.getElementById('quantity');
	var currentValue = parseInt(input.value);
	var minValue = parseInt(input.min);
	var maxValue = parseInt(input.max);

	if (btn.getAttribute('data-type') === 'minus') {
		if (currentValue > minValue) {
			input.value = currentValue - 1;
		}
	} else if (btn.getAttribute('data-type') === 'plus') {
		if (currentValue < maxValue) {
			input.value = currentValue + 1;
		}
	}
}

window.onload = function() {
	var quantities = document.querySelectorAll('.input-number');
	var totalQuantity = 0;
	for (var i = 0; i < quantities.length; i++) {
		totalQuantity += parseInt(quantities[i].value);
	}
	document.getElementById('totalQuantity').innerText = totalQuantity;
};