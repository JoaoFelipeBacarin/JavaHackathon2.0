package org.unialfa.dao;

import org.unialfa.model.Cuidador;
import org.unialfa.model.Idoso;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CuidadorDao {
    private Connection connection;

    public CuidadorDao() throws Exception{
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

    public void inserir (Cuidador cuidador) throws Exception{
        String sql = "insert into cuidador (nome, idIdoso) values (?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, cuidador.getNome());
        ps.setInt(2, cuidador.getIdIdoso().intValue());

        ps.execute();
    }

    public void atualizar(Cuidador cuidador) throws SQLException {
        String sql = "UPDATE cuidador SET nome = ?, idIdoso = ? WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, cuidador.getNome());
        ps.setInt(2, cuidador.getIdIdoso().intValue());
        ps.setLong(3, cuidador.getId());

        ps.execute();
    }

    public void remover(int id) throws SQLException {
        String sql = "DELETE FROM cuidador WHERE cuidador.id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.execute();
    }

    public List<Cuidador> listarTodos() throws SQLException {
        List<Cuidador> cuidador = new ArrayList<Cuidador>();

        ResultSet rs = connection.prepareStatement("SELECT * FROM cuidador").executeQuery();
        while (rs.next()) {
            cuidador.add(new Cuidador(
                    rs.getLong("id"),
                    rs.getString("nome"),
                    rs.getLong("idIdoso")
            ));
        }
        rs.close();
        return cuidador;
    }

    public List<String> listarTodosIdososCuidador() {
        List<String> nomesIdosos = new ArrayList<>();
        try {
            var dao = new IdosoDao();
            List<Idoso> idosos = dao.listarTodos();
            for (Idoso idoso : idosos) {
                nomesIdosos.add(idoso.getNome());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return nomesIdosos;
    }
}
