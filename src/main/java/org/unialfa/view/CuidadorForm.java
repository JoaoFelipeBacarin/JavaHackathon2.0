package org.unialfa.view;

import org.unialfa.dao.AgendaDao;
import org.unialfa.dao.CuidadorDao;
import org.unialfa.model.Cuidador;
import org.unialfa.service.CuidadorService;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CuidadorForm extends JFrame {
    private CuidadorService service;
    private JLabel labelId;
    private JTextField campoId;
    private JLabel labelIdIdoso;
    private JComboBox<String> campoIdIdoso;
    private JLabel labelNomeCuidador;
    private JTextField campoNomeCuidador;
    private JButton botaoSalvar;
    private JButton botaoCancelar;
    private JButton botaoDeletar;
    private JTable tabelaCuidador;

    public CuidadorForm() {
        service = new CuidadorService();

        setTitle("Cuidador");
        setSize(400, 400);

        JPanel painelEntrada = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);

        labelId = new JLabel();
        painelEntrada.add(labelId, constraints);

        campoId = new JTextField(5);
        campoId.setEnabled(false);
        campoId.setVisible(false);
        painelEntrada.add(campoId, constraints);

        labelNomeCuidador = new JLabel("Nome:");
        constraints.gridx = 0;
        constraints.gridy = 2;
        painelEntrada.add(labelNomeCuidador, constraints);

        campoNomeCuidador = new JTextField(15);
        campoNomeCuidador.setEnabled(true);
        constraints.gridx = 1;
        constraints.gridy = 2;
        painelEntrada.add(campoNomeCuidador, constraints);

        labelIdIdoso = new JLabel("Idoso:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        painelEntrada.add(labelIdIdoso, constraints);

        campoIdIdoso = new JComboBox<>();
        preencherIdosos();
        campoIdIdoso.setEnabled(true);
        constraints.gridx = 1;
        constraints.gridy = 1;
        painelEntrada.add(campoIdIdoso, constraints);

        botaoSalvar = new JButton("Salvar");
        botaoSalvar.addActionListener(e -> {
            try {
                executarAcaoDoBotao();
            } catch (Exception ex) {
                ex.printStackTrace(); // Trate a exceção de forma adequada
            }
        });
        constraints.gridx = 1;
        constraints.gridy = 6;
        painelEntrada.add(botaoSalvar, constraints);

        botaoCancelar = new JButton("Cancelar");
        botaoCancelar.addActionListener(e -> limparCampos());
        constraints.gridx = 0;
        constraints.gridy = 6;
        painelEntrada.add(botaoCancelar, constraints);

        botaoDeletar = new JButton("Deletar");
        botaoDeletar.addActionListener(e -> deletarCuidador());
        constraints.gridx = 2;
        constraints.gridy = 6;
        painelEntrada.add(botaoDeletar, constraints);

        JPanel painelSaida = new JPanel(new BorderLayout());

        tabelaCuidador = new JTable();
        tabelaCuidador.setModel(carregarDadosCuidador());
        tabelaCuidador.getSelectionModel().addListSelectionListener(e -> {
            try {
                selecionarCuidador(e);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        tabelaCuidador.setDefaultEditor(Object.class, null);

        JScrollPane scrollPane = new JScrollPane(tabelaCuidador);
        painelSaida.add(scrollPane, BorderLayout.CENTER);

        getContentPane().add(painelEntrada, BorderLayout.NORTH);
        getContentPane().add(painelSaida, BorderLayout.CENTER);

        setLocationRelativeTo(null);
    }

    private void selecionarCuidador(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            int selectedRow = tabelaCuidador.getSelectedRow();
            if (selectedRow != -1) {
                Long id = (Long) tabelaCuidador.getValueAt(selectedRow, 0);
                campoId.setText(id.toString());
                String nome = (String) tabelaCuidador.getValueAt(selectedRow, 1);
                campoNomeCuidador.setText(nome);
            }
        }
    }

    private void limparCampos() {
        campoNomeCuidador.setText("");
        campoId.setText("");
    }

    private Cuidador construirCuidador() throws Exception {
        return campoId.getText().isEmpty()
                ? new Cuidador(campoNomeCuidador.getText())
                : new Cuidador(campoNomeCuidador.getText(), Long.parseLong(campoId.getText()));
    }

    private void executarAcaoDoBotao() throws Exception {
        try {
            service.salvar(construirCuidador());
            limparCampos();
            tabelaCuidador.setModel(carregarDadosCuidador());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private DefaultTableModel carregarDadosCuidador() {
        // Implementação inicial para retornar um modelo vazio
        DefaultTableModel model = new DefaultTableModel();

        model.addColumn("ID");
        model.addColumn("Nome");

        service.listarCuidador().forEach(cuidador -> {
            model.addRow(new Object[]{
                    cuidador.getId(),
                    cuidador.getNome()
            });
        });

        return model;
    }

    private void deletarCuidador() {
        service.deletar((int) Long.parseLong(campoId.getText()));
        limparCampos();
        tabelaCuidador.setModel(carregarDadosCuidador());
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
}
