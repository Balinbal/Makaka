package Logic;

import java.io.*;

public class SaveAndLoad {
    private static String repository = "repository";
//    private boolean isDirCreated = false;

    private static boolean createDirIfNeeded() {
        // Check it the directory where we store the files was created, if not create it.
        File dir = new File(repository);
        if (!(dir.exists())) {
            return dir.mkdir();
        }
        return true;
    }

    public static void saveSolution(int level, char[][]board,String playerName) {

        try {
            if (createDirIfNeeded()) {
            File filePath = new File(repository, playerName+"-"+String.valueOf(level));

            if (board!= null) {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath+".txt"));
            writer.write (ConvertBoardToString(board));

            //Close writer
            writer.close();

            }
            }
        } catch (IOException exception) {
            System.out.println(String.join(": ", "Couldn't saveSolution file error", exception.toString()));
        }
    }


    public static char[][] LoadSolution(int level,String playerName) {

        try {
                String board=null;
                File filePath = new File(repository, playerName+"-"+String.valueOf(level)+".txt");
                if (filePath.exists())
                {
                    BufferedReader reader= new BufferedReader(new FileReader(filePath));
                    board = reader.readLine();
                    //Close reader
                    reader.close();
                    String[] splitted = board.split(";");
                    char[][] ret = new char[splitted.length][splitted[0].length()];
                    for (int i = 0; i < splitted.length; ++i) {
                        for (int j = 0; j < splitted[0].length(); ++j) {
                            ret[i][j] = splitted[i].charAt(j);
                        }
                    }
                    return (ret);
                }
        } catch (IOException exception) {
            System.out.println(String.join(": ", "Couldn't saveSolution file error", exception.toString()));
            return new char[][]{};
        }
        return new char[0][];
    }

    private static String ConvertBoardToString(char[][] board)
    {
        String stringBoard="";
        for(int i=0; i<board.length; i++) {
            for(int j=0; j<board[i].length; j++) {
                stringBoard += board[i][j];
            }
            stringBoard+=";";
        }
        return stringBoard.toString();
    }


}