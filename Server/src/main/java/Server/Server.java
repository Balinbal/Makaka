package Server;

import ClientHandler.ClientHandler;

// T - client handler
public interface Server {

    /////////
    //Methods
    /////////

    void start(ClientHandler clientHandler);
    void stop();


}
