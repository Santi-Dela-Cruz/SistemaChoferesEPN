package Sistema.src.clasesEntidades;
import java.util.Date;

public class RegistroEstados {
    private int idRegEst;
    private Date fechaIngreso;
    private Date horaIngreso;
    private String estadoChofer;
    private boolean autorizacionChofer;
    private int idChofer;

    public int getIdRegEst() {
        return idRegEst;
    }

    public void setIdRegEst(int idRegEst) {
        this.idRegEst = idRegEst;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Date getHoraIngreso() {
        return horaIngreso;
    }

    public void setHoraIngreso(Date horaIngreso) {
        this.horaIngreso = horaIngreso;
    }

    public String getEstadoChofer() {
        return estadoChofer;
    }

    public void setEstadoChofer(String estadoChofer) {
        this.estadoChofer = estadoChofer;
    }

    public boolean isAutorizacionChofer() {
        return autorizacionChofer;
    }

    public void setAutorizacionChofer(boolean autorizacionChofer) {
        this.autorizacionChofer = autorizacionChofer;
    }

    public int getIdChofer() {
        return idChofer;
    }

    public void setIdChofer(int idChofer) {
        this.idChofer = idChofer;
    }
}
