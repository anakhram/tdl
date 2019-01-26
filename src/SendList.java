import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;


public class SendList extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        resp.setContentType("text/html;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter pw = resp.getWriter();
        pw.println("<body id = \"sendlist\">");
        String recipient = req.getParameter("userforsend");
        File folderOfRecipient = new File("Users/" + recipient);
        if(folderOfRecipient.exists()){
            HttpSession session = req.getSession();
            String path =(String) session.getAttribute("path");
            String way = (String) session.getAttribute("way");
            String listName = way.substring(way.lastIndexOf("/"));
            String wayToCopy = ("Users/" + recipient + "/" + "Inbox"  + "/" + listName);
            copyDir(way, wayToCopy);
            pw.println("<body bgcolor=\"pink\">");
            pw.println("<h3> Список отправлен пользователю " + recipient + "<h3>");
            pw.println("<p><a href=\"homepage?path=" + path + "\"> Назад </a></p>");
        }
        else{
            pw.println("<h3> user not found </h3>");
        }
        pw.println("</body>");
    }

    public static boolean copyDir(final String way, final String wayToCopy) {
        final File srcFile = new File(way);
        final File dstFile = new File(wayToCopy);
        if (srcFile.exists() && srcFile.isDirectory() && !dstFile.exists()) {
            dstFile.mkdir();
            File nextSrcFile;
            String nextSrcFilename, nextDstFilename;
            for (String filename : srcFile.list()) {
                nextSrcFilename = srcFile.getAbsolutePath()
                        + File.separator + filename;
                nextDstFilename = dstFile.getAbsolutePath()
                        + File.separator + filename;
                nextSrcFile = new File(nextSrcFilename);
                if (nextSrcFile.isDirectory()) {
                    copyDir(nextSrcFilename, nextDstFilename);
                } else {
                    copyFile(nextSrcFilename, nextDstFilename);
                }
            }
            return true;
        } else {
            System.out.println("Error: folder already exist");
            return false;
        }
    }

    public static boolean copyFile(final String way, final String wayToCopy) {
        final File srcFile = new File(way);
        final File dstFile = new File(wayToCopy);
        if (srcFile.exists() && srcFile.isFile() && !dstFile.exists()) {
            try (InputStream in = new FileInputStream(srcFile);
                 OutputStream out = new FileOutputStream(dstFile)) {
                byte[] buffer = new byte[65535];
                int bytes;
                while ((bytes = in.read(buffer)) > 0) {
                    out.write(buffer, 0, bytes);
                }
            } catch (FileNotFoundException ex) {
                return false;
            } catch (IOException ex) {
                return false;
            }
            return true;
        } else {
            return false;
        }
    }
}

