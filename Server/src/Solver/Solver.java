package Solver;
import Board.Solution;
import Searchable.Searchable;
import State.State;

// T is the searchable and S is the convention of the steps to the solution of the problem.
public interface Solver<T, P> {

    /////////
    //Methods
    /////////

    Solution<P> solve(Searchable<T, P> searchable);

    Solution<P> solve(String searchable);
}
