package Solver;
import Board.MatrixBoard;
import Board.Position;
import Board.Solution;
import Searchable.PipeSearchable;
import Searchable.Searchable;
import Algorithms.Searcher;
import State.PipeGameState;


// common solver for the pipe game
public class PipeGameSolver implements Solver<MatrixBoard, Position> {

    // This searcher is injected into the common solver and solves the given problem
    private Searcher<MatrixBoard, Position> searcher;

    // C-TOR
    public PipeGameSolver(Searcher<MatrixBoard, Position> searcher) {
        this.searcher = searcher;
    }

    @Override
    public Solution<Position> solve(Searchable<MatrixBoard, Position> searchable) {
        return this.searcher.search(searchable);
    }

    @Override
    public Solution<Position> solve(String problem) {
        MatrixBoard board = new MatrixBoard(problem);
        PipeGameState state = new PipeGameState(board);
        PipeSearchable searchable = new PipeSearchable(state);
        return this.solve(searchable);
    }
}
