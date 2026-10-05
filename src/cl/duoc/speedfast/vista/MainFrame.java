package vista;

import dao.EntregaDAO;
import dao.PedidoDAO;
import dao.RepartidorDAO;
import modelo.Entrega;
import modelo.Pedido;
import modelo.Repartidor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MainFrame extends JFrame {

    // instancias de los objetos dao para interactuar con la base de datos
    private RepartidorDAO repartidorDAO = new RepartidorDAO();
    private PedidoDAO pedidoDAO = new PedidoDAO();
    private EntregaDAO entregaDAO = new EntregaDAO();

    // componentes para la pestana de repartidores
    private JTextField txtNombreRepartidor;
    private JTable tablaRepartidores;
    private DefaultTableModel modeloRepartidores;
    private int idRepartidorSeleccionado = -1;

    // componentes para la pestana de pedidos
    private JTextField txtDireccionPedido;
    private JComboBox<String> cmbTipoPedido;
    private JComboBox<String> cmbEstadoPedido;
    private JTable tablaPedidos;
    private DefaultTableModel modeloPedidos;
    private int idPedidoSeleccionado = -1;

    // componentes para la pestana de entregas
    private JComboBox<Repartidor> cmbRepartidorEntrega;
    private JComboBox<Pedido> cmbPedidoEntrega;
    private JTextField txtFechaEntrega;
    private JTextField txtHoraEntrega;
    private JTable tablaEntregas;
    private DefaultTableModel modeloEntregas;
    private int idEntregaSeleccionada = -1;

    // constructor principal para inicializar la interfaz grafica y sus pestanas
    public MainFrame() {
        setTitle("SpeedFast - Sistema de Gestion Integral");
        setSize(1200, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // creacion del panel de pestanas para organizar los modulos
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("Repartidores", crearPanelRepartidores());
        tabbedPane.add("Pedidos", crearPanelPedidos());
        tabbedPane.add("Entregas", crearPanelEntregas());

        add(tabbedPane);

        // carga inicial de datos desde la base de datos hacia las tablas y combobox
        actualizarDatosRepartidores();
        actualizarDatosPedidos();
        actualizarDatosEntregas();
    }

    // metodo que construye y configura el panel visual de repartidores
    private JPanel crearPanelRepartidores() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel form = new JPanel(new FlowLayout(FlowLayout.LEFT));
        form.add(new JLabel("Nombre:"));
        txtNombreRepartidor = new JTextField(15);
        form.add(txtNombreRepartidor);

        JButton btnGuardar = new JButton("Guardar");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnLimpiar = new JButton("Limpiar");

        form.add(btnGuardar);
        form.add(btnActualizar);
        form.add(btnEliminar);
        form.add(btnLimpiar);
        panel.add(form, BorderLayout.NORTH);

        modeloRepartidores = new DefaultTableModel(new String[]{"ID", "Nombre"}, 0);
        tablaRepartidores = new JTable(modeloRepartidores);
        panel.add(new JScrollPane(tablaRepartidores), BorderLayout.CENTER);

        // evento para detectar la seleccion de una fila en la tabla de repartidores
        tablaRepartidores.getSelectionModel().addListSelectionListener(e -> {
            int fila = tablaRepartidores.getSelectedRow();
            if (fila >= 0) {
                idRepartidorSeleccionado = (int) modeloRepartidores.getValueAt(fila, 0);
                txtNombreRepartidor.setText((String) modeloRepartidores.getValueAt(fila, 1));
            }
        });

        // accion para guardar un nuevo repartidor
        btnGuardar.addActionListener(e -> {
            if (txtNombreRepartidor.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "El nombre no puede estar vacio.", "Validacion", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Repartidor r = new Repartidor(txtNombreRepartidor.getText().trim());
            if (repartidorDAO.create(r)) {
                JOptionPane.showMessageDialog(this, "Repartidor registrado con exito.");
                actualizarDatosRepartidores();
                limpiarRepartidor();
            } else {
                JOptionPane.showMessageDialog(this, "Error al registrar repartidor.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // accion para actualizar un repartidor existente
        btnActualizar.addActionListener(e -> {
            if (idRepartidorSeleccionado == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un repartidor de la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Repartidor r = new Repartidor(idRepartidorSeleccionado, txtNombreRepartidor.getText().trim());
            if (repartidorDAO.update(r)) {
                JOptionPane.showMessageDialog(this, "Repartidor actualizado con exito.");
                actualizarDatosRepartidores();
                limpiarRepartidor();
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // accion para eliminar un repartidor seleccionado
        btnEliminar.addActionListener(e -> {
            if (idRepartidorSeleccionado == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un repartidor de la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int confirmar = JOptionPane.showConfirmDialog(this, "Esta seguro de eliminar?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirmar == JOptionPane.YES_OPTION) {
                if (repartidorDAO.delete(idRepartidorSeleccionado)) {
                    JOptionPane.showMessageDialog(this, "Repartidor eliminado.");
                    actualizarDatosRepartidores();
                    limpiarRepartidor();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnLimpiar.addActionListener(e -> limpiarRepartidor());
        return panel;
    }

    // metodo para refrescar los datos de la tabla y combobox de repartidores
    private void actualizarDatosRepartidores() {
        modeloRepartidores.setRowCount(0);
        List<Repartidor> lista = repartidorDAO.readAll();
        for (Repartidor r : lista) {
            modeloRepartidores.addRow(new Object[]{r.getId(), r.getNombre()});
        }
        if (cmbRepartidorEntrega != null) {
            cmbRepartidorEntrega.removeAllItems();
            for (Repartidor r : lista) {
                cmbRepartidorEntrega.addItem(r);
            }
        }
    }

    private void limpiarRepartidor() {
        txtNombreRepartidor.setText("");
        idRepartidorSeleccionado = -1;
        tablaRepartidores.clearSelection();
    }

    // metodo que construye y configura el panel visual de pedidos
    private JPanel crearPanelPedidos() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel form = new JPanel(new FlowLayout(FlowLayout.LEFT));
        form.add(new JLabel("Direccion:"));
        txtDireccionPedido = new JTextField(12);
        form.add(txtDireccionPedido);

        form.add(new JLabel("Tipo:"));
        cmbTipoPedido = new JComboBox<>(new String[]{"COMIDA", "ENCOMIENDA", "EXPRESS"});
        form.add(cmbTipoPedido);

        form.add(new JLabel("Estado:"));
        cmbEstadoPedido = new JComboBox<>(new String[]{"PENDIENTE", "EN_REPARTO", "ENTREGADO"});
        form.add(cmbEstadoPedido);

        JButton btnGuardar = new JButton("Guardar");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnLimpiar = new JButton("Limpiar");

        form.add(btnGuardar);
        form.add(btnActualizar);
        form.add(btnEliminar);
        form.add(btnLimpiar);
        panel.add(form, BorderLayout.NORTH);

        modeloPedidos = new DefaultTableModel(new String[]{"ID", "Direccion", "Tipo", "Estado"}, 0);
        tablaPedidos = new JTable(modeloPedidos);
        panel.add(new JScrollPane(tablaPedidos), BorderLayout.CENTER);

        // evento para cargar los datos del pedido seleccionado en los campos del formulario
        tablaPedidos.getSelectionModel().addListSelectionListener(e -> {
            int fila = tablaPedidos.getSelectedRow();
            if (fila >= 0) {
                idPedidoSeleccionado = (int) modeloPedidos.getValueAt(fila, 0);
                txtDireccionPedido.setText((String) modeloPedidos.getValueAt(fila, 1));
                cmbTipoPedido.setSelectedItem(modeloPedidos.getValueAt(fila, 2));
                cmbEstadoPedido.setSelectedItem(modeloPedidos.getValueAt(fila, 3));
            }
        });

        // accion para registrar un nuevo pedido
        btnGuardar.addActionListener(e -> {
            if (txtDireccionPedido.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "La direccion es obligatoria.", "Validacion", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Pedido p = new Pedido(txtDireccionPedido.getText().trim(), cmbTipoPedido.getSelectedItem().toString(), cmbEstadoPedido.getSelectedItem().toString());
            if (pedidoDAO.create(p)) {
                JOptionPane.showMessageDialog(this, "Pedido registrado con exito.");
                actualizarDatosPedidos();
                limpiarPedido();
            } else {
                JOptionPane.showMessageDialog(this, "Error al registrar pedido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // accion para actualizar un pedido existente
        btnActualizar.addActionListener(e -> {
            if (idPedidoSeleccionado == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un pedido de la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Pedido p = new Pedido(idPedidoSeleccionado, txtDireccionPedido.getText().trim(), cmbTipoPedido.getSelectedItem().toString(), cmbEstadoPedido.getSelectedItem().toString());
            if (pedidoDAO.update(p)) {
                JOptionPane.showMessageDialog(this, "Pedido actualizado con exito.");
                actualizarDatosPedidos();
                limpiarPedido();
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar pedido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // accion para eliminar un pedido seleccionado
        btnEliminar.addActionListener(e -> {
            if (idPedidoSeleccionado == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un pedido de la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int confirmar = JOptionPane.showConfirmDialog(this, "Esta seguro de eliminar este pedido?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirmar == JOptionPane.YES_OPTION) {
                if (pedidoDAO.delete(idPedidoSeleccionado)) {
                    JOptionPane.showMessageDialog(this, "Pedido eliminado.");
                    actualizarDatosPedidos();
                    limpiarPedido();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar pedido.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnLimpiar.addActionListener(e -> limpiarPedido());
        return panel;
    }

    // metodo para refrescar la tabla y el combobox de pedidos
    private void actualizarDatosPedidos() {
        modeloPedidos.setRowCount(0);
        List<Pedido> lista = pedidoDAO.readAll();
        for (Pedido p : lista) {
            modeloPedidos.addRow(new Object[]{p.getId(), p.getDireccion(), p.getTipo(), p.getEstado()});
        }
        if (cmbPedidoEntrega != null) {
            cmbPedidoEntrega.removeAllItems();
            for (Pedido p : lista) {
                cmbPedidoEntrega.addItem(p);
            }
        }
    }

    private void limpiarPedido() {
        txtDireccionPedido.setText("");
        cmbTipoPedido.setSelectedIndex(0);
        cmbEstadoPedido.setSelectedIndex(0);
        idPedidoSeleccionado = -1;
        tablaPedidos.clearSelection();
    }

    // metodo que construye y configura el panel visual de entregas y relaciones
    private JPanel crearPanelEntregas() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel form = new JPanel(new FlowLayout(FlowLayout.LEFT));
        form.add(new JLabel("Pedido:"));
        cmbPedidoEntrega = new JComboBox<>();
        form.add(cmbPedidoEntrega);

        form.add(new JLabel("Repartidor:"));
        cmbRepartidorEntrega = new JComboBox<>();
        form.add(cmbRepartidorEntrega);

        form.add(new JLabel("Fecha (YYYY-MM-DD):"));
        txtFechaEntrega = new JTextField(8);
        form.add(txtFechaEntrega);

        form.add(new JLabel("Hora (HH:MM:SS):"));
        txtHoraEntrega = new JTextField(6);
        form.add(txtHoraEntrega);

        JButton btnGuardar = new JButton("Guardar");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnLimpiar = new JButton("Limpiar");

        form.add(btnGuardar);
        form.add(btnActualizar);
        form.add(btnEliminar);
        form.add(btnLimpiar);
        panel.add(form, BorderLayout.NORTH);

        modeloEntregas = new DefaultTableModel(new String[]{"ID", "ID Pedido", "ID Repartidor", "Fecha", "Hora"}, 0);
        tablaEntregas = new JTable(modeloEntregas);
        panel.add(new JScrollPane(tablaEntregas), BorderLayout.CENTER);

        // evento para recuperar los datos de la entrega seleccionada en la tabla
        tablaEntregas.getSelectionModel().addListSelectionListener(e -> {
            int fila = tablaEntregas.getSelectedRow();
            if (fila >= 0) {
                idEntregaSeleccionada = (int) modeloEntregas.getValueAt(fila, 0);
                txtFechaEntrega.setText(modeloEntregas.getValueAt(fila, 3).toString());
                txtHoraEntrega.setText(modeloEntregas.getValueAt(fila, 4).toString());
            }
        });

        // accion para registrar una nueva entrega vinculando pedido y repartidor
        btnGuardar.addActionListener(e -> {
            if (cmbPedidoEntrega.getSelectedItem() == null || cmbRepartidorEntrega.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un pedido y un repartidor.", "Validacion", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (txtFechaEntrega.getText().trim().isEmpty() || txtHoraEntrega.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "La fecha y hora son obligatorias.", "Validacion", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Pedido pedidoSel = (Pedido) cmbPedidoEntrega.getSelectedItem();
            Repartidor repSel = (Repartidor) cmbRepartidorEntrega.getSelectedItem();

            Entrega ent = new Entrega(pedidoSel.getId(), repSel.getId(), txtFechaEntrega.getText().trim(), txtHoraEntrega.getText().trim());

            if (entregaDAO.create(ent)) {
                JOptionPane.showMessageDialog(this, "Entrega registrada con exito.");
                actualizarDatosEntregas();
                limpiarEntrega();
            } else {
                JOptionPane.showMessageDialog(this, "Error al registrar entrega.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // accion para actualizar una entrega existente
        btnActualizar.addActionListener(e -> {
            if (idEntregaSeleccionada == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione una entrega de la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Pedido pedidoSel = (Pedido) cmbPedidoEntrega.getSelectedItem();
            Repartidor repSel = (Repartidor) cmbRepartidorEntrega.getSelectedItem();

            Entrega ent = new Entrega(idEntregaSeleccionada, pedidoSel.getId(), repSel.getId(), txtFechaEntrega.getText().trim(), txtHoraEntrega.getText().trim());

            if (entregaDAO.update(ent)) {
                JOptionPane.showMessageDialog(this, "Entrega actualizada con exito.");
                actualizarDatosEntregas();
                limpiarEntrega();
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar entrega.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // accion para eliminar una entrega seleccionada
        btnEliminar.addActionListener(e -> {
            if (idEntregaSeleccionada == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione una entrega de la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int confirmar = JOptionPane.showConfirmDialog(this, "Esta seguro de eliminar esta entrega?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirmar == JOptionPane.YES_OPTION) {
                if (entregaDAO.delete(idEntregaSeleccionada)) {
                    JOptionPane.showMessageDialog(this, "Entrega eliminada.");
                    actualizarDatosEntregas();
                    limpiarEntrega();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar entrega.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnLimpiar.addActionListener(e -> limpiarEntrega());
        return panel;
    }

    // metodo para refrescar los datos de la tabla de entregas
    private void actualizarDatosEntregas() {
        modeloEntregas.setRowCount(0);
        List<Entrega> lista = entregaDAO.readAll();
        for (Entrega ent : lista) {
            modeloEntregas.addRow(new Object[]{ent.getId(), ent.getIdPedido(), ent.getIdRepartidor(), ent.getFecha(), ent.getHora()});
        }
    }

    private void limpiarEntrega() {
        txtFechaEntrega.setText("");
        txtHoraEntrega.setText("");
        idEntregaSeleccionada = -1;
        tablaEntregas.clearSelection();
    }

    // punto de entrada principal de la aplicacion de escritorio
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}