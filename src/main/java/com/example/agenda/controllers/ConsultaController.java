package com.example.agenda.controllers;

import com.example.agenda.models.Consulta;
import com.example.agenda.models.Paciente;
import com.example.agenda.repositories.AgendaRepository;
import com.example.agenda.repositories.ConsultaRepository;
import com.example.agenda.repositories.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private AgendaRepository agendaRepository;

    @Autowired
    private PacienteRepository pacienteRepository;
@PostMapping
public ResponseEntity<Consulta> agendarConsulta(@RequestBody Consulta consulta) {
    // Verifica a agenda e carrega com EAGER os dados associados
    var agenda = agendaRepository.findById(consulta.getAgenda().getId())
            .orElseThrow(() -> new RuntimeException("Agenda não encontrada"));

    // Verifica o paciente
    var paciente = pacienteRepository.findById(consulta.getPaciente().getId())
            .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

    // Verifica se a dataHora foi informada
    if (consulta.getDataHora() == null) {
        throw new RuntimeException("Data e hora da consulta não informadas");
    }

    // Define as associações e salva a consulta
    consulta.setAgenda(agenda);
    consulta.setPaciente(paciente);

    Consulta novaConsulta = consultaRepository.save(consulta);

    // Retorna a consulta com as associações já preenchidas
    return ResponseEntity.status(HttpStatus.CREATED).body(novaConsulta);
}


    @GetMapping
    public List<Consulta> listarConsultas() {
        return consultaRepository.findAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelarConsulta(@PathVariable Long id) {
        if (consultaRepository.existsById(id)) {
            consultaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
