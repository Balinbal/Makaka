package Model;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Level {

    @Id
    @GeneratedValue
    private Integer id;

    private String board;
    private Integer level;

    public Level() {}

    public Level(Integer id, String board, Integer level) {
        this.id = id;
        this.board = board;
        this.level = level;
    }

    public int getLevel() {return this.level;}
    public void setLevel(int level) {this.level = level;}
    public String getBoard() {return this.board;}
    public void setBoard(String board) {this.board = board;}
}
