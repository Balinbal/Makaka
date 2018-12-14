package Logic;

public interface IServerCommunicator {
    String getBoardSolution(char[][] board);
    void setIp(String ip);
    void setPort(int port);
}
