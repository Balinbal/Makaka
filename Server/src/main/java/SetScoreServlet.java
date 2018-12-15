import Model.Level;
import Model.ModelUtil;
import Model.Score;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import sun.util.logging.PlatformLogger;

import java.io.*;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.*;
import javax.servlet.http.*;


public class SetScoreServlet extends HttpServlet {

    private String getBoard(int level)
    {
        SessionFactory sessionFactory = ModelUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        List<Level> levels = session.createQuery("from Level where level=" + Integer.toString(level)).list();

        session.close();
        if (levels.size() == 0) {
            return "";
        }
        return levels.get(0).getBoard();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String user = request.getParameter("user");
        int level = Integer.parseInt(request.getParameter("level"));
        int steps = Integer.parseInt(request.getParameter("steps"));
        int time = Integer.parseInt(request.getParameter("time"));

        SessionFactory sessionFactory = ModelUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        PrintWriter writer = response.getWriter();

        int currentScore = time+steps;
        List<Score> score = session.createQuery("from Score where user='" + user + "' and level=" + Integer.toString(level)).list();
        boolean shouldInsert = false;
        if (score.size() > 0) {
            if(score.get(0).getScore() > currentScore) {
                session.createQuery("delete Score where user='" + user + "' and level=" + Integer.toString(level)).executeUpdate();
                shouldInsert = true;
            } else {
                writer.append("Better score exists");
            }
        } else {
            shouldInsert = true;
        }

        if (shouldInsert) {
            Score scoreRecord = new Score();
            scoreRecord.setUser(user);
            scoreRecord.setLevel(level);
            scoreRecord.setSteps(steps);
            scoreRecord.setTime(time);
            scoreRecord.setScore(time+steps);
            session.save(scoreRecord);
            writer.append("Updated");
        }

        session.getTransaction().commit();
        session.close();

        // set response headers
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        // create HTML form

    }
}