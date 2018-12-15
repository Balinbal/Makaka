import Model.Level;
import Model.ModelUtil;
import Model.Score;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import sun.util.logging.PlatformLogger;

import java.io.*;
import java.util.List;
import javax.servlet.*;
import javax.servlet.http.*;


public class TopSolversForLevelServlet extends HttpServlet {

    private String getTopSolvers(int level)
    {
        SessionFactory sessionFactory = ModelUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        List<Score> scores = session.createQuery("from Score where level=" + Integer.toString(level)+" order by score asc").list();

        session.close();
        StringBuilder builder = new StringBuilder();
        if (scores.size() == 0) {
            return "";
        }

        for (int i = 0; i < 10 && i<scores.size(); ++i) {
            builder.append(scores.get(i).getUser());
            builder.append(",");
            builder.append(scores.get(i).getTime());
            builder.append(",");
            builder.append(scores.get(i).getSteps());
            builder.append("|");
        }
        return builder.toString();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        int level = Integer.parseInt(request.getParameter("level"));

        // set response headers
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        // create HTML form
        PrintWriter writer = response.getWriter();
        writer.append(this.getTopSolvers(level));

    }
}