package view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class BoardDisplayer extends Canvas {

    private StringProperty pipeImageFolder;
    private StringProperty startImageName;
    private StringProperty goalImageName;
    private StringProperty horizontalImageName;
    private StringProperty verticalImageName;
    private StringProperty imageName7;
    private StringProperty fImageName;
    private StringProperty jImageName;
    private StringProperty lImageName;
    char[][] board;


    public void setBoard(char[][] board) {
        this.board = board;
        redraw();
    }

    @Override
    public double minHeight(double width)
    {
        return 64;
    }

    @Override
    public double maxHeight(double width)
    {
        return 1000;
    }

    @Override
    public double prefHeight(double width) {
        return width * this.board.length / this.board[0].length;
    }

    @Override
    public double prefWidth(double height)
    {
        return height * this.board[0].length / this.board.length;

    }

    @Override
    public double minWidth(double height)
    {
        return 64;
    }

    @Override
    public double maxWidth(double height)
    {
        return 10000;
    }

    @Override
    public boolean isResizable()
    {
        return true;
    }

    @Override
    public void resize(double width, double height)
    {
        setWidth(width);
        setHeight(height);
        redraw();
    }


    public char[][] getBoard() {
        return this.board;
    }

    public int getBoardHeight()
    {
        return board.length;
    }

    public int getBoardWidth() {
        return board[0].length;
    }

    public String getStartImageName() {
        return startImageName.get();
    }


    public String getGoalImageName() {
        return goalImageName.get();
    }

    public String getHorizontalImageName() {
        return horizontalImageName.get();
    }

    public String getVerticalImageName() {
        return verticalImageName.get();
    }


    public String getImageName7() {
        return imageName7.get();
    }

    public String getFImageName() {
        return fImageName.get();
    }

    public String getJImageName() {
        return jImageName.get();
    }

    public String getLImageName() {
        return lImageName.get();
    }

    public void setStartImageName(String starImageName) {
        this.startImageName.set(starImageName);
    }

    public void setGoalImageName(String goalImageName) {
        this.goalImageName.set(goalImageName);
    }

    public void setHorizontalImageName(String horizontalImageName) {
        this.horizontalImageName.set(horizontalImageName);
    }

    public void setVerticalImageName(String verticalImageName) {
        this.verticalImageName.set(verticalImageName);
    }

    public void setImageName7(String imageName7) {
        this.imageName7.set(imageName7);
    }

    public void setFImageName(String fImageName) {
        this.fImageName.set(fImageName);
    }

    public void setJImageName(String jImageName) {
        this.jImageName.set(jImageName);
    }

    public void setLImageName(String lImageName) {
        this.lImageName.set(lImageName);
    }

    public BoardDisplayer(){
        pipeImageFolder = new SimpleStringProperty();
        startImageName = new SimpleStringProperty();
        goalImageName = new SimpleStringProperty();
        horizontalImageName = new SimpleStringProperty();
        verticalImageName = new SimpleStringProperty();
        imageName7 = new SimpleStringProperty();
        fImageName = new SimpleStringProperty();
        jImageName = new SimpleStringProperty();
        lImageName = new SimpleStringProperty();
    }

    public String getPipeFileName() {
        return pipeImageFolder.get();
    }


    public void setPipeFileName(String pipeFileName) {
        this.pipeImageFolder.set(pipeFileName);
    }


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
                 imageName = startImageName.get();
                 break;
            case 'G' :
                imageName = goalImageName.get();
                break;
            case '7' :
                imageName = imageName7.get();
                break;
            case 'F' :
                imageName = fImageName.get();
                break;
            case '-' :
                imageName = horizontalImageName.get();
                break;
            case 'J' :
                imageName = jImageName.get();
                break;
            case 'L' :
                imageName = lImageName.get();
                break;
            case '|' :
                imageName = verticalImageName.get();
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
