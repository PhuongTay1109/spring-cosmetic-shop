document.addEventListener("DOMContentLoaded", function(event) {
	const categoryNameElement = document.getElementById("category-name");
    const categoryName = categoryNameElement.getAttribute("data-category-name");
    console.log(categoryName);
	async function fetchProducts() {
		try {
			const response = await fetch(
				`/api/products?category_name=${categoryName}`,
			);
			if (!response.ok) {
				throw new Error(`HTTP error: ${response.status}`);
			}
			const data = await response.json();
			console.log(data);
		}
		catch (error) {
			console.error(`Could not get products: ${error}`);
		}
	}

	fetchProducts();
});