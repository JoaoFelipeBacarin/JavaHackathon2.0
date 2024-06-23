package org.unialfa.service;

import org.unialfa.dao.AgendaDao;
import org.unialfa.model.Agenda;

import java.util.Collections;
import java.util.List;

public class AgendaService {
    public void salvar(Agenda agenda) {
        try {
            AgendaDao dao = new AgendaDao();
            if (agenda.getId() == null) {
                dao.inserir(agenda);
            } else {
                dao.atualizar(agenda);
            }
        } catch (Exception e) {
            System.out.println("Erro ao salvar Agenda: " + e.getMessage());
        }
    }

    public void deletar(int idAgenda) {
        try {
            AgendaDao dao = new AgendaDao();
            dao.remover(idAgenda);
        } catch (Exception e) {
            System.out.println("Erro ao deletar Agenda: " + e.getMessage());
        }
    }

    public List<Agenda> listarAgendas() {
        try {
            var dao = new AgendaDao();
            return dao.listarTodos();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
    }
}
