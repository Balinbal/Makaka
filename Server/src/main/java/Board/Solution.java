package Board;
import State.State;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;


// P is the position type of the step
public class Solution<P> implements Serializable {

    // For the class serialization and deserialization!
    private static final long serialVersionUID = 30L;
    // Variables
    private ArrayList<Step<P>> steps;

    // C-TORS
    public Solution(State<?, P> goalState) {
        State<?, P> currentState = goalState;
        this.steps = new ArrayList<>();
        if (currentState != null) {
            while(currentState.getCameFrom() != null) {
                // add step
                this.addStep(currentState.getStep());
                currentState = currentState.getCameFrom();
            }
            Collections.reverse(this.steps);
        }
    }

    public Solution(Solution<P> solution) {
        this.setSteps(solution.getSteps());

    }

    public Solution() {
        super();
        this.setSteps(null);
    }

    // Methods

    public ArrayList<Step<P>> getSteps() {
        return steps;
    }

    private void setSteps(ArrayList<Step<P>> steps) {
        this.steps = new ArrayList<Step<P>>();
        if(steps != null) {
            this.steps.addAll(steps);
        }
    }

    private void addStep(Step<P> step) {
        if (step != null && this.steps != null) {
           this.steps.add(step);
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Step<P> step : this.steps) {
            result.append(step.toString());
            if (!step.equals(this.steps.get(this.steps.size() - 1))) {
                 result.append("\n");
            }
        }
        return result.toString();
    }
}
