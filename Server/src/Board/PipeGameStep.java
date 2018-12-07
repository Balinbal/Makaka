package Board;

public class PipeGameStep extends Step<Position> {

    private Integer rotations;

    public PipeGameStep(Position position, Integer rotations) {
        this.setRotations(rotations);
        this.setPosition(position);
    }

    public Integer getRotations() {
        return rotations;
    }

    public void setRotations(Integer rotations) {
        this.rotations = rotations;
    }

    @Override
    public void setPosition(Position position) {
        if (position != null) {
            this.position = new Position(position);
        }
    }

    @Override
    public String toString() {
        return String.join(",", this.getPosition().toString(), this.getRotations().toString());
    }
}
