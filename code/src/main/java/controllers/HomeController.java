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
import models.custom.CustomDAO;
import models.notification.NotificationDAO;
import models.notification.NotificationDTO;
import models.product.ProductDAO;
import models.product.ProductQuery;
import models.user.UserDTO;

@WebServlet(name = "HomeController", urlPatterns = { "/" })
public class HomeController extends HttpServlet {

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession();
		UserDTO user = (UserDTO) session.getAttribute("usersession");
		
		request.setAttribute("tieuThuyet", ProductDAO.getProductsByCategoryId(1,
				new ProductQuery(null, null, ProductQuery.SortParam.NAME, SortOrder.DESC, 1, 20)));
		request.setAttribute("tacPhamKinhDien", ProductDAO.getProductsByCategoryId(5,
				new ProductQuery(null, null, ProductQuery.SortParam.NAME, SortOrder.DESC, 1, 20)));
		request.setAttribute("kinhDi", ProductDAO.getProductsByCategoryId(16,
				new ProductQuery(null, null, ProductQuery.SortParam.NAME, SortOrder.DESC, 1, 20)));
		request.setAttribute("tranhTruyen", ProductDAO.getProductsByCategoryId(9,
				new ProductQuery(null, null, ProductQuery.SortParam.NAME, SortOrder.DESC, 1, 20)));
		request.setAttribute("mostSelledProducts", CustomDAO.getMostSelledProducts());
		request.setAttribute("categories", CategoryDAO.getCategories());
		
		boolean anyRead = false;
		if (user != null) {
			List<NotificationDTO> notifications = NotificationDAO.getNotificationsOfUser(user.getId(), Duration.ofDays(30));
			request.setAttribute("notifications", notifications);			
			anyRead = notifications.stream()
				    .anyMatch(NotificationDTO::isRead);
		}
		request.setAttribute("anyRead", !anyRead);			
		
		request.getRequestDispatcher("/pages/Home.jsp").forward(request, response);
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
