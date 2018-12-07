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

    char[][] board;

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
            case 's' :
                 imageName ="s.jpg";
                 break;
            case 'g' :
                imageName ="g.jpg";
                break;
            case '7' :
                imageName ="7.jpg";
                break;
            case 'f' :
                imageName ="f.jpg";
                break;
            case '-' :
                imageName ="horizontal.jpg";
                break;
            case 'j' :
                imageName ="j.jpg";
                break;
            case 'l' :
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
