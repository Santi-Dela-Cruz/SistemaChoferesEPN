package Sistema.src.funcionesGestiosChoferes;

import Sistema.src.clasesEntidades.Chofer;
import Sistema.src.clasesEntidades.Huella;
import Sistema.src.clasesEntidades.Rutas;
import Sistema.src.clasesEntidades.Vehiculo;
import Sistema.src.objetoAccesoDatos.ChoferDAO;
import Sistema.src.objetoAccesoDatos.HuellaDAO;
import Sistema.src.objetoAccesoDatos.RutasDAO;
import Sistema.src.objetoAccesoDatos.VehiculoDAO;

public class RegistroChofer {

    private HuellaDAO huellaDAO = new HuellaDAO();
    private ChoferDAO choferDAO = new ChoferDAO();
    private VehiculoDAO vehiculoDAO = new VehiculoDAO();
    private RutasDAO rutasDAO = new RutasDAO();

    public void registrarNuevoChofer(String idCedula, String nombre, String apellido, String telefono, String idCodigoHuella, String idPlaca, String tipoVehiculo, String nombreRuta) {
        try {
            if (choferDAO.existeChoferPorCedula(idCedula)) {
                throw new RuntimeException("Error: La cédula ya existe en el sistema.");
            }

            if (choferDAO.existeChoferPorTelefono(telefono)) {
                throw new RuntimeException("Error: El teléfono ya existe en el sistema.");
            }

            Chofer chofer = new Chofer();
            chofer.setIdCedula(idCedula);
            chofer.setNombre(nombre);
            chofer.setApellido(apellido);
            chofer.setTelefono(telefono);

            choferDAO.agregarChofer(chofer);

            int idChofer = choferDAO.obtenerTodoChoferes().stream()
                    .filter(c -> c.getIdCedula().equals(idCedula))
                    .findFirst()
                    .get()
                    .getIdChofer();

            if (huellaDAO.existeHuella(idChofer) || 
                vehiculoDAO.existeVehiculo(idChofer) || 
                rutasDAO.existeRuta(idChofer)) {
                throw new RuntimeException("Error: Existen registros duplicados para el chofer, vehículo, huella o ruta.");
            }

            Huella huella = new Huella();
            huella.setIdCodigoHuella(idCodigoHuella);
            huella.setIdChofer(idChofer);
            huellaDAO.agregarHuella(huella);

            Vehiculo vehiculo = new Vehiculo();
            vehiculo.setIdPlaca(idPlaca);
            vehiculo.setTipoVehiculo(tipoVehiculo);
            vehiculo.setIdChofer(idChofer);
            vehiculoDAO.agregarVehiculo(vehiculo);

            Rutas ruta = new Rutas();
            ruta.setNombreRuta(nombreRuta);
            ruta.setIdChofer(idChofer);
            rutasDAO.agregarRuta(ruta);

            System.out.println("Registro completado con éxito.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al registrar el chofer: " + e.getMessage());
        }
    }
}
