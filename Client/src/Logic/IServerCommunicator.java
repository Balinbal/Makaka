package Logic;

public interface IServerCommunicator {
    String getBoardSolution(char[][] board);
    char[][] getBoard(int level);
    void setIp(String ip);
    void setPort(int port);
}
