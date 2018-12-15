package Server.ThreadPool;

import ClientHandler.ClientHandler;
import Server.RequestEnvelope;

import java.io.IOException;

public class ClientHandlerRunnable implements Runnable {

    private RequestEnvelope envelope;
    private ClientHandler handler;

    ClientHandlerRunnable (RequestEnvelope e, ClientHandler h)
    {
        this.envelope = e;
        this.handler = h;
    }

    @Override
    public void run(){
        try {
            System.out.println("Handling " + this.envelope.request);
            this.handler.handle(
                    this.envelope.clientSocket.getInputStream(),
                    this.envelope.clientSocket.getOutputStream(),
                    this.envelope.request);
            this.envelope.clientSocket.close();
        }
        catch (IOException e) {

        }
    }
}
