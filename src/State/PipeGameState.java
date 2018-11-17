package State;
import Board.*;

import java.awt.*;
import java.util.ArrayList;


public class PipeGameState extends State<MatrixBoard, Position> {

    private double cost;

    // C-TOR

    public PipeGameState(MatrixBoard state) {
        this.setState(state);
        this.setCurrentPosition(null);
        this.setStep(null);
    }

    public PipeGameState(State<MatrixBoard, Position> pipeGameState) {
        if (pipeGameState != null) {
            this.setState(pipeGameState.getState());
            this.setCameFrom(pipeGameState.getCameFrom());
            this.setCurrentPosition(pipeGameState.getCurrentPosition());
            this.setStep(pipeGameState.getStep());
        }
    }

    // Methods

    @Override
    public void setStep(Step<Position> step){
        if (step == null || ((PipeGameStep)step).getRotations().equals(0)) {
            super.setStep(null);
            return;
        }
        super.setStep(step);
    }
    // Added method which sets the default currentPosition to be the start of the board.

    @Override
    public void setCurrentPosition(Position currentPosition) {
        if (currentPosition == null) {
            super.setCurrentPosition(this.state.getStart());
            return;
        }
        super.setCurrentPosition(currentPosition);

    }

    public void setState(MatrixBoard state) {
        if (state != null) {
            this.state = new MatrixBoard(state);
        }
    }

    public boolean equals(State<MatrixBoard, Position> state) {
        if (state == null) { return false; }
        return this.state.equals(state.state);
    }

    private ArrayList<Position> initializeFromDirections() {
        Position currentLocation = this.getCurrentPosition();
        Position up = new Position(currentLocation.getPositionUp());
        Position down = new Position(currentLocation.getPositionDown());
        Position left = new Position(currentLocation.getPositionLeft());
        Position right = new Position(currentLocation.getPositionRight());

        return new ArrayList<Position>() {{
            add(up);
            add(down);
            add(right);
            add(left);
        }};
    }


    public ArrayList<State<MatrixBoard, Position>> getAllNeighbors() {
        ArrayList<State<MatrixBoard, Position>> allNeighbors = null;
        try {

            allNeighbors = new ArrayList<>();
            // Check what are my options of moves up, down, left and right
            Position currentLocation = this.getCurrentPosition();
            MatrixBoard tmpBoard = new MatrixBoard(this.state);

            // Creating all the possible move directions
            ArrayList<Position> directions = this.initializeFromDirections();
            // For each direction check if you can reach
            for (Position direction: directions) {
                // After 3 rotations everything comes back to the initial state
                Integer maxRotations = 3;
                if (!direction.equals(currentLocation)
                        && tmpBoard.getPipe(direction) != null
                        && !tmpBoard.getPipe(direction).isEmpty()
                        &&( getCameFrom() == null || ( getCameFrom() != null
                        && !(direction.equals(getCameFrom().getCurrentPosition()))))) {
                    for (Integer rotations = 0; rotations <= maxRotations; rotations++ ) {
                        if (rotations > 0) {
                            // with each iteration rotate the pipe in the location of the direction
                            Pipe pipe = tmpBoard.getPipe(direction);
                            if (pipe!= null){
                                pipe.rotate();
                            }
                        }
                        // Check if the move is valid, if so, no need to rotate anything, add this direction to the list.
                        if (tmpBoard.isValidMove(currentLocation, direction)) {
                            State<MatrixBoard, Position> neighbor = new PipeGameState(this);

                            neighbor.updateState(new PipeGameStep(direction, rotations));
                            allNeighbors.add(neighbor);
                        }
                    }

                }
            }

        } catch (Exception ex) {
            System.out.println(String.join(": ", "PipeGameState.getAllNeighbors(): Error details" , ex.getMessage()));
        }

        return allNeighbors;
    }

    @Override
    public boolean isGoal() {
        try {
            return this.state.getPipe(this.getCurrentPosition()).getPipeVal().equals('g');
        } catch (NullPointerException ex) {
            return false;
        }

    }

    @Override
    public String toString() {
        return null;
    }

    @Override
    protected void updateState(Step<Position> step) {
        if (this.state != null) {
            this.state.getPipe(step.getPosition()).rotate(((PipeGameStep) step).getRotations());
//            this.setCameFrom(newCameFrom);
            this.setCurrentPosition(step.getPosition());
            this.setStep(step);
        }
    }

    @Override
    public double generateCost() {
        double cost = 0;
        try {
            Position endPosition = this.getState().findEndPosition();
            Position currentPosition = this.getCurrentPosition();
            // Calculate the absolute value of the way from current position to the goal
//            cost = Math.abs(currentPosition.getRow() - endPosition.getRow()) + Math.abs(currentPosition.getCol() - endPosition.getCol());
            cost = Math.abs(Point.distance(
                    currentPosition.getRow(),
                    currentPosition.getCol(),
                    endPosition.getRow(),
                    endPosition.getCol()));
        } catch (Exception ex) {
            System.out.println(String.join(": ", "PipeGameState.generateCost(): Error details" , ex.getMessage()));
        }
        return cost;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
