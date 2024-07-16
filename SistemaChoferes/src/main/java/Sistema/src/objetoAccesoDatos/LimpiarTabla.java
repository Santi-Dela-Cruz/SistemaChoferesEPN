package Sistema.src.objetoAccesoDatos;

import Sistema.src.conexionBaseDatos.Conexion;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class LimpiarTabla {

    private void truncarTabla(String nombreTabla) {
        String sql = "TRUNCATE TABLE " + nombreTabla;
        try (Connection conn = Conexion.conectar();
             Statement stmt = conn.createStatement()) {
            stmt.execute("SET FOREIGN_KEY_CHECKS = 0");
            stmt.execute(sql);
            stmt.execute("SET FOREIGN_KEY_CHECKS = 1");
            System.out.println("Tabla " + nombreTabla + " truncada");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void limpiarTodasTablas() {
        truncarTabla("registro_penalizaciones");
        truncarTabla("registro_estados");
        truncarTabla("rutas");
        truncarTabla("vehiculo");
        truncarTabla("huella");
        truncarTabla("choferes");
    }
}
