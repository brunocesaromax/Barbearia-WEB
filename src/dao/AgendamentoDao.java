package dao;

import connection.SingleConnection;
import modelo.Agendamento;
import modelo.EnumServico;

import java.sql.*;
import java.util.ArrayList;

public class AgendamentoDao {

    private Connection connection;

    public AgendamentoDao() {
        connection = SingleConnection.getConnection();
    }

    public void salvar(Agendamento agendamento) {

        java.sql.Timestamp date;

        try {
            String sql = "INSERT INTO AgendamentoServlet (nomeCliente, valor, data, servico, observacao, barbeiro_id) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = null;
            statement = connection.prepareStatement(sql);
            statement.setString(1, agendamento.getNomeCliente());
            statement.setString(2, String.valueOf(agendamento.getValor()));
            date = new java.sql.Timestamp(agendamento.getData().getTime());// Uso de timestamp para persistir também hora e minuto
            statement.setTimestamp(3, date);
            statement.setInt(4, agendamento.getServico().ordinal());
            statement.setString(5, agendamento.getObservacao());
            statement.setLong(6, agendamento.getUsuario().getId());

            statement.execute();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void update(Agendamento agendamento) {

        java.sql.Timestamp date;

        try {
            String sql = "UPDATE AgendamentoServlet SET nomeCliente=?, valor=?, data=?, servico=?, "
                    + " observacao=? WHERE id=?";

            PreparedStatement statement = null;
            statement = connection.prepareStatement(sql);
            statement.setString(1, agendamento.getNomeCliente());
            statement.setString(2, String.valueOf(agendamento.getValor()));
            date = new java.sql.Timestamp(agendamento.getData().getTime());// Uso de timestamp para persistir também hora e minuto
            statement.setTimestamp(3, date);
            statement.setInt(4, agendamento.getServico().ordinal());
            statement.setString(5, agendamento.getObservacao());
            //statement.setLong(6, agendamento.getBarbeiro().getId());
            statement.setLong(6, agendamento.getId());
            statement.execute();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Agendamento> findAllByIdBarbeiro(Long idBarbeiro, int qtdDias) {

        ArrayList<Agendamento> agendamentos = new ArrayList<>();

        StringBuilder sql = new StringBuilder("SELECT * FROM AgendamentoServlet WHERE barbeiro_id = " + idBarbeiro);
        String clausulaOrderBy = " ORDER BY data";

        switch (qtdDias) {
            case 7:
                sql.append(" AND data BETWEEN DATE_SUB(CURRENT_TIMESTAMP(), INTERVAL 7 DAY) AND  CURRENT_TIMESTAMP()");
                break;
            case 30:
                sql.append(" AND data BETWEEN DATE_SUB(CURRENT_TIMESTAMP(), INTERVAL 1 MONTH) AND  CURRENT_TIMESTAMP()");
                break;
            case 90:
                sql.append(" AND data BETWEEN DATE_SUB(CURRENT_TIMESTAMP(), INTERVAL 3 MONTH) AND  CURRENT_TIMESTAMP()");
                break;
            case 180:
                sql.append(" AND data BETWEEN DATE_SUB(CURRENT_TIMESTAMP(), INTERVAL 6 MONTH) AND  CURRENT_TIMESTAMP()");
                break;
            case 365:
                sql.append(" AND data BETWEEN DATE_SUB(CURRENT_TIMESTAMP(), INTERVAL 1 YEAR) AND  CURRENT_TIMESTAMP()");
                break;
            default:
                break;
        }

        sql.append(clausulaOrderBy);

        try {
            Statement statement = null;
            statement = connection.prepareStatement(sql.toString());
            ResultSet result = statement.executeQuery(sql.toString());

            while (result.next()) {
                Agendamento agendamento = new Agendamento();
                agendamento.setId(result.getLong("id"));
                agendamento.setNomeCliente(result.getString("nomeCliente"));
                agendamento.setValor(result.getFloat("valor"));
                agendamento.setData(result.getTimestamp("data"));
                agendamento.setServico(EnumServico.valueOf(result.getInt("servico")));
                agendamento.setObservacao(result.getString("observacao"));
                agendamentos.add(agendamento);
            }

        } catch (SQLException e) {
            e.printStackTrace();

        }
        return agendamentos;
    }

    public int numeroAgendamentosEmAtrito(Long idBarbeiro, Date data) {

        java.sql.Timestamp date;
        int contador = 0;

        try {
            date = new java.sql.Timestamp(data.getTime());// Uso de timestamp para persistir também hora e minuto
            String sql = "SELECT COUNT(*) FROM AgendamentoServlet WHERE barbeiro_id = 5 AND ('" + date + "' BETWEEN data AND DATE_ADD(data, INTERVAL 40 MINUTE)) OR ('" + date + "' BETWEEN DATE_SUB(data, INTERVAL 40 MINUTE) AND data);";
            Statement statement = null;
            statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                contador = (int) result.getLong("COUNT(*)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contador;
    }

    /*public Agendamento findById(Long id) {

        boolean resultado = false;
        Agendamento agendamento = null;

        //A instrução try -with-resources, que fechará a conexão automaticamente
        try (Connection conn = SingleConnection.getConnection()) {

            String sql = "SELECT * FROM AgendamentoServlet WHERE id =" + id;

            Statement statement = conn.prepareStatement(sql);
            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                agendamento = new Agendamento();
                agendamento.setId(result.getLong("id"));
                agendamento.setNomeCliente(result.getString("nomeCliente"));
                agendamento.setValor(result.getFloat("valor"));
                agendamento.setData(result.getTimestamp("data"));
                agendamento.setServico(EnumServico.valueOf(result.getInt("servico")));
                agendamento.setObservacao(result.getString("observacao"));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return agendamento;
    }*/

    /*DeletANDo agendamentos de forma automática sempre que um agendamento ou atualização for feito, dessa forma manterá a tabela de agendamento sempre atualizada próximo a data atual
    public static boolean deletarAgendamentosAutomatico() {
        boolean resultado = false;
        //A instrução try -with-resources, que fechará a conexão automaticamente
        try (Connection conn = ConeccaoMySql.getConexaoMySQL()) {

            String sql = "DELETE FROM AgendamentoServlet WHERE CURRENT_TIMESTAMP() > DATE_ADD(data, INTERVAL 7 DAY)";
            PreparedStatement statement = conn.prepareStatement(sql);
            //statement.setString(1, String.valueOf(id));
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                resultado = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return resultado;
    }*/

    public void delete(Long id) {

        try {

            String sql = "DELETE FROM AgendamentoServlet WHERE id = ?";
            PreparedStatement statement = null;
            statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            statement.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public ArrayList<Agendamento> findAllByIdBarbeiroAndInterval(Long idBarbeiro, Date dataInicio, Date dataFim) {

        ArrayList<Agendamento> agendamentos = new ArrayList<>();
        java.sql.Timestamp dataInicioSQL;
        java.sql.Timestamp dataFimSQL;

            dataInicioSQL = new java.sql.Timestamp(dataInicio.getTime());
            dataFimSQL = new java.sql.Timestamp(dataFim.getTime());

        try {
            String sql = "SELECT * FROM AgendamentoServlet WHERE barbeiro_id = " + idBarbeiro + " AND data BETWEEN '" + dataInicioSQL + "' AND DATE_ADD('" + dataFimSQL + "', INTERVAL 1 DAY) ORDER BY data";
            Statement statement = null;
            statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                Agendamento agendamento = new Agendamento();
                agendamento.setId(result.getLong("id"));
                agendamento.setNomeCliente(result.getString("nomeCliente"));
                agendamento.setValor(result.getFloat("valor"));
                agendamento.setData(result.getTimestamp("data"));
                agendamento.setServico(EnumServico.valueOf(result.getInt("servico")));
                agendamento.setObservacao(result.getString("observacao"));
                agendamentos.add(agendamento);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return agendamentos;
    }
}