package org.unialfa.view;

import org.unialfa.dao.AgendaDao;
import org.unialfa.model.Agenda;
import org.unialfa.service.AgendaService;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;


public class AgendaForm extends JFrame {
    private AgendaService service;
    private JLabel labelId;
    private JTextField campoId;
    private JLabel labelIdAgente;
    private JComboBox campoIdAgente;
    private JLabel labelIdIdoso;
    private JComboBox campoIdIdoso;
    private JLabel labelIdVacina;
    private JComboBox campoIdVacina;
    private JLabel labelDataVisita;
    private JTextField campoDataVisita;
    private JLabel labelHoraVisita;
    private JTextField campoHoraVisita;
    private JLabel labelInfo;
    private JTextField campoInfo;
    private JLabel labelDataAplicacao;
    private JTextField campoDataAplicacao;
    private JButton botaoSalvar;
    private JButton botaoCancelar;
    private JButton botaoDeletar;
    private JTable tabelaAgenda;
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyy");

    public AgendaForm() {
        service = new AgendaService();

        setTitle("Agenda");
        setSize(800, 500);

        JPanel painelEntrada = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);

        labelId = new JLabel();
        painelEntrada.add(labelId, constraints);

        campoId = new JTextField(5);
        campoId.setEnabled(false);
        campoId.setVisible(false);
        painelEntrada.add(campoId, constraints);

        labelIdAgente = new JLabel("Agente:");
        constraints.gridx = 0;
        constraints.gridy = 0;
        painelEntrada.add(labelIdAgente, constraints);

        campoIdAgente = new JComboBox<>();
        preencherAgentes();
        campoIdAgente.setEnabled(true);
        constraints.gridx = 1;
        constraints.gridy = 0;
        painelEntrada.add(campoIdAgente, constraints);

        labelIdIdoso = new JLabel("Idoso:");
        constraints.gridx = 2;
        constraints.gridy = 0;
        painelEntrada.add(labelIdIdoso, constraints);

        campoIdIdoso = new JComboBox<>();
        preencherIdosos();
        campoIdIdoso.setEnabled(true);
        constraints.gridx = 3;
        constraints.gridy = 0;
        painelEntrada.add(campoIdIdoso, constraints);

