package Sistema.src.objetoAccesoDatos;

import Sistema.src.clasesEntidades.Huella;
import Sistema.src.conexionBaseDatos.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HuellaDAO {
    private Connection connection;

    public HuellaDAO() {
        connection = Conexion.conectar();
    }

    public void agregarHuella(Huella huella) {
        if (existeHuella(huella.getIdChofer())) {
            System.out.println("Error: La huella con el ID del chofer " + huella.getIdChofer() + " ya existe.");
            return;
        }
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO huella (id_Codigo_Huella, fecha_RegHuella, id_Chofer) VALUES (?, CURDATE(), ?)");
            preparedStatement.setString(1, huella.getIdCodigoHuella());
            preparedStatement.setInt(2, huella.getIdChofer());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarHuella(int idHuella) {
        if (!existeHuella(idHuella)) {
            System.out.println("Error: La huella con el ID " + idHuella + " no existe.");
            return;
        }
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("DELETE FROM huella WHERE id_Huella=?");
            preparedStatement.setInt(1, idHuella);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizarHuella(Huella huella) {
        if (!existeHuella(huella.getIdHuella())) {
            System.out.println("Error: La huella con el ID " + huella.getIdHuella() + " no existe.");
            return;
        }
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("UPDATE huella SET id_Codigo_Huella=?, fecha_RegHuella=CURDATE(), id_Chofer=? WHERE id_Huella=?");
            preparedStatement.setString(1, huella.getIdCodigoHuella());
            preparedStatement.setInt(2, huella.getIdChofer());
            preparedStatement.setInt(3, huella.getIdHuella());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Huella> obtenerTodoHuella() {
        List<Huella> huellas = new ArrayList<Huella>();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM huella");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Huella huella = new Huella();
                huella.setIdHuella(rs.getInt("id_Huella"));
                huella.setIdCodigoHuella(rs.getString("id_Codigo_Huella"));
                huella.setFechaRegHuella(rs.getDate("fecha_RegHuella"));
                huella.setIdChofer(rs.getInt("id_Chofer"));
                huellas.add(huella);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return huellas;
    }

    public Huella obtenerHuellaPorId(int idHuella) {
        Huella huella = new Huella();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM huella WHERE id_Huella=?");
            preparedStatement.setInt(1, idHuella);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                huella.setIdHuella(rs.getInt("id_Huella"));
                huella.setIdCodigoHuella(rs.getString("id_Codigo_Huella"));
                huella.setFechaRegHuella(rs.getDate("fecha_RegHuella"));
                huella.setIdChofer(rs.getInt("id_Chofer"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return huella;
    }

    public Huella obtenerHuellaPorCodigo(String idCodigoHuella) {
        Huella huella = new Huella();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM huella WHERE id_Codigo_Huella=?");
            preparedStatement.setString(1, idCodigoHuella);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                huella.setIdHuella(rs.getInt("id_Huella"));
                huella.setIdCodigoHuella(rs.getString("id_Codigo_Huella"));
                huella.setFechaRegHuella(rs.getDate("fecha_RegHuella"));
                huella.setIdChofer(rs.getInt("id_Chofer"));
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return huella;
    }

    public boolean existeHuella(int idChofer) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM huella WHERE id_Chofer=?");
            preparedStatement.setInt(1, idChofer);
            ResultSet rs = preparedStatement.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
