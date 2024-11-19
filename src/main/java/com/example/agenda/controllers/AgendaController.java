package com.example.agenda.controllers;

import com.example.agenda.models.Agenda;
import com.example.agenda.models.Medico;
import com.example.agenda.repositories.AgendaRepository;
import com.example.agenda.repositories.MedicoRepository;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agendas")
public class AgendaController {

    @Autowired
    private AgendaRepository agendaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @PostMapping
    public ResponseEntity<Agenda> criarAgenda(@RequestBody Agenda agenda) {
        Medico medico = medicoRepository.findById(agenda.getMedico().getId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Médico não encontrado"));
    agenda.setMedico(medico);
    Agenda novaAgenda = agendaRepository.save(agenda);
    return new ResponseEntity<>(novaAgenda, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Agenda> listarAgendas() {
        return agendaRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Agenda> buscarAgendaPorId(@PathVariable Long id) {
        return agendaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAgenda(@PathVariable Long id) {
        if (agendaRepository.existsById(id)) {
            agendaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
