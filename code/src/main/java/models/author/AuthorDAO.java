package models.author;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import utils.DBUtils;

public class AuthorDAO {

	public static @NotNull List<AuthorDTO> getAuthors(@NotNull AuthorQuery query) {
		List<AuthorDTO> list = new ArrayList<>();
		try {
			Connection con = DBUtils.getConnection();
			String sql = "select * from [author] where [author_id] >= ? and [author_id] <= ?";
			if (query.getSearchBy() != null) {
				sql += " and [" + query.getSearchBy() + "] like ?";
			}
			if (query.getSortBy() != null) {
				sql += " order by [" + query.getSortBy() + "] " + query.getSortOrder();
			}
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, query.getStartId());
			stmt.setInt(2, query.getEndId());
			if (query.getKeyword() != null) {
				stmt.setString(3, "%" + query.getKeyword() + "%");
			}
			ResultSet rs = stmt.executeQuery();
			while (rs != null && rs.next()) {
				int id = rs.getInt("author_id");
				String name = rs.getString("name");
				list.add(new AuthorDTO(id, name));
			}
            con.close();
		} catch (SQLException ex) {
			System.out.println("Failed to get authors. Details:" + ex.getMessage());
			ex.printStackTrace();
		}
		return list;
	}
	
	public static @NotNull List<AuthorDTO> getAuthors() {
		List<AuthorDTO> list = new ArrayList<>();
		try {
			Connection con = DBUtils.getConnection();
			String sql = "select * from [author] order by name asc";
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while (rs != null && rs.next()) {
				int id = rs.getInt("author_id");
				String name = rs.getString("name");
				list.add(new AuthorDTO(id, name));
			}
		} catch (SQLException ex) {
			System.out.println("Failed to get authors. Details:" + ex.getMessage());
			ex.printStackTrace();
		}
		return list;
	}

	public static @NotNull List<AuthorDTO> getAuthorsTest(@NotNull AuthorQuery query) {
		List<AuthorDTO> list = new ArrayList<>();
		try {
			Connection con = DBUtils.getConnection();
			String sql = "SELECT * FROM (SELECT *, ROW_NUMBER() OVER (ORDER BY " + query.getSortBy() + " "
					+ query.getSortOrder()
					+ ") AS row_num FROM [author]) as num_tb WHERE row_num >= ? AND row_num <= ?";

			PreparedStatement stmt = con.prepareStatement(sql);

			stmt.setInt(1, query.getStartId());
			stmt.setInt(2, query.getEndId());
			ResultSet rs = stmt.executeQuery();

			while (rs != null && rs.next()) {
				int id = rs.getInt("author_id");
				String name = rs.getString("name");
				list.add(new AuthorDTO(id, name));
			}

		} catch (SQLException ex) {
			System.out.println("Failed to get authors. Details:" + ex.getMessage());
			ex.printStackTrace();
		}
		return list;
	}

	public static @Nullable AuthorDTO getAuthorById(int authorId) {
		AuthorDTO author = null;
		try {
			Connection con = DBUtils.getConnection();
			String sql = "SELECT * FROM [author] WHERE [author_id] = ?";
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setInt(1, authorId);
			ResultSet rs = stm.executeQuery();
			if (rs != null && rs.next()) {
				author = new AuthorDTO(rs.getInt("author_id"), rs.getString("name"));
			}
			con.close();
		} catch (SQLException ex) {
			System.out.println("Error in getting author. Details:" + ex.getMessage());
			ex.printStackTrace();
		}
		return author;
	}

	public static @Nullable Integer addAuthor(@NotNull AuthorDTO author) {
		Integer result = null;
		try {
			Connection con = DBUtils.getConnection();
			String sql = "INSERT INTO [author] ([name]) VALUES (?)";
			PreparedStatement stm = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stm.setString(1, author.getName());
			if (stm.executeUpdate() > 0) {
				ResultSet rs = stm.getGeneratedKeys();
				if (rs.next()) {
					result = rs.getInt(1);
				}
			}
		} catch (SQLException ex) {
			System.out.println("Error in adding author. Details:" + ex.getMessage());
			ex.printStackTrace();
		}
		return result;
	}

	public static Integer updateAuthor(@NotNull AuthorDTO author) {
		Integer result = null;
		try {
			Connection con = DBUtils.getConnection();
			String sql = " UPDATE [author] SET [name] = ? WHERE [author_id] = ?";
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setString(1, author.getName());
			stm.setInt(2, author.getId());
			stm.executeUpdate();
			result = author.getId();
		} catch (SQLException ex) {
			System.out.println("Error in updating author. Details:" + ex.getMessage());
			ex.printStackTrace();
		}
		return result;
	}

	public static boolean deleteAuthor(int authorId) {
		boolean ok = false;
		try {
			Connection con = DBUtils.getConnection();
			String sql = "DELETE FROM [author] WHERE [author_id] = ? ";
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setInt(1, authorId);
			ok = stm.executeUpdate() > 0;
			con.close();
		} catch (SQLException ex) {
			System.out.println("Error in deleting author. Details:" + ex.getMessage());
			ex.printStackTrace();
		}
		return ok;
	}

	public static @NotNull List<AuthorDTO> getAuthorsOfProduct(int productId) {
		List<AuthorDTO> authors = new ArrayList<>();
		try {
			Connection con = DBUtils.getConnection();
			String sql = "SELECT a.author_id, a.name FROM [author] a " + " INNER JOIN [authors_of_product] pa "
					+ " ON a.author_id = pa.author_id " + " WHERE pa.product_id = ?";
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setInt(1, productId);
			ResultSet rs = stm.executeQuery();
			while (rs != null && rs.next()) {
				AuthorDTO author = new AuthorDTO(rs.getInt("author_id"), rs.getString("name"));
				authors.add(author);
			}
            con.close();
		} catch (SQLException ex) {
			System.out.println("Error in getting authors of product. Details: " + ex.getMessage());
			ex.printStackTrace();
		}
		return authors;
	}

	public static boolean addAuthorsOfProduct(int productId, int authorId) {
		boolean ok = false;
		try {
			Connection con = DBUtils.getConnection();
			String sql = "INSERT INTO [authors_of_product] ([product_id], [author_id]) VALUES (?, ?)";
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setInt(1, productId);
			stm.setInt(2, authorId);
			ok = stm.executeUpdate() > 0;
			con.close();
		} catch (SQLException ex) {
			System.out.println("Error in adding authors of product. Details: " + ex.getMessage());
			ex.printStackTrace();
		}
		return ok;
	}
}
