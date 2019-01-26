import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Properties;

public class SaveEdit extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        resp.setContentType("text/html;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");
        String name = req.getParameter("edittheme");
        String body = req.getParameter("editbody");
        String deadline = req.getParameter("editdeadline");
        HttpSession session = req.getSession();
        String path = (String)session.getAttribute("path");
        String home = (String)req.getSession().getAttribute("home");
        //LocalDate date = LocalDate.now();
        UserList edited = new UserList(name, body, deadline, req.getParameter("date"));
        Properties saver = new Properties();
        saver.put("name", edited.name);
        saver.put("body", edited.body);
        saver.put("date",edited.date.toString());
        if (edited.deadline != null) {
            saver.put("deadline", edited.deadline.toString());
        }
        System.out.println(path);
        String newPath = path.substring(0, path.lastIndexOf("/"));
            try {
                FileOutputStream pathToSave = new FileOutputStream(home + "/" + path + "/" + "properties.dat");
                saver.store(pathToSave, "properties");
                pathToSave.close();
                File createDir = new File (home + "/" + path);
                File redir = new File(home + "/" + newPath + "/" + edited.name);
                createDir.renameTo(redir);
            } catch (IOException e) {
                e.printStackTrace();
            }
        //resp.sendRedirect("");
       // resp.sendRedirect("homepage?path=" + newPath + "/" + edited.name);
        String sr = new String (("homepage?path=" + newPath + "/" + edited.name).getBytes("UTF-8"),"ISO-8859-1");
        resp.sendRedirect(sr);

    }
}
