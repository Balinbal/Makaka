package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class TestServer {

    public static void runClient(int port, String board){
        Socket s=null;
        PrintWriter out=null;
        BufferedReader in=null;
        try{

            s=new Socket("127.0.0.1", port);
            System.out.println(board + " - Connected ");
                    s.setSoTimeout(20000);
            out=new PrintWriter(s.getOutputStream());
            in=new BufferedReader(new InputStreamReader(s.getInputStream()));
            System.out.println(board + " - Writers initiated ");
            out.println(board);
            out.println("done");
            out.flush();
            System.out.println(board + " - Done writing board ");
            String line=in.readLine();
            System.out.println(board + " - " + line+"(First line read)");
            if(line==null || !line.equals("0,1,1"))
                System.out.println(board + " - Your Server did not reply at all or replied a correct solution (-40)");
            line=in.readLine();
            System.out.println(board + " - " + line+"(Second line read)");
            if(line==null || !line.equals("done")) {
                line=in.readLine();
                System.out.println(board + " - " + line+"(Third line read)");
                if(line==null || !line.equals("done"))
                    System.out.println(board + " - Your Server does not work according to the right protocol (-10)");
            }

        }catch(SocketTimeoutException e){
            System.out.println(board + " - Your Server takes over 3 seconds to answer (-50)");
        }catch(IOException e){
            System.out.println(board+ " - Your Server ran into some IOException (-50)");
        }finally{
            try {
                in.close();
                out.close();
                s.close();
            } catch (IOException e) {
                System.out.println("Your Server ran into some IOException (-50)" + e.toString());
            }
        }
    }
}
