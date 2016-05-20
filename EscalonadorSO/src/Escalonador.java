import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public abstract class Escalonador {

	private JFrame frame;
	private JScrollPane jsCores;
	private JScrollPane jsAptos;
	private JScrollPane jsTerminados;
	private JPanel pCores;
	private JPanel pAptos;
	private JPanel pTerminados;
	private JTextField textField;
	private JScrollPane jsBlocos;
	private JPanel pBlocos;
	Thread t;
	/**
	 * Launch the application.
	 */
	// public static void main(String[] args) {
	// EventQueue.invokeLater(new Runnable() {
	// public void run() {
	// try {
	// Escalonador window = new Escalonador();
	// window.frame.setVisible(true);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// });
	// }

	/**
	 * Create the application.
	 */
	public Escalonador() {
		initialize();
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 720);
		frame.getContentPane().setBackground(new Color(139, 0, 0));
		frame.getContentPane().setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		pAptos = new JPanel();
		pAptos.setBackground(new Color(255, 127, 80));
		pAptos.setBounds(15, 220, 720, 80);
		frame.getContentPane().add(pAptos);

		pTerminados = new JPanel();
		pTerminados.setBackground(new Color(255, 127, 80));
		pTerminados.setBounds(15, 330, 720, 80);
		frame.getContentPane().add(pTerminados);

		pCores = new JPanel();
		pCores.setBackground(new Color(255, 127, 80));
		pCores.setBounds(15, 95, 720, 80);
		frame.getContentPane().add(pCores);

		jsCores = new JScrollPane(pCores);
		jsCores.setBounds(15, 95, 720, 80);
		frame.getContentPane().add(jsCores);

		textField = new JTextField();
		textField.setBounds(200, 5, 366, 33);
		frame.getContentPane().add(textField);
		textField.setText("Trabalho de SO");
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setForeground(new Color(0, 128, 0));
		textField.setFont(new Font("Tahoma", Font.PLAIN, 22));
		textField.setEditable(false);
		textField.setColumns(20);
		textField.setBackground(new Color(139, 0, 0));

		jsAptos = new JScrollPane(pAptos);
		jsAptos.setBounds(15, 220, 720, 80);
		frame.getContentPane().add(jsAptos);

		jsTerminados = new JScrollPane(pTerminados);
		jsTerminados.setBounds(15, 330, 720, 80);
		frame.getContentPane().add(jsTerminados);

		JLabel lblCores = new JLabel("Cores");
		lblCores.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblCores.setForeground(Color.GREEN);
		lblCores.setBounds(15, 70, 40, 25);
		frame.getContentPane().add(lblCores);

		JLabel lblAptos = new JLabel("Aptos");
		lblAptos.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblAptos.setForeground(Color.GREEN);
		lblAptos.setBounds(15, 190, 40, 25);
		frame.getContentPane().add(lblAptos);

		JLabel lblTerminadosabortados = new JLabel("Terminados/Abortados");
		lblTerminadosabortados.setForeground(Color.GREEN);
		lblTerminadosabortados.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblTerminadosabortados.setBounds(15, 305, 150, 25);
		frame.getContentPane().add(lblTerminadosabortados);

		JButton btnAdicionar = new JButton("Adicionar");
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addProcess();
			}

		});
		btnAdicionar.setBounds(355, 190, 90, 25);
		frame.getContentPane().add(btnAdicionar);
		
		JLabel labelGM = new JLabel("Gerenciador de Memoria");
		labelGM.setForeground(Color.GREEN);
		labelGM.setFont(new Font("Tahoma", Font.PLAIN, 12));
		labelGM.setBounds(15, 420, 150, 25);
		frame.getContentPane().add(labelGM);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		pBlocos = new JPanel();
		pBlocos.setBounds(15, 445, 720, 80);
		pBlocos.setBackground(new Color(255, 127, 80));
		frame.getContentPane().add(pBlocos);
		jsBlocos = new JScrollPane(pBlocos);
		jsBlocos.setBounds(15, 445, 720, 80);
		frame.getContentPane().add(jsBlocos);
		frame.setLocation(300,7);
		frame.setVisible(true);
		t = new Thread(){
			public void run() {
				while(true){
				escalonar();
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
			};
		};
	t.start();
	}

	public abstract void addProcess();

	public abstract void escalonar();

	public abstract void terminar(Core c);

	public void addAptos(Processo p) {
		pAptos.add(p.getPanel());
		pAptos.revalidate();
		pAptos.repaint();

	}

	public void addCores(Core c) {
		pCores.add(c.getPanel());
		pCores.revalidate();
		pCores.repaint();
	}

	public void addTerminados(Processo p) {
		pTerminados.add(p.getPanel());
		pTerminados.revalidate();
		pTerminados.repaint();

	}

	public void removeAptos(int index) {
		pAptos.remove(index);
		pAptos.revalidate();
		pAptos.repaint();
	}

	public void removeAptos(Processo p) {
		pAptos.remove(p.getPanel());
		pAptos.revalidate();
		pAptos.repaint();
	}

	public void removeCores() {
		pCores.removeAll();
	}

	public void removerAptos() {
		pAptos.removeAll();
	}
	
	public void readdCores() {
		pCores.add(pAptos.getComponent(0));
		pCores.revalidate();
		pCores.repaint();
		
	}
	public void repintarCore(Core c){
		pCores.revalidate();
		pCores.repaint();
	}

	public JFrame getFrame() {
		// TODO Auto-generated method stub
		return frame;
	}

	public void repintar() {
		pAptos.revalidate();
		pAptos.repaint();
		pCores.revalidate();
		pCores.repaint();
		pTerminados.revalidate();
		pTerminados.repaint();
		pBlocos.revalidate();
		pBlocos.repaint();
	}
	
	public void addBloco(Bloco b){
		pBlocos.add(b.getPanel());
	}
}
