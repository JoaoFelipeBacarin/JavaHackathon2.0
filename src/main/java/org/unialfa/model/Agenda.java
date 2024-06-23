package org.unialfa.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Agenda {
    private Long id;
    private Long idAgente;
    private Long idIdoso;
    private Long idVacina;
    private LocalDate dataVisita;
    private LocalTime horaVisita;
    private String info;
    private LocalDate dataAplicacao;
    private String agenteNome;
    private String idosoNome;
    private String vacinaNome;

    public Agenda(Long idAgente, Long idIdoso, Long idVacina, LocalDate dataVisita, LocalTime horaVisita, String info, LocalDate dataAplicacao, String agenteNome, String idosoNome, String vacinaNome) {
        this.idAgente = idAgente;
        this.idIdoso = idIdoso;
        this.idVacina = idVacina;
        this.dataVisita = dataVisita;
        this.horaVisita = horaVisita;
        this.info = info;
        this.dataAplicacao = dataAplicacao;
        this.agenteNome = agenteNome;
        this.idosoNome = idosoNome;
        this.vacinaNome = vacinaNome;
    }

    public Agenda(Long id, Long idAgente, Long idIdoso, Long idVacina, LocalDate dataVisita, LocalTime horaVisita, String info, LocalDate dataAplicacao, String agenteNome, String idosoNome, String vacinaNome) {
        this.id = id;
        this.idAgente = idAgente;
        this.idIdoso = idIdoso;
        this.idVacina = idVacina;
        this.dataVisita = dataVisita;
        this.horaVisita = horaVisita;
        this.info = info;
        this.dataAplicacao = dataAplicacao;
        this.agenteNome = agenteNome;
        this.idosoNome = idosoNome;
        this.vacinaNome = vacinaNome;
    }

    public Agenda(long id, long idAgente, long idIdoso, long idVacina, LocalDate dataVisita, LocalTime horaVisita, String text, LocalDate dataAplicacao) {
    }

    public Agenda(long idAgente, long idIdoso, long idVacina, LocalDate dataVisita, LocalTime horaVisita, String text, LocalDate dataAplicacao) {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdAgente() {
        return idAgente;
    }

    public void setIdAgente(Long idAgente) {
        this.idAgente = idAgente;
    }

    public Long getIdIdoso() {
        return idIdoso;
    }

    public void setIdIdoso(Long idIdoso) {
        this.idIdoso = idIdoso;
    }

    public Long getIdVacina() {
        return idVacina;
    }

    public void setIdVacina(Long idVacina) {
        this.idVacina = idVacina;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public LocalDate getDataAplicacao() {
        return dataAplicacao;
    }

    public void setDataAplicacao(LocalDate dataAplicacao) {
        this.dataAplicacao = dataAplicacao;
    }

    public String getAgenteNome() {
        return agenteNome;
    }

    public String getIdosoNome() {
        return idosoNome;
    }

    public String getVacinaNome() {
        return vacinaNome;
    }

    public LocalDate getDataVisita() {
        return dataVisita;
    }

    public void setDataVisita(LocalDate dataVisita) {
        this.dataVisita = dataVisita;
    }

    public LocalTime getHoraVisita() {
        return horaVisita;
    }

    public void setHoraVisita(LocalTime horaVisita) {
        this.horaVisita = horaVisita;
    }

    @Override
    public String toString() {
        return "Agenda{" +
                "id=" + id +
                ", idAgente=" + idAgente +
                ", idIdoso=" + idIdoso +
                ", idVacina=" + idVacina +
                ", dataVisita=" + dataVisita +
                ", horaVisita=" + horaVisita +
                ", info='" + info + '\'' +
                ", dataAplicacao=" + dataAplicacao +
                ", agenteNome='" + agenteNome + '\'' +
                ", idosoNome='" + idosoNome + '\'' +
                ", vacinaNome='" + vacinaNome + '\'' +
                '}';
    }
}
