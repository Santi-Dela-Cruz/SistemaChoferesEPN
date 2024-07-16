package Sistema.src.objetoAccesoDatos;

import Sistema.src.clasesEntidades.RegistroEstados;
import Sistema.src.conexionBaseDatos.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RegistroEstadosDAO {
    private Connection connection;

    public RegistroEstadosDAO() {
        connection = Conexion.conectar();
    }

    public boolean registrarIngreso(int idChofer, String estadoChofer, boolean autorizacion) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO registro_estados (fecha_Ingreso, hora_Ingreso, estado_Chofer, autorizacion_Chofer, id_Chofer) VALUES (CURDATE(), CURTIME(), ?, ?, ?)");
            preparedStatement.setString(1, estadoChofer);
            preparedStatement.setBoolean(2, autorizacion);
            preparedStatement.setInt(3, idChofer);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void eliminarRegistroEstado(int idRegEst) {
        if (!existeRegistroEstado(idRegEst)) {
            System.out.println("Error: El registro de estado con el ID " + idRegEst + " no existe.");
            return;
        }
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("DELETE FROM registro_estados WHERE id_RegEst=?");
            preparedStatement.setInt(1, idRegEst);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizarRegistroEstado(RegistroEstados registroEstado) {
        if (!existeRegistroEstado(registroEstado.getIdRegEst())) {
            System.out.println("Error: El registro de estado con el ID " + registroEstado.getIdRegEst() + " no existe.");
            return;
        }
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("UPDATE registro_estados SET fecha_Ingreso=CURDATE(), hora_Ingreso=CURTIME(), estado_Chofer=?, autorizacion_Chofer=?, id_Chofer=? WHERE id_RegEst=?");
            preparedStatement.setString(1, registroEstado.getEstadoChofer());
            preparedStatement.setBoolean(2, registroEstado.isAutorizacionChofer());
            preparedStatement.setInt(3, registroEstado.getIdChofer());
            preparedStatement.setInt(4, registroEstado.getIdRegEst());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<RegistroEstados> obtenerTodoRegistroEstados() {
        List<RegistroEstados> registros = new ArrayList<RegistroEstados>();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM registro_estados");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                RegistroEstados registroEstado = new RegistroEstados();
                registroEstado.setIdRegEst(rs.getInt("id_RegEst"));
                registroEstado.setFechaIngreso(rs.getDate("fecha_Ingreso"));
                registroEstado.setHoraIngreso(rs.getTime("hora_Ingreso"));
                registroEstado.setEstadoChofer(rs.getString("estado_Chofer"));
                registroEstado.setAutorizacionChofer(rs.getBoolean("autorizacion_Chofer"));
                registroEstado.setIdChofer(rs.getInt("id_Chofer"));
                registros.add(registroEstado);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return registros;
    }

    public RegistroEstados obtenerRegistroEstadoPorId(int idRegEst) {
        RegistroEstados registroEstado = new RegistroEstados();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM registro_estados WHERE id_RegEst=?");
            preparedStatement.setInt(1, idRegEst);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                registroEstado.setIdRegEst(rs.getInt("id_RegEst"));
                registroEstado.setFechaIngreso(rs.getDate("fecha_Ingreso"));
                registroEstado.setHoraIngreso(rs.getTime("hora_Ingreso"));
                registroEstado.setEstadoChofer(rs.getString("estado_Chofer"));
                registroEstado.setAutorizacionChofer(rs.getBoolean("autorizacion_Chofer"));
                registroEstado.setIdChofer(rs.getInt("id_Chofer"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return registroEstado;
    }

    public boolean existeRegistroEstado(int idRegEst) {
        return obtenerRegistroEstadoPorId(idRegEst) != null;
    }
}
