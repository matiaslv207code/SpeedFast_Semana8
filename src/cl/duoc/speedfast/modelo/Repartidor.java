package modelo;

public class Repartidor {
    // atributos que representan a un repartidor en el sistema
    private int id;
    private String nombre;

    // constructor con id incluido para cuando se obtienen datos desde la base de datos
    public Repartidor(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    // constructor sin id para crear un nuevo repartidor antes de registrarlo
    public Repartidor(String nombre) {
        this.nombre = nombre;
    }

    // metodos getter y setter para acceder y modificar las propiedades de forma segura
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    // metodo tostring sobrescrito para mostrar el id y nombre de forma legible
    @Override
    public String toString() {
        return id + " - " + nombre; // util para el JComboBox
    }
}