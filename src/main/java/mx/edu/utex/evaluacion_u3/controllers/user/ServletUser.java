package mx.edu.utex.evaluacion_u3.controllers.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mx.edu.utex.evaluacion_u3.models.DaoUser;
import mx.edu.utex.evaluacion_u3.models.User;
import mx.edu.utex.evaluacion_u3.models.leak.DaoLeak;
import mx.edu.utex.evaluacion_u3.models.leak.Leak;


import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;


@WebServlet(name = "ServletUser",
        urlPatterns = {
                "/api/auth",
                "/api/auth/login",
                "/api/auth/logout",
                "/api/user/index",
                "/api/user/admin",
                "/api/user/charge",
                "/api/user/user",
                "/api/user/accept",
                "/api/user/decline"
        })
public class ServletUser extends HttpServlet {
    String action, redirect = "/api/user/admin";
    HttpSession session;
    String id, username, password, roleId;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        action = req.getServletPath();
        switch (action) {
            case "/api/auth/login":
                redirect = "/index.jsp";
                break;
            case "/api/auth/logout":
                HttpSession session = req.getSession(false);  // Obtener la sesión existente (sin crear una nueva)
                if (session != null) {
                    session.invalidate();  // Invalidar la sesión para cerrarla
                    redirect = "/api/auth/login?result=true&message=" + URLEncoder
                            .encode("Sesión cerrada correctamente", StandardCharsets.UTF_8);
                }else{
                    redirect = "/api/auth/login?result=false&message=" + URLEncoder
                            .encode("Error en cerrar la Sesion", StandardCharsets.UTF_8);
                }
                break;
            case "/api/user/admin":

                redirect = "/views/admin.jsp";
                break;
            case "/api/user/charge":
                redirect = "/views/charge.jsp";
                break;
            case "/api/user/user":
                List<Leak> leaks3 = new DaoLeak().findAll();
                req.setAttribute("leaks", leaks3 );
                System.out.println(leaks3);
                redirect = "/views/user.jsp";
                break;
        }
        req.getRequestDispatcher(redirect)
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");
        action = req.getServletPath();
        switch (action) {
            case "/api/auth":
                username = req.getParameter("username");
                password = req.getParameter("password");
                try {
                    User user = new DaoUser().loadUserByUsernameAndPassword(username, password);
                    if (user != null) {
                        session = req.getSession();
                        session.setAttribute("user", user);
                        switch (user.getRole().getRole()) {
                            case "ADMIN_ROLE":
                                redirect = "/leak/admin";
                                break;
                            case "CHARGE_ROLE":
                                redirect = "/leak/charge";
                                break;
                            case "USER_ROLE":
                                redirect = "/leak/user";
                                break;
                        }
                    } else {
                        throw new Exception("Credentials mismatch");
                    }
                } catch (Exception e) {
                    redirect = "/api/user/login?result=false&message=" + URLEncoder
                            .encode("Usuario y/o contraseña incorrecta",
                                    StandardCharsets.UTF_8);
                }
                break;
        }
        resp.sendRedirect(req.getContextPath()
                + redirect);
    }
}
