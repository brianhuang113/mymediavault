package mediavault.Viewers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class AutoSearch extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String q = req.getParameter("q");
		PrintWriter wr = res.getWriter();
		wr.write(Viewer.AutoSearch(q));
		wr.flush();
		wr.close();
	}
}
