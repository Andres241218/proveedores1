package co.edu.upb.dto;

import java.util.Date;


public class EvaluationDTO {
    private Date date;
    private int rating;
    private String detail; 
    private Long usuarioId;
    private Long proveedorId;

    // Constructor por defecto
    public EvaluationDTO() {
    }

    // Constructor con parámetros
    public EvaluationDTO(Date date, int rating, Long usuarioId,String detail, Long proveedorId) {
        this.date = date;
        this.detail = detail;
        this.rating = rating;
        this.usuarioId = usuarioId;
        this.proveedorId = proveedorId;
    }

    // Getters y Setters
    public String getDetail() {
        return detail;
    }
    public void setDetail(String detail) {
        this.detail = detail;
    }
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(Long proveedorId) {
        this.proveedorId = proveedorId;
    }
}
