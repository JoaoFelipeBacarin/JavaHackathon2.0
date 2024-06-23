package org.unialfa;

import org.unialfa.view.*;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame{
    private JButton botaoAgenda;
    private JButton botaoAgente;
    private JButton botaoCuidador;
    private JButton botaoIdoso;
    private JButton botaoVacina;

    public Main() {
        setTitle("Tela de Início");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel painelEntrada = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);

        botaoAgenda = new JButton("Agendas");
        botaoAgenda.addActionListener(e -> {
            try {
                new AgendaForm().setVisible(true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        constraints.gridx = 0;
        constraints.gridy = 1;
        painelEntrada.add(botaoAgenda, constraints);

        botaoAgente = new JButton("Agentes de Saúde");
        botaoAgente.addActionListener(e -> {
            try {
                new AgenteForm().setVisible(true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        constraints.gridx = 0;
        constraints.gridy = 0;
        painelEntrada.add(botaoAgente, constraints);

        botaoCuidador = new JButton("Cuidadores");
        botaoCuidador.addActionListener(e -> {
            try {
                new CuidadorForm().setVisible(true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        constraints.gridx = 1;
        constraints.gridy = 0;
        painelEntrada.add(botaoCuidador, constraints);

        botaoIdoso = new JButton("Idosos");
        botaoIdoso.addActionListener(e -> {
            try {
                new IdosoForm().setVisible(true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        constraints.gridx = 2;
        constraints.gridy = 0;
        painelEntrada.add(botaoIdoso, constraints);

        botaoVacina = new JButton("Vacinas");
        botaoVacina.addActionListener(e -> {
            try {
                new VacinaForm().setVisible(true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        constraints.gridx = 1;
        constraints.gridy = 1;
        painelEntrada.add(botaoVacina, constraints);

        add(painelEntrada, BorderLayout.CENTER);
        setLocationRelativeTo(null);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            var form = new Main();
            form.setVisible(true);
        });
    }
}