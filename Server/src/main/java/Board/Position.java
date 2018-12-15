package Board;

//TODO : Consider of having interface for this class

import java.io.Serializable;

public class Position implements Serializable{

    // For the class serialization and deserialization!
    private static final long serialVersionUID = 32L;

    // Variables
    private Integer row;
    private Integer col;

    // C-TOR

    public Position(Integer col, Integer row) {
        this.setCol(col);
        this.setRow(row);
    }

    public Position(Position position) {
        if (position != null) {
            this.setCol(position.getCol());
            this.setRow(position.getRow());
            return;
        }
        this.setCol(0);
        this.setRow(0);
    }

    // Setters and Getters
    public Integer getRow() {
        return row;
    }

    private void setRow(Integer row) {
        if (row >= 0)
            this.row = row;
        else this.row = 0;
    }

    public Integer getCol() {
        return col;
    }

    private void setCol(Integer col) {
        if (col >= 0)
            this.col = col;
        else this.col = 0;
    }

    public Position getPositionUp() {
        Integer newRow = this.getRow() - 1;
        return new Position(this.col, newRow);
    }

    public Position getPositionRight() {
        Integer newCol = this.col + 1;
        return new Position(newCol, this.row);
    }

    public Position getPositionLeft() {
        Integer newCol = this.getCol() - 1;
        return new Position(newCol, this.row);

    }

    public Position getPositionDown() {
        Integer newRow = this.getRow() + 1;
        return new Position(this.col, newRow);
    }

    @Override
    public String toString() {
        return String.join(",", this.getRow().toString(), this.getCol().toString());
    }

    public boolean equals(Position obj) {
        return (obj.getRow().equals(this.getRow()) && obj.getCol().equals(this.getCol()));
    }
}
