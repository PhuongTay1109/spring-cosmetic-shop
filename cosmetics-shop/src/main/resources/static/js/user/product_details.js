


document.addEventListener("DOMContentLoaded", function (event) {
	const relatedProductsSeperate = document.querySelector("#related-products-seperate");
	const productId = relatedProductsSeperate.getAttribute("data-productId");
	const totalRelatedProducts = relatedProductsSeperate.getAttribute("data-totalRelatedProducts")
	const pageNext = document.querySelector("#page-next")
	const pagePrevious = document.querySelector("#page-previous")
	const perPage = 12
	let currentPage = 0;
	console.log(totalRelatedProducts)
	let totalPage = generatePageButtons(totalRelatedProducts, perPage);
	let pageNumber = document.querySelectorAll(".page-number");
	handlePagination()
	function generatePageButtons(total, perPage) {
		window.scroll(0, 0);
		let totalPage = Math.ceil(total / perPage);
		const pagination = document.getElementById('related-products-pagination');
		let html = "";
		for (let i = 0; i < totalPage; ++i) {
			html += `<li class="page-item"><button style="display: none" class="page-link page-btn-size page-number">${i + 1}</button></li>`
		}
		pagination.lastElementChild.insertAdjacentHTML('beforebegin', html);
		//<li class="page-item"><button class="page-link page-number">1</button></li>
		return totalPage;

	}

	async function handlePageClick(page) {
		const products = await fetch(`/api/related_products?id=${productId}&page=${page}`,
			{
				method: 'GET',
				headers: {
					"Content-type": "application/json"
				}
			}).then(response => response.json()).then(data => data);
		//Remove related products after "relatedProductsSeperate"
		currentPage = page;

		pageNumber.forEach((page, index) => {
			page.style.display = "none"; // Hide the page number
			page.textContent = index + 1;
			page.classList.remove('disabled');
		});

		let pageShow = 2
		if (page == 0 || page == totalPage - 1) {
			pageShow = 3;
		}

		for (let i = 0; i <= pageShow; ++i) {
			if (pageNumber[page - i]) {
				pageNumber[page - i].style.display = 'block';
			}
			if (pageNumber[page + i]) {
				pageNumber[page + i].style.display = 'block';
			}
		}

		let dotPage = page - (pageShow + 1);
		if (pageNumber[dotPage] && dotPage != 0) {
			pageNumber[dotPage].textContent = '...';
			pageNumber[dotPage].style.display = 'block';
			pageNumber[dotPage].classList.add('disabled');
		}
		dotPage = page + (pageShow + 1);
		if (pageNumber[dotPage] && dotPage != 0) {
			pageNumber[dotPage].textContent = '...';
			pageNumber[dotPage].style.display = 'block';
			pageNumber[dotPage].classList.add('disabled');
		}


		pageNumber[0].style.display = "block";
		pageNumber[totalPage - 1].style.display = "block";
		pageNumber[currentPage].focus();
		let nextElement = relatedProductsSeperate.nextElementSibling;
		while (nextElement && nextElement.id === "related-product") {

			// Remove the next sibling element
			if (nextElement.parentNode) {
				nextElement.parentNode.removeChild(nextElement);
			}
			nextElement = relatedProductsSeperate.nextElementSibling;
		}

		let html = "";
		for (let product of products) {
			html += `
		<div id="related-product" class="col-md-3 mb-4">
				<div class="card position-relative">
						<div style="z-index: 2" class="position-absolute top-0 end-0 mt-2 me-2">
							<i class="bi bi-heart" style="font-size: 1.5rem;"></i>
						</div>
						<div style="z-index: 2" class="position-absolute top-0 end-0 mt-icon me-2">
							<i class="bi bi-cart" style="font-size: 1.5rem;"></i>
						</div>
						<a class="related-product-img" href="/product/${product.id}">
							<img src="${product.imageLink}" style="height: 300px; width:100%" class="card-img-top" />
						</a>
							
						<div class="card-body">
							<h5 class="card-title fs-6 text-nowrap text-overflow-ellipsis" >${product.name}</h5>
							<p class="card-title fst-italic lead text-nowrap text-overflow-ellipsis" >
								${product.description}
							</p>
							<p class="card-text">$${product.price}</p>
							<a href="#" class="card-text text-decoration-none btn btn-primary">Buy Now</a>
						</div>
					</div>
		</div>`
		}
		relatedProductsSeperate.insertAdjacentHTML("afterend", html)
		window.scroll(0, 0);
	}


	async function handlePagination() {
		await handlePageClick(currentPage)
		pageNumber = Array.from(pageNumber)
		for (const page of pageNumber) {
			page.addEventListener("click", async function (event) {
				const currentPage = pageNumber.indexOf(event.currentTarget); //Get index of clickedPagte
				pageNumber[currentPage].focus();
				await handlePageClick(currentPage);
			})
		}
		pagePrevious.addEventListener("click", async function (event) {
			currentPage = currentPage == 0 ? totalPage - 1 : currentPage - 1;
			pageNumber[currentPage].focus()
			handlePageClick(currentPage);
		})
		pageNext.addEventListener("click", async function (event) {
			currentPage = currentPage == totalPage - 1 ? 0 : currentPage + 1;
			pageNumber[currentPage].focus()
			handlePageClick(currentPage);
		})
	}
	window.scroll(0,0);
})


