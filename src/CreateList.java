

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class CreateList extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        String user = null;
        Cookie [] userInfo = req.getCookies();

        if (userInfo != null)
        {
            for(int i = 0; i < userInfo.length; i++){
                String name = userInfo[i].getName();
                if (name.equals("sessionLog")){
                    user = userInfo[i].getValue();
                }
            }
        }

        String name = req.getParameter("newtheme");
        String body = req.getParameter("newbody");
        String deadline = req.getParameter("newdeadline");
        HttpSession session = req.getSession();
        String path = (String)session.getAttribute("path");
        LocalDate date = LocalDate.now();
        UserList created = new UserList(name, body, deadline, date.toString());
        Properties saver = new Properties();
        saver.put("name", created.name);
        saver.put("body", created.body);
        if (created.deadline != null) {
            saver.put("deadline", created.deadline.toString());
        }
        saver.put("date",created.date.toString());
        File createDir = new File ("Users/" + user + "/" + path + "/" + name);
        System.out.println(path);
        if (createDir.mkdir()) {
            try {
                FileOutputStream pathToSave = new FileOutputStream(createDir + "/" + "properties.dat");
                saver.store(pathToSave, "properties");
                pathToSave.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //resp.sendRedirect("");
        String sr = new String (("homepage?path=" + path).getBytes("UTF-8"),"ISO-8859-1");
        resp.sendRedirect(sr);
       //resp.sendRedirect( resp.encodeRedirectURL("homepage?path=" + path));


    }
}
