import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import javax.servlet.http.Cookie;
import java.nio.file.Files;
import java.util.Random;

public class Auth extends HttpServlet{

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter pw = resp.getWriter();
        pw.println("<body id=error>");
        req.setCharacterEncoding("UTF-8");
        String login = req.getParameter("login");
        String pass = req.getParameter("pass");
        File check = new File("Users/" + login);
        if (check.exists()){
            FileReader password = new FileReader(check.getAbsolutePath() + "/" + "password.txt");
            BufferedReader passwordbr = new BufferedReader(password);
            String content = passwordbr.readLine();
            if(content.equals(pass)) {
                Random randId = new Random();
                Integer numId = 1000000 + randId.nextInt(9999999 - 1000000);
                Cookie sessionId = new Cookie("sessionId", numId.toString());
                Cookie sessionLog = new Cookie("sessionLog", login);
                FileWriter userSession = new FileWriter("Users/" + login + "/" + numId.toString());
                resp.addCookie(sessionId);
                resp.addCookie(sessionLog);
               // userSession.write(numId.toString());
                userSession.close();
                //pw.println("<H3>" + "." + "</H3>");
                password.close();

                /*RequestDispatcher loginPoster = req.getRequestDispatcher("/homepage");
                loginPoster.forward(req, resp);*/

                resp.sendRedirect("homepage?path=");
                //pw.println("<h1> asdasdas </h1>");
                //pw.println("<form action = homepage.html> <p><input type =submit value = Войти> </p> </form>");
            }
            else{
                pw.println("<H3>" +  "Неверный логин и/или пароль" + "</H3>");
            }
        }
        else{
            pw.println("<H3>" +  "Неверный логин и/или пароль" + "</H3>");
        }
        pw.println("</body>");
    }
}