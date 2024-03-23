

async function fetchData() {
	const response = await fetch("/api/related_products?id=1&page=0", 
	{
		method: 'GET',
		headers: {
			"Content-type" : "application/json"
		}
	});
	const data = await response.json();
	console.log(data)
	return data;
}
fetchData();