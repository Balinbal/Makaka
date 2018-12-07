package Algorithms;
import Board.Solution;
import Searchable.Searchable;

// T is the searchable, S is the steps to the solution of the problem
public interface Searcher<T, P> {

    Solution<P> search(Searchable<T, P> s);

    // get how many nodes were evaluated by the algorithm
    int getNumberOfNodesEvaluated();

}
