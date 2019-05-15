package filter;

import connection.SingleConnection;
import modelo.Usuario;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;

@WebFilter(urlPatterns = {"/pages/*"}) // Toda url, requisição será interceptada
public class Filter implements javax.servlet.Filter {

    private static Connection connection;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        connection = SingleConnection.getConnection();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {


        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        //Pegando seção
        HttpSession session = httpServletRequest.getSession();
        //Pegando usuário da seção, retorna null caso usuário nao esteja logado

        String urlParaAutenticacao = httpServletRequest.getServletPath();

        Usuario usuarioSessao = (Usuario) session.getAttribute("usuarioSessao");

        if (usuarioSessao == null && !urlParaAutenticacao.equalsIgnoreCase("/pages/Login")) { // Usuário não logado
            //Redirecionamento em java
            RequestDispatcher dispatcher = servletRequest.getRequestDispatcher("/index.jsp?url=" + urlParaAutenticacao);
            dispatcher.forward(servletRequest, servletResponse);
            return; // Para o processo redirecionar
        }

        filterChain.doFilter(servletRequest,servletResponse);
/*
        try {
            filterChain.doFilter(servletRequest, servletResponse); // Interceptar os requests e dar os respondes
            connection.commit();

        }catch (Exception e){

            try {
                e.printStackTrace();// mostra a pilha de erros...
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }*/

    }

    @Override
    public void destroy() {

    }
}
