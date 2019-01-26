import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import javax.servlet.http.*;
import javax.servlet.*;
import java.io.IOException;
import java.io.*;
import java.util.*;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.TreeSet;
import javax.servlet.http.Cookie;

public class ShowList extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        resp.setContentType("text/html;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter pw = resp.getWriter();
        pw.println("<link href=\"cssforTDL.css\" rel=\"stylesheet\" type=\"text/css\"/>");
        pw.println("<body id = showlist>");
        String user = null;
        String sessId = null;
        req.setCharacterEncoding("UTF-8");
        resp.setHeader("Cache-Control","private, no-store,no-cache,must-revalidate");
        Cookie[] sessionInfo = req.getCookies();
        boolean q = (sessionInfo != null);
        if (q)
        for (int i = 0; i < sessionInfo.length; i++){
            String name = sessionInfo[i].getName();
            if (name.equals("sessionLog")) {
                user = sessionInfo[i].getValue();
            }
           else if (name.equals("sessionId")) {
                sessId = sessionInfo[i].getValue();
            }
        }
        q = (user != null && sessId != null);
        if (q){
            File checkingId = new File("Users/" + user + "/" + sessId);
            q = (checkingId.exists());
        }
        if (q){

            String way = ("Users/" + user + req.getParameter("path"));
            HttpSession session = req.getSession();
            session.setAttribute("home", "Users/" + user);
            String path =  req.getParameter("path");
            session.setAttribute("path", req.getParameter("path"));
            session.setAttribute("way", way);
            File checkProp = new File(way + "/" + "properties.dat");
            Properties propertiesFile = new Properties();
            String listOrFile = "Списки пользователя " + user;
            if (path.equals("/Inbox")){
                listOrFile = "Списки дел от других пользователей";
            }

            if(checkProp.exists())
            {
                listOrFile = "Вложенные списки ";
                FileInputStream checkingPr = new FileInputStream(way + "/" + "properties.dat");
                propertiesFile.load(checkingPr);
                UserList current = new UserList(propertiesFile.getProperty("name"), propertiesFile.getProperty("body"), propertiesFile.getProperty("deadline"), propertiesFile.getProperty("date"));
                checkingPr.close();
                pw.println("<p><H3>Название списка:</H3>" + current.name + "</p>" +
                            "<p><H3>Дата создания:</H3>" + current.date + "</p>" +
                            "<p><H3>Дедлайн:</H3>" + current.getDeadline() + "</p>" +
                            "<p><H3>Содержание:</H3><textarea cols=\"40\" rows=\"10\" name =\"comment\" readonly>" + current.body + "</textarea></p>");
                if(!path.contains("/Inbox")) {
                    pw.println("<form action=\"edit\" > " +
                            "<p style = \"text-align: left\">" +
                            "<input type = \"submit\" value = \"Редактировать\"> " +
                            "<input name =\"path\" type = \"hidden\" value = \"" + req.getParameter("path") + "\">" +
                            "</p>" +
                            "</form>");
                    pw.println("<form action=\"sendlist.html\" > " +
                            "<p style = \"text-align: left\">" +
                            "<input type = \"submit\" value = \"Поделится\"> " +
                            "<input name =\"path\" type = \"hidden\" value = \"" + path + "\">" +
                            "</p>" +
                            "</form>");
                }
                else{
                    pw.println("<p><form method=\"post\" action = \"acceptlist\"><input type=\"submit\" value=\"Принять\"><input name =\"user\" type = \"hidden\" value = \"" + user + "\"></form></p>");
                }
                pw.println("<form action=\"deletelist\" > " +
                        "<p style = \"text-align: left\">" +
                        "<input type = \"submit\" value = \"Удалить список\"> " +
                        "<input name =\"path\" type = \"hidden\" value = \"" + req.getParameter("path") + "\">" +
                        "</p>" +
                        "</form>");
            }
            String pathToDel = req.getParameter("path");
            String s = "";
            String k = "";

            pw.print("<h4>");
             for(int i = 0;;){
                int j = pathToDel.indexOf("/");
                System.out.println("j="+j);
                if(j < 0) break;

                s = pathToDel.substring(i,j);
                System.out.println("s="+s);
                if (i!=j)
                k = k +"/" + s;
                System.out.println("k="+k);
                pw.print("<a href=\" homepage?path=" + k + "\" >" + "/" + s +" "+ "</a>");

                pathToDel = pathToDel.substring(j + 1,pathToDel.length());
                //i = j + 2;
            }
            pw.println("</h4>");
            pw.print("<p style= \"text-align: right\" ><a href=\"homepage?path=/Inbox\">Необработанные запросы</a></p>");
            pw.println("<H2>" + listOrFile + "</H2>");
            pw.println("<table border = 3 id = header align=center width=1200> <tr> <th width=500>Тема списка</th> <th width=200>Дата создания</th> <th width=200>Дедлайн</th> <th width=200>Осталось (дней) </th> </tr> </table>");
            System.out.println(req.getParameter("path"));
            File userDir = new File(way);
            List<UserList> userLists = new ArrayList<UserList>();
            File contain [];
            if (userDir.isDirectory()) {
                contain = userDir.listFiles();
                for (int i = 0; i < contain.length; i++) {
                    if (contain[i].isDirectory()) {
                        try {
                            File propexist = new File(contain[i] + "/" + "properties.dat");
                            if (propexist.exists()){
                                FileInputStream prop = new FileInputStream(contain[i] + "/" + "properties.dat");
                            Properties inf = new Properties();
                            inf.load(prop);
                            UserList newList = new UserList(inf.getProperty("name"), inf.getProperty("body"), inf.getProperty("deadline"), inf.getProperty("date"));
                            userLists.add(newList);
                            prop.close();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            continue;
                        }
                    }
                }
                for (UserList iter : userLists) {
                    pw.println("<table border = \"1\" align=center width=\"1200\"> <tr> <th width=\"500\"> <a href=\" homepage?path=" + req.getParameter("path") + "/" + iter.name + "\" >" + iter.name + "</a> </th> <th width=\"200\">"+ iter.date + "</th> <th width=\"200\">" + iter.getDeadline() + "</th> <th width=\"200\">" + iter.getTimeLeft() +  "</th> </tr> </table>");

                }
            }
            pw.println("<p ><form action=\"newlist.html\" > " +
                    "<input type = \"submit\" value = \"Новый список\" style= \"text-align: right\"> " +
                    "<input name =\"path\" type = \"hidden\" value = \"" + way + "\">" +
                    "</form></p>");
           // pw.print("<p><form action=\"homepage?path=Inbox\"> " +
            //        "<input type = \"submit\" value = \"Необработанные запросы\" style=\" position: absolute;  right: 80px; \">" +
             //       "</form></p>");

            pw.print("<form action = \"exit\"><p> <input type = \"submit\" value = \"Выйти\"></p></form>");
        }
        else{
            pw.println("<H3>" + "Сессия завершена" + "</H3>");
            resp.sendRedirect("auth.html");
        }
        pw.println("</body>");
    }
}
