// To save as "<TOMCAT_HOME>\webapps\hello\WEB-INF\classes\HelloServlet.java"
import ClientHandler.ServletHandler;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class HelloServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String board = request.getParameter("board");
        ServletHandler h = new ServletHandler();
        // set response headers
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        // create HTML form
        PrintWriter writer = response.getWriter();
        writer.append(h.handle(board));

    }
}