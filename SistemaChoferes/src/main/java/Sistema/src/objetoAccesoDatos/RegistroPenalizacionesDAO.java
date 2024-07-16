package Sistema.src.objetoAccesoDatos;

import Sistema.src.clasesEntidades.RegistroPenalizaciones;
import Sistema.src.conexionBaseDatos.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RegistroPenalizacionesDAO {
    private Connection connection;

    public RegistroPenalizacionesDAO() {
        connection = Conexion.conectar();
    }

    public boolean actualizarPenalizaciones(int idChofer) {
        try {
            // Verificar si ya existe un registro de penalizaciones para el chofer
            PreparedStatement checkPenalizaciones = connection
                    .prepareStatement("SELECT n_Infracciones FROM registro_penalizaciones WHERE id_Chofer=?");
            checkPenalizaciones.setInt(1, idChofer);
            ResultSet rs = checkPenalizaciones.executeQuery();
            
            if (rs.next()) {
                // Si existe, incrementar el número de infracciones
                int infracciones = rs.getInt("n_Infracciones") + 1;
                PreparedStatement updatePenalizaciones = connection
                        .prepareStatement("UPDATE registro_penalizaciones SET n_Infracciones=?, penalizacion_Chofer=? WHERE id_Chofer=?");
                updatePenalizaciones.setInt(1, infracciones);
                updatePenalizaciones.setString(2, infracciones >= 3 ? "Despedido" : "Advertencia");
                updatePenalizaciones.setInt(3, idChofer);
                updatePenalizaciones.executeUpdate();
                
                // Si el número de infracciones es 3 o más, indicar que el chofer debe ser despedido
                return infracciones >= 3;
            } else {
                // Si no existe, insertar un nuevo registro de penalización con una infracción
                PreparedStatement insertPenalizacion = connection
                        .prepareStatement("INSERT INTO registro_penalizaciones (n_Infracciones, penalizacion_Chofer, id_Chofer) VALUES (?, ?, ?)");
                insertPenalizacion.setInt(1, 1);
                insertPenalizacion.setString(2, "Advertencia");
                insertPenalizacion.setInt(3, idChofer);
                insertPenalizacion.executeUpdate();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void eliminarPenalizacionesPorChofer(int idChofer) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("DELETE FROM registro_penalizaciones WHERE id_Chofer=?");
            preparedStatement.setInt(1, idChofer);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void agregarRegistroPenalizacion(RegistroPenalizaciones registroPenalizacion) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO registro_penalizaciones (n_Infracciones, penalizacion_Chofer, id_Chofer) VALUES (?, ?, ?)");
            preparedStatement.setInt(1, registroPenalizacion.getNInfracciones());
            preparedStatement.setString(2, registroPenalizacion.getPenalizacionChofer());
            preparedStatement.setInt(3, registroPenalizacion.getIdChofer());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarRegistroPenalizacion(int idRegPen) {
        if (!existeRegistroPenalizacion(idRegPen)) {
            System.out.println("Error: El registro de penalización con el ID " + idRegPen + " no existe.");
            return;
        }
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("DELETE FROM registro_penalizaciones WHERE id_RegPen=?");
            preparedStatement.setInt(1, idRegPen);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizarRegistroPenalizacion(RegistroPenalizaciones registroPenalizacion) {
        if (!existeRegistroPenalizacion(registroPenalizacion.getIdRegPen())) {
            System.out.println("Error: El registro de penalización con el ID " + registroPenalizacion.getIdRegPen() + " no existe.");
            return;
        }
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("UPDATE registro_penalizaciones SET n_Infracciones=?, penalizacion_Chofer=?, id_Chofer=? WHERE id_RegPen=?");
            preparedStatement.setInt(1, registroPenalizacion.getNInfracciones());
            preparedStatement.setString(2, registroPenalizacion.getPenalizacionChofer());
            preparedStatement.setInt(3, registroPenalizacion.getIdChofer());
            preparedStatement.setInt(4, registroPenalizacion.getIdRegPen());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<RegistroPenalizaciones> obtenerTodoRegistroPenalizaciones() {
        List<RegistroPenalizaciones> registros = new ArrayList<RegistroPenalizaciones>();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM registro_penalizaciones");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                RegistroPenalizaciones registroPenalizacion = new RegistroPenalizaciones();
                registroPenalizacion.setIdRegPen(rs.getInt("id_RegPen"));
                registroPenalizacion.setNInfracciones(rs.getInt("n_Infracciones"));
                registroPenalizacion.setPenalizacionChofer(rs.getString("penalizacion_Chofer"));
                registroPenalizacion.setIdChofer(rs.getInt("id_Chofer"));
                registros.add(registroPenalizacion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return registros;
    }

    public RegistroPenalizaciones obtenerRegistroPenalizacionPorId(int idRegPen) {
        RegistroPenalizaciones registroPenalizacion = new RegistroPenalizaciones();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM registro_penalizaciones WHERE id_RegPen=?");
            preparedStatement.setInt(1, idRegPen);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                registroPenalizacion.setIdRegPen(rs.getInt("id_RegPen"));
                registroPenalizacion.setNInfracciones(rs.getInt("n_Infracciones"));
                registroPenalizacion.setPenalizacionChofer(rs.getString("penalizacion_Chofer"));
                registroPenalizacion.setIdChofer(rs.getInt("id_Chofer"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return registroPenalizacion;
    }

    public boolean existeRegistroPenalizacion(int idRegPen) {
        return obtenerRegistroPenalizacionPorId(idRegPen) != null;
    }
}
