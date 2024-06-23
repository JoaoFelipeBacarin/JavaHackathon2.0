package org.unialfa.model;

public class Agente {
    private Long id;
    private String nome;

    public Agente(String nome) {
        this.nome = nome;
    }

    public Agente(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "Agente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }
}
