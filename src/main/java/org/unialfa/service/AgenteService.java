package org.unialfa.service;

import org.unialfa.dao.AgenteDao;
import org.unialfa.model.Agente;

import java.util.Collections;
import java.util.List;

public class AgenteService {
    public void salvar(Agente agente) {
        try {
            AgenteDao dao = new AgenteDao();
            if (agente.getId() == null) {
                dao.inserir(agente);
            } else {
                dao.atualizar(agente);
            }
        } catch (Exception e) {
            System.out.println("Erro ao salvar Agente: " + e.getMessage());
        }
    }

    public void deletar(int idAgente) {
        try {
            AgenteDao dao = new AgenteDao();
            dao.remover(idAgente);
        } catch (Exception e) {
            System.out.println("Erro ao deletar diretor: " + e.getMessage());
        }
    }

    public List<Agente> listarAgentes() {
        try {
            var dao = new AgenteDao();
            return dao.listarTodos();
        } catch (Exception e){
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
    }
}
