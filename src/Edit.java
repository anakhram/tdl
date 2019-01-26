import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

public class Edit extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter pw = resp.getWriter();
        pw.println("<link href=\"cssforTDL.css\" rel=\"stylesheet\" type=\"text/css\"/>");
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");
        String home = (String)req.getSession().getAttribute("home");
        FileInputStream readProp = new FileInputStream(home + "/" + req.getParameter("path")+ "/" + "properties.dat");
        Properties toLoadProp = new Properties();
        toLoadProp.load(readProp);
        UserList listToEdit;
        readProp.close();
        listToEdit = new UserList(toLoadProp.getProperty("name"), toLoadProp.getProperty("body"), toLoadProp.getProperty("deadline"), toLoadProp.getProperty("date"));
        pw.println("<body id = \"editing\"><h2> Редактирование</h2>" +
"<form name=\"editlist\" method=\"post\" action=\"saveedit\">" +
"<p><b>Тема:  </b>"+
 "<input type =\"text\" name =\"edittheme\" placeholder =\"Введите тему\" value=" + listToEdit.name + "></p>"+
"<p><b>Дэдлайн (в формате yyyy-mm-dd):  </b>" +
    "<input type =\"text\" name =\"editdeadline\" pattern = \"[0-9]{4}-[0-9]{2}-[0-9]{2}\" placeholder =\"Введите дэдлайн\" value=" + listToEdit.deadline + "></p>" +
"<p><b>Содержание списка: </b><Br>" +
    "<p> </p>" +
"<textarea name=\"editbody\" cols=\"40\" rows=\"10\" listToEdit.body>" + listToEdit.body +"</textarea></p>" +
    "<p><input type=\"submit\" value=\"Сохранить\">" +
    "<input type=\"reset\" value=\"Очистить\"></p>" +
                        "<input type = \"hidden\" name=\"date\" value=" + toLoadProp.getProperty("date") + ">"+
"</form></body> ");
    }
}
