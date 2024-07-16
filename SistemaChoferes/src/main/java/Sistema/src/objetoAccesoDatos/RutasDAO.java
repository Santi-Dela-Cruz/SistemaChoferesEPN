package Sistema.src.objetoAccesoDatos;

import Sistema.src.clasesEntidades.Rutas;
import Sistema.src.conexionBaseDatos.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RutasDAO {
    private Connection connection;

    public RutasDAO() {
        connection = Conexion.conectar();
    }

    public void agregarRuta(Rutas ruta) {
        if (existeRuta(ruta.getIdChofer())) {
            System.out.println("Error: La ruta con el ID del chofer " + ruta.getIdChofer() + " ya existe.");
            return;
        }
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO rutas (nombre_Ruta, id_Chofer) VALUES (?, ?)");
            preparedStatement.setString(1, ruta.getNombreRuta());
            preparedStatement.setInt(2, ruta.getIdChofer());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarRuta(int idRuta) {
        if (!existeRuta(idRuta)) {
            System.out.println("Error: La ruta con el ID " + idRuta + " no existe.");
            return;
        }
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("DELETE FROM rutas WHERE id_Ruta=?");
            preparedStatement.setInt(1, idRuta);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizarRuta(Rutas ruta) {
        if (!existeRuta(ruta.getIdRuta())) {
            System.out.println("Error: La ruta con el ID " + ruta.getIdRuta() + " no existe.");
            return;
        }
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("UPDATE rutas SET nombre_Ruta=?, id_Chofer=? WHERE id_Ruta=?");
            preparedStatement.setString(1, ruta.getNombreRuta());
            preparedStatement.setInt(2, ruta.getIdChofer());
            preparedStatement.setInt(3, ruta.getIdRuta());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Rutas> obtenerTodoRutas() {
        List<Rutas> rutas = new ArrayList<Rutas>();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM rutas");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Rutas ruta = new Rutas();
                ruta.setIdRuta(rs.getInt("id_Ruta"));
                ruta.setNombreRuta(rs.getString("nombre_Ruta"));
                ruta.setIdChofer(rs.getInt("id_Chofer"));
                rutas.add(ruta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rutas;
    }

    public Rutas obtenerRutaPorId(int idRuta) {
        Rutas ruta = new Rutas();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM rutas WHERE id_Ruta=?");
            preparedStatement.setInt(1, idRuta);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                ruta.setIdRuta(rs.getInt("id_Ruta"));
                ruta.setNombreRuta(rs.getString("nombre_Ruta"));
                ruta.setIdChofer(rs.getInt("id_Chofer"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ruta;
    }

    public Rutas obtenerRutaPorChoferId(int idChofer) {
        Rutas ruta = new Rutas();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM rutas WHERE id_Chofer=?");
            preparedStatement.setInt(1, idChofer);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                ruta.setIdRuta(rs.getInt("id_Ruta"));
                ruta.setNombreRuta(rs.getString("nombre_Ruta"));
                ruta.setIdChofer(rs.getInt("id_Chofer"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ruta;
    }

    public boolean existeRuta(int idChofer) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM rutas WHERE id_Chofer=?");
            preparedStatement.setInt(1, idChofer);
            ResultSet rs = preparedStatement.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
