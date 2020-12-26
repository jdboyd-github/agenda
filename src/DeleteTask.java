import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DeleteTask")
public class DeleteTask extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DeleteTask() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String keyword = request.getParameter("name");
		search(keyword, response);
	}

	void search(String keyword, HttpServletResponse response) throws IOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			DBConnection.getDBConnection();
			connection = DBConnection.connection;

			String selectSQL = "DELETE FROM Tasks WHERE name=?";
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, keyword);
			preparedStatement.execute();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		response.sendRedirect("ListTasks");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
