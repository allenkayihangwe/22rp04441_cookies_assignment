import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the cookie
        Cookie[] cookies = request.getCookies();
        String storedValue = "";
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("username".equals(cookie.getName())) {
                    storedValue = cookie.getValue();
                    break;
                }
            }
        }

        // Generate HTML response
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html lang=\"en\">");
        out.println("<head>");
        out.println("    <meta charset=\"UTF-8\">");
        out.println("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
        out.println("    <title>Username/Email Form</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("    <h1>Enter your username or email</h1>");
        out.println("    <form action=\"UserServlet\" method=\"post\">");
        out.println("        <label for=\"username\">Username/Email:</label>");
        out.println("        <input type=\"text\" id=\"username\" name=\"username\" value=\"" + storedValue + "\">");
        out.println("        <input type=\"submit\" value=\"Submit\">");
        out.println("    </form>");
        out.println("</body>");
        out.println("</html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the username/email from the form
        String username = request.getParameter("username");

        // Validate input (ensure username is not null or empty)
        if (username == null || username.trim().isEmpty()) {
            username = "";
        }

        // Create a cookie to store the username/email
        Cookie cookie = new Cookie("username", username);
        cookie.setMaxAge(60 * 60 * 24 * 7); // Set cookie to expire in 1 week
        response.addCookie(cookie);

        // Redirect to the same servlet to handle GET request and display the updated form
        response.sendRedirect("UserServlet");
    }
}
