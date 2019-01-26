import javax.servlet.http.HttpServlet;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import javax.servlet.http.Cookie;

public class Exit extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        String user = null;
        String sessId = null;
        Cookie[] sessionInfo;
        sessionInfo = req.getCookies();
        boolean q = (sessionInfo != null);
        if (q) {
            for (int i = 0; i < sessionInfo.length; i++) {
                String name = sessionInfo[i].getName();
                if (name.equals("sessionLog")) {
                    user = sessionInfo[i].getValue();
                }
                if (name.equals("sessionId")) {
                    sessId = sessionInfo[i].getValue();
                }
            }
        }
        File toDel = new File("Users/" + user + "/" + sessId);
        toDel.delete();
        resp.sendRedirect("index.html");
    }

}
