package mediavault.Viewers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mediavault.Ctrls.Ctrl;

@SuppressWarnings("serial")
public class Update extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		try {
			String desc = req.getParameter("desc");
			String filename = req.getParameter("filename");
			Boolean isShared = Boolean.parseBoolean(req
					.getParameter("isShared"));
			String blobkey = req.getParameter("blobkey");
			String contenttype = req.getParameter("contenttype");

			Ctrl.UpdateFile(desc, filename, blobkey, isShared, contenttype);
			res.sendRedirect("/msg.jsp?msg=Updated successfully");
		} catch (Exception ex) {
			res.sendRedirect("/msg.jsp?msg=Updated file failed:"
					+ ex.getMessage());
		}
	}
}
