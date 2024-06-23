package org.unialfa.model;

import java.time.LocalDate;

public class Idoso {
    private Long id;
    private String nome;
    private String cpf;
    private String telefone;
    private String endereco;
    private String historicoMedico;
    private LocalDate dataNascimento;

    public Idoso(String nome, String cpf, String telefone, String endereco, String historicoMedico, LocalDate dataNascimento) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.endereco = endereco;
        this.historicoMedico = historicoMedico;
        this.dataNascimento = dataNascimento;
    }

    public Idoso(Long id, String nome, String cpf, String telefone, String endereco, String historicoMedico, LocalDate dataNascimento) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.endereco = endereco;
        this.historicoMedico = historicoMedico;
        this.dataNascimento = dataNascimento;
    }

    public Idoso(long id, String nome) {
    }

    @Override
    public String toString() {
        return "Idoso{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", telefone='" + telefone + '\'' +
                ", endereco='" + endereco + '\'' +
                ", historicoMedico='" + historicoMedico + '\'' +
                ", dataNascimento=" + dataNascimento +
                '}';
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getHistoricoMedico() {
        return historicoMedico;
    }

    public void setHistoricoMedico(String historicoMedico) {
        this.historicoMedico = historicoMedico;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
}
