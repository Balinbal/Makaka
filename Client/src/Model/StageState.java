package Model;

import Controller.BoardController;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class StageState {
    char[][] board;
    int level;
    int steps;
    int time;


    public StageState(char[][] board, int level, int steps, int time)
    {
        this.board = board;
        this.level = level;
        this.steps = steps;
        this.time = time;
    }

    public char[][] getBoard() {return this.board;}
    public int getLevel() {return this.level;}
    public int getSteps() {return this.steps;}
    public int getTime() {return this.time;}

    public static StageState fromFile(String path)
    {
        try {
            FileInputStream fileIn = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            char[][] board = BoardController.fromString(in.readUTF());
            int level = in.readInt();
            int steps = in.readInt();
            int time = in.readInt();
            return new StageState(board, level, steps, time);
        }
        catch (Exception e) {
            return null;
        }
    }

    public void toFile(String path)
    {
        try {
            FileOutputStream fileOut = new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeUTF(BoardController.toString(this.board));
            out.writeInt(this.level);
            out.writeInt(this.steps);
            out.writeInt(this.time);
            out.flush();
            out.close();
        }
        catch (Exception e) {
        }
    }
}
