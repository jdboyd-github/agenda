
/**
 * @file SimpleFormInsert.java
 */
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/InsertTask")
public class InsertTask extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public InsertTask() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userName = request.getParameter("name");
		String email = request.getParameter("date");
		String phone = request.getParameter("priority");

		Connection connection = null;
		String insertSql = " INSERT INTO Tasks (id, name, date, priority) values (default, ?, ?, ?)";

		try {
			DBConnection.getDBConnection();
			connection = DBConnection.connection;
			PreparedStatement preparedStmt = connection.prepareStatement(insertSql);
			preparedStmt.setString(1, userName);
			preparedStmt.setString(2, email);
			preparedStmt.setString(3, phone);
			preparedStmt.execute();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Set response content type
		response.sendRedirect("ListTasks");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
