import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ListTasks")
public class ListTasks extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ListTasks() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		search(response);
	}

	void search(HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html><head>" + //
				"<link rel=\"stylesheet\" href=\"style.css\">" + //
				"</head><body>" + //
				"<div class=\"menu\">" + //
				"<div class=\"label\">Task Menu</div>" + //
				"<div class=\"spacer\"></div>" + //
				"<a href=\"insert.html\" class=\"item\"><span>Insert</span></a>" + //
				"<a href=\"delete.html\" class=\"item\"><span>Delete</span></a>" + //
				"<a href=\"#\" class=\"item\"><span>View All</span></a>" + "</div><div class=\"form\">" + //
				"<h1>Task List</h1>" + //
				"<ul>");

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			DBConnection.getDBConnection();
			connection = DBConnection.connection;

			String selectSQL = "SELECT * FROM Tasks ORDER BY date, priority";
			preparedStatement = connection.prepareStatement(selectSQL);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				String name = rs.getString("name").trim();
				String date = rs.getString("date").trim();
				String priority = rs.getString("priority").trim();
				
				out.println("<li>" + name + "  |  " + date + "  |  " + priority + "</li><br>");
			}

			out.println("</ul>" + "</body></html>");

			rs.close();
			preparedStatement.close();
			connection.close();
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} catch (SQLException se2) {
			}
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
