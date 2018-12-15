package Logic;

public interface IServerCommunicator {
    String getBoardSolution(char[][] board);
    char[][] getBoard(int level);
    void markScore(String user, int level, int time, int steps);
    void setIp(String ip);
    void setPort(int port);
}
