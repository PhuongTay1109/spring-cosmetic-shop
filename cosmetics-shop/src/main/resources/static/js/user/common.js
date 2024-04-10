/**
 * 
 */
async function updateCartNumber() {
	try {
		const response = await $.ajax({
			type: 'GET',
			url: '/api/cart'
		});

		let cartNumber = 0;
			response.forEach(function(cartItem) {
	        cartNumber += cartItem.quantity;
	    });
	
	    document.getElementById('cart-number').textContent = cartNumber;
	} 
	catch (error) {
		console.error('Error retrieving cart items: ' + error);
	}
};