import Model.Level;
import Model.ModelUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import sun.util.logging.PlatformLogger;

import java.io.*;
import java.util.List;
import javax.servlet.*;
import javax.servlet.http.*;


public class GetLevelServlet extends HttpServlet {

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

        int level = Integer.parseInt(request.getParameter("level"));

        // set response headers
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        // create HTML form
        PrintWriter writer = response.getWriter();
        writer.append(this.getBoard(level));

    }
}