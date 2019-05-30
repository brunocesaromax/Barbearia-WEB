package servlets;


import Utilitarios.Util;
import dao.UsuarioDao;
import modelo.Usuario;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

@WebServlet("/pages/editarUsuario")
@MultipartConfig
public class EdicaoUsuario extends HttpServlet {

    private UsuarioDao usuarioDao = new UsuarioDao();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String acao = request.getParameter("acao");
        Usuario usuarioSessao = (Usuario) request.getSession().getAttribute("usuarioSessao");
        RequestDispatcher requestDispatcher = null;
        Usuario usuario = new Usuario();

        String email = request.getParameter("email");

        String nome = request.getParameter("nome");
        int idade = Integer.parseInt(request.getParameter("idade"));
        String senha = request.getParameter("senha");
        String rua = request.getParameter("rua");
        String bairro = request.getParameter("bairro");
        String cidade = request.getParameter("cidade");
        String estado = request.getParameter("estado");
        String cep = request.getParameter("cep");

        if (ServletFileUpload.isMultipartContent(request)) {

            Part imagem = request.getPart("imagem");

            String imagemBase64 = new Base64().encodeBase64String(Util.convertStreamtoByte(imagem.getInputStream()));

            usuario.setImagem(imagemBase64);
            usuario.setContenttype(imagem.getContentType());
        }

        usuario.setBairro(bairro);
        usuario.setCep(cep);
        usuario.setCidade(cidade);
        usuario.setEstado(estado);
        usuario.setEmail(email);
        usuario.setNome(nome);
        usuario.setRua(rua);
        usuario.setSenha(senha);
        usuario.setIdade(idade);

        if ((acao.equalsIgnoreCase("cadastrar") || acao.equalsIgnoreCase("editar"))
                && (usuarioDao.getByEmail(email) == null || usuarioSessao.getEmail().equalsIgnoreCase(email))) {

            if (usuarioSessao != null) {
                usuario.setId(usuarioSessao.getId());
            }

            usuarioDao.salvarOuAtualizar(usuario);

            /*Atualizando o usuário da sessão*/
            request.getSession().setAttribute("usuarioSessao", usuario);

            if (acao.equalsIgnoreCase("editar")) {
                request.setAttribute("msgSucessoEdicao","Edição feita com sucesso.");
                requestDispatcher = request.getRequestDispatcher("/pages/editarUsuario.jsp");
            } else {
                requestDispatcher = request.getRequestDispatcher("/index.jsp");
            }

        } else if ((acao.equalsIgnoreCase("cadastrar") || acao.equalsIgnoreCase("editar"))
                && usuarioDao.getByEmail(email) != null) {


            request.setAttribute("msgCadastro", "Já existe outro usuário cadastrado com o email inserido. Tente com outro email.");
            if (acao.equalsIgnoreCase("cadastrar")) {
                requestDispatcher = request.getRequestDispatcher("/logarOuCadastrar?acao=cadastrar");
            } else {
                requestDispatcher = request.getRequestDispatcher("/pages/editarUsuario.jsp");
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
