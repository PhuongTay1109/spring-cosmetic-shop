function validateInput(form) {
    const nameInput = form.querySelector('input[name="name"]');
    const brandInput = form.querySelector('input[name="brand"]');
    const priceInput = form.querySelector('input[name="price"]');
    const ratingInput = form.querySelector('input[name="rating"]');
    const tagListInput = form.querySelector('input[name="tagList"]');
    const productTypeInput = form.querySelector('input[name="productType"]');
    const descriptionInput = form.querySelector('textarea[name="description"]');
    const submitButton = form.querySelector("button[type=submit]");
    const imageInput = form.querySelector('#image-input');
    form.querySelector('.brand-error').innerHTML = '';
    submitButton.disabled = true;


    const imagePreview = form.querySelector('#image-preview');
    //Preview image
    imageInput.addEventListener('change', function () {
        const file = this.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = function (e) {
                // const img = new Image();
                imagePreview.src = e.target.result;
                imagePreview.style.display = 'block';
            }
            reader.readAsDataURL(file);
        }
        submitButton.disabled = false;
    });

    brandInput.addEventListener('input', function (e) {
        form.querySelector('.brand-error').innerHTML = '';
        // form.querySelector('.submit-btn').classList.remove('disabled');
        const brand = e.target.value;
        const regex = /^[A-Z][a-z]*( [A-Z][a-z]*)*$/;
        if (!regex.test(brand)) {
            form.querySelector('.brand-error').innerHTML = 'Brand must only contain letters, and the first word must be capitalized!';
            submitButton.disabled = true;

        } else {
            submitButton.disabled = false;
        }
    })
    priceInput.addEventListener('input', function (e) {
        form.querySelector('.price-error').innerHTML = '';
        // form.querySelector('.submit-btn').classList.remove('disabled');
        const price = e.target.value;
        if (price <= 0) {
            form.querySelector('.price-error').innerHTML = 'Price must be greater than zero';
            submitButton.disabled = true;

        } else {
            submitButton.disabled = false;
        }
    })
    productTypeInput.addEventListener('input', function (e) {
        form.querySelector('.productType-error').innerHTML = '';
        // form.querySelector('.submit-btn').classList.remove('disabled');
        const productType = e.target.value;
        const regex = /^[a-z]*( [a-z]+)*$/;
        if (!regex.test(productType)) {
            form.querySelector('.productType-error').innerHTML = 'Product type must only contain letters';
            submitButton.disabled = true;

        } else {
            submitButton.disabled = false;
        }
    })
    tagListInput.addEventListener('input', function (e) {
        form.querySelector('.tagList-error').innerHTML = '';
        // form.querySelector('.submit-btn').classList.remove('disabled');
        const tagList = e.target.value;
        const regex = /^[a-z]*(, [a-z]+)*$/;
        if (!regex.test(tagList)) {
            form.querySelector('.tagList-error').innerHTML = 'Tag list must only contain letters. Each tag list needs to be separated by ",". Example: "water free, cruelty free"';
            submitButton.disabled = true;

        } else {
            submitButton.disabled = false;
        }
    })
    descriptionInput.addEventListener('input', function (e) {
        console.log("description changed")
        submitButton.disabled = false;
    })
}


document.addEventListener("DOMContentLoaded", function (event) {

    if (window.location.search.includes('imageUploaded=true')) {
        window.location.search = window.location.search.split('?')[0];
        window.location.reload();
    }
    const createProductModal = document.querySelector("#create-product-modal");
    const updateProductModal = document.querySelector("#update-product-modal");
    const deleteProductModal = document.querySelector("#delete-product-modal");

    createProductModal.addEventListener('show.bs.modal', function (event) {
        // Button that triggered the modal
        let button = event.relatedTarget
        // Extract info from data-bs-* attributes
        const form = document.querySelector('#create-product-form');
        validateInput(form)
    })

    updateProductModal.addEventListener('show.bs.modal', function (event) {
        // Button that triggered the modal
        let button = event.relatedTarget
        const productId = button.getAttribute('data-id');
        let product = productListToShow.filter(prod => prod.id == productId)[0]; // Return array, get the first element
        console.log(product)
        // Extract info from data-bs-* attributes
        const form = document.querySelector('#update-product-form');
        console.log(form)
        form.querySelector('input[name="id"]').value = product.id
        form.querySelector('input[name="name"]').value = product.name
        form.querySelector('input[name="categoryName"]').value = product.categoryName
        form.querySelector('input[name="imageLink"]').value = product.imageLink
        form.querySelector('input[name="brand"]').value = product.brand
        form.querySelector('input[name="price"]').value = product.price
        product.tagList = JSON.parse(product.tagList).join(", ") // "[\"a\",\"asd\"]" turns into "a, asd"
        form.querySelector('input[name="tagList"]').value = product.tagList
        form.querySelector('input[name="productType"]').value = product.productType
        form.querySelector('textarea[name="description"]').value = product.description
        validateInput(form)
        // Add event listener to clear file input when modal is closed
        $('#update-product-modal').on('hidden.bs.modal', function () {
            $('#image-input').val('');
            submitButton.disabled = true;
            imagePreview.src = product.imageLink;
        });
    })

    // })
    deleteProductModal.addEventListener('show.bs.modal', function (event) {
        // Button that triggered the modal
        let button = event.relatedTarget
        // Extract info from data-bs-* attributes
        const productId = button.getAttribute('data-id');
        const form = document.querySelector('#delete-product-form');
        const productIdInput = form.querySelector('input[name="id"]');
        productIdInput.value = productId;
        console.log(productListToShow)
    })
})