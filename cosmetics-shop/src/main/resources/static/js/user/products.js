const productsContainer = document.getElementById("products-container");
const categoryNameElement = document.getElementById("category-name");
const categoryName = categoryNameElement.getAttribute("data-categoryName");
const totalProducts = categoryNameElement.getAttribute("data-totalProductsByCategory");
const pageNext = document.querySelector("#page-next");
const pagePrevious = document.querySelector("#page-previous");
const pageSize = 16;
let currentPage = 0;
let totalPage = generatePageButtons(totalProducts, pageSize);
let pageList = document.querySelectorAll(".page-number");
let fetchedProducts = null;

window.addEventListener('popstate', function(event) {
	const currentPageFromHistory = event.state ? event.state.page : 0;
	currentPage = currentPageFromHistory - 1;
	handlePagination();
});

document.addEventListener("DOMContentLoaded", async function(event) {
	// Check if URL contains page parameter
	// If it has, update the currentPage
	const urlParams = new URLSearchParams(window.location.search);
	const pageParam = urlParams.get('page');
	if (pageParam !== null) {
		currentPage = parseInt(pageParam) - 1; // Subtract 1 because page index starts from 0
	}

	// Variable to store fetched data
	fetchedProducts = await fetchData();

	handlePagination();
});

// Fetch data from server
async function fetchData() {
	const response = await fetch(`/api/products?category_name=${categoryName}`);
	const data = await response.json();
	return data;
}

// Create page buttons
function generatePageButtons(totalProducts, pageSize) {
	window.scroll(0, 0);

	let totalPage = Math.ceil(totalProducts / pageSize);
	const pagination = document.getElementById('products-by-category-pagination');
	let html = "";

	for (let i = 0; i < totalPage; ++i) {
		html += `<li class="page-item">
                        <button class="page-link page-btn-size page-number" >${i + 1}</button>
                    </li>`
	}

	pagination.lastElementChild.insertAdjacentHTML('beforebegin', html);
	return totalPage;
}

function handlePageClick(pageNumber) {
	let startIndex = pageNumber * pageSize;
	let endIndex = Math.min(startIndex + pageSize, totalProducts);
	let productsOnPage = fetchedProducts.slice(startIndex, endIndex);

	let html = "";
	for (let product of productsOnPage) {
		html += `<div class="col-6 col-md-6 col-lg-3 mb-3">
                            <div class="card position-relative">
                                <a class="card-product-img" href="/product/${product.id}">
									<img src="${product.imageLink}" class="card-img-top" />
								</a>
                                <div class="card-body">
                                	<a class="link-primary text-decoration-none" href="/product/${product.id}">
                                    	<h5 class="card-title text-nowrap text-overflow-ellipsis">${product.name}</h5>
                                    </a>
                                    <p class="card-text fst-italic lead text-nowrap text-overflow-ellipsis"
                                        style="font-size: 16px" >
                                        ${product.description}
                                    </p>
                                    <p class="card-text">$${product.price}</p>
                                    <div class="rating">
					                    ${generateStars(product.rating)}
					                </div>
                                </div>
                            </div>
                        </div>`
	}

	productsContainer.innerHTML = html;
	updateURL(currentPage + 1);
	window.scroll(0, 0);
}

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

function handlePagination() {
	handlePageClick(currentPage);
	pageList[currentPage].focus();
	window.scroll(0, 0);

	pageList = Array.from(pageList);

	for (const page of pageList) {
		page.addEventListener("click", (event) => {
			currentPage = pageList.indexOf(event.currentTarget); //Get index of clickedPage
			pageList[currentPage].focus();
			handlePageClick(currentPage);

		})
	}

	pagePrevious.addEventListener("click", () => {
		currentPage = currentPage == 0 ? totalPage - 1 : currentPage - 1;
		pageList[currentPage].focus();
		handlePageClick(currentPage);
	})

	pageNext.addEventListener("click", () => {
		currentPage = currentPage == totalPage - 1 ? 0 : currentPage + 1;
		pageList[currentPage].focus();
		handlePageClick(currentPage);
	})
}

// Update URL with page number
function updateURL(pageNumber) {
	const newURL = window.location.pathname + '?page=' + pageNumber;
	history.pushState({ page: pageNumber }, '', newURL);
}