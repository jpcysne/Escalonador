import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.Color;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TelaConfigQF {

	private JFrame frame;
	private JTextField tfTam;
	private int tam;
	private JTextField tfQntL;
	private JTextField tfQntR;
	int qntL,qntR;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaConfigQF window = new TelaConfigQF();
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
	public TelaConfigQF() {
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

		JLabel lblFit = new JLabel("Quick Fit");
		lblFit.setForeground(new Color(0, 128, 0));
		lblFit.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblFit.setBackground(new Color(139, 0, 0));
		lblFit.setBounds(383, 251, 75, 25);
		frame.getContentPane().add(lblFit);

		JLabel lblTamanhoDaMemoria = new JLabel("Tamanho da Memoria:");
		lblTamanhoDaMemoria.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblTamanhoDaMemoria.setForeground(new Color(0, 128, 0));
		lblTamanhoDaMemoria.setBounds(243, 318, 135, 25);
		frame.getContentPane().add(lblTamanhoDaMemoria);

		tfTam = new JTextField("");
		tfTam.setBounds(383, 323, 70, 20);
		frame.getContentPane().add(tfTam);
		tfTam.setColumns(10);

		JLabel lblErro = new JLabel("Por favor, insira um valor maior que 0");
		lblErro.setForeground(new Color(255, 0, 0));
		lblErro.setBounds(468, 324, 250, 15);
		lblErro.setVisible(false);
		frame.getContentPane().add(lblErro);

		JLabel lblErro3 = new JLabel("Por favor, insira valores nos campos");
		lblErro3.setForeground(new Color(255, 0, 0));
		lblErro3.setBounds(339, 293, 250, 15);
		lblErro3.setVisible(false);
		frame.getContentPane().add(lblErro3);
		JButton btnIniciar = new JButton("Iniciar");
		btnIniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					tam = Integer.parseInt(tfTam.getText());
					qntL= Integer.parseInt(tfQntL.getText());
					qntR= Integer.parseInt(tfQntR.getText());
					if(tam<=0)
						lblErro.setVisible(true);
					else{
						frame.dispose();
						GerenciadorDeMemoria gm = new AlgoritmoQF(tam, qntL, qntR);
						TelaConfigEscalonador tce = new TelaConfigEscalonador(gm);
						tce.getFrame().setLocation(300, 7);
						tce.getFrame().setVisible(true);
					}
				} catch (Exception e1) {
					lblErro3.setVisible(true);
				}

			}
		});
		btnIniciar.setBounds(383, 424, 70, 25);
		frame.getContentPane().add(btnIniciar);
		
		JLabel lblQuantidadeDeListas = new JLabel("Quantidade de Listas:");
		lblQuantidadeDeListas.setForeground(new Color(0, 128, 0));
		lblQuantidadeDeListas.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblQuantidadeDeListas.setBounds(243, 354, 118, 25);
		frame.getContentPane().add(lblQuantidadeDeListas);
		
		JLabel lblQuantidadeDeRequisies = new JLabel("Quantidade de Requisi\u00E7\u00F5es:");
		lblQuantidadeDeRequisies.setForeground(new Color(0, 128, 0));
		lblQuantidadeDeRequisies.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblQuantidadeDeRequisies.setBounds(210, 390, 151, 25);
		frame.getContentPane().add(lblQuantidadeDeRequisies);
		
		tfQntL = new JTextField("");
		tfQntL.setColumns(10);
		tfQntL.setBounds(383, 357, 70, 20);
		frame.getContentPane().add(tfQntL);
		
		tfQntR = new JTextField("");
		tfQntR.setColumns(10);
		tfQntR.setBounds(383, 393, 70, 20);
		frame.getContentPane().add(tfQntR);
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
}