import javax.servlet.http.Cookie;
import java.io.*;

public class Directory {
    public void changing(String sessionId,String path){
        try {
            FileWriter direct = new FileWriter(path + "/" + sessionId);
            direct.write(sessionId);
            direct.close();
        }
        catch(Exception e)
        {

        }
    }

    public String getDir(String sessionId, String user){
    String buff = null;
        try{
            FileReader readDir = new FileReader("Users/" + user + "/" + sessionId);
            BufferedReader br = new BufferedReader(readDir);
            buff = br.readLine();
            readDir.close();
            br.close();
        }
        catch(Exception e) {

        }
        return buff;
    }
}
