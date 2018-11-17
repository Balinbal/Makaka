package Algorithms;
import Board.Solution;
import State.State;
import Searchable.Searchable;

import java.util.ArrayList;
import java.util.Stack;

public class DFSSearcher<T, P> implements Searcher<T, P> {

    public Solution<P> search(Searchable<T, P> searchable) {

        // Array list with all the states we visited at
        ArrayList<State<T, P>> visitedStates = new ArrayList<>();
        // Stack to manage which of the state we need to work on
        Stack<State<T, P>> stack = new Stack<>();
        // States that will be part of our solution
        State<T, P> rootSolution = searchable.getInitialState();
        stack.push(rootSolution);
        // As long as we have states that we need to work on
        while (!stack.empty()) {
            searchable.setCurrentState(stack.pop());
            visitedStates.add(searchable.getCurrentState());

            if (searchable.getCurrentState().isGoal()) {
                // Found the goal, return the back track from solution
                return new Solution<P>(searchable.getCurrentState());
            }
            ArrayList<State<T, P>> possibleStates = searchable.getAllPossibleStates();

            for (State<T, P> state : possibleStates) {
                if (!visitedStates.contains(state) && !stack.contains(state)) {
                    state.setCameFrom(searchable.getCurrentState());
                    visitedStates.add(state);
                    stack.push(state);
                }

            }
        }
        return null;
    }

    //TODO: fix this and understand if needed
    public int getNumberOfNodesEvaluated() {
        return 0;
    }

}
