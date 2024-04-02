
console.log(productList)

const productsContainer = document.getElementById("products-container");
const totalProducts = productList.length;
const pageSize = 4*6
const totalPage = generatePageButtons(totalProducts,pageSize)
let pageList = document.querySelectorAll(".page-number");
console.log(totalPage)
const pageNext = document.querySelector("#page-next");
const pagePrevious = document.querySelector("#page-previous");
let currentPage = 0;

window.addEventListener('popstate', function(event) {
	const currentPageFromHistory = event.state ? event.state.page : 0;
	console.log("Current Page from previous history:", currentPageFromHistory);
	currentPage = currentPageFromHistory - 1;
    console.log(currentPage)
	handlePagination()
});

handlePagination();

function handlePageClick(pageNumber) {
	let startIndex = pageNumber * pageSize;
	let endIndex = Math.min(startIndex + pageSize, totalProducts);
	let productsOnPage = productList.slice(startIndex, endIndex);

    pageList.forEach((page, index) => {
		page.style.display = "none"; // Hide the page number
		page.textContent = index + 1;
		page.classList.remove('disabled');
	});

	let pageShow = 2
	if (pageNumber == 0 || pageNumber == totalPage - 1) {
		pageShow = 3;
	}

	for (let i = 0; i <= pageShow; ++i) {
		if (pageList[pageNumber- i]) {
			pageList[pageNumber- i].style.display = 'block';
		}
		if (pageList[pageNumber+ i]) {
			pageList[pageNumber+ i].style.display = 'block';
		}
	}

	let dotPage = pageNumber - (pageShow + 1);
	if (pageList[dotPage] && dotPage != 0) {
		pageList[dotPage].textContent = '...';
		pageList[dotPage].style.display = 'block';
		pageList[dotPage].classList.add('disabled');
	}
	dotPage = pageNumber + (pageShow + 1);
	if (pageList[dotPage] && dotPage != totalPage - 1) {
		pageList[dotPage].textContent = '...';
		pageList[dotPage].style.display = 'block';
		pageList[dotPage].classList.add('disabled');
	}


	pageList[0].style.display = "block";
	pageList[totalPage - 1].style.display = "block";
	pageList[currentPage].focus();

	let html = "";
	for (let product of productsOnPage) {
		html += `<div class="col-md-3 mb-3">
        <div class="card position-relative">
            <div class="position-absolute top-0 end-0 mt-2 me-2">
                <i class="bi bi-heart" style="font-size: 1.5rem;"></i>
            </div>
            <div class="position-absolute top-0 end-0 mt-icon me-2">
                <i class="bi bi-cart" style="font-size: 1.5rem;"></i>
            </div>
            <a class="card-product-img" href="/product/${product.id}">
                <img src="${product.imageLink}" class="card-img-top" />
            </a>
            <div class="card-body">
                <h5 class="card-title text-nowrap text-overflow-ellipsis">${product.name}</h5>
                <p class="card-text fst-italic lead text-nowrap text-overflow-ellipsis"
                    style="font-size: 16px">
                    ${product.description}
                </p>
                <p class="card-text">$${product.price}</p>
                <a href="#" class="btn btn-primary">Buy Now</a>
            </div>
        </div>
    </div>`;
	}

	productsContainer.innerHTML = html;
	updateURL(currentPage + 1)
	window.scroll(0, 0);
}

function generatePageButtons(totalProducts, pageSize) {
	let totalPage = Math.ceil(totalProducts / pageSize);
	const pagination = document.getElementById('search-products-pagination');
    console.log(pagination)
	let html = "";

	for (let i = 0; i < totalPage; ++i) {
		html += `<li class="page-item">
                        <button class="page-link page-btn-size page-number"  >${i + 1}</button>
                    </li>`
	}

	pagination.lastElementChild.insertAdjacentHTML('beforebegin', html);
	return totalPage;
}

function handlePagination() {
	handlePageClick(currentPage);
    // console.log(currentPage);
	window.scroll(0, 0);

	pageList = Array.from(pageList);

	for (const page of pageList) {
		page.addEventListener("click", (event) => {
			currentPage = pageList.indexOf(event.currentTarget); //Get index of clickedPage
			handlePageClick(currentPage);

		})
	}

	pagePrevious.addEventListener("click", () => {
		currentPage = currentPage == 0 ? totalPage - 1 : currentPage - 1;
		handlePageClick(currentPage);
	})

	pageNext.addEventListener("click", () => {
		currentPage = currentPage == totalPage - 1 ? 0 : currentPage + 1;
		handlePageClick(currentPage);
	})
}

// Update URL with page number
function updateURL(pageNumber) {
    console.log(window.location.pathname);
	const newURL = window.location.pathname + `?keyword=${keyword}&page=${pageNumber}` ;
	console.log("newURL", newURL)
	history.pushState({keyword, page: pageNumber }, '', newURL);
}
