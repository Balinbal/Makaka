package Board;

import java.io.Serializable;

// P - the position type of the step
public abstract class Step<P> implements Serializable{

    // For the class serialization and deserialization!
    private static final long serialVersionUID = 31L;

    protected P position;

    public P getPosition() {
        return position;
    }

    public abstract void setPosition(P position);
}
