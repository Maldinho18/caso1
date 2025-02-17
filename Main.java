import java.util.Scanner;
public class Main{
    public static void main(String[] args){

        Scanner scanner = new Scanner(System.in);

        System.out.println("Indique el número de operarios: ");
        int numOperarios = scanner.nextInt();

        System.out.println("Indique el número de productos: ");
        int numProductos = scanner.nextInt();

        System.out.println("Indique la capacidad del buzón de revisión: ");
        int capacidad = scanner.nextInt();

        scanner.close();

        Buzon buzonRevision = new Buzon(capacidad);
        Buzon buzonReproceso = new Buzon(Integer.MAX_VALUE);
        Buzon deposito = new Buzon(Integer.MAX_VALUE);
        
        for (int i = 0; i < numOperarios; i++){
            new Productor(buzonRevision, buzonReproceso, i + 1).start();
            new Calidad(buzonRevision, buzonReproceso, deposito, numProductos, i + 1, numOperarios).start();
        }
    }
}

//comprobar cambios