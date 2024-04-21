function validateInput(brandInput, priceInput, tagListInput, productTypeInput, submitButton){
    brandInput.addEventListener('input', function (e) {
        document.querySelector('.brand-error').innerHTML = '';
        // document.querySelector('.submit-btn').classList.remove('disabled');
        const brand = e.target.value;
        const regex = /^[A-Z][a-z]*( [A-Z][a-z]*)*$/;
        if (!regex.test(brand)) {
            document.querySelector('.brand-error').innerHTML = 'Brand must only contain letters, and the first word must be capitalized!';
            submitButton.disabled = true;

        } else {
            submitButton.disabled = false;
        }
    })
    priceInput.addEventListener('input', function (e) {
        document.querySelector('.price-error').innerHTML = '';
        // document.querySelector('.submit-btn').classList.remove('disabled');
        const price = e.target.value;
        if (price <= 0) {
            document.querySelector('.price-error').innerHTML = 'Price must be greater than zero';
            submitButton.disabled = true;

        } else {
            submitButton.disabled = false;
        }
    })
    productTypeInput.addEventListener('input', function (e) {
        document.querySelector('.productType-error').innerHTML = '';
        // document.querySelector('.submit-btn').classList.remove('disabled');
        const productType = e.target.value;
        const regex = /^[a-z]*( [a-z]+)*$/;
        if (!regex.test(productType)) {
            document.querySelector('.productType-error').innerHTML = 'Product type must only contain letters';
            submitButton.disabled = true;

        } else {
            submitButton.disabled = false;
        }
    })
    tagListInput.addEventListener('input', function (e) {
        document.querySelector('.tagList-error').innerHTML = '';
        // document.querySelector('.submit-btn').classList.remove('disabled');
        const tagList = e.target.value;
        const regex = /^[a-z]*(, [a-z]+)*$/;
        if (!regex.test(tagList)) {
            document.querySelector('.tagList-error').innerHTML = 'Tag list must only contain letters. Each tag list needs to be separated by ",". Example: "water free, cruelty free"';
            submitButton.disabled = true;

        } else {
            submitButton.disabled = false;
        }
    })
}


document.addEventListener("DOMContentLoaded", function (event) {
    if (window.location.search.includes('imageUploaded=true')) {
        window.location.search = window.location.search.split('?')[0];
        window.location.reload();
    }
    const createCategoryModal = document.querySelector("#create-product-modal");
    // const updateCategoryModal = document.querySelector("#update-category-modal");
    // const deleteCategoryModal = document.querySelector("#delete-category-modal");
    const IMAGE_UPLOAD_DIRECTORY = "src/main/resources/static/img"

    createCategoryModal.addEventListener('show.bs.modal', function (event) {
        // Button that triggered the modal
        let button = event.relatedTarget
        // Extract info from data-bs-* attributes
        const form = document.querySelector('#create-product-form');
        const nameInput = form.querySelector('input[name="name"]');
        const brandInput = form.querySelector('input[name="brand"]');
        const priceInput = form.querySelector('input[name="price"]');
        const ratingInput = form.querySelector('input[name="rating"]');
        const tagListInput = form.querySelector('input[name="tagList"]');
        const productTypeInput = form.querySelector('input[name="productType"]');
        const descriptionInput = form.querySelector('input[name="description"]');
        const submitButton = form.querySelector("button[type=submit]");
        document.querySelector('.brand-error').innerHTML = '';
        submitButton.disabled = false;

        const imageInput = form.querySelector('#image-input');
        const imagePreview = form.querySelector('#image-preview');
        //Preview image
        imageInput.addEventListener('change', function () {
            const file = this.files[0];
            console.log(file)
            if (file) {
                const reader = new FileReader();
                reader.onload = function (e) {
                    // const img = new Image();
                    imagePreview.src = e.target.result;
                    imagePreview.style.display = 'block';
                }
                reader.readAsDataURL(file);
            }
        });
        validateInput(brandInput,priceInput,tagListInput,productTypeInput, submitButton)
    })

    // updateCategoryModal.addEventListener('show.bs.modal', function (event) {
    //     // Button that triggered the modal
    //     let button = event.relatedTarget
    //     // Extract info from data-bs-* attributes
    //     const form = document.querySelector('#update-category-form');
    //     const imagePreview = form.querySelector('#image-preview');
    //     const imageInput = form.querySelector('input[name="image"]');
    //     const newCategoryNameInput = form.querySelector('input[name="newCategoryName"]');
    //     const oldCategoryNameInput = form.querySelector('input[name="oldCategoryName"]');
    //     const categoryName = button.getAttribute('data-categoryName');
    //     oldCategoryNameInput.value = categoryName;
    //     const oldCategoryName = categoryName;
    //     const originalImageLink = button.getAttribute('data-imageLink');
    //     newCategoryNameInput.value = categoryName;
    //     imagePreview.src = originalImageLink;
    //     // console.log(orginialImageLink
    //     const submitButton = form.querySelector("button[type=submit]");
    //     submitButton.disabled = true;
    //     // Function to reset image preview to original avatar

    //     // Add event listener to clear file input when modal is closed
    //     $('#update-category-modal').on('hidden.bs.modal', function () {
    //         $('#image-input').val('');
    //         submitButton.disabled = true;
    //         imagePreview.src = originalImageLink;
    //     });



    //     newCategoryNameInput.addEventListener('input', function (e) {

    //         form.querySelector('.categoryName-error').innerHTML = '';
    //         // document.querySelector('.submit-btn').classList.remove('disabled');
    //         const categoryName = e.target.value;
    //         const regex = /^[A-Z][a-z]*( [a-z]+)*$/;
    //         if (!regex.test(categoryName) || categoryName == oldCategoryName ) {
    //             form.querySelector('.categoryName-error').innerHTML = !regex.test(categoryName) ? 'The category name must only contain letters, and the first word must be capitalized!' : '';
    //             submitButton.disabled = true;
                
    //         } else {
    //             submitButton.disabled = false;
    //         }
    //     })

    //     imageInput.addEventListener('change', function () {
    //         const file = this.files[0];
    //         console.log(file)
    //         if (file) {
    //             const reader = new FileReader();
    //             reader.onload = function (e) {
    //                 imagePreview.src = e.target.result;
    //             }
    //             reader.readAsDataURL(file);
    //             submitButton.disabled = false;
    //         }
    //     });
    //     //Preview image
    // })
   
    // })
    // deleteCategoryModal.addEventListener('show.bs.modal', function (event) {
    //     // Button that triggered the modal
    //     let button = event.relatedTarget
    //     // Extract info from data-bs-* attributes
    //     const categoryName = button.getAttribute('data-categoryName');
    //     const form = document.querySelector('#delete-category-form');
    //     const categoryNameInput = form.querySelector('input[name="categoryName"]');
    //     categoryNameInput.value = categoryName;
    //     console.log(form.querySelector('input[name="categoryName"]').value)
    // })
})