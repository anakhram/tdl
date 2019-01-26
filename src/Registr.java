import javax.servlet.http.*;
import javax.servlet.*;
import java.io.IOException;
import java.io.*;
import java.io.PrintWriter;


public class Registr extends HttpServlet{
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter pw = resp.getWriter();
        req.setCharacterEncoding("UTF-8");
        String nameDir =req.getParameter("newlogin");
        pw.println("<link href=\"cssforTDL.css\" rel=\"stylesheet\" type=\"text/css\"/>");
        pw.println("<body id=checkreg>");
        File userDir = new File("Users/" + nameDir);
        if (userDir.mkdir()){
            File inbox = new File("Users/" + nameDir + "/" + "Inbox");
            inbox.mkdir();
            //pw.println("<H3>" + userDir.getAbsolutePath() + "</H3>");
            FileWriter userPass = new FileWriter(userDir.getAbsolutePath() + "/" + "password.txt");
            String pass = req.getParameter("newpass");
            userPass.write(pass);
            pw.println("<H3>" + "Пользователь " + nameDir + " зарегистрирован" + "</H3>");
            userPass.close();
            pw.println("<form action = auth.html> <p><input type =submit value = Вернуться> </p> </form>");
        }
        else{
            pw.println("<H3>" + "Ошибка: Пользователь " + nameDir + " уже существует" + "</H3>");
            pw.println("<form action = registration.html> <p><input type =submit value = Вернуться> </p> </form>");
        }
        pw.println("</body>");
    }

}
