<%@page import="phuctho.tech.dao.ProductDao"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="phuctho.tech.connection.DbCon"%>
<%@page import="phuctho.tech.model.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
User auth = (User) request.getSession().getAttribute("auth");
if (auth != null) {
	request.setAttribute("auth", auth);
}

//get cartlist from sessionlist
ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart-list");
List<Cart> cartProduct = null;
if(cart_list!=null){
	ProductDao pDao = new ProductDao(DbCon.getConnection());
	cartProduct = pDao.getCartProducts(cart_list);
	double total = pDao.getTotalCartPrice(cart_list);
	request.setAttribute("cart_list", cart_list);
	request.setAttribute("total", total);
}
%>
<!DOCTYPE html>
<html>
<head>
<title>Cart Page</title>
<%@include file="includes/head.jsp"%>
</head>
<body>
	<%@include file="includes/navbar.jsp"%>

	<div class="container">
		<div class="d-flex py-3">
			<h3>Total Price: $ ${(total>0)?total:0}</h3>
			<a href="cart-check-out" class="mx-3 btn btn-primary">Check Out</a>
		</div>
		<table class="table table-light">
			<thead>
				<tr>
					<th scope="col">Name</th>
					<th scope="col">Category</th>
					<th scope="col">Price</th>
					<th scope="col">Buy Now</th>
					<th scope="col">Cancel</th>
				</tr>
			</thead>
			<tbody>
			<%if(cart_list!= null){
				for(Cart c: cartProduct){%>
					<tr>
					<td class="align-middle"><%=c.getName() %></td>
					<td class="align-middle"><%=c.getCategory() %></td>
					<td class="align-middle"><%=c.getPrice() %></td>
					<td>
						<form action="order-now" method="post" class="form-inline">
							<input type="hidden" name="id" value="<%=c.getId() %>" class="form-input">
							<div class="form-group d-flex justify-content-between w-50">
								<a class="btn btn-sm btn-decre" href="quantity-up-down?action=down&id=<%=c.getId()%>"> 
									<i class="fas fa-minus-square"></i>
								</a>
								<input type="text" name="quantity" class="form-control w-50" value="<%=c.getQuantity()%>" readonly> 
								<a class="btn btn-sm btn-incre" href="quantity-up-down?action=up&id=<%=c.getId()%>"> 
									<i class="fas fa-plus-square"></i>
								</a> 
							</div>
							<button type="submit" class="btn btn-sm btn-primary">Buy</button>
						</form>
					</td>
					
					<td><a class="btn btn-sm btn-outline-danger" href="remove-item-cart?id=<%=c.getId()%>">Remove</a></td>
				</tr>
			<% 	}
			}
			%>
				
			</tbody>
		</table>
	</div>

	<%@include file="includes/footer.jsp"%>
</body>
</html>