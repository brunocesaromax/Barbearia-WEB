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
import java.util.List;

@WebServlet("/pages/agendamento")
public class AgendamentoServlet extends HttpServlet {

    private AgendamentoDao agendamentoDao = new AgendamentoDao();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String acao = request.getParameter("acao");
        RequestDispatcher requestDispatcher = null;
        Long idAgendamentoEdicao = null;

        if (acao.equalsIgnoreCase("cadastrar")) {

            //Salvar ou Atualizar?
            if (!request.getParameter("agendamento").isEmpty()) {
                idAgendamentoEdicao = Long.valueOf(request.getParameter("agendamento"));
            }

            String nomeCliente = request.getParameter("nomeCliente");
            EnumServico servico = EnumServico.valor(request.getParameter("servico"));
            Date data = Util.getDataFormatada(request.getParameter("dataServico") + " 00:00");
            float valor = Util.getFloatSemVirgulas(request.getParameter("valor"));
            String horario = request.getParameter("horario");
            String observacao = request.getParameter("observacao");

            Agendamento agendamento = new Agendamento();
            agendamento.setNomeCliente(nomeCliente);
            agendamento.setServico(servico);
            agendamento.setDataServico(data);
            agendamento.setValor(valor);
            agendamento.setHorario(horario);
            agendamento.setObservacao(observacao);

            Usuario usuarioSessao = (Usuario) request.getSession().getAttribute("usuarioSessao");
            agendamento.setUsuario(usuarioSessao);


            if (idAgendamentoEdicao != null) {//caso seja atualização
                agendamento.setId(idAgendamentoEdicao);
                agendamentoDao.update(agendamento);
            } else {//caso seja cadastro
                agendamentoDao.salvar(agendamento);
            }

            List<Agendamento> agendamentos = buscarAgendamentosFormatados(usuarioSessao);
            request.setAttribute("agendamentos", agendamentos);
            requestDispatcher = request.getRequestDispatcher("/pages/agendamento.jsp");

        }

        requestDispatcher.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {

        String acao = request.getParameter("acao");
        RequestDispatcher requestDispatcher = null;

        if (acao.equalsIgnoreCase("listar")) {

            Usuario usuarioSessao = (Usuario) request.getSession().getAttribute("usuarioSessao");

            List<Agendamento> agendamentos = buscarAgendamentosFormatados(usuarioSessao);
            request.setAttribute("agendamentos", agendamentos);

            requestDispatcher = request.getRequestDispatcher("/pages/agendamento.jsp");
            requestDispatcher.forward(request, response);
        } else if (acao.equalsIgnoreCase("deletar")) {

            Long idAgendamento = Long.valueOf(request.getParameter("agendamento"));

            agendamentoDao.deletar(idAgendamento);

            request.setAttribute("msgAgendamento", "Agendamento excluído com sucesso.");

            requestDispatcher = request.getRequestDispatcher("/pages/agendamento?acao=listar");
            requestDispatcher.forward(request, response);
        } else if (acao.equalsIgnoreCase("editar")) {

            Long idAgendamento = Long.valueOf(request.getParameter("agendamento"));

            Agendamento agendamentoEdicao = agendamentoDao.findById(idAgendamento);

            request.setAttribute("agendamento", agendamentoEdicao);

            requestDispatcher = request.getRequestDispatcher("/pages/agendamento?acao=listar");
            requestDispatcher.forward(request, response);
        }

    }

    private List<Agendamento> buscarAgendamentosFormatados(Usuario usuarioSessao){

        List<Agendamento> agendamentos = agendamentoDao.findAllByIdBarbeiro(usuarioSessao.getId());

        agendamentos.forEach(agendamentoLoop -> {

            if (agendamentoLoop.getObservacao().length() > 15) {
                agendamentoLoop.setObservacao(agendamentoLoop.getObservacao().substring(0, 15).concat("..."));
            }

        });

        return agendamentos;
    }
}
