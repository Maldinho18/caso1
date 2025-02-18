public class Productor extends Thread {
    private final Buzon buzonRevision;
    private final Buzon buzonReproceso;
    private static int contador = 0;

    public Productor(Buzon buzonRevision, Buzon buzonReproceso, int id) {
        this.buzonRevision = buzonRevision;
        this.buzonReproceso = buzonReproceso;
        this.setName("Productor " + id);
    }

    public void run(){
        try {
            while(true){
                Producto prod;
                synchronized (buzonReproceso){
                    if (!buzonReproceso.isEmpty()){
                        prod = buzonReproceso.retirar();
                        if (prod.getId() == -1){
                            System.out.println(getName() + " recibió FIN. Terminando");
                            break;
                        }
                    } else {
                        prod = new Producto(++contador);
                    }
                }
                System.out.println(getName() + " ha producido el producto " + prod.getId());
                buzonRevision.depositar(prod);
                synchronized (buzonRevision){
                    buzonRevision.notifyAll();
                }
            }   
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
} 
