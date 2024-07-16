package Sistema.src.objetoAccesoDatos;

import Sistema.src.clasesEntidades.Chofer;
import Sistema.src.conexionBaseDatos.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChoferDAO {
    private Connection connection;

    public ChoferDAO() {
        connection = Conexion.conectar();
    }

    public void agregarChofer(Chofer chofer) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO choferes (id_Cedula, nombre, apellido, telefono) VALUES (?, ?, ?, ?)"
            );
            preparedStatement.setString(1, chofer.getIdCedula());
            preparedStatement.setString(2, chofer.getNombre());
            preparedStatement.setString(3, chofer.getApellido());
            preparedStatement.setString(4, chofer.getTelefono());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarChofer(int idChofer) {
        if (!existeChofer(idChofer)) {
            System.out.println("Error: El chofer con el ID " + idChofer + " no existe.");
            return;
        }
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("DELETE FROM choferes WHERE id_Chofer=?");
            preparedStatement.setInt(1, idChofer);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizarChofer(Chofer chofer) {
        if (!existeChofer(chofer.getIdChofer())) {
            System.out.println("Error: El chofer con el ID " + chofer.getIdChofer() + " no existe.");
            return;
        }
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("UPDATE choferes SET id_Cedula=?, nombre=?, apellido=?, telefono=? WHERE id_Chofer=?");
            preparedStatement.setString(1, chofer.getIdCedula());
            preparedStatement.setString(2, chofer.getNombre());
            preparedStatement.setString(3, chofer.getApellido());
            preparedStatement.setString(4, chofer.getTelefono());
            preparedStatement.setInt(5, chofer.getIdChofer());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Chofer> obtenerTodoChoferes() {
        List<Chofer> choferes = new ArrayList<Chofer>();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM choferes");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Chofer chofer = new Chofer();
                chofer.setIdChofer(rs.getInt("id_Chofer"));
                chofer.setIdCedula(rs.getString("id_Cedula"));
                chofer.setNombre(rs.getString("nombre"));
                chofer.setApellido(rs.getString("apellido"));
                chofer.setTelefono(rs.getString("telefono"));
                choferes.add(chofer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return choferes;
    }

    public Chofer obtenerChoferPorId(int idChofer) {
        Chofer chofer = new Chofer();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM choferes WHERE id_Chofer=?");
            preparedStatement.setInt(1, idChofer);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                chofer.setIdChofer(rs.getInt("id_Chofer"));
                chofer.setIdCedula(rs.getString("id_Cedula"));
                chofer.setNombre(rs.getString("nombre"));
                chofer.setApellido(rs.getString("apellido"));
                chofer.setTelefono(rs.getString("telefono"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chofer;
    }

    public boolean existeChofer(int idChofer) {
        return obtenerChoferPorId(idChofer) != null;
    }
    
    public boolean existeChoferPorCedula(String idCedula) {
        String sql = "SELECT COUNT(*) FROM choferes WHERE id_Cedula = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, idCedula);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al verificar la cédula del chofer: " + e.getMessage());
        }
        return false;
    }

    public boolean existeChoferPorTelefono(String telefono) {
        String sql = "SELECT COUNT(*) FROM choferes WHERE telefono = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, telefono);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al verificar el teléfono del chofer: " + e.getMessage());
        }
        return false;
    }
}
