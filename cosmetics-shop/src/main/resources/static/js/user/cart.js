/**
 * 
 */

let fetchedData;
let totalPrice = 0;

document.addEventListener("DOMContentLoaded", async function() {
	fetchedData = await fetchData();
	
	fetchedData.forEach(item => {
		const product = item.product;
		const quantity = item.quantity;
		
		totalPrice += item.quantity*item.product.price;
		
		thisProductPrice = item.quantity*item.product.price;
		
		// Limit the length of description
        const truncatedDescription = truncateDescription(product.description);
        
		const productElement = document.createElement('div');
		productElement.classList.add('row', 'border-bottom', 'border-top');
		productElement.innerHTML = `		               
                        <div class="row cart-main align-items-center">
                            <div class="col-2">
                            	<a href="/product/${item.product.id}">
									<img src="${item.product.imageLink}" class="cart-img"/>
								</a>
                            </div>
                            <div class="col">
                                <div class="row">
                                	<a class="text-decoration-none link-primary p-0" 
                                		href="/product/${item.product.id}"
                                		style="font-size: 20px">
                                		${item.product.name}
                                	</a>                                	
                                </div>
                                <div class="row text-muted" style="font-size: 14px">${truncatedDescription}</div>
                            </div>
                            <div class="col">
                                <button type="button" class="btn" data-type="minus" data-field="quantity"
									onclick="changeQuantity(this)">
									<i class="bi bi-dash"></i>
								</button>
								<input type="text" name="quantity" id="quantity" class="form-control input-number"
									style="width: 18%; display: inline-block" value="${item.quantity}" min="1" max="100">
								<button type="button" class="btn" data-type="plus" data-field="quantity"
									onclick="changeQuantity(this)">
									<i class="bi bi-plus"></i>
								</button>
                            </div>
                            <div class="col">
                            	<div class="d-flex justify-content-between">
                            		$ ${thisProductPrice}
                            		<a class="trash text-decoration-none text-dark">
                            			<i data-product-id="${item.product.id}" class="bi bi-trash-fill"></i>
                            		</a>
                            	</div>
                            </div>
                        </div>
                    </div>
		`;
		document.getElementById('cart-items').appendChild(productElement);
	})
	
	document.getElementById('total-price').textContent = "TOTAL PRICE: $ "+ totalPrice ;
	document.getElementById('total-price-summary').textContent = "$ "+ totalPrice ;
	console.log(fetchedData);
	
	// DELETE BUTTONS
	const trashIcons = document.getElementsByClassName("trash"); // live DOM => HTMLCollection	
	
	for(let i = 0; i < trashIcons.length; i++) {
		trashIcons[i].addEventListener('click', async () => {
			const trashIcon = trashIcons[i];
			const productId = trashIcon.querySelector('i').getAttribute("data-product-id");
			console.log(productId);
			try {
				const response = await fetch(`/api/cart/delete/${productId}`, {
					method: 'DELETE',
					headers: {
						'Content-Type': 'application/json'
					}					
				});
				
				if(!response.ok) 
					throw new Error("An error occurred while deleting the product.")
				
				window.location.reload();
			}
			catch(error) {
				console.log(error);				
			}
		})
	}
});

function truncateDescription(description) {
    const maxLength = 100; 
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

