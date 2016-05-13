import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.Color;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class TelaConfigRR {

	private JFrame frame;
	private JTextField tfQntCores;
	private JTextField tfQntProc;
	private JTextField tfQuantum;
	private int qntCores;
	private int qntProc;
	private int quantum;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaConfigRR window = new TelaConfigRR();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TelaConfigRR() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 12));
		frame.getContentPane().setBackground(new Color(139, 0, 0));
		frame.setBounds(100, 100, 800, 720);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblFilaDePrioridade = new JLabel("Fila de Prioridade com Round-Robin");
		lblFilaDePrioridade.setForeground(new Color(0, 128, 0));
		lblFilaDePrioridade.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblFilaDePrioridade.setBackground(new Color(139, 0, 0));
		lblFilaDePrioridade.setBounds(280, 225, 225, 25);
		frame.getContentPane().add(lblFilaDePrioridade);
		
		JLabel lblQuantidadeDeCores = new JLabel("Quantidade de Cores:");
		lblQuantidadeDeCores.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblQuantidadeDeCores.setForeground(new Color(0, 128, 0));
		lblQuantidadeDeCores.setBounds(280, 315, 120, 25);
		frame.getContentPane().add(lblQuantidadeDeCores);
		
		JLabel lblProcessosIniciais = new JLabel("Processos iniciais:");
		lblProcessosIniciais.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblProcessosIniciais.setForeground(new Color(0, 128, 0));
		lblProcessosIniciais.setBounds(280, 355, 120, 25);
		frame.getContentPane().add(lblProcessosIniciais);
		
		JLabel lblQuantum = new JLabel("Quantum:");
		lblQuantum.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblQuantum.setForeground(new Color(0, 128, 0));
		lblQuantum.setBounds(280, 395, 120, 25);
		frame.getContentPane().add(lblQuantum);
		
		tfQntCores = new JTextField();
		tfQntCores.setBounds(420, 320, 40, 20);
		frame.getContentPane().add(tfQntCores);
		tfQntCores.setColumns(10);
		
		tfQntProc = new JTextField();
		tfQntProc.setColumns(10);
		tfQntProc.setBounds(420, 360, 40, 20);
		frame.getContentPane().add(tfQntProc);
		
		tfQuantum = new JTextField();
		tfQuantum.setColumns(10);
		tfQuantum.setBounds(420, 400, 40, 20);
		frame.getContentPane().add(tfQuantum);
		
		JLabel lblErro = new JLabel("Por favor, insira um valor entre 1 e 64");
		lblErro.setForeground(new Color(255, 0, 0));
		lblErro.setBounds(470, 322, 250, 15);
		lblErro.setVisible(false);
		frame.getContentPane().add(lblErro);

		JLabel lblErro2 = new JLabel("Por favor, insira um valor acima de 0");
		lblErro2.setForeground(Color.RED);
		lblErro2.setBounds(470, 362, 250, 15);
		lblErro2.setVisible(false);
		frame.getContentPane().add(lblErro2);

		JLabel lblErro3 = new JLabel("Por favor, insira valores nos campos");
		lblErro3.setForeground(new Color(255, 0, 0));
		lblErro3.setBounds(350, 290, 250, 15);
		lblErro3.setVisible(false);
		frame.getContentPane().add(lblErro3);
		
		JLabel lblErro4 = new JLabel("Por favor, insira um valor entre 2 e 20");
		lblErro4.setForeground(Color.RED);
		lblErro4.setBounds(470, 402, 250, 15);
		lblErro4.setVisible(false);
		frame.getContentPane().add(lblErro4);
		
		JButton btnIniciar = new JButton("Iniciar");
		btnIniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					qntCores = Integer.parseInt(tfQntCores.getText());
					qntProc = Integer.parseInt(tfQntProc.getText());
					quantum = Integer.parseInt(tfQuantum.getText());
					if (qntCores < 1 || qntCores > 64) {
						lblErro.setVisible(true);
					} else if (qntProc <= 0) {
						lblErro2.setVisible(true);
					}else if(quantum<2 || quantum >20){
						lblErro4.setVisible(true);
					} else {
						frame.dispose();
						Escalonador esc = new AlgoritmoRR(qntCores,qntProc,quantum);
						esc.getFrame().setLocation(300,7);
						esc.getFrame().setVisible(true);
					}
				}catch(Exception e1){
					lblErro3.setVisible(true);
				}
			}
		});
		btnIniciar.setBounds(410, 440, 70, 25);
		frame.getContentPane().add(btnIniciar);
		
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
}
