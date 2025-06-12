//package com.ginasiouniforagenda.AgendamentoWeb.controller;
//
//import com.ginasiouniforagenda.AgendamentoWeb.domain.event.Agendamento;
//import com.ginasiouniforagenda.AgendamentoWeb.domain.event.AgendamentoRequestDTO;
//import com.ginasiouniforagenda.AgendamentoWeb.domain.event.AgendamentoResponseDTO;
//import com.ginasiouniforagenda.AgendamentoWeb.repository.AgendamentoRepository;
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.format.annotation.DateTimeFormat;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.UUID;
//
//@RestController
//@RequestMapping("/agendamento")
//public class AgendamentoController {
//
//    @Autowired
//    private AgendamentoRepository agendamentoRepository;
//
//    @PostMapping("/cadastro")
//    public ResponseEntity postAgendamento(@RequestBody @Valid AgendamentoRequestDTO body){
//        boolean existe = agendamentoRepository.existsByDateTimeAndPlace(body.dateTime(), body.place());
//
//        if (existe) {
//            return ResponseEntity
//                    .status(HttpStatus.CONFLICT)
//                    .body("Já existe um evento cadastrado nesse local e horário.");
//        }
//
//        Agendamento agendamento = new Agendamento(body);
//
//        this.agendamentoRepository.save(agendamento);
//
//        return ResponseEntity.ok().build();
//    }
//
//    @GetMapping("/lugar/{place}")
//    public ResponseEntity<List<AgendamentoResponseDTO>> getAgendamentoByName(@PathVariable("place") String place){
//        List<AgendamentoResponseDTO> agendamentoResponseDTOList = agendamentoRepository.findByPlaceContainingIgnoreCase(place)
//                .stream()
//                .map(AgendamentoResponseDTO::new)
//                .toList();
//        if (agendamentoResponseDTOList.isEmpty()){
//            return ResponseEntity.noContent().build();
//        }
//        return ResponseEntity.ok(agendamentoResponseDTOList);
//    }
//
//    @GetMapping("/listagem")
//    public ResponseEntity getAllAgendamentos(){
//        List<AgendamentoResponseDTO> agendamentoResponseDTOList = this.agendamentoRepository.findAll().stream().map(AgendamentoResponseDTO::new).toList();
//
//        return ResponseEntity.ok(agendamentoResponseDTOList);
//    }
//    @GetMapping("/dia-hora/{dateTime}")
//    public ResponseEntity<List<AgendamentoResponseDTO>> getAgendamentoByDateTime(
//            @PathVariable("dateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime) {
//
//        List<AgendamentoResponseDTO> agendamentoResponseDTOList = agendamentoRepository
//                .findByDateTime(dateTime)
//                .stream()
//                .map(AgendamentoResponseDTO::new)
//                .toList();
//
//        if (agendamentoResponseDTOList.isEmpty()) {
//            return ResponseEntity.noContent().build();
//        }
//
//        return ResponseEntity.ok(agendamentoResponseDTOList);
//    }
//
//    @GetMapping("/mes/{year}/{month}")
//    public ResponseEntity<List<AgendamentoResponseDTO>> getAgendamentoByMonth(
//            @PathVariable("year") int year,
//            @PathVariable("month") int month) {
//
//        if (month < 1 || month > 12) {
//            return ResponseEntity.badRequest().build();
//        }
//
//        LocalDateTime startDate = LocalDateTime.of(year, month, 1, 0, 0);
//        LocalDateTime endDate = startDate.plusMonths(1).minusSeconds(1);
//
//        List<AgendamentoResponseDTO> agendamentoResponseDTOList = agendamentoRepository
//                .findByDateTimeBetween(startDate, endDate)
//                .stream()
//                .map(AgendamentoResponseDTO::new)
//                .toList();
//
//        if (agendamentoResponseDTOList.isEmpty()) {
//            return ResponseEntity.noContent().build();
//        }
//
//        return ResponseEntity.ok(agendamentoResponseDTOList);
//    }
//
//    @PutMapping("/edit/{id}")
//    public ResponseEntity<?> editarAgendamento(
//            @PathVariable UUID id,
//            @RequestBody @Valid AgendamentoRequestDTO editedAgendamento){
//
//        if(!agendamentoRepository.existsById(id)){
//            return ResponseEntity.notFound().build();
//        }
//
//        if(agendamentoRepository.existsByDateTimeAndPlaceAndIdNot(
//                editedAgendamento.dateTime(),
//                editedAgendamento.place(),
//                id)){
//            return ResponseEntity.status(HttpStatus.CONFLICT)
//                    .body("Já existe outro agendamento neste local e horário.");
//
//        }
//        Agendamento agendamentoAtualizado = new Agendamento(editedAgendamento);
//        agendamentoAtualizado.setId(id);
//        agendamentoRepository.save(agendamentoAtualizado);
//        return ResponseEntity.ok(new AgendamentoResponseDTO(agendamentoAtualizado));
//    }
//
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity deleteAgendamento(@PathVariable("id") UUID id) {
//        try{
//            if(!agendamentoRepository.existsById(id)){
//                return ResponseEntity
//                        .status(HttpStatus.NOT_FOUND)
//                        .body("Agendamento não encontrado com o ID: " + id);
//            }
//
//            agendamentoRepository.deleteById(id);
//
//            return ResponseEntity
//                    .ok("Agendamento com ID " + id + " foi deletado com sucesso.");
//        } catch(Exception e){
//            return ResponseEntity
//                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Erro ao deletar agendamento" + e.getMessage());
//        }
//    }
//}




















//
//package com.ginasiouniforagenda.AgendamentoWeb.controller;
//
//import com.ginasiouniforagenda.AgendamentoWeb.domain.event.Agendamento;
//import com.ginasiouniforagenda.AgendamentoWeb.domain.event.AgendamentoRequestDTO;
//import com.ginasiouniforagenda.AgendamentoWeb.domain.event.AgendamentoResponseDTO;
//import com.ginasiouniforagenda.AgendamentoWeb.repository.AgendamentoRepository;
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.format.annotation.DateTimeFormat;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.time.LocalDateTime;
//import java.time.LocalTime; // Import LocalTime
//import java.time.YearMonth; // Import YearMonth
//import java.time.format.DateTimeFormatter;
//import java.time.format.DateTimeParseException;
//import java.util.List;
//import java.util.UUID;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//@RestController
//@RequestMapping("/agendamento")
//public class AgendamentoController {
//
//    private static final Logger logger = LoggerFactory.getLogger(AgendamentoController.class);
//
//    @Autowired
//    private AgendamentoRepository agendamentoRepository;
//
//    @PostMapping("/cadastro")
//    public ResponseEntity<?> postAgendamento(@RequestBody @Valid AgendamentoRequestDTO body) {
//        logger.debug("Recebendo requisição para criar agendamento: {}", body);
//        boolean existe = agendamentoRepository.existsByDateTimeAndPlace(body.dateTime(), body.place());
//        if (existe) {
//            logger.warn("Conflito: Já existe um evento cadastrado em {} no local {}", body.dateTime(), body.place());
//            return ResponseEntity
//                    .status(HttpStatus.CONFLICT)
//                    .body("Já existe um evento cadastrado nesse local e horário.");
//        }
//
//        Agendamento agendamento = new Agendamento(body);
//        agendamentoRepository.save(agendamento);
//        logger.info("Agendamento criado com sucesso: ID {}", agendamento.getId());
//
//        return ResponseEntity.ok().build();
//    }
//
//    @GetMapping("/lugar/{place}")
//    public ResponseEntity<List<AgendamentoResponseDTO>> getAgendamentoByPlace(@PathVariable("place") String place) {
//        logger.debug("Buscando agendamentos para o local: {}", place);
//        List<AgendamentoResponseDTO> agendamentoResponseDTOList = agendamentoRepository
//                .findByPlaceContainingIgnoreCase(place)
//                .stream()
//                .map(AgendamentoResponseDTO::new)
//                .toList();
//
//        return agendamentoResponseDTOList.isEmpty()
//                ? ResponseEntity.noContent().build()
//                : ResponseEntity.ok(agendamentoResponseDTOList);
//    }
//
//    @GetMapping("/listagem")
//    public ResponseEntity<List<AgendamentoResponseDTO>> getAllAgendamentos() {
//        logger.debug("Buscando todos os agendamentos");
//        List<AgendamentoResponseDTO> agendamentoResponseDTOList = agendamentoRepository
//                .findAll()
//                .stream()
//                .map(AgendamentoResponseDTO::new)
//                .toList();
//
//        return agendamentoResponseDTOList.isEmpty()
//                ? ResponseEntity.noContent().build()
//                : ResponseEntity.ok(agendamentoResponseDTOList);
//    }
//
//    @GetMapping("/dia-hora/{dateTime}")
//    public ResponseEntity<List<AgendamentoResponseDTO>> getAgendamentoByDateTime(
//            @PathVariable("dateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime) {
//        logger.debug("Buscando agendamentos para a data/hora: {}", dateTime);
//        List<AgendamentoResponseDTO> agendamentoResponseDTOList = agendamentoRepository
//                .findByDateTime(dateTime)
//                .stream()
//                .map(AgendamentoResponseDTO::new)
//                .toList();
//
//        return agendamentoResponseDTOList.isEmpty()
//                ? ResponseEntity.noContent().build()
//                : ResponseEntity.ok(agendamentoResponseDTOList);
//    }
//
//    @GetMapping("/mes/{year}/{month}")
//    public ResponseEntity<List<AgendamentoResponseDTO>> getAgendamentoByMonth(
//            @PathVariable("year") int year,
//            @PathVariable("month") int month) {
//        logger.debug("Buscando agendamentos para {}/{}", year, month);
//        if (month < 1 || month > 12) {
//            logger.warn("Mês inválido: {}", month);
//            return ResponseEntity.badRequest().build();
//        }
//
//        // Calculate the first and last day of the month
//        YearMonth yearMonth = YearMonth.of(year, month);
//        LocalDateTime startDate = yearMonth.atDay(1).atStartOfDay(); // First day of the month at 00:00:00
//        LocalDateTime endDate = yearMonth.atEndOfMonth().atTime(LocalTime.MAX); // Last day of the month at 23:59:59.999999999
//
//        List<AgendamentoResponseDTO> agendamentoResponseDTOList = agendamentoRepository
//                .findByDateTimeBetween(startDate, endDate)
//                .stream()
//                .map(AgendamentoResponseDTO::new)
//                .toList();
//
//        logger.debug("Encontrados {} agendamentos para {}/{}", agendamentoResponseDTOList.size(), year, month);
//        return agendamentoResponseDTOList.isEmpty()
//                ? ResponseEntity.noContent().build()
//                : ResponseEntity.ok(agendamentoResponseDTOList);
//    }
//
//    @GetMapping("/periodo")
//    public ResponseEntity<List<AgendamentoResponseDTO>> getAgendamentoByPeriodo(
//            @RequestParam("inicio") String inicioStr,
//            @RequestParam("fim") String fimStr) {
//        logger.debug("Buscando agendamentos para período: inicio={}, fim={}", inicioStr, fimStr);
//        try {
//            // Using ISO_LOCAL_DATE to parse dates as YYYY-MM-DD and then setting time
//            LocalDateTime inicio = LocalDateTime.parse(inicioStr + "T00:00:00");
//            LocalDateTime fim = LocalDateTime.parse(fimStr + "T23:59:59.999999999"); // Include last nanosecond of the day
//
//            if (inicio.isAfter(fim)) {
//                logger.warn("Data de início {} é posterior à data de fim {}", inicioStr, fimStr);
//                return ResponseEntity.badRequest().body(null);
//            }
//
//            List<AgendamentoResponseDTO> agendamentoResponseDTOList = agendamentoRepository
//                    .findByDateTimeBetween(inicio, fim)
//                    .stream()
//                    .map(AgendamentoResponseDTO::new)
//                    .toList();
//
//            logger.debug("Encontrados {} agendamentos para o período {} a {}",
//                    agendamentoResponseDTOList.size(), inicioStr, fimStr);
//            return agendamentoResponseDTOList.isEmpty()
//                    ? ResponseEntity.noContent().build()
//                    : ResponseEntity.ok(agendamentoResponseDTOList);
//
//        } catch (DateTimeParseException e) {
//            logger.error("Erro ao parsear datas: inicio={}, fim={}, erro={}", inicioStr, fimStr, e.getMessage());
//            return ResponseEntity.badRequest().body(null);
//        } catch (Exception e) {
//            logger.error("Erro ao processar período: {}", e.getMessage());
//            return ResponseEntity.badRequest().body(null);
//        }
//    }
//
//    @PutMapping("/edit/{id}")
//    public ResponseEntity<?> editarAgendamento(
//            @PathVariable UUID id,
//            @RequestBody @Valid AgendamentoRequestDTO editedAgendamento) {
//        logger.debug("Editando agendamento ID: {}", id);
//        if (!agendamentoRepository.existsById(id)) {
//            logger.warn("Agendamento não encontrado: ID {}", id);
//            return ResponseEntity.notFound().build();
//        }
//
//        if (agendamentoRepository.existsByDateTimeAndPlaceAndIdNot(
//                editedAgendamento.dateTime(),
//                editedAgendamento.place(),
//                id)) {
//            logger.warn("Conflito: Já existe outro agendamento em {} no local {}",
//                    editedAgendamento.dateTime(), editedAgendamento.place());
//            return ResponseEntity
//                    .status(HttpStatus.CONFLICT)
//                    .body("Já existe outro agendamento neste local e horário.");
//        }
//
//        Agendamento agendamentoAtualizado = new Agendamento(editedAgendamento);
//        agendamentoAtualizado.setId(id);
//        agendamentoRepository.save(agendamentoAtualizado);
//        logger.info("Agendamento atualizado com sucesso: ID {}", id);
//
//        return ResponseEntity.ok(new AgendamentoResponseDTO(agendamentoAtualizado));
//    }
//
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<?> deleteAgendamento(@PathVariable UUID id) {
//        logger.debug("Deletando agendamento ID: {}", id);
//        try {
//            if (!agendamentoRepository.existsById(id)) {
//                logger.warn("Agendamento não encontrado: ID {}", id);
//                return ResponseEntity
//                        .status(HttpStatus.NOT_FOUND)
//                        .body("Agendamento não encontrado com ID: " + id);
//            }
//
//            agendamentoRepository.deleteById(id);
//            logger.info("Agendamento deletado com sucesso: ID {}", id);
//
//            return ResponseEntity.ok("Agendamento com ID " + id + " foi deletado com sucesso.");
//        } catch (Exception e) {
//            logger.error("Erro ao deletar agendamento ID {}: {}", id, e.getMessage());
//            return ResponseEntity
//                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Erro ao deletar agendamento: " + e.getMessage());
//        }
//    }
//}


package com.ginasiouniforagenda.AgendamentoWeb.controller;

import com.ginasiouniforagenda.AgendamentoWeb.domain.event.Agendamento;
import com.ginasiouniforagenda.AgendamentoWeb.domain.event.AgendamentoRequestDTO;
import com.ginasiouniforagenda.AgendamentoWeb.domain.event.AgendamentoResponseDTO;
import com.ginasiouniforagenda.AgendamentoWeb.repository.AgendamentoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/agendamento")
public class AgendamentoController {

    @Autowired
    private AgendamentoRepository repository;

    @GetMapping("/mes/{year}/{month}")
    public ResponseEntity<List<AgendamentoResponseDTO>> getAgendamentoByMonth(@PathVariable int year, @PathVariable int month) {
        LocalDateTime startOfMonth = LocalDateTime.of(year, month, 1, 0, 0);
        LocalDateTime endOfMonth = startOfMonth.with(TemporalAdjusters.lastDayOfMonth()).withHour(23).withMinute(59).withSecond(59);
        List<Agendamento> agendamentos = repository.findByDateTimeBetween(startOfMonth, endOfMonth);
        if (agendamentos == null) {
            return ResponseEntity.ok(Collections.emptyList());
        }
        List<AgendamentoResponseDTO> response = agendamentos.stream()
                .map(AgendamentoResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/cadastro")
    @Transactional
    public ResponseEntity<AgendamentoResponseDTO> create(@RequestBody AgendamentoRequestDTO data) {
        if (repository.existsByDateTimeAndPlace(data.dateTime(), data.place())) {
            return ResponseEntity.badRequest().build();
        }
        Agendamento agendamento = new Agendamento(data);
        repository.save(agendamento);
        return ResponseEntity.ok(new AgendamentoResponseDTO(agendamento));
    }

    @PutMapping("/edit/{id}")
    @Transactional
    public ResponseEntity<AgendamentoResponseDTO> update(@PathVariable UUID id, @RequestBody AgendamentoRequestDTO data) {
        Agendamento agendamento = repository.findById(id).orElse(null);
        if (agendamento == null || repository.existsByDateTimeAndPlaceAndIdNot(data.dateTime(), data.place(), id)) {
            return ResponseEntity.badRequest().build();
        }
        agendamento.setPlace(data.place());
        agendamento.setDateTime(data.dateTime());
        agendamento.setDayOfWeek(data.dayOfWeek());
        agendamento.setTimeSlot(data.timeSlot());
        agendamento.setReservationType(data.reservationType());
        agendamento.setBookedBy(data.bookedBy());
        agendamento.setEventDescription(data.eventDescription());
        agendamento.setAdditionalDetails(data.additionalDetails());
        repository.save(agendamento);
        return ResponseEntity.ok(new AgendamentoResponseDTO(agendamento));
    }

    @DeleteMapping("/delete/{id}")
    @Transactional
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/place/{place}")
    public ResponseEntity<List<AgendamentoResponseDTO>> getAgendamentoByPlace(@PathVariable String place) {
        List<Agendamento> agendamentos = repository.findByPlaceContainingIgnoreCase(place);
        List<AgendamentoResponseDTO> response = agendamentos.stream()
                .map(AgendamentoResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<AgendamentoResponseDTO>> getAgendamentoByDate(@PathVariable String date) {
        LocalDateTime dateTime = LocalDateTime.parse(date);
        List<Agendamento> agendamentos = repository.findByDateTime(dateTime);
        List<AgendamentoResponseDTO> response = agendamentos.stream()
                .map(AgendamentoResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
}