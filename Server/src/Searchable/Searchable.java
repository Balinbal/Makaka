package Searchable;
import State.State;

import java.util.ArrayList;
import java.util.Comparator;

// T is the board type, P is the position type
public interface Searchable<T, P> {

    State<T, P> getCurrentState();
    void setCurrentState(State<T, P> currentState);

    State<T, P> getInitialState();
    void setInitialState(State<T, P> initialState);

    State<T, P> getGoalState();
    void setGoalState(State<T, P> goalState);

    // -*** Returns all the possible states in the current state (possible moves) ***-
    ArrayList<State<T, P>> getAllPossibleStates();

    Comparator<State<T, P>> getComparator();
}
