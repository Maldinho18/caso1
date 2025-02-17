public class Calidad extends Thread {
    private final Buzon buzonRevision;
    private final Buzon buzonReproceso;
    private final Buzon deposito;
    private final int maxFallos;
    private int fallos = 0;
    private int productosAprobados = 0;
    private final int totalProductos;
    private final int numOperarios;

    public Calidad(Buzon buzonRevision, Buzon buzonReproceso, Buzon deposito, int totalProductos, int id, int numOperarios) {
        this.buzonRevision = buzonRevision;
        this.buzonReproceso = buzonReproceso;
        this.deposito = deposito;
        this.maxFallos = totalProductos / 10;
        this.totalProductos = totalProductos;
        this.numOperarios = numOperarios;
        this.setName("Calidad " + id);
    }

    public void run() {
        try {
            while(true){
                Thread.sleep(100);
                Producto prod = buzonRevision.retirar();
                System.out.println(getName() + " ha recibido el producto " + prod.getId());

                if (fallos < maxFallos && prod.getId() % 7 == 0) {
                    prod.setAprobado(false);
                    buzonReproceso.depositar(prod);
                    fallos++;
                    System.out.println(getName() + " rechazado y enviado a reproceso " );
                } else {
                    prod.setAprobado(true);
                    deposito.depositar(prod);
                    productosAprobados++;
                    System.out.println(getName() + " aprobado y enviado al deposito " );

                    if (productosAprobados >= totalProductos){
                        System.out.println("Calidad: Se han aprobado todos los productos. Enviando FIN. ");
                        for (int i = 0; i < numOperarios; i++){
                            buzonReproceso.depositar(new Producto(-1));
                        }
                        break;
                    }
                } 
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
