import Model.Level;
import Model.ModelUtil;
import Model.ModelUtil.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import sun.util.logging.PlatformLogger;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;


public class GetLevelServlet extends HttpServlet {

    private String getBoard(int level)
    {
        SessionFactory sessionFactory = ModelUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Level dbLevel = new Level();
        dbLevel.setBoard("SXG");
        dbLevel.setLevel(1);

        Level dbLevel2 = new Level();
        dbLevel.setBoard("SXXG");
        dbLevel.setLevel(2);

        session.save(dbLevel);
        session.getTransaction().commit();
        session.close();
        return "Bam";
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