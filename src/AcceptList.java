import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;

public class AcceptList extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        resp.setContentType("text/html;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter pw = resp.getWriter();
        pw.println("<body bgcolor=\"pink\">");
        String userPath = req.getParameter("user");
        File folderDST = new File("Users/" + userPath);
        if(folderDST.exists()){
            HttpSession session = req.getSession();
            String way = (String) session.getAttribute("way");
            String listName = way.substring(way.lastIndexOf("/"));
            String wayToCopy = ("Users/" + userPath + "/" + listName);
            SendList.copyDir(way, wayToCopy);
            File deleteDir = new File(way);
            DeleteList.deleteFolder(deleteDir);
            pw.println("<h3> Список добавлен <h3>");
            pw.println("<a href=\"homepage?path=\"> На главную </a>");
        }
        else{
            pw.println("<h3> user not found </h3>");
        }
        pw.println("</body>");
    }
}
