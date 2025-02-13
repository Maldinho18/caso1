import java.util.LinkedList;
import java.util.Queue;

public class Buzon {
    private final int capacidad;
    private final Queue<Producto> productos = new LinkedList<>();

    public Buzon(int capacidad) {
        this.capacidad = capacidad;
    }
    
    public synchronized void depositar(Producto producto) throws InterruptedException {
        while (productos.size() == capacidad) {
            System.out.println(Thread.currentThread().getName() + " esperando para depositar. Buzón lleno. ");
            wait();
        }
        productos.add(producto);
        System.out.println(Thread.currentThread().getName() + " ha depositado un producto ");
        notifyAll();
    }

    public synchronized Producto retirar() throws InterruptedException {
        while (productos.isEmpty()) {
            System.out.println(Thread.currentThread().getName() + " esperando para retirar. Buzón vacio. ");
            wait();
        }
        Producto prod = productos.poll();
        System.out.println(Thread.currentThread().getName() + " ha retirado un producto ");
        notifyAll();
        return prod;
    }

    public synchronized boolean isEmpty(){  
        return productos.isEmpty();
    }
}
