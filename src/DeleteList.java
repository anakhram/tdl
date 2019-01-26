import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.File;

public class DeleteList extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        resp.setCharacterEncoding("UTF-8");
        String home = (String) req.getSession().getAttribute("home");
        String pathToDel = req.getParameter("path");

        File qq = new File(home + "/" + pathToDel);
        System.out.println(qq);
        deleteFolder(qq);
        String newPath = pathToDel.substring(0, pathToDel.lastIndexOf("/"));
        String sr = new String (("homepage?path=" + newPath).getBytes("UTF-8"),"ISO-8859-1");
        resp.sendRedirect(sr);
    }

    public static void deleteFolder(File s) {
        try {
        File[] ff = s.listFiles();

        if (ff != null) {
            for (File f : ff) {
                System.out.println(f.toString());
                    if (f.isDirectory()) {
                        deleteFolder(f);

                    } else {
                        f.delete();
                    }
                }
            }
            s.delete();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
