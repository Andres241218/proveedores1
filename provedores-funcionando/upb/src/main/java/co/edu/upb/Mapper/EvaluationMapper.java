package co.edu.upb.Mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.edu.upb.dto.EvaluationDTO;
import co.edu.upb.entities.Evaluation;
import co.edu.upb.repository.SuppliersRepository;
import co.edu.upb.repository.UserRepository;

@Component
public class EvaluationMapper {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SuppliersRepository supplierRepository;

    public Evaluation toEntity(EvaluationDTO dto) {
        Evaluation evaluation = new Evaluation();
        evaluation.setDate(dto.getDate());
        evaluation.setRating(dto.getRating());
        evaluation.setDetail(dto.getDetail());

        // Establecer usuario
        if (dto.getUsuarioId() != null) {
            userRepository.findById(dto.getUsuarioId()).ifPresent(evaluation::setUsuario);
        }

        // Establecer proveedor
        if (dto.getProveedorId() != null) {
            supplierRepository.findById(dto.getProveedorId()).ifPresent(evaluation::setProveedor);
        }

        return evaluation;
    }
}

