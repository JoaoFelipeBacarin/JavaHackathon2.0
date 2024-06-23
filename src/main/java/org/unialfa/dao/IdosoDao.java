package org.unialfa.dao;

import org.unialfa.model.Idoso;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IdosoDao {
    private Connection connection;

    public IdosoDao() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hackathon?useTimezone=true&serverTimezone=UTC", "root", "");
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void inserir(Idoso idoso) throws SQLException {
        String sql = "INSERT INTO idoso (nome, cpf, telefone, endereco, historicoMedico, dataNascimento) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, idoso.getNome());
        ps.setString(2, idoso.getCpf());
        ps.setString(3, idoso.getTelefone());
        ps.setString(4, idoso.getEndereco());
        ps.setString(5, idoso.getHistoricoMedico());
        ps.setDate(6, Date.valueOf(idoso.getDataNascimento()));

        ps.execute();
    }

    public void atualizar(Idoso idoso) throws SQLException {
        String sql = "UPDATE agente SET nome = ?, cpf = ?, telefone = ?, endereco = ?, historicoMedico = ?, dataNascimeto = ? WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, idoso.getNome());
        ps.setString(2, idoso.getCpf());
        ps.setString(3, idoso.getTelefone());
        ps.setString(4, idoso.getEndereco());
        ps.setString(5, idoso.getHistoricoMedico());
        ps.setDate(6, Date.valueOf(idoso.getDataNascimento()));
        ps.setLong(7, idoso.getId());

        ps.execute();
    }

    public void remover(int id) throws SQLException {
        String sql = "DELETE FROM agente WHERE idoso.id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.execute();
    }

    public List<Idoso> listarTodos() throws SQLException {
        List<Idoso> idosos = new ArrayList<>();
        ResultSet rs = connection.prepareStatement("SELECT * FROM idoso").executeQuery();
        while (rs.next()) {
            idosos.add(new Idoso(
                    rs.getLong("id"),
                    rs.getString("nome"),
                    rs.getString("cpf"),
                    rs.getString("telefone"),
                    rs.getString("endereco"),
                    rs.getString("historicoMedico"),
                    rs.getDate("dataNascimento").toLocalDate()
            ));
        }
        rs.close();
        return idosos;
    }
}
