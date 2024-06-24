package org.unialfa.dao;


import org.unialfa.model.Agenda;
import org.unialfa.model.Agente;
import org.unialfa.model.Idoso;
import org.unialfa.model.Vacina;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AgendaDao {
    private Connection connection;

    public AgendaDao() throws Exception {
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

    public void inserir(Agenda agenda) throws Exception {
        String sql = "INSERT INTO agenda (idAgente, idIdoso, idVacina, dataVisita, horaVisita, info, dataAplicacao) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setLong(1, agenda.getIdAgente());
        ps.setLong(2, agenda.getIdIdoso());
        ps.setLong(3, agenda.getIdVacina());
        ps.setDate(4, Date.valueOf(agenda.getDataVisita()));
        ps.setTime(5, Time.valueOf(agenda.getHoraVisita()));
        ps.setString(6, agenda.getInfo());
        ps.setDate(7, Date.valueOf(agenda.getDataAplicacao()));

        ps.execute();
    }

    public void atualizar(Agenda agenda) throws SQLException {
        String sql = "UPDATE agenda SET idAgente = ?, idIdoso = ?, idVacina = ?, dataVisita = ?, horaVisita = ?, info = ?, dataAplicacao = ? WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setLong(1, agenda.getIdAgente());
        ps.setLong(2, agenda.getIdIdoso());
        ps.setLong(3, agenda.getIdVacina());
        ps.setDate(4, Date.valueOf(agenda.getDataVisita()));
        ps.setTime(5, Time.valueOf(agenda.getHoraVisita()));
        ps.setString(6, agenda.getInfo());
        ps.setDate(7, Date.valueOf(agenda.getDataAplicacao()));
        ps.setLong(8, agenda.getId());

        ps.execute();
    }

    public void remover(int id) throws SQLException {
        String sql = "DELETE FROM agenda WHERE agenda.id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setLong(1, id);
        ps.execute();
    }

    public List<Agenda> listarTodos() throws SQLException {
        List<Agenda> agendas = new ArrayList<>();

        String sql = "SELECT * FROM agenda";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Long id = rs.getLong("id");
                Long idAgente = rs.getLong("idAgente");
                Long idIdoso = rs.getLong("idIdoso");
                Long idVacina = rs.getLong("idVacina");
                LocalDate dataVisita = rs.getDate("dataVisita").toLocalDate();
                LocalTime horaVisita = rs.getTime("horaVisita").toLocalTime();
                String info = rs.getString("info");
                LocalDate dataAplicacao = rs.getDate("dataAplicacao").toLocalDate();

                agendas.add(new Agenda(id, idAgente, idIdoso, idVacina, dataVisita, horaVisita, info, dataAplicacao));
            }
        }
        return agendas;
    }

    public List<String> listarTodasVacinas() {
        List<String> nomesVacinas = new ArrayList<>();
        try {
            var dao = new VacinaDao();
            List<Vacina> Vacinas = dao.listarTodos();
            for (Vacina vacina : Vacinas) {
                nomesVacinas.add(vacina.getNome());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return nomesVacinas;
    }

    public List<String> listarTodosIdososAgenda() {
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

    public Agenda buscarPorId(Long id) {
        Agenda agenda = null;
        String sql = "SELECT * FROM agenda WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                agenda = new Agenda(
                        rs.getLong("id"),
                        rs.getLong("idAgente"),
                        rs.getLong("idIdoso"),
                        rs.getLong("idVacina"),
                        rs.getDate("dataVisita").toLocalDate(),
                        rs.getTime("horaVisita").toLocalTime(),
                        rs.getString("info"),
                        rs.getDate("dataAplicacao").toLocalDate()
                );
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return agenda;
    }

    public List<Long> listarTodosIdsAgentes() {
        List<Long> idsAgentes = new ArrayList<>();
        String query = "SELECT id FROM agente";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                idsAgentes.add(rs.getLong("id"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return idsAgentes;
    }

    public String obterNomeAgentePorId(Long id) {
        String nomeAgente = null;
        String query = "SELECT nome FROM agente WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    nomeAgente = rs.getString("nome");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return nomeAgente;
    }

    public List<Long> listarTodosIdsIdosos() {
        List<Long> idsIdosos = new ArrayList<>();
        String query = "SELECT id FROM idoso";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                idsIdosos.add(rs.getLong("id"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return idsIdosos;
    }

    public String obterNomeIdosoPorId(Long id) {
        String nomeIdoso = null;
        String query = "SELECT nome FROM idoso WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    nomeIdoso = rs.getString("nome");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return nomeIdoso;
    }

    public List<Long> listarTodosIdsVacinas() {
        List<Long> idsVacinas = new ArrayList<>();
        String query = "SELECT id FROM vacina";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                idsVacinas.add(rs.getLong("id"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return idsVacinas;
    }

    public String obterNomeVacinaPorId(Long id) {
        String nomeVacina = null;
        String query = "SELECT nome FROM vacina WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    nomeVacina = rs.getString("nome");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return nomeVacina;
    }
}
