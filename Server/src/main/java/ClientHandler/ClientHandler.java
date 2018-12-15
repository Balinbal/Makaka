package ClientHandler;
import java.io.InputStream;
import java.io.OutputStream;

public interface ClientHandler {

    void handle(InputStream inFromClient, OutputStream outToClient, String request);

}
