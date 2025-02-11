public class Producto {
    private final int id;
    private boolean aprobado;

    public Producto(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public boolean isAprobado() {
        return aprobado;
    }

    public void setAprobado(boolean aprobado) {
        this.aprobado = aprobado;
    }
}
