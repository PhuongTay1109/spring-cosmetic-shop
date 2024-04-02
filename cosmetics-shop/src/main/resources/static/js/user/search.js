const productsContainer = document.getElementById("products-container");
const totalProducts = productList.length;
const pageSize = 4 * 6
const pagination = document.getElementById('search-products-pagination');
const totalPage = generatePageButtons(totalProducts, pageSize)
let pageList = document.querySelectorAll(".page-number");
console.log(totalPage)
const pageNext = document.querySelector("#page-next");
const pagePrevious = document.querySelector("#page-previous");
const categoryCheckboxes = document.querySelectorAll('input[name="categoryName"]');
let checkedCategories = [] //For handle filter
let currentPage = 0;

window.addEventListener('popstate', function (event) {
    const currentPageFromHistory = event.state ? event.state.page : 0;
    console.log("Current Page from previous history:", currentPageFromHistory);
    currentPage = currentPageFromHistory - 1;
    console.log(currentPage)
    handlePagination()
});

handlePagination(currentPage, totalPage, pageList, productList);
handleFilter();

function handlePageClick(pageNumber, pageSize, totalPage, pageList, productList) {
    let startIndex = pageNumber * pageSize;
    let endIndex = Math.min(startIndex + pageSize, totalProducts);
    console.log(productList);
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
        if (pageList[pageNumber - i]) {
            pageList[pageNumber - i].style.display = 'block';
        }
        if (pageList[pageNumber + i]) {
            pageList[pageNumber + i].style.display = 'block';
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
                <h5 class="link-primary card-title text-nowrap text-overflow-ellipsis">${product.name}</h5>
                <p class="card-text fst-italic lead text-nowrap text-overflow-ellipsis"
                    style="font-size: 16px">
                    ${product.description}
                </p>
                <p class="card-text">$${product.price}</p>
                <p class="card-text rating">
		            ${generateStars(product.rating)}
                </p>
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

function handlePagination(currentPage, totalPage, pageList, productList) {
    handlePageClick(currentPage, pageSize, totalPage, pageList, productList);
    // console.log(currentPage);
    window.scroll(0, 0);

    pageList = Array.from(pageList);

    for (const page of pageList) {
        page.addEventListener("click", (event) => {
            currentPage = pageList.indexOf(event.currentTarget); //Get index of clickedPage
            handlePageClick(currentPage, pageSize, totalPage, pageList, productList);

        })
    }

    pagePrevious.addEventListener("click", () => {
        currentPage = currentPage == 0 ? totalPage - 1 : currentPage - 1;
        handlePageClick(currentPage, pageSize, totalPage, pageList, productList);
    })

    pageNext.addEventListener("click", () => {
        currentPage = currentPage == totalPage - 1 ? 0 : currentPage + 1;
        handlePageClick(currentPage, pageSize, totalPage, pageList, productList);
    })
}

// Update URL with page number
function updateURL(pageNumber) {
    // console.log(window.location.pathname);
    const newURL = window.location.pathname + `?keyword=${keyword}&page=${pageNumber}`;
    // console.log("newURL", newURL)
    history.pushState({ keyword, page: pageNumber }, '', newURL);
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

function handleFilter() {
    // console.log(categoryCheckboxes);
    categoryCheckboxes.forEach(function (checkbox) {
        checkbox.addEventListener("change", function (event) {
            let thisCheckBox = event.target;
            let categoryName = thisCheckBox.value;
            if (thisCheckBox.checked) {
                // Add category name to the array if checked
                checkedCategories.push(categoryName);
            } else {
                // Remove category name from the array if unchecked
                const index = checkedCategories.indexOf(categoryName);
                if (index !== -1) {
                    checkedCategories.splice(index, 1);
                }
            }
            let filteredProducts = []
            if (checkedCategories.length == 0) {
                filteredProducts = productList
            }
            else {
                filteredProducts = productList.filter(product => {
                    return checkedCategories.some(checkedCategory => checkedCategory === product.categoryName)
                })
            }
            console.log(filteredProducts)

            // Get all children of the UL element
            const children = pagination.children;

            // Remove all children except the first and last
            for (let i = children.length - 2; i > 0; i--) {
                pagination.removeChild(children[i]);
            }
            let totalPage = generatePageButtons(filteredProducts.length, pageSize)
            let currentPageOfFilterProducts = 0
            pageList = document.querySelectorAll(".page-number");
            handlePagination(currentPageOfFilterProducts, totalPage, pageList, filteredProducts)

        })
    })
}