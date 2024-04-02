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

let sortBy = undefined;

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

function handlePageClick(pageNumber, sortBy) {
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
	if(sortBy != undefined)
		updateURL(currentPage + 1, sortBy);
	else
		updateURL(currentPage + 1);
	window.scroll(0, 0);
}

function generateStars(rating) {
    let stars = '';
    for (let i = 0; i < rating; i++) {
        stars += '<i class="bi bi-star-fill" style="color: gold;"></i>';
    }
    return stars;
}

function handlePagination() {
	handlePageClick(currentPage, sortBy);
	pageList[currentPage].focus();
	window.scroll(0, 0);

	pageList = Array.from(pageList);

	for (const page of pageList) {
		page.addEventListener("click", (event) => {
			currentPage = pageList.indexOf(event.currentTarget); //Get index of clickedPage
			pageList[currentPage].focus();
			handlePageClick(currentPage, sortBy);
		})
	}

	pagePrevious.addEventListener("click", () => {
		currentPage = currentPage == 0 ? totalPage - 1 : currentPage - 1;
		pageList[currentPage].focus();
		handlePageClick(currentPage, sortBy);
	})

	pageNext.addEventListener("click", () => {
		currentPage = currentPage == totalPage - 1 ? 0 : currentPage + 1;
		pageList[currentPage].focus();
		handlePageClick(currentPage, sortBy);
	})
}

function updateURL(pageNumber, sortBy) {
    const params = new URLSearchParams(window.location.search);
    params.set('page', pageNumber);
    if (sortBy !== undefined) { 
        params.set('sort', sortBy);
    } else {
        params.delete('sort'); 
    }
    const newURL = `${window.location.pathname}?${params.toString()}`;
    history.pushState({ page: pageNumber }, '', newURL);
}


// Handle sort feature
document.querySelectorAll('.sort-item').forEach(item => {
	item.addEventListener('click', (event) => {
		sortBy = event.target.getAttribute('data-sort-method');
		sortProducts(sortBy);
		// load products of current after sort
		handlePageClick(currentPage, sortBy); 
	});
});

function sortProducts(sortBy) {
    let sortedProducts = [];
    const button = document.getElementById('sortButton');
    switch (sortBy) {		
          case 'best-selling':
            button.innerText = 'Best Selling';
            //sortedProducts = fetchedProducts.sort((a, b) => b.sold - a.sold);
            break; 
          case 'price-descending':
            button.innerText = 'Price Descending';
            sortedProducts = fetchedProducts.sort((a, b) => b.price - a.price);
            break;
          case 'price-ascending':
            button.innerText = 'Price Ascending';
            sortedProducts = fetchedProducts.sort((a, b) => a.price - b.price);
            break;
          case 'name-ascending':
            button.innerText = 'Name A-Z';
            sortedProducts = fetchedProducts.sort((a, b) => a.name.localeCompare(b.name));
            break;
       	  case 'name-descending':
            button.innerText = 'Name Z-A';
            sortedProducts = fetchedProducts.sort((a, b) => b.name.localeCompare(a.name));
            break;
        }

    fetchedProducts = sortedProducts;
}