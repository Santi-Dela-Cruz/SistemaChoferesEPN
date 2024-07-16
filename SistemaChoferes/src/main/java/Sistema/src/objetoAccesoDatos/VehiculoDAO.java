package Sistema.src.objetoAccesoDatos;

import Sistema.src.clasesEntidades.Vehiculo;
import Sistema.src.conexionBaseDatos.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VehiculoDAO {
    private Connection connection;

    public VehiculoDAO() {
        connection = Conexion.conectar();
    }

    public void agregarVehiculo(Vehiculo vehiculo) {
        if (existeVehiculo(vehiculo.getIdChofer())) {
            System.out.println("Error: El vehículo con el ID del chofer " + vehiculo.getIdChofer() + " ya existe.");
            return;
        }
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO vehiculo (id_Placa, tipo_Vehiculo, id_Chofer) VALUES (?, ?, ?)");
            preparedStatement.setString(1, vehiculo.getIdPlaca());
            preparedStatement.setString(2, vehiculo.getTipoVehiculo());
            preparedStatement.setInt(3, vehiculo.getIdChofer());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarVehiculo(int idVehiculo) {
        if (!existeVehiculo(idVehiculo)) {
            System.out.println("Error: El vehículo con el ID " + idVehiculo + " no existe.");
            return;
        }
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("DELETE FROM vehiculo WHERE id_Vehiculo=?");
            preparedStatement.setInt(1, idVehiculo);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizarVehiculo(Vehiculo vehiculo) {
        if (!existeVehiculo(vehiculo.getIdVehiculo())) {
            System.out.println("Error: El vehículo con el ID " + vehiculo.getIdVehiculo() + " no existe.");
            return;
        }
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("UPDATE vehiculo SET id_Placa=?, tipo_Vehiculo=?, id_Chofer=? WHERE id_Vehiculo=?");
            preparedStatement.setString(1, vehiculo.getIdPlaca());
            preparedStatement.setString(2, vehiculo.getTipoVehiculo());
            preparedStatement.setInt(3, vehiculo.getIdChofer());
            preparedStatement.setInt(4, vehiculo.getIdVehiculo());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Vehiculo> obtenerTodoVehiculos() {
        List<Vehiculo> vehiculos = new ArrayList<Vehiculo>();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM vehiculo");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Vehiculo vehiculo = new Vehiculo();
                vehiculo.setIdVehiculo(rs.getInt("id_Vehiculo"));
                vehiculo.setIdPlaca(rs.getString("id_Placa"));
                vehiculo.setTipoVehiculo(rs.getString("tipo_Vehiculo"));
                vehiculo.setIdChofer(rs.getInt("id_Chofer"));
                vehiculos.add(vehiculo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehiculos;
    }

    public Vehiculo obtenerVehiculoPorId(int idVehiculo) {
        Vehiculo vehiculo = new Vehiculo();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM vehiculo WHERE id_Vehiculo=?");
            preparedStatement.setInt(1, idVehiculo);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                vehiculo.setIdVehiculo(rs.getInt("id_Vehiculo"));
                vehiculo.setIdPlaca(rs.getString("id_Placa"));
                vehiculo.setTipoVehiculo(rs.getString("tipo_Vehiculo"));
                vehiculo.setIdChofer(rs.getInt("id_Chofer"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehiculo;
    }

    public Vehiculo obtenerVehiculoPorChoferId(int idChofer) {
        Vehiculo vehiculo = new Vehiculo();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM vehiculo WHERE id_Chofer=?");
            preparedStatement.setInt(1, idChofer);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                vehiculo.setIdVehiculo(rs.getInt("id_Vehiculo"));
                vehiculo.setIdPlaca(rs.getString("id_Placa"));
                vehiculo.setTipoVehiculo(rs.getString("tipo_Vehiculo"));
                vehiculo.setIdChofer(rs.getInt("id_Chofer"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehiculo;
    }

    public boolean existeVehiculo(int idChofer) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM vehiculo WHERE id_Chofer=?");
            preparedStatement.setInt(1, idChofer);
            ResultSet rs = preparedStatement.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
