package mx.edu.utex.evaluacion_u3.controllers.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mx.edu.utex.evaluacion_u3.models.leak.DaoLeak;
import mx.edu.utex.evaluacion_u3.models.leak.Leak;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@WebServlet(name = "leaks",
        urlPatterns = {
                "/leak/leaks",
                "/leak/leak",
                "/leak/save",
                "/leak/accept",
                "/leak/decline",
                "/leak/update",
                "/leak/view",
                "/leak/view-create",
                "/leak/admin",
                "/leak/charge",
                "/leak/user",
                "/leak/aprove"
        })
public class ServletLeak extends HttpServlet {
    private String action;
    private String redirect = "/leak/leaks";
    private String id, name,title,description, status;
    private Leak leak;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        action = req.getServletPath();
        switch (action) {
            case "/leak/admin":
                List<Leak> leaks = new DaoLeak().findAllAdmin();
                System.out.println(leaks);
                req.setAttribute("leaks", leaks );
                redirect = "/views/admin.jsp";
                break;
            case "/leak/charge":
                List<Leak> leaks2 = new DaoLeak().findAll();
                req.setAttribute("leaks", leaks2 );
                redirect = "/views/charge.jsp";
                break;
            case "/leak/user":
                List<Leak> leaks3 = new DaoLeak().findAll();
                req.setAttribute("leaks", leaks3 );
                System.out.println(leaks3);
                redirect = "/views/user.jsp";
                break;
            case "/leak/leaks":
                redirect = "/leak/user";
                break;
            case "/leak/view-create":
                redirect = "/views/create.jsp";
                break;
            case "/leak/leak-update"://NO
                id = req.getParameter("id");
                leak = new DaoLeak().findOne(
                        id != null ? Long.parseLong(id) : 0
                );
                if (leak != null) {
                    req.setAttribute("leak", leak);
                    redirect = "...";
                } else {
                    redirect = "/leak/leak?result" + false +
                            "&message=" +
                            URLEncoder.encode("Recurso no encontrado", StandardCharsets.UTF_8);
                }
                break;
            default:
                System.out.println(action);
        }
        req.getRequestDispatcher(redirect).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");
        action = req.getServletPath();
        switch (action) {
            case "/leak/save":
                name = req.getParameter("name");
                title = req.getParameter("title");
                description = req.getParameter("description");
                String tipo = req.getParameter("tipo");
                leak = new Leak(0L, name, title,description, "PENDIENTE", tipo);
                boolean result = new DaoLeak().save(leak);
                if (result)
                    redirect = "/leak/user?result= " + true + "&message=" + URLEncoder.encode("¡Éxito! Falta o retardo registrado correctamente.", StandardCharsets.UTF_8);
                else
                    redirect = "/leak/user?result= " + false + "&message=" + URLEncoder.encode("¡Error! Acción no realizada correctamente.", StandardCharsets.UTF_8);
                break;

            case "/leak/delete"://NO
                id = req.getParameter("id");
                if (new DaoLeak().delete(Long.parseLong(id)))
                    redirect = "/leak/leak?result= " + true
                            + "&message="
                            + URLEncoder.encode("¡Éxito! Retardo eliminado correctamente.",
                            StandardCharsets.UTF_8);
                else
                    redirect = "/leak/leak?result= "
                            + false + "&message="
                            + URLEncoder.encode("¡Error! Acción no realizada correctamente.",
                            StandardCharsets.UTF_8);
                break;
            case "/leak/decline":
                Long id = Long.valueOf(req.getParameter("id"));
                boolean resultD = new DaoLeak().decline(id);
                if (resultD) {
                    redirect = "/leak/charge";
                }else {
                    redirect = "/leak/charge?result= "
                            + false + "&message="
                            + URLEncoder.encode("¡Error! Acción no realizada correctamente.",
                            StandardCharsets.UTF_8);
                }
                break;
            case "/leak/accept":
                Long id2 = Long.valueOf(req.getParameter("id"));
                boolean resultA = new DaoLeak().accept(id2);
                if (resultA) {
                    redirect = "/leak/charge";
                }else {
                    redirect = "/leak/charge?result= "
                            + false + "&message="
                            + URLEncoder.encode("¡Error! Acción no realizada correctamente.",
                            StandardCharsets.UTF_8);
                }
                break;
            case "/leak/aprove":
                Long id3 = Long.valueOf(req.getParameter("id"));
                boolean resultAp = new DaoLeak().aprove(id3);
                if (resultAp) {
                    redirect = "/leak/admin";
                }else {
                    redirect = "/leak/admin?result= "
                            + false + "&message="
                            + URLEncoder.encode("¡Error! Acción no realizada correctamente.",
                            StandardCharsets.UTF_8);
                }
                break;
            default:
                redirect = "/leak/leak";
        }
        resp.sendRedirect(req.getContextPath() + redirect);
    }
}
