package servlets;


import Utilitarios.Util;
import dao.AgendamentoDao;
import modelo.Agendamento;
import modelo.EnumServico;
import modelo.Usuario;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@WebServlet("/pages/agendamento")
public class AgendamentoServlet extends HttpServlet {

    private AgendamentoDao agendamentoDao = new AgendamentoDao();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String acao = request.getParameter("acao");
        RequestDispatcher requestDispatcher = null;

        if (acao.equalsIgnoreCase("cadastrar")) {

            String nomeCliente = request.getParameter("nomeCliente");
            EnumServico servico = EnumServico.valor(request.getParameter("servico"));
            Date data = Util.getDataFormatada(request.getParameter("data") + " 00:00");
            float valor = Util.getFloatSemVirgulas(request.getParameter("valor"));
            String horario = request.getParameter("horario");
            String observacao = request.getParameter("observacao");

            Agendamento agendamento = new Agendamento();
            agendamento.setNomeCliente(nomeCliente);
            agendamento.setServico(servico);
            agendamento.setData(data);
            agendamento.setValor(valor);
            agendamento.setHorario(horario);
            agendamento.setObservacao(observacao);

            Usuario usuarioSessao = (Usuario) request.getSession().getAttribute("usuarioSessao");
            agendamento.setUsuario(usuarioSessao);

            agendamentoDao.salvar(agendamento);

            request.setAttribute("agendamentos",agendamentoDao.findAllByIdBarbeiro(usuarioSessao.getId()));
            requestDispatcher = request.getRequestDispatcher("/pages/agendamento.jsp");

        }

        requestDispatcher.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {

        String acao = request.getParameter("acao");

        if (acao.equalsIgnoreCase("listar")) {

            Usuario usuarioSessao = (Usuario) request.getSession().getAttribute("usuarioSessao");
            request.setAttribute("agendamentos", agendamentoDao.findAllByIdBarbeiro(usuarioSessao.getId()));
            RequestDispatcher requestDispatcher = null;

            requestDispatcher = request.getRequestDispatcher("/pages/agendamento.jsp");
            requestDispatcher.forward(request, response);
        }

    }
}
