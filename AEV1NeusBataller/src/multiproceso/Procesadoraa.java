package multiproceso;

import java.util.concurrent.CountDownLatch;

public class Procesadoraa {
	private static int Ejecucion=0;
	public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("Uso: Procesadora \"JAMON:12,POLLO:6,...\"");
            System.exit(2);
        }

        int cJamon = 0, cPollo = 0, cBacalao = 0, cQueso = 0;

        String[] pares = args[0].split(",");
        for (String par : pares) {
            if (par == null || par.isBlank()) continue;
            
            String[] tipoYCantidad = par.split(":");
            TipoCroqueta tipo = TipoCroqueta.from(tipoYCantidad[0]);
            int cantidad = Integer.parseInt(tipoYCantidad[1]);

            if (tipo == TipoCroqueta.JAMON) {
            	cJamon=cJamon+cantidad;
            }
            if (tipo == TipoCroqueta.POLLO) {
            	cPollo=cPollo+cantidad;
            }
            if (tipo == TipoCroqueta.BACALAO) {
            	cBacalao=cBacalao+cantidad;
            }
            if (tipo == TipoCroqueta.QUESO) {
            	cQueso=cQueso+cantidad;
            }
        } 
            	
        int total = cJamon + cPollo + cBacalao + cQueso;
        System.out.println("[PROCESADORA] Total croquetas: " + total);

        // Latch para esperar a que terminen todas las croquetas
        CountDownLatch latch = new CountDownLatch(total);

        //hilos por tipo (uno por croqueta)
        lanzarTipo(TipoCroqueta.JAMON,   cJamon,   latch);
        lanzarTipo(TipoCroqueta.POLLO,   cPollo,   latch);
        lanzarTipo(TipoCroqueta.BACALAO, cBacalao, latch);
        lanzarTipo(TipoCroqueta.QUESO,   cQueso,   latch);

        //Esperar fin de todas
        latch.await();

        System.out.println("PROCESADORA_FIN");
        System.exit(0);
    }
	private static boolean Capacidad = false;

	private static void lanzarTipo(TipoCroqueta tipo, int cantidad, CountDownLatch latch) {
        for (int i = 1; i <= cantidad; i++) {
            // Si hay 100 o más hilos en marcha, esperar 3s y reintentar
            while (Ejecucion >= 100) {
            	if (!Capacidad) {
                    System.out.println("CAPACIDAD COMPLETA. Reintentando en 3s...");
                    Capacidad = true;
                }
                try { Thread.sleep(3000); } catch (InterruptedException ignored) {}
            }
            Ejecucion++;
            final int num = i;
            new Thread(() -> fabricarCroqueta(tipo, num, latch)).start();
        }
    }
	
    private static void fabricarCroqueta(TipoCroqueta tipo, int num, CountDownLatch latch) {
        try {
           // System.out.println("INICIO " + tipo.nombre + " #" + num);
            try { Thread.sleep(tipo.tiempoFabricacion); } catch (InterruptedException e) { /* mínimo */ }
          //  System.out.println("FIN " + tipo.nombre + " #" + num);
        } finally {
            Ejecucion--;
            latch.countDown();
        }
    }
}
