package Clases;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;


public class Personaje implements Serializable {
    private long personajeId;
    private long usuarioId; // Este campo es parte de la clave compuesta
    private String personajeNombre;
    private int caracteristicaFuerza;
    private int caracteristicaDestreza;
    private int caracteristicaConstitucion;
    private int caracteristicaInteligencia;
    private int caracteristicaSabiduria;
    private int caracteristicaCarisma;
    private int bonoCompetencia;
    // No incluir la foto como byte[] en la clase móvil, a menos que sea necesario
    private byte[] foto;

    public Personaje(long personajeId, long usuarioId, String personajeNombre, int caracteristicaFuerza,
                     int caracteristicaDestreza, int caracteristicaConstitucion, int caracteristicaInteligencia,
                     int caracteristicaSabiduria, int caracteristicaCarisma, int bonoCompetencia) {
        this.personajeId = personajeId;
        this.usuarioId = usuarioId;
        this.personajeNombre = personajeNombre;
        this.caracteristicaFuerza = caracteristicaFuerza;
        this.caracteristicaDestreza = caracteristicaDestreza;
        this.caracteristicaConstitucion = caracteristicaConstitucion;
        this.caracteristicaInteligencia = caracteristicaInteligencia;
        this.caracteristicaSabiduria = caracteristicaSabiduria;
        this.caracteristicaCarisma = caracteristicaCarisma;
        this.bonoCompetencia = bonoCompetencia;
    }

    public Personaje() {
    }

    public Personaje(JSONObject obj) throws JSONException {
        this.personajeId = obj.getJSONObject("id").getLong("personajeId");
        this.usuarioId = obj.getJSONObject("id").getLong("usuarioId");
        this.personajeNombre = obj.getString("personajeNombre");
        this.caracteristicaFuerza = obj.getInt("caracteristicaFuerza");
        this.caracteristicaDestreza = obj.getInt("caracteristicaDestreza");
        this.caracteristicaConstitucion = obj.getInt("caracteristicaConstitucion");
        this.caracteristicaInteligencia = obj.getInt("caracteristicaInteligencia");
        this.caracteristicaSabiduria = obj.getInt("caracteristicaSabiduria");
        this.caracteristicaCarisma = obj.getInt("caracteristicaCarisma");
        this.bonoCompetencia = obj.getInt("bonoCompetencia");
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

    public String getNombre() {
        return personajeNombre;
    }

    public void setNombre(String personajeNombre) {
        this.personajeNombre = personajeNombre;
    }

    public int getFuerza() {
        return caracteristicaFuerza;
    }

    public void setFuerza(int caracteristicaFuerza) {
        this.caracteristicaFuerza = caracteristicaFuerza;
    }

    public int getDestreza() {
        return caracteristicaDestreza;
    }

    public void setDestreza(int caracteristicaDestreza) {
        this.caracteristicaDestreza = caracteristicaDestreza;
    }

    public int getConstitucion() {
        return caracteristicaConstitucion;
    }

    public void setConstitucion(int caracteristicaConstitucion) {
        this.caracteristicaConstitucion = caracteristicaConstitucion;
    }

    public int getInteligencia() {
        return caracteristicaInteligencia;
    }

    public void setInteligencia(int caracteristicaInteligencia) {
        this.caracteristicaInteligencia = caracteristicaInteligencia;
    }

    public int getSabiduria() {
        return caracteristicaSabiduria;
    }

    public void setSabiduria(int caracteristicaSabiduria) {
        this.caracteristicaSabiduria = caracteristicaSabiduria;
    }

    public int getCarisma() {
        return caracteristicaCarisma;
    }

    public void setCarisma(int caracteristicaCarisma) {
        this.caracteristicaCarisma = caracteristicaCarisma;
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
                ", personajeNombre='" + personajeNombre + '\'' +
                ", caracteristicaFuerza=" + caracteristicaFuerza +
                ", caracteristicaDestreza=" + caracteristicaDestreza +
                ", caracteristicaConstitucion=" + caracteristicaConstitucion +
                ", caracteristicaInteligencia=" + caracteristicaInteligencia +
                ", caracteristicaSabiduria=" + caracteristicaSabiduria +
                ", caracteristicaCarisma=" + caracteristicaCarisma +
                ", bonoCompetencia=" + bonoCompetencia +
                '}';
    }
}


