package Logic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPServerCommunicator implements  IServerCommunicator {

    private String serverIp;
    private int port;
    public static String baseURL = "/PipeServer";
    public static String solveAPI = "/sayhello";

    public HTTPServerCommunicator(String serverIp, int port)
    {
        this.serverIp = serverIp;
        this.port = port;
    }

    @Override
    public String getBoardSolution(char[][] board) {
        StringBuilder boardSB = new StringBuilder();
        for (int i = 0; i < board.length; ++i) {
            for (int j = 0; j < board[0].length; ++j) {
                if (board[i][j] == '|') {
                    boardSB.append('X');
                } else {
                    boardSB.append(board[i][j]);
                }

            }
            boardSB.append(";");
        }
        try {
            return this.get(HTTPServerCommunicator.solveAPI, "?board=" + boardSB.toString());
        } catch (Exception e) {
            return "";
        }
    }

    @Override
    public void setIp(String ip) {
        this.serverIp = ip;
    }

    @Override
    public void setPort(int port) {
        this.port = port;
    }

    private String get(String api, String params) throws Exception {
        String url = "http://" + this.serverIp + ":" + Integer.toString(this.port) +
                HTTPServerCommunicator.baseURL + api + params;

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");


        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }

}
