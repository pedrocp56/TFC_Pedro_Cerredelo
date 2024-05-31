package Clases;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Usuario implements Serializable {
        private Long id;
        private String nombre;
        private String contrasenha;
        private String estado;
        private String foto;

    public Usuario(long id, String nombre, String contrasenha, String estado, String foto) {
        this.id = id;
        this.nombre = nombre;
        this.contrasenha = contrasenha;
        this.estado = estado;
        this.foto = foto;
    }

    public Usuario(JSONObject obj) throws JSONException {
        this.id = obj.getLong("id");
        this.nombre = obj.getString("nombre");
        this.contrasenha = obj.getString("contrasenha");
        this.estado = obj.optString("estado", ""); // Usar optString para manejar valores null
        this.foto = obj.optString("foto", null); // Usar optString para manejar valores null
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
