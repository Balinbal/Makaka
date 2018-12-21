package ClientHandler;
import Algorithms.DFSSearcher;
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
public class ServletHandler {

    private Solver<MatrixBoard, Position> solver;
    private CacheManager<Position> cacheManager;
    private BufferedReader reader;
    private PrintWriter writer;


    public ServletHandler() {
        this.solver = new PipeGameSolver(new DFSSearcher<>());
        this.cacheManager = new FileManager<Position>();
    }

    public String handle(String request) {
        System.out.println(request + " - Handle ");

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
                return this.writeResponse(solution);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    private String writeResponse(Solution<Position> response) {
        StringBuilder builder = new StringBuilder();
        if (response != null) {
            for (Step<Position> step: response.getSteps()) {
                builder.append(step.toString());
                builder.append("|");
            }
        }
        builder.append("done");
        return builder.toString();
    }
}
