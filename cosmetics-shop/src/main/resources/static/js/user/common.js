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

function generateStars(rating) {
	let fullStars = Math.floor(rating); // Get the number of full stars
	let halfStar = rating - fullStars >= 0.5; // Check if there's a half star

	let stars = '';

	// Generate full stars
	for (let i = 0; i < fullStars; i++) {
		stars += '<i class="bi bi-star-fill" style="color: gold;"></i>';
	}

	// Generate half star if needed
	if (halfStar) {
		stars += '<i class="bi bi-star-half" style="color: gold;"></i>';
		fullStars++; // Increment the count of full stars
	}

	// Generate empty stars to fill up the remaining space (if any)
	for (let i = fullStars; i < 5; i++) {
		stars += '<i class="bi bi-star" style="color: gold;"></i>';
	}

	return stars;
}