
const relatedProductsSeperate = document.querySelector("#related-products-seperate");
const product_id = relatedProductsSeperate.getAttribute("data-id");
async function handlePageClick(id, page) {
	const products = await fetch(`/api/related_products?id=${id}&page=${page}`, 
	{
		method: 'GET',
		headers: {
			"Content-type" : "application/json"
		}
	}).then(response => response.json()).then(data => data);
	console.log(products)
	
	console.log(product_id)
	let html = "";
	for (let product of products) {
		html +=`
		<div class="col-md-3 mb-4">
				<div class="card position-relative">
						<div class="position-absolute top-0 end-0 mt-2 me-2">
							<i class="bi bi-heart" style="font-size: 1.5rem;"></i>
						</div>
						<div class="position-absolute top-0 end-0 mt-5 me-2">
							<i class="bi bi-cart" style="font-size: 1.5rem;"></i>
						</div>
						<a class="related-product-img" href="/product/${product.id}">
							<img src="${product.imageLink}" style="height: 300px; width:100%" class="card-img-top" />
						</a>
						
						<div class="card-body">
							<h5 class="card-title fs-6">${product.name}</h5>
							<p class="card-text">${product.price}</p>
							<a href="#" class="btn btn-primary">Buy Now</a>
						</div>
					</div>
		</div>`
	}
	relatedProductsSeperate.insertAdjacentHTML("afterend",html)
	return data;
}
handlePageClick(product_id,4)