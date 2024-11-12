package co.edu.upb.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import co.edu.upb.entities.Evaluation;
import co.edu.upb.entities.Suppliers;
import co.edu.upb.entities.User;
import co.edu.upb.repository.EvaluationRepository;
import co.edu.upb.repository.SuppliersRepository;
import co.edu.upb.repository.UserRepository;

@Service
public class EvaluationsService {

    private final EvaluationRepository evaluationRepository;
    private final SuppliersRepository suppliersRepository;
    private final UserRepository userRepository;

    public EvaluationsService(EvaluationRepository evaluationRepository, SuppliersRepository suppliersRepository, UserRepository userRepository) {
        this.evaluationRepository = evaluationRepository;
        this.suppliersRepository = suppliersRepository;
        this.userRepository = userRepository;
    }

    public Optional<Evaluation> getEvaluationsById(long id) {
        return evaluationRepository.findById(id);
    }

    public List<Evaluation> getAllEvaluations() {
        return evaluationRepository.findAll();
    }

    public Evaluation addEvaluation(Evaluation evaluation) {
        System.out.println(evaluation.getProveedor());
        Suppliers proveedor = suppliersRepository.findById(evaluation.getProveedor().getSuppliersId())
                .orElseThrow(() -> new IllegalArgumentException("Proveedor no encontrado"));
        User usuario = userRepository.findById(evaluation.getUsuario().getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        // Asignar proveedor y usuario a la evaluación
        evaluation.setProveedor(proveedor);
        evaluation.setUsuario(usuario);

        return evaluationRepository.save(evaluation);
    }
}
