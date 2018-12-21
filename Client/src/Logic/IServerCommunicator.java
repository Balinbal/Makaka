package Logic;

import Model.ScoreRepresentation;

import java.util.List;

public interface IServerCommunicator {
    String getBoardSolution(char[][] board);
    char[][] getBoard(int level);
    void markScore(String user, int level, int time, int steps);
    List<ScoreRepresentation> getTopForLevel(int level);
    void set(String serverUrl);
    String getCurrentURL();

}
