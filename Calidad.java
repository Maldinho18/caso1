public class Calidad extends Thread {
    private final Buzon buzonRevision;
    private final Buzon buzonReproceso;
    private final Buzon deposito;
    private final int maxFallos;
    private int fallos = 0;
    private int productosAprobados = 0;
    private final int totalProductos;

    public Calidad(Buzon buzonRevision, Buzon buzonReproceso, Buzon deposito, int totalProductos) {
        this.buzonRevision = buzonRevision;
        this.buzonReproceso = buzonReproceso;
        this.deposito = deposito;
        this.maxFallos = totalProductos / 10;
        this.totalProductos = totalProductos;
    }

    public void run() {
        try {
            while(true){
                Thread.sleep(100);
                Producto prod = buzonRevision.retirar();
                System.out.println("Calidad " + Thread.currentThread().getId() + " ha recibido el producto " + prod.getId());

                if (fallos < maxFallos && prod.getId() % 7 == 0) {
                    prod.setAprobado(false);
                    buzonReproceso.depositar(prod);
                    fallos++;
                    System.out.println("Producto " + Thread.currentThread().getId() + " rechazado y enviado a reproceso " );
                } else {
                    prod.setAprobado(true);
                    deposito.depositar(prod);
                    productosAprobados++;
                    System.out.println("Producto " + Thread.currentThread().getId() + " aprobado y enviado al deposito " );

                    if (productosAprobados >= totalProductos){
                        System.out.println("Calidad: Se han aprobado todos los productos. Enviando FIN. ");
                        buzonReproceso.depositar(new Producto(-1));
                        break;
                    }
                } 
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
