jQuery(document).ready(function($) {
	updateCartItemCount();
});

function updateCartItemCount() {
	$.ajax({
		url : '/cart/items/count',
		type : "GET",
		dataType : "json",
		contentType : "application/json",
		complete : function(responseData, status, xhttp) {
			$('#cart-item-count').text(
					'(' + responseData.responseJSON.count + ')');
		}
	});
}

function updateCartItemQuantity(productId){
	  var a = document.getElementById('productId').value;
	  alert(a);
}

function updateCartItemQuantity(productId, quantity) {
	alert("hi");
	$.ajax({
		url : '/cart/items',
		type : "POST",
		dataType : "json",
		contentType : "application/json",
		data : '{ "product" :{ "productId":"' + productId + '"},"quantity":"'
				+ quantity + '"}',
		complete : function(responseData, status, xhttp) {
			updateCartItemCount();
			location.href = '/products'
		}
	});
}

function removeItemFromCart(sku) {
	$.ajax({
		url : '/cart/items/' + sku,
		type : "DELETE",
		dataType : "json",
		contentType : "application/json",
		complete : function(responseData, status, xhttp) {
			updateCartItemCount();
			location.href = '/cart'
		}
	});
}
