package multiproceso;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Lanzadoraa extends JFrame {

	private static final long serialVersionUID = 1L;
	
	//Componentes Interfaz
	private JPanel contentPane;
	private JSpinner spJamon;
	private JSpinner spPollo;
	private JSpinner spBacalao;
	private JSpinner spQueso;
	private JCheckBox cbCajasDe6;
	private JButton btnFabricar;
	/**
	 * Launch the application.
	 */
	
	// muestra ventana principal
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Lanzadoraa frame = new Lanzadoraa();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	
	//Constructor UI
	public Lanzadoraa() {
		//Configuración ventana
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(6, 2, 8, 8));
		//Tamaño centrado y titulo
		setSize(520,260);
		setLocationRelativeTo(null);
		setTitle("Fabrica de Croquetas");
		
		//botones
		JLabel lblNewLabel = new JLabel("Jamón");
		contentPane.add(lblNewLabel);
		
		spJamon=new JSpinner(new SpinnerNumberModel(0, 0, 10000, 1)); //SpinnerNumber para que no bajen de 0
		contentPane.add(spJamon);
		
		JLabel lblNewLabel_1 = new JLabel("Pollo");
		contentPane.add(lblNewLabel_1);
		
		
		spPollo=new JSpinner(new SpinnerNumberModel(0, 0, 10000, 1));
		contentPane.add(spPollo);
		
		JLabel lblNewLabel_2 = new JLabel("Bacalao");
		contentPane.add(lblNewLabel_2);
		
		spBacalao=new JSpinner(new SpinnerNumberModel(0, 0, 10000, 1));
		contentPane.add(spBacalao);
		
		JLabel lblNewLabel_4 = new JLabel("Queso");
		contentPane.add(lblNewLabel_4);
		
		spQueso=new JSpinner(new SpinnerNumberModel(0, 0, 10000, 1));
		contentPane.add(spQueso);
			
		cbCajasDe6 = new JCheckBox("Validar cajas de 6 por tipo");
		contentPane.add(cbCajasDe6);
		
		btnFabricar = new JButton("Fabricar");
		contentPane.add(btnFabricar);
		
		//Boton que llama al metodo
		btnFabricar.addActionListener(e -> fabricar());
    }
		//metodo se ejecuta al pulsar el botón Fabricar
		 private void fabricar() {
		        int jamon = (int) spJamon.getValue();
		        int pollo = (int) spPollo.getValue();
		        int bacalao = (int) spBacalao.getValue();
		        int queso = (int) spQueso.getValue();
		        
		        int total = jamon + pollo + bacalao + queso;
		        System.out.println("[LANZADORA] Pedido recibido:");
		        System.out.println(" Jamón: " + jamon + ", Pollo: " + pollo +
		                           ", Bacalao: " + bacalao + ", Queso: " + queso);
		        System.out.println(" Total croquetas: " + total);
		        
		        //Validar minimo una
		        if (total == 0) {
		            JOptionPane.showMessageDialog(this,"Debe seleccionar al menos una croqueta");
		            return;
		        }
		        //Validar cajas si esta marcada la casilla
		        if (cbCajasDe6.isSelected()) {
		            if (jamon % 6 != 0 || pollo % 6 != 0 || bacalao % 6 != 0 || queso % 6 != 0) {
		                JOptionPane.showMessageDialog(this,
		                    "Cada tipo debe ser de 6");
		                return;
		            }
		        }
		        //Crear lista tipo y cantidad
		            List<String> args = new ArrayList<>();
		            if (jamon > 0)   args.add("JAMON:" + jamon);
		            if (pollo > 0)   args.add("POLLO:" + pollo);
		            if (bacalao > 0) args.add("BACALAO:" + bacalao);
		            if (queso > 0)   args.add("QUESO:" + queso);
		            String counts = String.join(",",args);
		 
		            //Desactivar botón para que no se repita el pedido
		            btnFabricar.setEnabled(false);
		            long t0 = System.currentTimeMillis();
		           
		            try {
		            	lanzarProcesadora(counts);
		            	//Esperando a que termine
		                long t1 = System.currentTimeMillis();
		                double secs = (t1 - t0) / 1000.0;

		                JOptionPane.showMessageDialog(this,
		                    "Procesadora finalizada \nTiempo total: " + secs + " s",
		                    "Fabricación completada", JOptionPane.INFORMATION_MESSAGE);

		            } finally {
		                btnFabricar.setEnabled(true);
		            }
		        }
		        // Lanza Procesadora y espera a que termine
		        private void lanzarProcesadora(String counts) {
		            try {
		                String javaHome  = System.getProperty("java.home");
		                String javaBin   = javaHome + File.separator + "bin" + File.separator + "java";
		                String classpath = new File("bin").getAbsolutePath();
		                String clase= "multiproceso.Procesadoraa";

		                ProcessBuilder pb = new ProcessBuilder(javaBin, "-cp", classpath, clase, counts);
		                pb.inheritIO();
		                Process p = pb.start();

		               p.waitFor();

		            } catch (Exception ex) {
		                ex.printStackTrace();
		            }
		        }
		    }