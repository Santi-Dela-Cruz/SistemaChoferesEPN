package Sistema.src.funcionesGestiosChoferes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import Sistema.src.clasesEntidades.Chofer;
import Sistema.src.clasesEntidades.Huella;
import Sistema.src.clasesEntidades.Rutas;
import Sistema.src.clasesEntidades.Vehiculo;
import Sistema.src.objetoAccesoDatos.ChoferDAO;
import Sistema.src.objetoAccesoDatos.HuellaDAO;
import Sistema.src.objetoAccesoDatos.RegistroEstadosDAO;
import Sistema.src.objetoAccesoDatos.RegistroPenalizacionesDAO;
import Sistema.src.objetoAccesoDatos.RutasDAO;
import Sistema.src.objetoAccesoDatos.VehiculoDAO;

public class RegistroIngreso {

    private HuellaDAO huellaDAO;
    private ChoferDAO choferDAO;
    private VehiculoDAO vehiculoDAO;
    private RutasDAO rutaDAO;
    private RegistroEstadosDAO registroEstadosDAO;
    private RegistroPenalizacionesDAO registroPenalizacionesDAO;

    public RegistroIngreso() {
        huellaDAO = new HuellaDAO();
        choferDAO = new ChoferDAO();
        vehiculoDAO = new VehiculoDAO();
        rutaDAO = new RutasDAO();
        registroEstadosDAO = new RegistroEstadosDAO();
        registroPenalizacionesDAO = new RegistroPenalizacionesDAO();
    }

    public void validarHuellaYRegistrarIngreso() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese el ID de la huella: ");
        String idHuella = scanner.nextLine();

        Huella huella = huellaDAO.obtenerHuellaPorCodigo(idHuella); // Cambiar a obtener por código
        if (huella == null) {
            System.out.println("Huella no encontrada. El usuario no existe.");
            return;
        }

        int idChofer = huella.getIdChofer();
        Chofer chofer = choferDAO.obtenerChoferPorId(idChofer);

        if (chofer == null) {
            System.out.println("Chofer no encontrado.");
            return;
        }

        Vehiculo vehiculo = vehiculoDAO.obtenerVehiculoPorChoferId(idChofer);

        if (vehiculo == null) {
            System.out.println("Vehículo no encontrado.");
            return;
        }

        Rutas ruta = rutaDAO.obtenerRutaPorChoferId(idChofer);

        if (ruta == null) {
            System.out.println("Ruta no encontrada.");
            return;
        }

        LocalDateTime ahora = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String fechaHoraIngreso = ahora.format(formatter);

        System.out.println("Detalles del chofer:");
        System.out.println("ID Cedula: " + chofer.getIdCedula());
        System.out.println("Nombre: " + chofer.getNombre());
        System.out.println("Apellido: " + chofer.getApellido());
        System.out.println("Teléfono: " + chofer.getTelefono());
        System.out.println("Placa del Vehículo: " + vehiculo.getIdPlaca());
        System.out.println("Tipo de Vehículo: " + vehiculo.getTipoVehiculo());
        System.out.println("Ruta: " + ruta.getNombreRuta());
        System.out.println("Fecha y Hora de Ingreso: " + fechaHoraIngreso);

        System.out.print("¿Desea realizar el test de alcohol? (si/no): ");
        String realizarTest = scanner.nextLine();

        if (!realizarTest.equalsIgnoreCase("si")) {
            return;
        }

        System.out.print("¿Está borracho? (si/no): ");
        String respuesta = scanner.nextLine();
        boolean autorizacion = !respuesta.equalsIgnoreCase("si");

        boolean successEstado = registroEstadosDAO.registrarIngreso(idChofer, respuesta.equalsIgnoreCase("si") ? "Borracho" : "Sobrio", autorizacion);
        if (successEstado) {
            System.out.println("Estado registrado correctamente.");
        } else {
            System.out.println("Error al registrar el estado.");
            return;
        }

        if (!autorizacion) {
            boolean despedido = registroPenalizacionesDAO.actualizarPenalizaciones(idChofer);
            if (despedido) {
                System.out.println("El chofer ha sido despedido.");
                registroPenalizacionesDAO.eliminarPenalizacionesPorChofer(idChofer); // Limpiar el registro de penalizaciones si es despedido
            } else {
                System.out.println("Penalización registrada.");
            }
        }

        System.out.println("Resultado del test de alcohol:");
        System.out.println("Nombre: " + chofer.getNombre());
        System.out.println("Autorización: " + (autorizacion ? "Autorizado" : "No autorizado"));
    }
}
