package co.edu.upb.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Getter
@Setter
@ToString(exclude = {"evaluations","questions"})
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Proveedor")
public class Suppliers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long suppliersId;

    @Column(name = "Nit", nullable = false)
    private String suppliersNit;

    @Column(name = "Nombre", nullable = false)
    private String suppliersName;

    @Column(name = "Estado", nullable = false)
    private String suppliersState;

    @Column(name = "Telefono")
    private String suppliersPhone;

    @Column(name = "Direccion")
    private String suppliersAddress;

    @Column(name = "Tipo")
    private String suppliersType;


    @OneToOne
    @JoinColumn(name = "ID_Predecesor",referencedColumnName = "ID")
    @JsonManagedReference
    @JsonIgnoreProperties({"suppliersPhone", "suppliersAddress", "suppliersType","predecessor","categoryName", "evaluations"})
    private Suppliers predecessor; 

    @ManyToOne
    @JoinColumn(name = "ID_Categoria",referencedColumnName = "ID")
    private Category categoryName; 

    //@SuppressWarnings("deprecation")
    @OneToMany(mappedBy = "proveedor", cascade = CascadeType.ALL, orphanRemoval = true)
    //@Where(clause = "ID_Proveedor = ID")
    private List<Evaluation> evaluations = new ArrayList<>(); 
    
   /* @OneToMany(mappedBy = "proveedor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions = new ArrayList<>();
 */
    // Método para añadir una evaluación
    public void addEvaluation(Evaluation evaluation) {
        evaluations.add(evaluation);
        
    }
    /*public void addQuestion(Question question) {
        questions.add(question);
    }*/
}
