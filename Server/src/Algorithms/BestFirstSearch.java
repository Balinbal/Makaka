package Algorithms;
import Board.Solution;
import Searchable.Searchable;
import State.State;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;


// T is the searchable and S is the step, K is the state
public class BestFirstSearch<T, P> implements Searcher<T, P> {

    public Solution<P> search(Searchable<T, P> searchable) {
        //Define Array list of all the visited States
        ArrayList<State<T, P>> visitedStates = new ArrayList<>();
        //Define the Comparator for the
        Comparator<State<T, P>> comparator = searchable.getComparator();
        //Define the Queue with the priorityQueue and use the comparator
        PriorityQueue<State<T, P>> queue = new PriorityQueue<State<T, P>>(10, comparator);
        //Add the State to the queue
        State<T, P> rootSolution = searchable.getInitialState();
        queue.add(rootSolution);
        visitedStates.add(rootSolution);
        while (!queue.isEmpty())
        {
            //Take the first State from the queue
            searchable.setCurrentState(queue.poll());
            //Add the currentState to the visitedState list
            //Check if the current State is the Goal
            if (searchable.getCurrentState() != null && searchable.getCurrentState().isGoal())
            {
                //return the backTrace of the current State
                return new Solution<P>(searchable.getCurrentState());
            }
            //Create array list to the possible States
            ArrayList<State<T, P>> possibleStates = searchable.getCurrentState() != null ?
                    searchable.getAllPossibleStates() :
                    new ArrayList<>();
            for (State<T, P> state : possibleStates)
            {
                //Check if the State Neighbor is already in the queue and visited state
                if(!visitedStates.contains(state) && !queue.contains(state))
                {
                    state.setCameFrom(searchable.getCurrentState());
                    queue.add(state);
                    visitedStates.add(state);
                }

                //TODO:Check if need to do extra checks here (like the example in the presentation)
            }
        }

        return null;
    }

    public int getNumberOfNodesEvaluated()
    {
        return 0;
    }

}
