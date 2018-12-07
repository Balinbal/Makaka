package view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class BoardDisplayer extends Canvas {

    public void setBoard(char[][] board) {
        this.board = board;
        redraw();
    }

    public char[][] getBoard() {
        return this.board;
    }
    char[][] board;

    public int getBoardHeight()
    {
        return board.length;
    }

    public int getBoardWidth() {
        return board[0].length;
    }

    public BoardDisplayer(){
        pipeImageFolder = new SimpleStringProperty();
    }

    public String getPipeFileName() {
        return pipeImageFolder.get();
    }


    public void setPipeFileName(String pipeFileName) {
        this.pipeImageFolder.set(pipeFileName);
    }

    private StringProperty pipeImageFolder;

    public void redraw(){
        if(board != null)
        {
            double Width = getWidth();
            double Height = getHeight();
            double pipeWidth = Width / board[0].length;
            double pipeHeight = Height / board.length;

            GraphicsContext gc = getGraphicsContext2D();

            for(int i=0; i<board.length; i++)
            {
                for(int j=0; j<board[i].length; j++)
                {
                    gc.drawImage(charToImage(board[i][j]), j*pipeWidth, i*pipeHeight, pipeWidth, pipeHeight);

                }
            }
        }

    }
    public Image charToImage(char c){
        String imageName = null;

        switch (c){
            case 'S' :
                 imageName ="s.jpg";
                 break;
            case 'G' :
                imageName ="g.jpg";
                break;
            case '7' :
                imageName ="7.jpg";
                break;
            case 'F' :
                imageName ="f.jpg";
                break;
            case '-' :
                imageName ="horizontal.jpg";
                break;
            case 'J' :
                imageName ="j.jpg";
                break;
            case 'L' :
                imageName ="l.jpg";
                break;
            case '|' :
                imageName ="vertical.jpg";
                break;
        }
        if(imageName != null)
            try {
                return new Image(new FileInputStream(pipeImageFolder.get()+imageName));
            } catch (FileNotFoundException e) {
                return null;
            }

        return null;
    }


}
