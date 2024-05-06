
let orders;

document.addEventListener('DOMContentLoaded', async () => {
    orders = await fetchOrders();
    console.log(orders);
    orders.reverse();
    console.log(orders);
    
    var container = document.getElementById("orderContainer");
    orders.forEach(function(order) {
        var orderHTML = `
        <div style="background-color: white; padding: 1rem; margin: 1rem 0">
            <div class="flex row">
                <strong style="font-size: 20px">Order number: ${order.id}</strong>
            </div>
            <div class="flex row">
                <div class="row d-inline">
                    <strong>Recipient name:</strong> ${order.name}
                </div>
                <div class="row d-inline">
                    <strong>Phone number:</strong> ${order.phone}
                </div>
                <div class="row d-inline">
                    <strong>Address:</strong> ${order.address}
                </div>
            </div>
            <div class="flex row">
                <div class="col">
                    <strong>Date of order:</strong> ${new Date(order.createdAt).toDateString()}
                </div>
                <div class="col">
                    <strong>Payment:</strong> ${order.paymentMethod}
                </div>
                <div class="col">
                    <strong>TOTAL COST: $${order.total}</strong> 
                </div>
            </div>
            <ul class="list-group">`;

        fetch(`/api/order/${order.id}`)
        .then(response => response.json())
        .then(orderItems => {
            orderItems.forEach(item => {
                var itemHTML = `
                   <li class="list-group-item">
					    <div class="d-flex lh-sm">
					        <div class="d-flex align-items-center" style="width: 52.7rem">
					            <a href="/product/${item.productId}">
					                <img class="order-product-image" src="${item.imageLink}">
					            </a>                                    
					            <div class="item-details">
					                <a class="text-decoration-none link-primary p-0" href="/product/${item.productId}">
					                    <h6 class="my-0">${item.name}</h6>
					                </a>
					                <small class="text-body-secondary">${item.categoryName}</small>
					            </div>
					        </div>
					        <span class="item-price" style="margin-left: auto;">
					            ${item.quantity} x $${item.price} = $${item.total}
					        </span>
					    </div>
					</li>
                `;
                orderHTML += itemHTML;
            });

            orderHTML += `</ul> </div>`;
            container.innerHTML += orderHTML;
        });
    });
});



async function fetchOrders() {
	const response = await fetch(`/api/orders`);
	const data = await response.json();
	return data;
}