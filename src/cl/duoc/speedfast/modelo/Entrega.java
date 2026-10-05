package modelo;

public class Entrega {
    // atributos principales que representan una entrega en el sistema
    private int id;
    private int idPedido;
    private int idRepartidor;
    private String fecha;
    private String hora;

    // constructor completo que incluye el id (util para leer registros desde la base de datos)
    public Entrega(int id, int idPedido, int idRepartidor, String fecha, String hora) {
        this.id = id;
        this.idPedido = idPedido;
        this.idRepartidor = idRepartidor;
        this.fecha = fecha;
        this.hora = hora;
    }

    // constructor sin el id (util para crear nuevos registros antes de que la base de datos les asigne un id autoincremental)
    public Entrega(int idPedido, int idRepartidor, String fecha, String hora) {
        this.idPedido = idPedido;
        this.idRepartidor = idRepartidor;
        this.fecha = fecha;
        this.hora = hora;
    }

    // metodos getter y setter para acceder y modificar cada atributo de forma segura
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdPedido() { return idPedido; }
    public void setIdPedido(int idPedido) { this.idPedido = idPedido; }

    public int getIdRepartidor() { return idRepartidor; }
    public void setIdRepartidor(int idRepartidor) { this.idRepartidor = idRepartidor; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getHora() { return hora; }
    public void setHora(String hora) { this.hora = hora; }

    // metodo toString sobrescrito para que el combobox muestre una descripcion clara de la entrega si se requiere
    @Override
    public String toString() {
        return "Entrega #" + id + " (Pedido: " + idPedido + ", Repartidor: " + idRepartidor + ")";
    }
}