package Model;

public class ScoreRepresentation {

    private String user;
    private int steps;
    private int time;

    public ScoreRepresentation(String user, int steps, int time)
    {
        this.user = user;
        this.steps = steps;
        this.time = time;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
