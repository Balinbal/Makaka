import ClientHandler.ServletHandler;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;


public class SolveServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String board = request.getParameter("board");
        ServletHandler h = new ServletHandler();
        // set response headers
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        PrintWriter writer = response.getWriter();
        writer.append(h.handle(board));

    }
}