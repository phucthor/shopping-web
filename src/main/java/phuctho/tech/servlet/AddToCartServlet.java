package phuctho.tech.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import phuctho.tech.model.*;

/**
 * Servlet implementation class AddToCartServlet
 */
@WebServlet("/add-to-cart")
public class AddToCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		
		try(PrintWriter out = response.getWriter()){
			ArrayList<Cart> cartList = new ArrayList<>();
			
			int id = Integer.parseInt(request.getParameter("id"));
			Cart cm = new Cart();
			cm.setId(id);
			cm.setQuantity(1);
			
			HttpSession session = request.getSession();
//			this session
			ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart-list");
			
			if(cart_list == null) {
				cartList.add(cm);
				session.setAttribute("cart-list", cartList);
				response.sendRedirect("index.jsp");
//				out.println("Session created and added to the list");
				
			}else {
				//define cartList not on this session
				cartList = cart_list;
				boolean exist = false;
				
				//cartList.contains(cm); hoac indexOf ko work tim id vs id, index vs index
				for (Cart c : cart_list) {
//					out.print(c.getId());
					if(c.getId()==id) {
						exist = true;
						out.println("<h3 style='color:crimson; text-align:center'>Item already exist in Cart.<br/><a href='cart.jsp'>Go to Cart Page</a></h3>");
					}
				}
				if(!exist) {
					cartList.add(cm);
//					out.print("product added");
					response.sendRedirect("index.jsp");
				}
			}
//			for (Cart c : cart_list) {
//				out.println(c.getId());
//			}
		}
	}

}
