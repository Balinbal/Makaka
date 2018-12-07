package Server;

import ClientHandler.ClientHandler;

import java.io.IOException;

// T - client handler
public interface Server {

    /////////
    //Methods
    /////////

    void start(ClientHandler clientHandler);
    void stop();


}
