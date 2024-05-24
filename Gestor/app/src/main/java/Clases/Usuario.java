package Clases;

import org.json.JSONException;
import org.json.JSONObject;

public class Usuario {
        private Long id;
        private String nombre;
        private String contrasenha;
        private String estado;
        private String foto;

    public Usuario(JSONObject obj) throws JSONException {
        setId( Long.parseLong(obj.get("id").toString()));
        setNombre(obj.get("nombre").toString());
        setContrasenha(obj.get("contrasenha").toString());
        setEstado(obj.get("estado").toString());
        setFoto(obj.get("foto").toString());
    }

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
        return this.contrasenha;
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

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", contrasenha='" + contrasenha + '\'' +
                ", estado='" + estado + '\'' +
                ", foto='" + foto + '\'' +
                '}';
    }
}
