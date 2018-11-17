package Server.ThreadPool;

import ClientHandler.ClientHandler;
import Server.RequestEnvelope;

import java.util.Comparator;
import java.util.concurrent.*;


public class PriorityJobScheduler {
    public static Comparator<RequestEnvelope> Comparator = new Comparator<RequestEnvelope>(){
        @Override
        public int compare(RequestEnvelope l, RequestEnvelope r) {
            String[] lValueSplitted = l.request.split("\n");
            int lvalue = lValueSplitted[0].length() * lValueSplitted.length;
            String[] rValueSplitted = r.request.split("\n");
            int rvalue = rValueSplitted[0].length() * rValueSplitted.length;

            return lvalue - rvalue;
        }
    };


    private ThreadPoolExecutor priorityJobPoolExecutor;
    private ExecutorService priorityJobScheduler
            = Executors.newSingleThreadExecutor();
    private PriorityBlockingQueue<RequestEnvelope> priorityQueue;

    public PriorityJobScheduler(Integer poolSize, Integer queueSize, ClientHandler handler) {
        priorityJobPoolExecutor = new ThreadPoolExecutor(poolSize, poolSize,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());

        priorityQueue = new PriorityBlockingQueue<RequestEnvelope>(
                queueSize,
                PriorityJobScheduler.Comparator);
        priorityJobScheduler.execute(() -> {
            while (true) {
                if (priorityJobPoolExecutor.getActiveCount() < poolSize) {
                    try {
                        ClientHandlerRunnable runnable = new ClientHandlerRunnable(priorityQueue.take(), handler);
                        priorityJobPoolExecutor.execute(runnable);
                    } catch (InterruptedException e) {
                        // exception needs special handling
                        break;
                    }
                }
                else {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }

    public void scheduleJob(RequestEnvelope envelope) {
        System.out.println("Schedule request for " + envelope.request);
        priorityQueue.add(envelope);
    }
}
