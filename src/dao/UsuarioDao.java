package dao;

import connection.SingleConnection;
import modelo.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDao {

    private Connection connection;

    public UsuarioDao() {
        connection = SingleConnection.getConnection();
    }

    public void salvar(Usuario usuario) {

        try {
            String sql = "insert into usuario(login,senha,nome,cep,rua,bairro,cidade,estado) values (?,?,?,?,?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, usuario.getLogin());
            statement.setString(2, usuario.getSenha());
            statement.setString(3, usuario.getNome());
            statement.setString(4, usuario.getCep());
            statement.setString(5, usuario.getRua());
            statement.setString(6, usuario.getBairro());
            statement.setString(7, usuario.getCidade());
            statement.setString(8, usuario.getEstado());

            statement.execute();
            connection.commit();

        } catch (SQLException e) {

            e.printStackTrace();

            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

        }
    }

    public void atualizar(Usuario usuario) {

        try {
            String sql = "update usuario set login = ?, senha = ?, nome = ? where id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, usuario.getLogin());
            preparedStatement.setString(2, usuario.getSenha());
            preparedStatement.setString(3, usuario.getNome());
            preparedStatement.setLong(4, usuario.getId());
            preparedStatement.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();

            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }

    }

    public Usuario getByEmailAndSenha(String email, String senha) {

        try {

            String sql = "select * from usuario where email = ? AND  senha=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            statement.setString(2, senha);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(resultSet.getLong("id"));
                return usuario;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return null;
    }
}
