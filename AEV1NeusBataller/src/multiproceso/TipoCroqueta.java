package multiproceso;

public class TipoCroqueta {
	    String nombre;
	    int tiempoFabricacion;
	
		private TipoCroqueta(String nombre, int tiempo) {
	        this.nombre = nombre;
	        this.tiempoFabricacion = tiempo;
		}
			public static final TipoCroqueta JAMON   = new TipoCroqueta("JAMON",   5000);
		    public static final TipoCroqueta POLLO   = new TipoCroqueta("POLLO",   6000);
		    public static final TipoCroqueta BACALAO = new TipoCroqueta("BACALAO", 7000);
		    public static final TipoCroqueta QUESO   = new TipoCroqueta("QUESO",   8000);

	
		    public static TipoCroqueta from(String nombre) {
		    	String s = nombre.toUpperCase();
		    	if (s.equals("JAMON")) {
		    		return JAMON;
		    	} else if (s.equals("POLLO")) {
		    		return POLLO;
		    	} else if (s.equals("BACALAO")) {
		    		return BACALAO;
		    	} else {
		    		return QUESO;
		    	}
		    }
		    	
    @Override 
    public String toString() {
        return nombre;
    }
}
