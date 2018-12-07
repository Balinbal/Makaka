package CacheManager;
import Board.Solution;

import java.io.*;

public class FileManager<P> implements CacheManager<P> {

    private final String repository = "repository";
    private boolean isDirCreated = false;

    private void createDirIfNeeded() {
        // Check it the directory where we store the files was created, if not create it.
        File dir = new File(this.repository);
        if (!(dir.exists())) {
            isDirCreated = dir.mkdir();
            return;
        }
        isDirCreated = true;
    }
    /**
     * Gets the board that we would like to saveSolution, generates it's id and saves it to the relevant file.
     * @param id - the id of the problem we would like to save
     */
    @Override
    public void saveSolution(String id, Solution<P> solution) {
        try {
            if (!this.isDirCreated) {this.createDirIfNeeded(); }
            File filePath = new File(this.repository, id);
            if (solution != null && !filePath.exists()) {
                boolean created = filePath.createNewFile();
                if (created) {
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                            new BufferedOutputStream(
                                    new FileOutputStream(filePath)
                            ));
//                    System.out.println(String.join(" ", "Saving the following solution:", solution.toString()));
                    objectOutputStream.writeObject(solution);
                    objectOutputStream.close();
                }
            }
        } catch (IOException exception) {
            System.out.println(String.join(": ", "Couldn't saveSolution file error", exception.toString()));
        }
    }

    /**
     * This methods loads a problem or a solution according to the given param
     * @param id - The id of the requested board (level)
     * @return - The requested board or NULL if id doesn't exist.
     */
    public Solution<P> loadSolution(String id) {
        try {
            File fileToLoad = new File(this.repository, id);
            ObjectInputStream objInput = new ObjectInputStream(new FileInputStream(fileToLoad));
            Solution<P> solution = (Solution<P>) objInput.readObject();
//            System.out.println(String.join(" ", "Loaded solution:\r\n", solution.toString()));
            return solution;
        } catch (IOException | ClassNotFoundException exception) {
//            System.out.println("This problem wasn't solved yet.");
        }
        return null;
    }
}
