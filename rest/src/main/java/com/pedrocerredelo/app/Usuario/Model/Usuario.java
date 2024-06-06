package com.pedrocerredelo.app.Usuario.Model;

import jakarta.persistence.*;

@Entity
@Table(schema = "Usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "Nombre", unique = true, nullable = false, length = 25)
    private String nombre;

    @Column(name = "Contrasenha", nullable = false, length = 25)
    private String contrasenha;

    @Column(name = "Estado", length = 100)
    private String estado;

    @Lob
    @Column(name = "Foto", columnDefinition = "LONGBLOB")
    private byte[] foto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasenha() {
        return contrasenha;
    }

    public void setContrasenha(String contrasenha) {
        this.contrasenha = contrasenha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }
}
