package Server;
import ClientHandler.ClientHandler;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Comparator;


public class MyServer implements Server {
    static int port = 32;
    private ServerSocket serverSocket;
    private boolean stop = false;


    public static Comparator<RequestEnvelope> Comparator = new Comparator<RequestEnvelope>(){
        @Override
        public int compare(RequestEnvelope l, RequestEnvelope r) {
            String[] lValueSplitted = l.request.split("\n");
            int lvalue = lValueSplitted[0].length() * lValueSplitted.length;
            String[] rValueSplitted = r.request.split("\n");
            int rvalue = rValueSplitted[0].length() * rValueSplitted.length;

            return rvalue - lvalue;
        }
    };

    public MyServer(int port) {
        this.port = port;
    }

    @Override
    public void start(ClientHandler clientHandler) {
        new Thread(() -> {
            try {
                activate(clientHandler);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

    }

    private void activate(ClientHandler clientHandler) throws IOException {
        serverSocket = new ServerSocket(MyServer.port);
        serverSocket.setSoTimeout(1000);
        System.out.println("The server is up.");

        while (!this.stop) {
            try {
                Socket aClient = serverSocket.accept();
//                System.out.println("Accepted connection.");


                BufferedReader reader = new BufferedReader(new InputStreamReader(aClient.getInputStream()));
                String request = readRequest(reader);


                clientHandler.handle(aClient.getInputStream(), aClient.getOutputStream());
                aClient.close();
            } catch (SocketTimeoutException e) {
            }
        }
        serverSocket.close();
    }

    @Override
    public void stop() {
        stop = true;
    }

    private String readRequest(BufferedReader reader) {

        StringBuilder request = new StringBuilder();
        String tmpLine;

        try {
            if (reader != null) {
                while (!(tmpLine = reader.readLine()).equals("done")) {
                    request = request.append(tmpLine);
                    request = request.append(System.lineSeparator());
                }
//                System.out.println("Problem" + request.toString());
                return request.toString();
            }
        } catch (IOException exception) {
            System.out.println(exception.toString());
        }

        System.out.println("ERROR: Failed to read client request.");
        return null;

    }
}