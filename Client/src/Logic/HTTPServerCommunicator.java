package Logic;

import Controller.BoardController;
import Model.ScoreRepresentation;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HTTPServerCommunicator implements  IServerCommunicator {

    private String serverUrl;
    public static String baseURL = "/PipeServer";
    public static String solveAPI = "/solve";
    public static String getLevelAPI = "/getlevel";
    public static String setScoreAPI = "/setscore";
    public static String toplevelsolversAPI = "/toplevelsolvers";

    public HTTPServerCommunicator(String serverUrl)
    {
        this.serverUrl = serverUrl;
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
    public char[][] getBoard(int level) {
        try {
            String res = this.get(HTTPServerCommunicator.getLevelAPI, "?level=" + Integer.toString(level)).toString();
            res = res.replace("X", "|");
            return BoardController.fromString(res);

        } catch (Exception e) {
            return new char[][]{};
        }

    }

    @Override
    public void markScore(String user, int level, int steps, int time) {
        try {
            StringBuilder params = new StringBuilder();
            params.append("?user=");
            params.append(user);
            params.append("&level=");
            params.append(level);
            params.append("&time=");
            params.append(time);
            params.append("&steps=");
            params.append(steps);
            this.get(HTTPServerCommunicator.setScoreAPI, params.toString());

        } catch (Exception e) {
        }
    }

    @Override
    public List<ScoreRepresentation> getTopForLevel(int level) {
        try {
            String res = this.get(HTTPServerCommunicator.toplevelsolversAPI, "?level=" + Integer.toString(level)).toString();
            String[] scores = res.split("\\|");
            ArrayList <ScoreRepresentation> scoresList = new ArrayList<ScoreRepresentation>();
            for (int i=0; i<scores.length; ++i)
            {
                String[] userResult = scores[i].split(",");
                scoresList.add(new ScoreRepresentation(userResult[0],Integer.parseInt(userResult[1]),Integer.parseInt(userResult[2])));
            }
            return scoresList;
        } catch (Exception e) {
            return new ArrayList<ScoreRepresentation>();
        }

    }

    @Override
    public void set(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    @Override
    public String getCurrentURL() {
        return this.serverUrl;
    }

    private String get(String api, String params) throws Exception {
        String url = "http://" + this.serverUrl +
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
