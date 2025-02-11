public class Main{
    public static void main(String[] args){
        int numOperarios = 4;
        int numProductos = 20;
        int capacidad = 5;

        Buzon buzonRevision = new Buzon(capacidad);
        Buzon buzonReproceso = new Buzon(Integer.MAX_VALUE);
        Buzon deposito = new Buzon(Integer.MAX_VALUE);
        
        for (int i = 0; i < numOperarios; i++){
            new Productor(buzonRevision, buzonReproceso).start();
            new Calidad(buzonRevision, buzonReproceso, deposito, numProductos/10).start();
        }
    }
}

//comprobar cambios