package model;

/**
 *
 * @author benjamin
 */
public class Mascota {

    private int id;
    private String nombre;
    private String animal;

    public Mascota() {
    }

    public Mascota(int id, String nombre, String animal) {
        this.id = id;
        this.nombre = nombre;
        this.animal = animal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAnimal() {
        return animal;
    }

    public void setAnimal(String animal) {
        this.animal = animal;
    }

    @Override
    public String toString() {
        return "Mascota{" + "id=" + id + ", nombre=" + nombre + ", animal=" + animal + '}';
    }
    
    
}
