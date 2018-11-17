package ClientHandler;
import Algorithms.BestFirstSearch;
import Board.MatrixBoard;
import Board.Position;
import Board.Solution;
import Board.Step;
import CacheManager.CacheManager;
import CacheManager.FileManager;
import Solver.PipeGameSolver;
import Solver.Solver;
import java.io.*;

// T is the board type, P is the position type
public class MyCHandler implements ClientHandler {

    private Solver<MatrixBoard, Position> solver;
    private CacheManager<Position> cacheManager;
    private BufferedReader reader;
    private PrintWriter writer;

//    public MyCHandler(Solver<T, P> solver, CacheManager<P> cacheManager) {
//        this.solver = solver;
//        this.cacheManager = cacheManager;
//    }

    public MyCHandler() {
        this.solver = new PipeGameSolver(new BestFirstSearch<>());
        this.cacheManager = new FileManager<Position>();
    }

    @Override
    public void handle(InputStream inFromClient, OutputStream outToClient, String request) {
        System.out.println(request + " - Handle ");
        this.reader = new BufferedReader(new InputStreamReader(inFromClient));
        this.writer = new PrintWriter(outToClient);

        if (request != null) {
            String problemId = String.valueOf(request.hashCode());
            try {
                // Check for existing solution
                Solution<Position> solution  = cacheManager.loadSolution(problemId);
                // If doesn't exist, solve it and return the solution (and save for the next time it is requested)
                if (solution == null) {
                    solution = this.solver.solve(request);
                    this.cacheManager.saveSolution(problemId, solution);
                }
                this.writeResponse(solution);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.cleanUp();
    }

    // Closes all open streams when finished handle
    private void cleanUp() {
        if (this.reader != null) {
            try {
                this.reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (this.writer != null) {
            this.writer.close();
        }
    }

    private void writeResponse(Solution<Position> response) {
        if (response != null && this.writer != null) {
            for (Step<Position> step: response.getSteps()) {
                writer.println(step.toString());
            }
        }
        writer.println("done");
        writer.flush();
    }
}
