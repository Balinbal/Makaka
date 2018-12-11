package Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerCommunicator {

    public ServerCommunicator(String ip, int port, int timeoutms)
    {
        this.ip = ip;
        this.port = port;
        this.timeout = timeoutms;
        this.sock = null;

    }

    public List<String> getSolution(char[][] board)
    {
        try {
            if (!this.connect())
                return null;
            PrintWriter out = new PrintWriter(this.sock.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(this.sock.getInputStream()));

            StringBuilder boardStr = new StringBuilder();
            for(int i = 0; i < board.length; ++i) {
                boardStr.append(board[i]);
                boardStr.append('\n');
            }
            out.println(boardStr.toString());
            out.println("done");
            out.flush();
            List<String> res = new ArrayList<String>();
            String line="";
            while (!line.equals("done") && line!=null) {
                line = in.readLine();
                res.add(line);
            }
            return res;
        } catch (IOException e) {
            return null;
        }

    }

    private boolean connect()
    {
        try {
            this.sock = new Socket(this.ip, this.port);
            this.sock.setSoTimeout(this.timeout);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private Socket sock;
    private String ip;
    private int port;
    private int timeout;
}
