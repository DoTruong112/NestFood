
// Biến lưu trữ thông tin giỏ hàng
var cartItems = [];

// Hàm thêm sản phẩm vào giỏ hàng
function addToCart(itemId, itemName, itemPrice, itemImage) {
	var existingItem = cartItems.find(item => item.id === itemId);
	if (existingItem) {
		existingItem.quantity++;
	} else {
		cartItems.push({
			id: itemId,
			name: itemName,
			price: parseFloat(itemPrice),
			quantity: 1,
			image:"user/assets/imgs/shop/" + itemImage
		});
	}
	updateCart();
	saveCartToLocalStorage();
}

function addToCartProduct(itemId, itemName, itemPrice, itemImage) {
    var quantityInput = document.getElementById("item-quantity");
    var quantity = parseInt(quantityInput.value);

    if (quantity > 0) {
        var existingItem = cartItems.find(item => item.id === itemId);

        if (existingItem) {
            existingItem.quantity += quantity;
        } else {
            cartItems.push({
                id: itemId,
                name: itemName,
                price: parseFloat(itemPrice),
                quantity: quantity,
                image: "user/assets/imgs/shop/" + itemImage
            });
        }

        updateCart();
        saveCartToLocalStorage();
    } else {
        alert("Please enter a valid quantity (greater than 0).");
    }
}

// Hàm xóa sản phẩm khỏi giỏ hàng
function removeFromCart(itemId) {
	cartItems = cartItems.filter(item => item.id !== itemId);
	updateCart();
	saveCartToLocalStorage();
}

// Hàm cập nhật giỏ hàng
function updateCart() {
	var qtyElement = document.getElementById("qty");
    var cartListElement = document.getElementById("cart-list");
    var totalElement = document.querySelector(".shopping-cart-total h4 span");

    // Cập nhật số lượng sản phẩm trong giỏ hàng
    qtyElement.textContent = cartItems.reduce((total, item) => total + item.quantity, 0);

    // Xóa danh sách sản phẩm trong giỏ hàng cũ
    cartListElement.innerHTML = "";

    // Cập nhật danh sách sản phẩm trong giỏ hàng
    cartItems.forEach(item => {
        var productItem = document.createElement("li");
        productItem.innerHTML = `
            <div class="shopping-cart-img">
                <a href="${item.url}"><img alt="${item.name}"  src="${item.image}"/></a>
            </div>
            <div class="shopping-cart-title">
                <h4><a href="${item.url}">${item.name}</a></h4>
                <h4><span>${item.quantity} × </span>${(item.price * item.quantity).toFixed(2)} VND</h4>
            </div>
            <div class="shopping-cart-delete">
                <a href="#" onclick="removeFromCart('${item.id}')"><i class="fi-rs-cross-small"></i></a>
            </div>
        `;
        cartListElement.appendChild(productItem);
    });

    // Cập nhật tổng số tiền trong giỏ hàng
    var totalPrice = cartItems.reduce((total, item) => total + item.price * item.quantity, 0);
    totalElement.textContent =  totalPrice.toFixed(2) + " VND";
    console.log(cartItems)
}

// Hàm cập nhật sản phẩm và tổng giá trị đơn hàng
function updateOrderSummary() {
    var orderProducts = document.getElementById("order-products");
    var orderTotal = document.getElementById("order-total");

    orderProducts.innerHTML = ""; // Xóa danh sách sản phẩm cũ

    var totalPrice = 0;
    var totalQuantity = 0;

    cartItems.forEach(item => {
        var productRow = document.createElement("div");
        productRow.className = "order-col";
        productRow.innerHTML = `
            <div>${item.quantity}x ${item.name}</div>
            <div>$${(item.price * item.quantity).toFixed(2)}</div>
        `;

        orderProducts.appendChild(productRow);
        totalPrice += item.price * item.quantity;
        totalQuantity += item.quantity;
    });

    orderTotal.textContent = "$" + totalPrice.toFixed(2);
}






// Hàm lưu giỏ hàng vào localStorage
function saveCartToLocalStorage() {
	localStorage.setItem('cartItems', JSON.stringify(cartItems));
}

// Hàm khôi phục giỏ hàng từ localStorage khi tải lại trang
function restoreCartFromLocalStorage() {
	var cartData = localStorage.getItem('cartItems');
	if (cartData) {
		cartItems = JSON.parse(cartData);
		updateCart();
		updateOrderSummary();
	}
}

// Gọi hàm khôi phục giỏ hàng từ localStorage khi tải lại trang
restoreCartFromLocalStorage();

// Hàm gửi dữ liệu giỏ hàng lên máy chủ khi click vào "Place order"
function placeOrder() {
    var url = "/order/createOrder"; // Địa chỉ API endpoint trên phía máy chủ để xử lý việc tạo đơn hàng

    // Lấy thông tin khách hàng từ các trường nhập liệu trong HTML
    var username = document.querySelector('input[name="username"]').value;
    var phone = document.querySelector('input[name="phone"]').value;
    var address = document.querySelector('input[name="address"]').value;

    // Tạo một đối tượng chứa thông tin Orders và OrderDetails để gửi đến máy chủ
    var orderData = {
        username: username,
        phone: phone,
        address: address,
        orderDetails: [] // Danh sách OrderDetails sẽ được thêm vào đây
    };

    // Lặp qua các mục trong giỏ hàng và thêm dữ liệu vào danh sách OrderDetails
    cartItems.forEach(item => {
        var detail = {
            productId: item.id,
            price: item.price,
            quantity: item.quantity
        };
        orderData.orderDetails.push(detail);
    });

    // Gửi dữ liệu đến máy chủ bằng phương thức POST qua AJAX
    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(orderData)
    })
    .then(response => {
    console.log(response); // Kiểm tra phản hồi từ máy chủ
    return response.text();
})
    .then(data => {
        // Xử lý phản hồi từ máy chủ nếu cần
        console.log("Đơn hàng đã được tạo thành công!");
        // Bạn có thể xóa cartItems và cập nhật giao diện giỏ hàng tại đây
        alert("Đặt hàng thành công!");
        cartItems = []; 
	    updateCart(); 
	    updateOrderSummary(); 
	    saveCartToLocalStorage();
	     
	    window.location.reload();
    })
    .catch(error => {
        console.error("Lỗi khi tạo đơn hàng:", error);
    });
}
