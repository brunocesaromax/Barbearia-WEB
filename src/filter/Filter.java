package filter;

import connection.SingleConnection;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebFilter(urlPatterns = {"/pages/*"}) // Toda url, requisição será interceptada
public class Filter implements javax.servlet.Filter {

    private static Connection connection;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        connection = SingleConnection.getConnection();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

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
        }

    }

    @Override
    public void destroy() {

    }
}
