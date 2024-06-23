package org.unialfa.view;

import org.unialfa.dao.AgendaDao;
import org.unialfa.model.Agenda;
import org.unialfa.service.AgendaService;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
    private JLabel labelDataHoraVisita;
    private JTextField campoDataHoraVisita;
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
        setSize(650, 500);

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

        labelDataHoraVisita = new JLabel("Data e Hora da Visita:");
        constraints.gridx = 0;
        constraints.gridy = 2;
        painelEntrada.add(labelDataHoraVisita, constraints);

        try {
            MaskFormatter dateTimeMask = new MaskFormatter("##/##/#### ##:##");
            dateTimeMask.setPlaceholderCharacter('_');
            campoDataHoraVisita = new JFormattedTextField(dateTimeMask);
            campoDataHoraVisita.setColumns(9);
            constraints.gridx = 1;
            constraints.gridy = 2;
            painelEntrada.add(campoDataHoraVisita, constraints);
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
        constraints.gridx = 2;
        constraints.gridy = 2;
        painelEntrada.add(labelDataAplicacao, constraints);

        try {
            MaskFormatter dateMask = new MaskFormatter("##/##/####");
            dateMask.setPlaceholderCharacter('_');
            campoDataAplicacao = new JFormattedTextField(dateMask);
            campoDataAplicacao.setColumns(6);
            constraints.gridx = 3;
            constraints.gridy = 2;
            painelEntrada.add(campoDataAplicacao, constraints);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        botaoSalvar = new JButton("Salvar");
        botaoSalvar.addActionListener(e -> executarAcaoDoBotao());
        constraints.gridx = 0;
        constraints.gridy = 3;
        painelEntrada.add(botaoSalvar, constraints);

        botaoCancelar = new JButton("Cancelar");
        botaoCancelar.addActionListener(e -> limparCampos());
        constraints.gridx = 1;
        constraints.gridy = 3;
        painelEntrada.add(botaoCancelar, constraints);

        botaoDeletar = new JButton("Deletar");
        botaoDeletar.addActionListener(e -> deletarAgenda());
        constraints.gridx = 2;
        constraints.gridy = 3;
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
                Agenda agenda = new AgendaDao().buscarPorId(id); // Chamando o método buscarPorId diretamente da classe AgendaDao

                campoId.setText(agenda.getId().toString());

                // Definir o agente selecionado
                for (int i = 0; i < campoIdAgente.getItemCount(); i++) {
                    if (campoIdAgente.getItemAt(i).equals(agenda.getAgenteNome())) {
                        campoIdAgente.setSelectedIndex(i);
                        break;
                    }
                }

                // Definir o idoso selecionado
                for (int i = 0; i < campoIdIdoso.getItemCount(); i++) {
                    if (campoIdIdoso.getItemAt(i).equals(agenda.getIdosoNome())) {
                        campoIdIdoso.setSelectedIndex(i);
                        break;
                    }
                }

                // Definir a vacina selecionada
                for (int i = 0; i < campoIdVacina.getItemCount(); i++) {
                    if (campoIdVacina.getItemAt(i).equals(agenda.getVacinaNome())) {
                        campoIdVacina.setSelectedIndex(i);
                        break;
                    }
                }

                String dataHoraVisitaStr = agenda.getDataHoraVisita().format(dateTimeFormatter);
                campoDataHoraVisita.setText(dataHoraVisitaStr);

                campoInfo.setText(agenda.getInfo());

                String dataAplicacaoStr = agenda.getDataAplicacao().format(dateFormatter);
                campoDataAplicacao.setText(dataAplicacaoStr);
            }
        }
    }

    private void limparCampos() {
        campoId.setText("");
        campoDataHoraVisita.setText("");
        campoInfo.setText("");
        campoDataAplicacao.setText("");
    }

    private Agenda construirAgenda() throws Exception {
        LocalDateTime dataHoraVisita = converterDataHora(campoDataHoraVisita.getText());
        LocalDate dataAplicacao = converterData(campoDataAplicacao.getText());
        return campoId.getText().isEmpty()
                ? new Agenda(dataHoraVisita, campoInfo.getText(), dataAplicacao)
                : new Agenda(Long.parseLong(campoId.getText()), (long) campoIdAgente.getSelectedIndex(), (long) campoIdIdoso.getSelectedIndex(), (long) campoIdVacina.getSelectedIndex(), dataHoraVisita, campoInfo.getText(), dataAplicacao);
    }

    private DefaultTableModel carregarDadosAgendas() {
        DefaultTableModel model = new DefaultTableModel();

        model.addColumn("ID");
        model.addColumn("Data e Hora da Agenda");
        model.addColumn("Agente");
        model.addColumn("Idoso");
        model.addColumn("Vacina");
        model.addColumn("Info");
        model.addColumn("Data da Aplicação");

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        service.listarAgendas().forEach(agenda -> {
            String dataHoraFormatada = agenda.getDataHoraVisita().format(dateTimeFormatter);
            String dataAplicacaoFormatada = agenda.getDataAplicacao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            model.addRow(new Object[]{
                    agenda.getId(), dataHoraFormatada, agenda.getAgenteNome(), agenda.getIdosoNome(),
                    agenda.getVacinaNome(), agenda.getInfo(), dataAplicacaoFormatada});

        });

        return model;
    }

    private void executarAcaoDoBotao() {
        try {
            service.salvar(construirAgenda());
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

    private LocalDateTime converterDataHora(String dataHora) throws DateTimeParseException {
        return LocalDateTime.parse(dataHora, dateTimeFormatter);
    }

    private LocalDate converterData(String data) throws DateTimeParseException {
        return LocalDate.parse(data, dateFormatter);
    }

    private void preencherAgentes() {
        try {
            AgendaDao dao = new AgendaDao();
            List<String> nomesAgentes = dao.listarTodosAgentes();
            for (String nome : nomesAgentes) {
                campoIdAgente.addItem(nome);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void preencherIdosos() {
        try {
            AgendaDao dao = new AgendaDao();
            List<String> nomesIdosos = dao.listarTodosIdososAgenda();
            for (String nome : nomesIdosos) {
                campoIdIdoso.addItem(nome);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void preencherVacinas() {
        try {
            AgendaDao dao = new AgendaDao();
            List<String> nomesVacinas = dao.listarTodasVacinas();
            for (String nome : nomesVacinas) {
                campoIdVacina.addItem(nome);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
