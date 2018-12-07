package Algorithms;

import Board.Solution;
import State.State;
import Searchable.Searchable;

import java.util.ArrayList;
import java.util.LinkedList;


public class BFSSearcher<T, P> implements Searcher<T, P> {

    public Solution<P> search(Searchable<T, P> searchable)
    {
        //Define Array list of all the visited States
        ArrayList<State<T, P>> visitedStates = new ArrayList<>();
        //Define the Queue for the discover States
        LinkedList<State<T, P>> queue = new LinkedList<State<T, P>>();
        //The first state -Initial state
        State<T, P> rootSolution = searchable.getInitialState();
        //Add the first State to the queue
        queue.add(rootSolution);
        //Run the algorithm while the queue is not empty
        while(!queue.isEmpty())
        {
            searchable.setCurrentState(queue.poll());
            //get the first one in queue
            visitedStates.add(searchable.getCurrentState());
            searchable.setCurrentState(searchable.getCurrentState());
            //check if the current state is the goal
            if (searchable.getCurrentState().isGoal())
            {
                //return the backTrace of the current State
                return new Solution<P>(searchable.getCurrentState());
            }
            //Create array list to the possible States
            ArrayList<State<T, P>> possibleStates = searchable.getAllPossibleStates();
            for (State<T, P> state : possibleStates)
            {
                //to have all the states that I came from them.
                state.setCameFrom(searchable.getCurrentState());
                //check if the possible state is already in the queue and visited state
                if(!queue.contains(state) && !visitedStates.contains(state))
                {
                    queue.add(state);
                    visitedStates.add(state);

                }
            }
        }
        return null;
    }

   //TODO: fix this and understand if needed
    public int getNumberOfNodesEvaluated()
    {
        return 0;
    }



}
