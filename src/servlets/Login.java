package servlets;


import dao.UsuarioDao;
import modelo.Usuario;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/logarOuCadastrar")
public class Login extends HttpServlet {

    private UsuarioDao usuarioDao = new UsuarioDao();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String acao = request.getParameter("acao");
        String url = request.getParameter("url");
        RequestDispatcher requestDispatcher = null;

        if (acao.equalsIgnoreCase("logar")){

            String email = request.getParameter("email");
            String senha = request.getParameter("senha");
            Usuario usuarioBD = usuarioDao.getByEmailAndSenha(email,senha);

            if (usuarioBD != null){
                request.getSession().setAttribute("usuarioSessao",usuarioBD);
                request.setAttribute("usuarioSessao",usuarioBD);
                requestDispatcher = request.getRequestDispatcher(url.equals("null") ? "/pages/paginaInicialUsuario.jsp" : url);
                requestDispatcher.forward(request,response);
                return;
            }else {
                requestDispatcher = request.getRequestDispatcher("/index.jsp");
                request.setAttribute("msgLogin", "Email ou senha inválidos! Tente novamente.");
                requestDispatcher.forward(request,response);
                return;
            }

        }else{
            requestDispatcher = request.getRequestDispatcher("/pages/cadastroUsuario.jsp");
            requestDispatcher.forward(request,response);
            return;

        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String acao = request.getParameter("acao");

        if (acao.equalsIgnoreCase("deslogar")){

            request.getSession().invalidate();
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.jsp");
            requestDispatcher.forward(request,response);
        }
    }
}
