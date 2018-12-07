import ClientHandler.MyCHandler;
import Server.MyServer;
import Server.Server;

public class MainServer {

    public static void main(String[] args) {

        Server s =  new MyServer(5555);
        s.start(new MyCHandler());
    }
}
