package Server;

import CacheManager.CacheManager;
import ClientHandler.ClientHandler;
import ClientHandler.MyCHandler;
import Solver.PipeGameSolver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class MyServer implements Server {

    private int port;
    private volatile boolean stop;

    // C-TOR
    public MyServer(int port) {
        this.port = port;
        stop = false;
    }

    // Methods

    public void Activate(ClientHandler clientHandler) throws IOException {
        System.out.println("Starting server");
        ServerSocket server = new ServerSocket(port);
        server.setSoTimeout(1000);
        while (!stop) {
            try {
                Socket aClient = server.accept(); // blocking call
                try {
                    System.out.println(aClient.toString());
                    clientHandler.handle(aClient.getInputStream(), aClient.getOutputStream());
                    aClient.getInputStream().close();
                    aClient.getOutputStream().close();
                } catch (IOException e) {
                    System.out.println(e.toString());
                } finally {
                    if (aClient != null)
                        aClient.close();
                }
            } catch (SocketTimeoutException e) {
                System.out.println(e.toString());
            } finally {
                try {
                    server.close();
                } catch (IOException e) {
                    System.out.println(e.toString());
                }
            }
        }
    }

    @Override
    public void start(ClientHandler clientHandler) {
        new Thread(() -> {
            try {
                Activate(clientHandler);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public void stop() {
        stop = true;
    }
}
