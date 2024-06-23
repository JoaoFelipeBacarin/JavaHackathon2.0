package org.unialfa.service;

import org.unialfa.dao.IdosoDao;
import org.unialfa.model.Idoso;

import java.util.Collections;
import java.util.List;

public class IdosoService {
    public void salvar(Idoso idoso) {
        try {
            IdosoDao dao = new IdosoDao();
            if (idoso.getId() == null) {
                dao.inserir(idoso);
            } else {
                dao.atualizar(idoso);
            }
        } catch (Exception e) {
            System.out.println("Erro ao salvar Idoso: " + e.getMessage());
        }
    }

    public void deletar(int idIdoso) {
        try {
            IdosoDao dao = new IdosoDao();
            dao.remover(idIdoso);
        } catch (Exception e) {
            System.out.println("Erro ao deletar Idoso: " + e.getMessage());
        }
    }

    public List<Idoso> listarIdosos() {
        try {
            var dao = new IdosoDao();
            return dao.listarTodos();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
    }
}
