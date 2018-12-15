package CacheManager;
import Board.Solution;
import java.io.IOException;

public interface CacheManager<P> {


    /////////
    //Methods
    /////////

    void saveSolution(String id, Solution<P> solution) throws IOException;
    Solution<P> loadSolution(String id) throws IOException;

}
