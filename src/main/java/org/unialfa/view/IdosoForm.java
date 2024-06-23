package org.unialfa.view;

import org.unialfa.model.Idoso;
import org.unialfa.service.IdosoService;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class IdosoForm extends JFrame {
    private IdosoService service;
    private JLabel labelId;
    private JTextField campoId;
    private JLabel labelNomeIdoso;
    private JTextField campoNomeIdoso;
    private JLabel labelCpf;
    private JTextField campoCpf;
    private JLabel labelTelefone;
    private JTextField campoTelefone;
    private JLabel labelEndereco;
    private JTextField campoEndereco;
    private JLabel labelHistorico;
    private JTextField campoHistorico;
    private JLabel labelDataNascimento;
    private JTextField campoDataNascimento;
    private JButton botaoSalvar;
    private JButton botaoCancelar;
    private JButton botaoDeletar;
    private JTable tabelaIdoso;
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyy");

    public IdosoForm() {
        service = new IdosoService();

        setTitle("Idoso");
        setSize(600, 500);

        JPanel painelEntrada = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);


        labelId = new JLabel();
        painelEntrada.add(labelId, constraints);

        campoId = new JTextField(5);
        campoId.setEnabled(false);
        campoId.setVisible(false);
        painelEntrada.add(campoId, constraints);


        labelNomeIdoso = new JLabel("Nome:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        painelEntrada.add(labelNomeIdoso, constraints);

        campoNomeIdoso = new JTextField(15);
        campoNomeIdoso.setEnabled(true);
        constraints.gridx = 1;
        constraints.gridy = 1;
        painelEntrada.add(campoNomeIdoso, constraints);


        labelCpf = new JLabel("CPF:");
        constraints.gridx = 0;
        constraints.gridy = 2;
        painelEntrada.add(labelCpf, constraints);

        campoCpf = new JTextField(15);
        campoCpf.setEnabled(true);
        constraints.gridx = 1;
        constraints.gridy = 2;
        painelEntrada.add(campoCpf, constraints);


        labelTelefone = new JLabel("Telefone:");
        constraints.gridx = 0;
        constraints.gridy = 3;
        painelEntrada.add(labelTelefone, constraints);

        campoTelefone = new JTextField(15);
        campoTelefone.setEnabled(true);
        constraints.gridx = 1;
        constraints.gridy = 3;
        painelEntrada.add(campoTelefone, constraints);


        labelEndereco = new JLabel("Endereço:");
        constraints.gridx = 0;
        constraints.gridy = 4;
        painelEntrada.add(labelEndereco, constraints);

        campoEndereco = new JTextField(15);
        campoEndereco.setEnabled(true);
        constraints.gridx = 1;
        constraints.gridy = 4;
        painelEntrada.add(campoEndereco, constraints);


        labelHistorico = new JLabel("Histórico Médico:");
        constraints.gridx = 0;
        constraints.gridy = 5;
        painelEntrada.add(labelHistorico, constraints);

        campoHistorico = new JTextField(15);
        campoHistorico.setEnabled(true);
        constraints.gridx = 1;
        constraints.gridy = 5;
        painelEntrada.add(campoHistorico, constraints);


        labelDataNascimento = new JLabel("Data de Nascimento");
        constraints.gridx = 0;
        constraints.gridy = 6;
        painelEntrada.add(labelDataNascimento, constraints);

        try {
            MaskFormatter dateMask = new MaskFormatter("##/##/####");
            dateMask.setPlaceholderCharacter('_');
            campoDataNascimento = new JFormattedTextField(dateMask);
            campoDataNascimento.setColumns(6);
            constraints.gridx = 1;
            constraints.gridy = 6;
            painelEntrada.add(campoDataNascimento, constraints);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        botaoSalvar = new JButton("Salvar");
        botaoSalvar.addActionListener(e -> executarAcaoDoBotao());
        constraints.gridx = 0;
        constraints.gridy = 7;
        painelEntrada.add(botaoSalvar, constraints);

        botaoCancelar = new JButton("Cancelar");
        botaoCancelar.addActionListener(e -> limparCampos());
        constraints.gridx = 1;
        constraints.gridy = 7;
        painelEntrada.add(botaoCancelar, constraints);

        botaoDeletar = new JButton("Deletar");
        botaoDeletar.addActionListener(e -> deletarIdoso());
        constraints.gridx = 2;
        constraints.gridy = 7;
        painelEntrada.add(botaoDeletar, constraints);

        JPanel painelSaida = new JPanel(new BorderLayout());

        tabelaIdoso = new JTable();
        tabelaIdoso.setModel(carregarDadosIdosos());
        tabelaIdoso.getSelectionModel().addListSelectionListener(e -> selecionarIdoso(e));
        tabelaIdoso.setDefaultEditor(Object.class, null);

        JScrollPane scrollPane = new JScrollPane(tabelaIdoso);
        painelSaida.add(scrollPane, BorderLayout.CENTER);

        getContentPane().add(painelEntrada, BorderLayout.NORTH);
        getContentPane().add(painelSaida, BorderLayout.CENTER);

        setLocationRelativeTo(null);
    }

    private void selecionarIdoso(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            int selectedRow = tabelaIdoso.getSelectedRow();
            if (selectedRow != -1) {
                var id = (Long) tabelaIdoso.getValueAt(selectedRow, 0);
                campoId.setText(id.toString());

                var nome = (String) tabelaIdoso.getValueAt(selectedRow, 1);
                campoNomeIdoso.setText(nome);

                var cpf = (String) tabelaIdoso.getValueAt(selectedRow, 2);
                campoCpf.setText(cpf);

                var telefone = (String) tabelaIdoso.getValueAt(selectedRow, 3);
                campoTelefone.setText(telefone);

                var endereco = (String) tabelaIdoso.getValueAt(selectedRow, 4);
                campoEndereco.setText(endereco);

                var historicoMedico = (String) tabelaIdoso.getValueAt(selectedRow, 5);
                campoHistorico.setText(historicoMedico);

                String dataNascimentoStr = (String) tabelaIdoso.getValueAt(selectedRow, 6);
                LocalDate dataNascimento = LocalDate.parse(dataNascimentoStr, dateFormatter);
                campoDataNascimento.setText(dataNascimentoStr);
            }
        }
    }

    private void limparCampos() {
        campoNomeIdoso.setText("");
        campoCpf.setText("");
        campoTelefone.setText("");
        campoEndereco.setText("");
        campoHistorico.setText("");
        campoDataNascimento.setText("");
        campoId.setText("");
    }

    private Idoso construirIdoso() throws Exception {
        LocalDate dataNascimento = converterData(campoDataNascimento.getText());
        return campoId.getText().isEmpty()
                ? new Idoso(campoNomeIdoso.getText(), campoCpf.getText(), campoTelefone.getText(), campoEndereco.getText(), campoHistorico.getText(), dataNascimento)
                : new Idoso(Long.parseLong(campoId.getText()), campoNomeIdoso.getText(), campoCpf.getText(), campoTelefone.getText(), campoEndereco.getText(), campoHistorico.getText(), dataNascimento);
    }

    private DefaultTableModel carregarDadosIdosos() {
        DefaultTableModel model = new DefaultTableModel();

        model.addColumn("ID");
        model.addColumn("Nome");
        model.addColumn("Cpf");
        model.addColumn("Telefone");
        model.addColumn("Endereço");
        model.addColumn("Histórico Médico");
        model.addColumn("Data de Nascimento");

        service.listarIdosos().forEach(idoso -> {
            String dataFormatada = idoso.getDataNascimento().format(dateFormatter);
            model.addRow(new Object[]{
                    idoso.getId(), idoso.getNome(), idoso.getCpf(), idoso.getTelefone(),
                    idoso.getEndereco(), idoso.getHistoricoMedico(), dataFormatada
            });
        });
        return model;
    }

    private void executarAcaoDoBotao() {
        try {
            service.salvar(construirIdoso());
            limparCampos();
            tabelaIdoso.setModel(carregarDadosIdosos());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deletarIdoso() {
        service.deletar((int) Long.parseLong(campoId.getText()));
        limparCampos();
        tabelaIdoso.setModel(carregarDadosIdosos());
    }

    private LocalDate converterData(String data) throws DateTimeParseException {
        return LocalDate.parse(data, dateFormatter);
    }
}
