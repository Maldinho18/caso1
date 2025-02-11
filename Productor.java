public class Productor extends Thread {
    private final Buzon buzonRevision;
    private final Buzon buzonReproceso;
    private int contador = 0;

    public Productor(Buzon buzonRevision, Buzon buzonReproceso) {
        this.buzonRevision = buzonRevision;
        this.buzonReproceso = buzonReproceso;
    }

    public void run(){
        try {
            while(true){
                Producto prod;
                synchronized (buzonReproceso){
                    if (!buzonReproceso.isEmpty()){
                        prod = buzonReproceso.retirar();
                        if (prod.getId() == -1){
                            System.out.println("Productor " + Thread.currentThread().getId() + " recibió FIN. Terminando");
                            break;
                        }
                    } else {
                        prod = new Producto(++contador);
                    }
                }
                System.out.println("Productor " + Thread.currentThread().getId() + " ha generado el producto " + prod.getId()); 
                buzonRevision.depositar(prod);
            }   
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
} 
