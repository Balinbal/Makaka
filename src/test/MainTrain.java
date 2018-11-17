package test;

//import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainTrain {

    public static void main(String[] args) throws InterruptedException {

        try {
            ExecutorService exec = Executors.newFixedThreadPool(5);
            exec.execute(() -> {
                TestServer.runClient(5555, "s|g");
            });
            Thread.sleep(50);
            exec.execute(() -> {
                TestServer.runClient(5555, "s||g");
            });
            Thread.sleep(50);
            exec.execute(() -> {
                TestServer.runClient(5555, "s|g");
            });
            Thread.sleep(50);
            exec.execute(() -> {
                TestServer.runClient(5555, "s||g");
            });
            Thread.sleep(50);
            exec.execute(() -> {
                TestServer.runClient(5555, "s||g");
            });
            //TestServer.runClient(5555);
        } finally {
        }
    }
}
