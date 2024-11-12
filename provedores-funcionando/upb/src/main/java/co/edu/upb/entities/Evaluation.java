package co.edu.upb.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Evaluacion")
@ToString
public class Evaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @Column(name = "Fecha", nullable = false)
    private Date date;

    @Column(name = "Calificacion", nullable = false)
    private int rating; 
    
    @Column(name = "Detalle", nullable = false)
    private String detail; 

    //@OneToMany(mappedBy = "evaluacion", cascade = CascadeType.ALL, orphanRemoval = true)
    //private List<Question> questions = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "ID_Proveedor", nullable = false,referencedColumnName = "ID")
    @JsonIgnore
    @JsonBackReference
    private Suppliers proveedor;

    @ManyToOne
    @JoinColumn(name = "ID_Usuario", nullable = false,referencedColumnName = "ID")
    @JsonIgnore
    private User usuario;
    
}
