package co.edu.upb.controllers;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.upb.Mapper.EvaluationMapper;
import co.edu.upb.dto.ApiResponse;
import co.edu.upb.dto.EvaluationDTO;
import co.edu.upb.entities.Evaluation;
import co.edu.upb.services.EvaluationsService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class EvaluationsController {

    private final EvaluationsService evaluationsService;
      

    @Autowired
    private EvaluationMapper evaluationMapper;

    public EvaluationsController(EvaluationsService evaluationsService) {
        this.evaluationsService = evaluationsService;
    }

    @GetMapping("/evaluations")
    public ApiResponse<List<Evaluation>> getAllEvaluations() {
        List<Evaluation> evaluations = evaluationsService.getAllEvaluations();
        return new ApiResponse<>(evaluations, "Lista de evaluaciones obtenida con éxito");
    }
    
    @GetMapping("/evaluations/{id}")
    public ApiResponse<Evaluation> getEvaluationsById(@PathVariable long id) {
        Optional<Evaluation> evaluations = evaluationsService.getEvaluationsById(id);
        if (evaluations.isPresent()) {
            return new ApiResponse<>(evaluations.get(), "Evaluación encontrada");
        } else {
            return new ApiResponse<>(null, "Evaluación no encontrada");
        }
    }

    @PostMapping("/evaluations")
    public ResponseEntity<Evaluation> createEvaluation(@RequestBody EvaluationDTO evaluationDTO) {
        Evaluation evaluation = evaluationMapper.toEntity(evaluationDTO);
        Evaluation savedEvaluation = evaluationsService.addEvaluation(evaluation);
        return new ResponseEntity<>(savedEvaluation, HttpStatus.CREATED);
    }
}

