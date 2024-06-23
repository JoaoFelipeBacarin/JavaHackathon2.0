package org.unialfa.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Agenda {
    private Long id;
    private Long idAgente;
    private Long idIdoso;
    private Long idVacina;
    private LocalDateTime dataHoraVisita;
    private String info;
    private LocalDate dataAplicacao;
    private String agenteNome;
    private String idosoNome;
    private String vacinaNome;


    public Agenda(LocalDateTime dataHoraVisita, String info, LocalDate dataAplicacao) {
        this.dataHoraVisita = dataHoraVisita;
        this.info = info;
        this.dataAplicacao = dataAplicacao;
    }

    public Agenda(Long id, Long idAgente, Long idIdoso, Long idVacina, LocalDateTime dataHoraVisita, String info, LocalDate dataAplicacao) {
        this.id = id;
        this.idAgente = idAgente;
        this.idIdoso = idIdoso;
        this.idVacina = idVacina;
        this.dataHoraVisita = dataHoraVisita;
        this.info = info;
        this.dataAplicacao = dataAplicacao;
    }

    public Agenda(long l, LocalDateTime dataHoraVisita, String text, LocalDate dataAplicacao) {
    }

    public Agenda(LocalDate dataAplicacao, String text, LocalDateTime dataHoraVisita) {
    }

    public Agenda(LocalDate dataAplicacao, String toolTipText, String toolTipText1, String toolTipText2, String text, LocalDateTime dataHoraVisita) {
    }

    public Agenda(long id, LocalDateTime dataHoraVisita, String text, String toolTipText, String toolTipText1, String toolTipText2, LocalDate dataAplicacao) {
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

    public LocalDateTime getDataHoraVisita() {
        return dataHoraVisita;
    }

    public void setDataHoraVisita(LocalDateTime dataHoraVisita) {
        this.dataHoraVisita = dataHoraVisita;
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

    @Override
    public String toString() {
        return "Agenda{" +
                "id=" + id +
                ", idAgente=" + idAgente +
                ", idIdoso=" + idIdoso +
                ", idVacina=" + idVacina +
                ", dataHoraVisita=" + dataHoraVisita +
                ", info='" + info + '\'' +
                ", dataAplicacao=" + dataAplicacao +
                '}';
    }
}
