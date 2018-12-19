package Logic;

import view.MainWindowController;

import java.io.*;

public class Save {
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