package models.custom.pagination;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.jetbrains.annotations.NotNull;

import models.author.AuthorQuery;
import models.order.OrderQuery;
import models.product.ProductDAO;
import models.product.ProductQuery;
import models.user.UserDAO;
import models.user.UserQuery;
import utils.DBUtils;

public class PaginationDAO {

	public static @NotNull PaginationDTO getProductsPagination(@NotNull ProductQuery query) {
		int total = ProductDAO.countProducts(query);
		String itemLink = "./search?keyword="+query.getKeyword()+"&sort="+query.getSortBy()+"&order="+query.getSortOrder()+"&page=";
		return new PaginationDTO(total, 1, query.getPage(), query.getLimit(), itemLink);
	}
	
	public static @NotNull PaginationDTO getProductsPagination(int categoryId, @NotNull ProductQuery query) {
		int total = ProductDAO.countProducts(categoryId,query);
		String itemLink = "./search?category="+categoryId+"&sort="+query.getSortBy()+"&order="+query.getSortOrder()+"&page=";
		return new PaginationDTO(total, 1, query.getPage(), query.getLimit(), itemLink);
	}
	
	public static @NotNull PaginationDTO getProductsManagerPagination(@NotNull ProductQuery query) {
		int total = ProductDAO.countProducts(query);
		String itemLink = "./admin?tab=ProductManager";
		if (query.getKeyword() != null) {
			itemLink += "&keyword="+query.getKeyword();			
		}
		itemLink += "&sort="+query.getSortBy()+"&order="+query.getSortOrder()+"&page=";
		return new PaginationDTO(total, 1, query.getPage(), query.getLimit(), itemLink);
	}

	public static @NotNull PaginationDTO getOrdersPagination(@NotNull OrderQuery query) {
		PaginationDTO pagination = null;
		try {
			Connection con = DBUtils.getConnection();
			String sql = "SELECT COUNT (order_id) as length FROM (SELECT *, ROW_NUMBER() OVER (ORDER BY [order_id] DESC) AS row_num FROM [order]) as num_tb";
			if (query.getSearch() != null) {

			}

			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();

			if (rs != null) {
				while (rs.next()) {
					int length = rs.getInt("length");
					pagination = new PaginationDTO(length, 1, (int) Math.ceil(length / query.getLimit()) + 1,
							query.getPage(), query.getLimit(), "./admin?tab=OrderManager&page=");
				}
			}
			return pagination;
		} catch (SQLException ex) {
			System.out.println("Failed to get products. Details:" + ex.getMessage());
			ex.printStackTrace();
		}
		return null;
	}

	public static @NotNull PaginationDTO getCustomersPagination(@NotNull UserQuery query) {
		int total = UserDAO.countUsers(query);
		return new PaginationDTO(total, 1, query.getPage(), query.getLimit(), "./admin?tab=CustomerManager&page=");
	}

	public static @NotNull PaginationDTO getAuthorsPagination(@NotNull AuthorQuery query) {
		PaginationDTO pagination = null;
		try {
			Connection con = DBUtils.getConnection();
			String sql = "SELECT COUNT (author_id) as length FROM (SELECT *, ROW_NUMBER() OVER (ORDER BY [author_id] DESC) AS row_num FROM [author]) as num_tb";
			if (query.getSearchBy() != null) {

			}

			PreparedStatement stmt = con.prepareStatement(sql);
//            stmt.setString(1, query.getSearch().toString());
//            stmt.setString(2, "%" + query.getKeyword() + "%");
			ResultSet rs = stmt.executeQuery();

			if (rs != null) {
				while (rs.next()) {
					int length = rs.getInt("length");
					pagination = new PaginationDTO(length, 1, (int) Math.ceil(length / query.getLimit()) + 1,
							query.getPage(), query.getLimit(), "./admin?tab=AuthorManager&page=");
				}
			}
			return pagination;
		} catch (SQLException ex) {
			System.out.println("Failed to get products. Details:" + ex.getMessage());
			ex.printStackTrace();
		}
		return null;
	}
}
