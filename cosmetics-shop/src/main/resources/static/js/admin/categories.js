if (window.location.search.includes("?success")) {
    // Remove the "?success" query parameter from the URL
    const newUrl = window.location.href.replace("?success", "");
    history.replaceState({}, document.title, newUrl);
    // Reload the page
    window.location.reload();
}

document.addEventListener("DOMContentLoaded", function (event) {
    if (window.location.search.includes('imageUploaded=true')) {
        window.location.search = window.location.search.split('?')[0];
        window.location.reload();
    }

    const updateCategoryModal = document.querySelector("#update-category-modal");
    const createCategoryModal = document.querySelector("#create-category-modal");
    const deleteCategoryModal = document.querySelector("#delete-category-modal");
    const IMAGE_UPLOAD_DIRECTORY = "src/main/resources/static/img"
    updateCategoryModal.addEventListener('show.bs.modal', function (event) {
        // Button that triggered the modal
        let button = event.relatedTarget
        // Extract info from data-bs-* attributes
        const categoryName = button.getAttribute('data-categoryName');
        console.log(categoryName)
        const imageLink = button.getAttribute('data-imageLink');
        console.log(imageLink);
    })
    createCategoryModal.addEventListener('show.bs.modal', function (event) {
        // Button that triggered the modal
        let button = event.relatedTarget
        // Extract info from data-bs-* attributes
        const form = document.querySelector('#create-category-form');
        const categoryNameInput = form.querySelector('input[name="categoryName"]');
        const imageInput = document.querySelector('#image-input');
        const imagePreview = document.querySelector('#image-preview');
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
        categoryNameInput.addEventListener('input', function (e) {

            document.querySelector('.categoryName-error').innerHTML = '';
            // document.querySelector('.submit-btn').classList.remove('disabled');
            const categoryName = e.target.value;
            const regex = /^[A-Z][a-z]*( [a-z])*$/;
            if (!regex.test(categoryName)) {
                document.querySelector('.categoryName-error').innerHTML = 'The category name must only contain letters, and the first word must be capitalized!';
                document.querySelector('#submit-btn').disabled = true;
            } else {
                document.querySelector('#submit-btn').disabled = false;
            }
        })

    })
    deleteCategoryModal.addEventListener('show.bs.modal', function (event) {
        // Button that triggered the modal
        let button = event.relatedTarget
        // Extract info from data-bs-* attributes
        const categoryName = button.getAttribute('data-categoryName');
        const form = document.querySelector('#delete-category-form');
        const categoryNameInput = form.querySelector('input[name="categoryName"]');
        categoryNameInput.value = categoryName;
        console.log(form.querySelector('input[name="categoryName"]').value)
    })
})
