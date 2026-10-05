# 🖥️ Actividad Sumativa Semana 8 – Desarrollo Orientado a Objetos II

## 👤 Autor del proyecto
- **Nombre completo:** Matias Felipe Lopez Villarroel
- **Sección:** A005
- **Carrera:** Analista Programador Computacional
- **Sede:** Modalidad Online


---

## 📘 Descripción general del sistema
Este proyecto corresponde al desarrollo integrador final para la empresa **SpeedFast**, titulado **"Sistema de Gestión Integral con Java Swing, JDBC y Patrón DAO"**.

La aplicación implementa una arquitectura en capas conectando una interfaz gráfica de escritorio con una base de datos relacional **MySQL** mediante JDBC. Permite realizar operaciones CRUD completas (Crear, Leer, Actualizar y Eliminar) en tiempo real para tres entidades principales interrelacionadas: Repartidores, Pedidos y Entregas.

### Funcionalidades principales:
- **Gestión de Conexión (`ConexionDB`):** Conexión segura al servidor local MySQL mediante `DriverManager`.
- **Capa de Acceso a Datos (DAO):** Clases especializadas (`RepartidorDAO`, `PedidoDAO` y `EntregaDAO`) que utilizan consultas parametrizadas (`PreparedStatement` y `ResultSet`) para garantizar la integridad y seguridad de la información[cite: 1].
- **Interfaz Gráfica Integral (`MainFrame`):** Desarrollada en Java Swing utilizando un panel de pestañas (`JTabbedPane`) para separar de forma ordenada la administración de cada entidad, con actualización automática en tablas (`JTable`) y menús desplegables (`JComboBox`) vinculados por llaves foráneas[cite: 1].

---

## 🧱 Estructura general del proyecto

```plaintext
📁 src/
├── conexion/             # Conexión centralizada con MySQL
│   └── ConexionDB.java
├── dao/                  # Clases de acceso a datos y lógica SQL
│   ├── EntregaDAO.java
│   ├── PedidoDAO.java
│   └── RepartidorDAO.java
├── modelo/               # Clases de entidad / POJOs del negocio
│   ├── Entrega.java
│   ├── Pedido.java
│   └── Repartidor.java
├── vista/                # Interfaz gráfica principal (Java Swing)
│   └── MainFrame.java
📁 lib/
    └── mysql-connector-j-*.jar   # Conector JDBC de MySQL
```
### ⚙️ Configuración de la Base de Datos
Ejecuta el siguiente script en MySQL Workbench para crear la base de datos y sus tablas con restricciones de integridad:

```
SQL
CREATE DATABASE IF NOT EXISTS speedfast_db;
USE speedfast_db;

CREATE TABLE IF NOT EXISTS repartidores (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS pedidos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    direccion VARCHAR(100) NOT NULL,
    tipo ENUM('COMIDA','ENCOMIENDA','EXPRESS'),
    estado ENUM('PENDIENTE','EN_REPARTO','ENTREGADO')
);

CREATE TABLE IF NOT EXISTS entregas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_pedido INT,
    id_repartidor INT,
    fecha DATE,
    hora TIME,
    FOREIGN KEY (id_pedido) REFERENCES pedidos(id) ON DELETE CASCADE,
    FOREIGN KEY (id_repartidor) REFERENCES repartidores(id) ON DELETE CASCADE
);
```
## ⚙️ Instrucciones para clonar y ejecutar el proyecto
1. Clona el repositorio desde GitHub:

```Bash
git clone https://github.com/matiaslv207code/SpeedFast_Semana8.git
```
2. Abre el proyecto completo en tu entorno IntelliJ IDEA.

3. Asegúrate de tener configurado el archivo .jar del conector de MySQL como librería externa del proyecto en la carpeta lib.

4. Verifica tus credenciales de acceso a MySQL (usuario root y tu contraseña configurada) en la clase ConexionDB.java.

5. Ejecuta la clase principal MainFrame.java ubicada dentro del paquete vista para iniciar la interfaz gráfica de gestión.

**Repositorio GitHub**: https://github.com/matiaslv207code/SpeedFast_Semana8
**Fecha de entrega**: [05/10/2026]

© Duoc UC | Escuela de Informática y Telecomunicaciones | Desarrollo Orientado a Objetos II