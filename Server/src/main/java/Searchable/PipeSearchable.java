package Searchable;

import Board.MatrixBoard;
import Board.PipeGameStep;
import Board.Position;
import State.PipeGameState;
import State.State;
import java.util.ArrayList;
import java.util.Comparator;


public class PipeSearchable implements Searchable<MatrixBoard, Position> {

    // variables
    private PipeGameState currentState;
    private PipeGameState initialState;
    private PipeGameState  goalState;

    // C-TOR

    public PipeSearchable(State<MatrixBoard, Position> initialState) {
        this.initialState = new PipeGameState(initialState);
        this.currentState = new PipeGameState(initialState);
        this.goalState = null;
    }

    // Methods

    @Override
    public State<MatrixBoard, Position> getCurrentState() {
        return this.currentState;
    }

    @Override
    public State<MatrixBoard, Position> getInitialState() {
        return this.initialState;
    }

    @Override
    public State<MatrixBoard, Position> getGoalState() {
        return this.goalState;
    }

    @Override
    public ArrayList<State<MatrixBoard, Position>> getAllPossibleStates() {
        return this.currentState.getAllNeighbors();
    }

    @Override
    public Comparator<State<MatrixBoard, Position>> getComparator() {
        return new PipeSearchable.StateComparator();
    }

    class StateComparator implements Comparator<State<MatrixBoard, Position>> {
        /**
         * This function will calculate which of the given states are closer to the goal
         * @param state1: the first state to compare
         * @param state2: the second state to compare
         * @return : case state2 is closer return -1. case state1 is closer return 1. case of no difference return 0.
         */
        @Override
        public int compare(State<MatrixBoard, Position> state1, State<MatrixBoard, Position> state2) {
            // In case the the current position of the given state is the same, compare by rotations
            if (state1.getCurrentPosition().equals(state2.getCurrentPosition())) {
                int rotations1 = state1.getStep() != null ? ((PipeGameStep)state1.getStep()).getRotations() : 0;
                int rotations2 = state2.getStep() != null ? ((PipeGameStep)state2.getStep()).getRotations() : 0;
                return Double.compare(rotations1, rotations2);
            } else {
                return Double.compare(state1.generateCost(), state2.generateCost());
            }
        }
    }

    @Override
    public void setCurrentState(State<MatrixBoard, Position> currentState) {
        this.currentState = new PipeGameState(currentState);
    }

    @Override
    public void setInitialState(State<MatrixBoard, Position> initialState) {
        this.initialState = new PipeGameState(initialState);
    }

    @Override
    public void setGoalState(State<MatrixBoard, Position> goalState) {
        this.goalState = new PipeGameState(goalState);
    }
}
