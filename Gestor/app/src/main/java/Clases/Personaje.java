package Clases;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Arrays;

public class Personaje implements Serializable {
    private long personajeId;
    private long usuarioId; // Este campo es parte de la clave compuesta
    private Usuario usuario;
    private String nombre;
    private int fuerza;
    private int destreza;
    private int constitucion;
    private int inteligencia;
    private int sabiduria;
    private int carisma;
    private int bonoCompetencia;
    private byte[] foto;

    public Personaje(long personajeId, long usuarioId, Usuario usuario, String nombre, int fuerza, int destreza, int constitucion,
                     int inteligencia, int sabiduria, int carisma, int bonoCompetencia, byte[] foto) {
        this.personajeId = personajeId;
        this.usuarioId = usuarioId;
        this.usuario = usuario;
        this.nombre = nombre;
        this.fuerza = fuerza;
        this.destreza = destreza;
        this.constitucion = constitucion;
        this.inteligencia = inteligencia;
        this.sabiduria = sabiduria;
        this.carisma = carisma;
        this.bonoCompetencia = bonoCompetencia;
        this.foto = foto;
    }

    public Personaje(JSONObject obj) throws JSONException {
        this.personajeId = obj.getLong("personajeId");
        this.usuarioId = obj.getLong("usuarioId"); // Asegúrate de que esto esté presente en el JSON
        this.usuario = new Usuario(obj.getJSONObject("usuario"));
        this.nombre = obj.getString("personajeNombre");
        this.fuerza = obj.getInt("caracteristicaFuerza");
        this.destreza = obj.getInt("caracteristicaDestreza");
        this.constitucion = obj.getInt("caracteristicaConstitucion");
        this.inteligencia = obj.getInt("caracteristicaInteligencia");
        this.sabiduria = obj.getInt("caracteristicaSabiduria");
        this.carisma = obj.getInt("caracteristicaCarisma");
        this.bonoCompetencia = obj.getInt("bonoCompetencia");
        this.foto = obj.has("foto") ? obj.getString("foto").getBytes() : null;
    }

    public Personaje() {
    }

    // Getters y setters
    public long getPersonajeId() {
        return personajeId;
    }

    public void setPersonajeId(long personajeId) {
        this.personajeId = personajeId;
    }

    public long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getFuerza() {
        return fuerza;
    }

    public void setFuerza(int fuerza) {
        this.fuerza = fuerza;
    }

    public int getDestreza() {
        return destreza;
    }

    public void setDestreza(int destreza) {
        this.destreza = destreza;
    }

    public int getConstitucion() {
        return constitucion;
    }

    public void setConstitucion(int constitucion) {
        this.constitucion = constitucion;
    }

    public int getInteligencia() {
        return inteligencia;
    }

    public void setInteligencia(int inteligencia) {
        this.inteligencia = inteligencia;
    }

    public int getSabiduria() {
        return sabiduria;
    }

    public void setSabiduria(int sabiduria) {
        this.sabiduria = sabiduria;
    }

    public int getCarisma() {
        return carisma;
    }

    public void setCarisma(int carisma) {
        this.carisma = carisma;
    }

    public int getBonoCompetencia() {
        return bonoCompetencia;
    }

    public void setBonoCompetencia(int bonoCompetencia) {
        this.bonoCompetencia = bonoCompetencia;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    @Override
    public String toString() {
        return "Personaje{" +
                "personajeId=" + personajeId +
                ", usuarioId=" + usuarioId +
                ", " + usuario.toString() +
                ", nombre='" + nombre + '\'' +
                ", fuerza=" + fuerza +
                ", destreza=" + destreza +
                ", constitucion=" + constitucion +
                ", inteligencia=" + inteligencia +
                ", sabiduria=" + sabiduria +
                ", carisma=" + carisma +
                ", bonoCompetencia=" + bonoCompetencia +
                ", foto='" + Arrays.toString(foto) + '\'' +
                '}';
    }
}

