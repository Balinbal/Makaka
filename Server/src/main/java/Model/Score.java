package Model;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.criteria.CriteriaBuilder;

@Entity
@Table
public class Score {

    @Id
    @GeneratedValue
    private Integer id;

    private String user;
    private Integer level;
    private Integer time;
    private Integer steps;
    private Integer score;

    public Score() {}

    public Score(Integer id, String user, Integer level, Integer time, Integer steps, Integer score) {
        this.id = id;
        this.user = user;
        this.level = level;
        this.time = time;
        this.steps = steps;
        this.score = score;

    }

    public int getLevel() {return this.level;}
    public void setLevel(int level) {this.level = level;}

    public String getUser() {return this.user;}
    public void setUser(String user) {this.user = user;}

    public int getTime() {return this.time;}
    public void setTime(int time) {this.time= time;}

    public int getSteps() {return this.steps;}
    public void setSteps(int steps) {this.steps = steps;}

    public int getScore() {return this.score;}
    public void setScore(int score) {this.score = score;}

}
