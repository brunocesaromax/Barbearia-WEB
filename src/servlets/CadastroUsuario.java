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

@WebServlet("/cadastrarUsuario")
public class CadastroUsuario extends HttpServlet {

    private UsuarioDao usuarioDao = new UsuarioDao();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String acao = request.getParameter("acao");
        RequestDispatcher requestDispatcher = null;

        String email = request.getParameter("email");

        if ((acao.equalsIgnoreCase("cadastrar") || acao.equalsIgnoreCase("edicao"))
                && usuarioDao.getByEmail(email) == null) {

            String nome = request.getParameter("nome");
            int idade = Integer.parseInt(request.getParameter("idade"));
            String senha = request.getParameter("senha");
            String rua = request.getParameter("rua");
            String bairro = request.getParameter("bairro");
            String cidade = request.getParameter("cidade");
            String estado = request.getParameter("estado");
            String cep = request.getParameter("cep");

            Usuario usuario = new Usuario();
            usuario.setBairro(bairro);
            usuario.setCep(cep);
            usuario.setCidade(cidade);
            usuario.setEstado(estado);
            usuario.setEmail(email);
            usuario.setNome(nome);
            usuario.setRua(rua);
            usuario.setSenha(senha);
            usuario.setIdade(idade);
            usuarioDao.salvarOuAtualizar(usuario);

            if (acao.equalsIgnoreCase("editar")) {
                requestDispatcher = request.getRequestDispatcher("/pages/paginaInicialUsuario.jsp");
            } else {
                requestDispatcher = request.getRequestDispatcher("/index.jsp");
            }

        } else if ((acao.equalsIgnoreCase("cadastrar") || acao.equalsIgnoreCase("editar"))
                && usuarioDao.getByEmail(email) != null) {


            request.setAttribute("msgCadastro", "Já existe um usuário cadastrado com o email inserido. Tente com outro email.");
            if (acao.equalsIgnoreCase("cadastrar")) {
                requestDispatcher = request.getRequestDispatcher("/logarOuCadastrar?acao=cadastrar");
            } else {
                requestDispatcher = request.getRequestDispatcher("/cadastrarUsuario?acao=editar");
            }
        } else {

            Boolean flagEdicao = Boolean.valueOf(request.getParameter("flagEdicao"));

            if (!flagEdicao) {
                requestDispatcher = request.getRequestDispatcher("/index.jsp");
            } else {
                requestDispatcher = request.getRequestDispatcher("/pages/paginaInicialUsuario.jsp");
            }
        }

        requestDispatcher.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {

    }
}
