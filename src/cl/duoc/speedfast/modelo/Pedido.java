package modelo;

public class Pedido {
    // atributos que definen las caracteristicas de un pedido en el sistema
    private int id;
    private String direccion;
    private String tipo; // COMIDA, ENCOMIENDA, EXPRESS
    private String estado; // PENDIENTE, EN_REPARTO, ENTREGADO

    // constructor con id incluido para cuando se recuperan datos desde la base de datos
    public Pedido(int id, String direccion, String tipo, String estado) {
        this.id = id;
        this.direccion = direccion;
        this.tipo = tipo;
        this.estado = estado;
    }

    // constructor sin id para crear un nuevo pedido antes de registrarlo en la base de datos
    public Pedido(String direccion, String tipo, String estado) {
        this.direccion = direccion;
        this.tipo = tipo;
        this.estado = estado;
    }

    // metodos getter y setter para acceder y modificar cada propiedad de forma segura
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    // metodo tostring sobrescrito para mostrar una descripcion clara del pedido en el combobox de la interfaz
    @Override
    public String toString() {
        return id + " - " + direccion + " (" + tipo + ")"; // util para el JComboBox
    }
}