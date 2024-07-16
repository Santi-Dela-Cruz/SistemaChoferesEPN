package Sistema.src.clasesEntidades;

public class RegistroPenalizaciones {
    private int idRegPen;
    private int nInfracciones;
    private String penalizacionChofer;
    private int idChofer;

    public int getIdRegPen() {
        return idRegPen;
    }

    public void setIdRegPen(int idRegPen) {
        this.idRegPen = idRegPen;
    }

    public int getNInfracciones() {
        return nInfracciones;
    }

    public void setNInfracciones(int nInfracciones) {
        this.nInfracciones = nInfracciones;
    }

    public String getPenalizacionChofer() {
        return penalizacionChofer;
    }

    public void setPenalizacionChofer(String penalizacionChofer) {
        this.penalizacionChofer = penalizacionChofer;
    }

    public int getIdChofer() {
        return idChofer;
    }

    public void setIdChofer(int idChofer) {
        this.idChofer = idChofer;
    }
}
