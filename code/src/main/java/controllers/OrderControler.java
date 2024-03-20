package controllers;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.SortOrder;
import models.category.CategoryDAO;
import models.notification.NotificationDAO;
import models.notification.NotificationDTO;
import models.order.OrderDAO;
import models.order.OrderDTO;
import models.user.UserDTO;

@WebServlet(name = "OrderControler", urlPatterns = { "/order" })
public class OrderControler extends HttpServlet {

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		HttpSession session = request.getSession();
		UserDTO user = (UserDTO) session.getAttribute("usersession");
		request.setAttribute("categories", CategoryDAO.getCategories());
		
		if (user != null) {
			boolean anyRead = false;
			if (user != null) {
				List<NotificationDTO> notifications = NotificationDAO.getNotificationsOfUser(user.getId(), Duration.ofDays(30));
				request.setAttribute("notifications", notifications);			
				anyRead = notifications.stream()
					    .anyMatch(NotificationDTO::isRead);
			}
			request.setAttribute("anyRead", !anyRead);
			String tempOrderId = request.getParameter("orderId");
			if (tempOrderId != null) {
				Integer orderId = Integer.parseInt(tempOrderId);
				OrderDTO order = OrderDAO.getOrder(orderId);
				request.setAttribute("order", order);
				request.getRequestDispatcher("/pages/DetailOrder.jsp").forward(request, response);
				return;
			}
			String status = request.getParameter("status") != null ? request.getParameter("status") : "";
			String sortBy = request.getParameter("sortBy") != null ? request.getParameter("sortBy") : "order_id";
			String sortOrder = request.getParameter("sortOrder") != null ? request.getParameter("sortOrder") : "ASC";

			List<OrderDTO> orders = OrderDAO.getOrdersByUserId(user.getId(), status.toUpperCase(), sortBy,
					SortOrder.valueOf(sortOrder.toUpperCase()));

			request.setAttribute("orders", orders);
			request.setAttribute("currentStatus", status);
			request.setAttribute("currentSortBy", sortBy);
			request.setAttribute("currentSortOrder", sortOrder);

			request.getRequestDispatcher("/pages/Order.jsp").forward(request, response);
		} else {
			response.sendRedirect("login.jsp");
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	public String getServletInfo() {
		return "Short description";
	}// </editor-fold>

}
