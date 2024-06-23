package org.unialfa.service;

import org.unialfa.dao.CuidadorDao;
import org.unialfa.model.Cuidador;

import java.util.Collections;
import java.util.List;

public class CuidadorService {
    public void salvar(Cuidador cuidador) {
        try {
            CuidadorDao dao = new CuidadorDao();
            if (cuidador.getId() == null) {
                dao.inserir(cuidador);
            } else {
                dao.atualizar(cuidador);
            }
        } catch (Exception e) {
            System.out.println("Erro ao salvar Cuidador: " + e.getMessage());
        }
    }

    public void deletar(int idCuidador) {
        try {
            CuidadorDao dao = new CuidadorDao();
            dao.remover(idCuidador);
        } catch (Exception e) {
            System.out.println("Erro ao deletar cuidador: " + e.getMessage());
        }
    }

    public List<Cuidador> listarCuidador() {
        try {
            var dao = new CuidadorDao();
            return dao.listarTodos();
        } catch (Exception e){
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
    }
}
