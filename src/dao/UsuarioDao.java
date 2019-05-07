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

    public void salvarOuAtualizar(Usuario usuario) {

        try {

            String sql;

            if (usuario.getId() == null) {
                sql = "insert into usuario(email,senha,nome,cep,rua,bairro,cidade,estado,idade) values (?,?,?,?,?,?,?,?,?)";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, usuario.getEmail());
                statement.setString(2, usuario.getSenha());
                statement.setString(3, usuario.getNome());
                statement.setString(4, usuario.getCep());
                statement.setString(5, usuario.getRua());
                statement.setString(6, usuario.getBairro());
                statement.setString(7, usuario.getCidade());
                statement.setString(8, usuario.getEstado());
                statement.setLong(9, usuario.getIdade());
                statement.execute();
            } else {
                sql = "UPDATE usuario SET email = ?,senha = ?,nome =?,cep=?,rua=?,bairro=?,cidade=?,estado=?,idade=? WHERE id = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, usuario.getEmail());
                statement.setString(2, usuario.getSenha());
                statement.setString(3, usuario.getNome());
                statement.setString(4, usuario.getCep());
                statement.setString(5, usuario.getRua());
                statement.setString(6, usuario.getBairro());
                statement.setString(7, usuario.getCidade());
                statement.setString(8, usuario.getEstado());
                statement.setLong(9, usuario.getIdade());
                statement.setLong(10, usuario.getId());
                statement.execute();
            }

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
            preparedStatement.setString(1, usuario.getEmail());
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

    public Usuario getByEmail(String email) {

        try {

            String sql = "select * from usuario where email = ? ";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(resultSet.getLong("id"));
                usuario.setNome(resultSet.getString("nome"));
                usuario.setEmail(resultSet.getString("email"));

                return usuario;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return null;
    }

    public Usuario getByEmailAndSenha(String email, String senha) {

        try {

            String sql = "select * from usuario where email = ? AND senha = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            statement.setString(2, senha);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(resultSet.getLong("id"));
                usuario.setNome(resultSet.getString("nome"));
                usuario.setEmail(resultSet.getString("email"));
                usuario.setIdade(resultSet.getInt("idade"));
                usuario.setSenha(resultSet.getString("senha"));
                usuario.setRua(resultSet.getString("rua"));
                usuario.setBairro(resultSet.getString("bairro"));
                usuario.setCidade(resultSet.getString("cidade"));
                usuario.setEstado(resultSet.getString("estado"));
                usuario.setCep(resultSet.getString("cep"));
                return usuario;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return null;
    }
}
