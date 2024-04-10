console.log(topRatingProducts)
console.log(newArrivalProducts)

const newArrivalWrapper = document.getElementById('new-arrival-wrapper');
const topRatingWrapper = document.getElementById('top-rating-wrapper');

const NUM_OF_PRODUCTS = 32
const DISPLAYED_PRODUCTS = 4


displayProductList(newArrivalProducts, newArrivalWrapper)
displayProductList(topRatingProducts, topRatingWrapper)

document.addEventListener("DOMContentLoaded", async function() {
	await updateCartNumber();
});


function displayProductList(productList, productWrapper) {
    let html = '';
    for (let i = 0; i < 4; ++i) {
        html += `<div class="col-3 mb-3">
                    <div class="card position-relative">
                        <a class="card-product-img" href="/product/${productList[i].id}">
                            <img src="${productList[i].imageLink}" class="card-img-top" />
                        </a>
                        <div class="card-body">
                            <h5 class="link-primary card-title text-nowrap text-overflow-ellipsis">${productList[i].name}</h5>
                            <p class="card-text fst-italic lead text-nowrap text-overflow-ellipsis"
                                style="font-size: 16px">
                                ${productList[i].description}
                            </p>
                            <p class="card-text">$${productList[i].price}</p>
                            <p class="card-text rating">
                                ${generateStars(productList[i].rating)}
                            </p>
                        </div>
                    </div>
                </div>`
    }
    productWrapper.innerHTML = html;
    let object = {page : 0}; // To pass reference

    const nextButton = productWrapper.parentNode.querySelector(".btn-right-arrow");
    const previousButton = productWrapper.parentNode.querySelector(".btn-left-arrow");
    previousButton.addEventListener("click", function () {
        handlePreviousButton(productList, productWrapper, object)
    })
    nextButton.addEventListener("click", function () {
        handleNextButton(productList, productWrapper, object)
    })
    

}

function handlePreviousButton(productList, productWrapper, object) {

    // console.log("page ", page)

    object.page = object.page - 1;
    if (object.page == -1) {
        object.page = NUM_OF_PRODUCTS / DISPLAYED_PRODUCTS - 1;
    }
    let products = productList.slice(DISPLAYED_PRODUCTS * object.page, DISPLAYED_PRODUCTS * object.page + DISPLAYED_PRODUCTS);

    console.log('productList in previous btn', products)
    let html = ``
    for (let product of products) {
        html += `<div class="slide-left col-3 mb-3">
                    <div class="card position-relative">
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
                        </div>
                    </div>
                </div>`
    }
    productWrapper.innerHTML = html;
}

function handleNextButton(productList, productWrapper, object) {
    // console.log("page ", page)

    object.page = object.page + 1;
    if (object.page == NUM_OF_PRODUCTS / DISPLAYED_PRODUCTS) {
        object.page = 0;
    }

    let products = productList.slice(DISPLAYED_PRODUCTS * object.page, DISPLAYED_PRODUCTS * object.page + DISPLAYED_PRODUCTS);
    console.log('productList in nextbtn', products)
    let html = ``
    for (let product of products) {
        html += `<div class="slide-right col-3 mb-3">
                    <div class="card position-relative">
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
                        </div>
                    </div>
                </div>`
    }
    productWrapper.innerHTML = html;
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