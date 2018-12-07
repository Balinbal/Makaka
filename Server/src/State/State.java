package State;
import Board.Position;
import Board.Step;
import java.util.ArrayList;


// T is the board, P is the position type
public abstract class State<T, P> {

    // Variables
    protected T state;
    private State<T, P> cameFrom;
    private  P currentPosition;
    private  Step<P> step;
    private double cost;

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public P getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(P currentPosition) {
        if (currentPosition != null) {
            this.currentPosition = currentPosition;
        }
    }

    // Methods

    public Step<P> getStep() {
        return step;
    }

    public void setStep(Step<P> step) {
        if (step != null) {
            this.step = step;
            return;
        }
        this.step = null;
    }

    public void setCameFrom(State<T, P> cameFrom) {
        if (cameFrom != null) {
            this.cameFrom = cameFrom;
        }
    }

    public abstract void setState(T state);

    public T getState() {
        return state;
    }

    public State<T, P> getCameFrom() {
        return cameFrom;
    }

    public abstract double generateCost();

    public abstract ArrayList<State<T, P>> getAllNeighbors();

    // this method is required to update the state of, in every change we make
    protected abstract void updateState(Step<P> step);

    public abstract boolean isGoal();

    public abstract String toString();
}
