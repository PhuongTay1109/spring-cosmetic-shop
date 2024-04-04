const productsContainer = document.getElementById("products-container");
const categoryNameElement = document.getElementById("category-name");
const categoryName = categoryNameElement.getAttribute("data-categoryName");
let pagination = document.getElementById('products-by-category-pagination');
let totalProducts = categoryNameElement.getAttribute("data-totalProductsByCategory");
const pageNext = document.querySelector("#page-next");
const pagePrevious = document.querySelector("#page-previous");
const pageSize = 16;
let currentPage = 0;
let totalPage = generatePageButtons(totalProducts, pageSize);
let pageList = document.querySelectorAll(".page-number");

// data that fetched
let fetchedProducts = null;
let productListToShow = null;

let sortBy = undefined;

window.addEventListener('popstate', function(event) {
	const currentPageFromHistory = event.state ? event.state.page : 0;
	currentPage = currentPageFromHistory - 1;
	handlePageClick(currentPage, totalPage, pageList, productListToShow, sortBy);
});

document.addEventListener("DOMContentLoaded", async function(event) {
	// Check if URL contains any parameter
	// If it has, update the current page
	const urlParams = new URLSearchParams(window.location.search);
	const pageParam = urlParams.get('page');
	const sortParam = urlParams.get('sort');
	
	if (pageParam !== null) {
		currentPage = parseInt(pageParam) - 1; // Subtract 1 because page index starts from 0
	}

	// Variable to store fetched data
	fetchedProducts = await fetchData();
	// At the begin, i show fetchedproduct
	// but if there is any change (filter, sort)
	// i need to store the new product list
	productListToShow = fetchedProducts;
	
	if (sortParam != null) {
		sortBy = sortParam;
		sortProducts(sortBy);
	}	

	handlePagination(currentPage, totalPage, pageList, productListToShow, sortBy);
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
	let html = "";

	for (let i = 0; i < totalPage; ++i) {
		html += `<li class="page-item">
                        <button class="page-link page-btn-size page-number" >${i + 1}</button>
                    </li>`
	}

	pagination.lastElementChild.insertAdjacentHTML('beforebegin', html);
	return totalPage;
}

function handlePageClick(currentPage, totalPage, pageList, productList, sortBy) {
	let startIndex = currentPage * pageSize;
	let endIndex = Math.min(startIndex + pageSize, totalProducts);
	let productsOnPage = productList.slice(startIndex, endIndex);

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
	
	if(sortBy != undefined) {
		updateURL(currentPage + 1, sortBy);
	}
	else {
		updateURL(currentPage + 1);
	}
			
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

function handlePagination(currentPage, totalPage, pageList, productList, sortBy) {
    handlePageClick(currentPage, totalPage, pageList, productList, sortBy);
    pageList[currentPage].focus();
    window.scroll(0, 0);

    pageList = Array.from(pageList);

    for (const page of pageList) {
        page.addEventListener("click", (event) => {
            currentPage = pageList.indexOf(event.currentTarget); //Get index of clickedPage
            console.log("page: " + currentPage);
            pageList[currentPage].focus();
            handlePageClick(currentPage, totalPage, pageList, productList, sortBy); // Pass arguments here
        })
    }
    
    pagePrevious.addEventListener("click", () => {
        currentPage = currentPage == 0 ? totalPage - 1 : currentPage - 1;
        pageList[currentPage].focus();
        handlePageClick(currentPage, totalPage, pageList, productList, sortBy); // Pass arguments here
    })
    
    pageNext.addEventListener("click", () => {
        currentPage = currentPage == totalPage - 1 ? 0 : currentPage + 1;
        console.log("next: " + currentPage);
        handlePageClick(currentPage, totalPage, pageList, productList, sortBy); // Pass arguments here
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

// SORT 
document.querySelectorAll('.sort-item').forEach(item => {
	item.addEventListener('click', (event) => {
		sortBy = event.target.getAttribute('data-sort-method');
		sortProducts(sortBy);
		handlePagination(0, totalPage, pageList, productListToShow, sortBy);
	});
});

function sortProducts(sortBy) {
    let sortedProducts = [];
    const button = document.getElementById('sortButton');
    switch (sortBy) {		
          case 'top-rating':
            button.innerText = 'Top Rating';
            sortedProducts = productListToShow.sort((a, b) => b.rating - a.rating);
            break; 
          case 'price-descending':
            button.innerText = 'Price Descending';
            sortedProducts = productListToShow.sort((a, b) => b.price - a.price);
            break;
          case 'price-ascending':
            button.innerText = 'Price Ascending';
            sortedProducts = productListToShow.sort((a, b) => a.price - b.price);
            break;
          case 'name-ascending':
            button.innerText = 'Name A-Z';
            sortedProducts = productListToShow.sort((a, b) => a.name.localeCompare(b.name));
            break;
       	  case 'name-descending':
            button.innerText = 'Name Z-A';
            sortedProducts = productListToShow.sort((a, b) => b.name.localeCompare(a.name));
            break;
        }

	// update products
    productListToShow = sortedProducts;
}

// FILTER
document.querySelectorAll('.custom-control-input').forEach(input => {
	input.addEventListener('change', () => {
		filterProducts();
	});
});

function filterProducts() {
    // get selected type and brand list
    const selectedTypes = Array.from(document.querySelectorAll('.custom-control-input[name="type"]:checked')).map(input => input.value);
    const selectedBrands = Array.from(document.querySelectorAll('.custom-control-input[name="brand"]:checked')).map(input => input.value);
	
    // filter
    let filteredProducts = fetchedProducts.filter(product => {
        return (selectedTypes.length === 0 || selectedTypes.includes(product.productType)) && 
               (selectedBrands.length === 0 || selectedBrands.includes(product.brand));
    });
    
     if (filteredProducts.length === 0) {
        productsContainer.innerHTML = '<p>Hix. No products. Can you try turning off the filter conditions and searching again?</p>';
        pagination.classList.add("hidden");
    }
    else {
		 // update products
	    productListToShow = filteredProducts;
	    totalProducts = productListToShow.length;
	    
	    totalPage = Math.ceil(totalProducts / pageSize);
	    
	    // remove the old page number
		const children = pagination.children;
		for (let i = children.length - 2; i > 0; i--) {
			pagination.removeChild(children[i]);
		}
	        
	    // update other information of page
	    generatePageButtons(totalProducts, pageSize);
	    pageList = document.querySelectorAll(".page-number");
	    
	    currentPage = 0;
	    handlePagination(currentPage, totalPage, pageList, productListToShow, sortBy);
	}   
}

function clearAllFiltersAndSort() {
    // Clear all checkboxes
    document.querySelectorAll('.custom-control-input').forEach(input => {
        input.checked = false;
    });
    
    pagination.classList.remove("hidden");

    // Clear sort button text
    const sortButton = document.getElementById('sortButton');
    sortButton.innerHTML = '&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;';
    // Clear sort by variable
    sortBy = undefined;

    // Re-render products
    productListToShow = fetchedProducts;
    totalProducts = productListToShow.length;
    
    totalPage = Math.ceil(totalProducts / pageSize);
    
    // remove the old page number
	const children = pagination.children;
	for (let i = children.length - 2; i > 0; i--) {
		pagination.removeChild(children[i]);
	}
        
    // update other information of page
    generatePageButtons(totalProducts, pageSize);
    pageList = document.querySelectorAll(".page-number");
    
    handlePagination(0, totalPage, pageList, productListToShow, sortBy);
}
