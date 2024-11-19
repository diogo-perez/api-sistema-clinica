package com.example.agenda.models;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Agenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "medico_id")
    private Medico medico;

    @ElementCollection
    @CollectionTable(name = "agenda_dias_semana", joinColumns = @JoinColumn(name = "agenda_id"))
    @Column(name = "dia_semana")
    private List<String> diasSemana;

    @ElementCollection
    @CollectionTable(name = "agenda_horarios", joinColumns = @JoinColumn(name = "agenda_id"))
    @Column(name = "horario")
    private List<String> horarios;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public List<String> getDiasSemana() {
        return diasSemana;
    }

    public void setDiasSemana(List<String> diasSemana) {
        this.diasSemana = diasSemana;
    }

    public List<String> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<String> horarios) {
        this.horarios = horarios;
    }
}