        labelIdVacina = new JLabel("Vacina:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        painelEntrada.add(labelIdVacina, constraints);

        campoIdVacina = new JComboBox<>();
        preencherVacinas();
        campoIdVacina.setEnabled(true);
        constraints.gridx = 1;
        constraints.gridy = 1;
        painelEntrada.add(campoIdVacina, constraints);

        labelDataVisita = new JLabel("Data e Hora da Visita:");
        constraints.gridx = 0;
        constraints.gridy = 2;
        painelEntrada.add(labelDataVisita, constraints);

        try {
            MaskFormatter dateTimeMask = new MaskFormatter("##/##/####");
            dateTimeMask.setPlaceholderCharacter('_');
            campoDataVisita = new JFormattedTextField(dateTimeMask);
            campoDataVisita.setColumns(6);
            constraints.gridx = 1;
            constraints.gridy = 2;
            painelEntrada.add(campoDataVisita, constraints);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        labelHoraVisita = new JLabel("Data e Hora da Visita:");
        constraints.gridx = 2;
        constraints.gridy = 2;
        painelEntrada.add(labelHoraVisita, constraints);

        try {
            MaskFormatter dateTimeMask = new MaskFormatter("##:##");
            dateTimeMask.setPlaceholderCharacter('_');
            campoHoraVisita = new JFormattedTextField(dateTimeMask);
            campoHoraVisita.setColumns(3);
            constraints.gridx = 3;
            constraints.gridy = 2;
            painelEntrada.add(campoHoraVisita, constraints);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        labelInfo = new JLabel("Info:");
        constraints.gridx = 2;
        constraints.gridy = 1;
        painelEntrada.add(labelInfo, constraints);

        campoInfo = new JTextField(15);
        campoInfo.setEnabled(true);
        constraints.gridx = 3;
        constraints.gridy = 1;
        painelEntrada.add(campoInfo, constraints);

        labelDataAplicacao = new JLabel("Data da Aplicação:");
        constraints.gridx = 0;
        constraints.gridy = 3;
        painelEntrada.add(labelDataAplicacao, constraints);

        try {
            MaskFormatter dateMask = new MaskFormatter("##/##/####");
            dateMask.setPlaceholderCharacter('_');
            campoDataAplicacao = new JFormattedTextField(dateMask);
            campoDataAplicacao.setColumns(6);
            constraints.gridx = 1;
            constraints.gridy = 3;
            painelEntrada.add(campoDataAplicacao, constraints);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        botaoSalvar = new JButton("Salvar");
        botaoSalvar.addActionListener(e -> executarAcaoDoBotao());
        constraints.gridx = 0;
        constraints.gridy = 4;
        painelEntrada.add(botaoSalvar, constraints);

        botaoCancelar = new JButton("Cancelar");
        botaoCancelar.addActionListener(e -> limparCampos());
        constraints.gridx = 1;
        constraints.gridy = 4;
        painelEntrada.add(botaoCancelar, constraints);

        botaoDeletar = new JButton("Deletar");
        botaoDeletar.addActionListener(e -> deletarAgenda());
        constraints.gridx = 2;
        constraints.gridy = 4;
        painelEntrada.add(botaoDeletar, constraints);

        JPanel painelSaida = new JPanel(new BorderLayout());

        tabelaAgenda = new JTable();
        tabelaAgenda.setModel(carregarDadosAgendas());
        tabelaAgenda.getSelectionModel().addListSelectionListener(e -> {
            try {
                selecionarAgenda(e);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        tabelaAgenda.setDefaultEditor(Object.class, null);

        JScrollPane scrollPane = new JScrollPane(tabelaAgenda);
        painelSaida.add(scrollPane, BorderLayout.CENTER);

        getContentPane().add(painelEntrada, BorderLayout.NORTH);
        getContentPane().add(painelSaida, BorderLayout.CENTER);

        setLocationRelativeTo(null);

    }

    private void selecionarAgenda(ListSelectionEvent e) throws Exception {
        if (!e.getValueIsAdjusting()) {
            int selectedRow = tabelaAgenda.getSelectedRow();
            if (selectedRow != -1) {
                Long id = (Long) tabelaAgenda.getValueAt(selectedRow, 0);
                Agenda agenda = null;

                try {
                    agenda = new AgendaDao().buscarPorId(id);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                if (agenda != null) {
                    campoId.setText(agenda.getId().toString());
                    campoIdAgente.setSelectedItem(agenda.getAgenteNome());
                    campoIdIdoso.setSelectedItem(agenda.getIdosoNome());
                    campoIdVacina.setSelectedItem(agenda.getVacinaNome());
                    campoDataVisita.setText(agenda.getDataVisita().format(dateFormatter));
                    campoHoraVisita.setText(agenda.getHoraVisita().format(dateTimeFormatter));
                    campoInfo.setText(agenda.getInfo());
                    campoDataAplicacao.setText(agenda.getDataAplicacao().format(dateFormatter));
                }
            }
        }
    }

    private void limparCampos() {
        campoId.setText("");
        campoDataVisita.setText("");
        campoHoraVisita.setText("");
        campoInfo.setText("");
        campoDataAplicacao.setText("");
    }

    private Agenda construirAgenda(DateTimeFormatter timeFormatter) throws Exception {
        LocalDate dataVisita = converterData(campoDataVisita.getText());
        LocalTime horaVisita = converterHora(campoHoraVisita.getText());
        LocalDate dataAplicacao = converterData(campoDataAplicacao.getText());

        int indexAgente = campoIdAgente.getSelectedIndex();
        int indexIdoso = campoIdIdoso.getSelectedIndex();
        int indexVacina = campoIdVacina.getSelectedIndex();
        Long idAgente = idsAgentes.get(indexAgente);
        Long idIdoso = idsAgentes.get(indexIdoso);
        Long idVacina = idsAgentes.get(indexVacina);


        if (campoId.getText().isEmpty()) {
            return new Agenda(idAgente, idIdoso, idVacina, dataVisita, horaVisita, campoInfo.getText(), dataAplicacao);
        } else {
            long id = Long.parseLong(campoId.getText());
            return new Agenda(id, idAgente, idIdoso, idVacina, dataVisita, horaVisita, campoInfo.getText(), dataAplicacao);
        }
    }

    private DefaultTableModel carregarDadosAgendas() {
        DefaultTableModel model = new DefaultTableModel();

        model.addColumn("ID");
        model.addColumn("Data da Agenda");
        model.addColumn("Hora da Agenda");
        model.addColumn("Agente");
        model.addColumn("Idoso");
        model.addColumn("Vacina");
        model.addColumn("Info");
        model.addColumn("Data da Aplicação");

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        List<Agenda> agendas = service.listarAgendas();
        for (Agenda agenda : agendas) {
            String dataVisitaFormatada = agenda.getDataVisita() != null ? agenda.getDataVisita().format(dateFormatter) : "";
            String horaVisitaFormatada = agenda.getHoraVisita() != null ? agenda.getHoraVisita().format(timeFormatter) : "";
            String dataAplicacaoFormatada = agenda.getDataAplicacao() != null ? agenda.getDataAplicacao().format(dateFormatter) : "";

            model.addRow(new Object[]{
                    agenda.getId(), dataVisitaFormatada, horaVisitaFormatada,
                    agenda.getAgenteNome(), agenda.getIdosoNome(), agenda.getVacinaNome(),
                    agenda.getInfo(), dataAplicacaoFormatada
            });
        }

        return model;
    }

    private void executarAcaoDoBotao() {
        try {
            service.salvar(construirAgenda(dateTimeFormatter));
            limparCampos();
            tabelaAgenda.setModel(carregarDadosAgendas());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deletarAgenda() {
        service.deletar((int) Long.parseLong(campoId.getText()));
        limparCampos();
        tabelaAgenda.setModel(carregarDadosAgendas());
    }

    private LocalDate converterData(String data) throws DateTimeParseException {
        return LocalDate.parse(data, dateFormatter);
    }

    private LocalTime converterHora(String hora) throws DateTimeParseException {
        String horaSemMascara = hora.replace("_", "0");
        return LocalTime.parse(horaSemMascara);
    }

    private void preencherAgentes() {
        try {
            AgendaDao dao = new AgendaDao();
            idsAgentes = dao.listarTodosIdsAgentes(); // Atualiza a lista de IDs de agentes
            for (Long id : idsAgentes) {
                campoIdAgente.addItem(dao.obterNomeAgentePorId(id));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<Long> idsAgentes = new ArrayList<>();

    private void preencherIdosos() {
        try {
            AgendaDao dao = new AgendaDao();
            List<Long> idsIdosos = dao.listarTodosIdsIdosos();
            for (Long id : idsIdosos) {
                campoIdIdoso.addItem(dao.obterNomeIdosoPorId(id));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void preencherVacinas() {
        try {
            AgendaDao dao = new AgendaDao();
            List<Long> idsVacinas = dao.listarTodosIdsVacinas();
            for (Long id : idsVacinas) {
                campoIdVacina.addItem(dao.obterNomeVacinaPorId(id));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
