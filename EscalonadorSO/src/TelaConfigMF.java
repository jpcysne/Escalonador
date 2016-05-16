import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.Color;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TelaConfigMF {

	private JFrame frame;
	private JTextField tfTam;
	private int tam;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaConfigMF window = new TelaConfigMF();
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
	public TelaConfigMF() {
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

		JLabel lblFit = new JLabel("Merge Fit");
		lblFit.setForeground(new Color(0, 128, 0));
		lblFit.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblFit.setBackground(new Color(139, 0, 0));
		lblFit.setBounds(405, 254, 75, 25);
		frame.getContentPane().add(lblFit);

		JLabel lblTamanhoDaMemoria = new JLabel("Tamanho da Memoria:");
		lblTamanhoDaMemoria.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblTamanhoDaMemoria.setForeground(new Color(0, 128, 0));
		lblTamanhoDaMemoria.setBounds(280, 315, 135, 25);
		frame.getContentPane().add(lblTamanhoDaMemoria);

		tfTam = new JTextField("");
		tfTam.setBounds(420, 320, 40, 20);
		frame.getContentPane().add(tfTam);
		tfTam.setColumns(10);

		JLabel lblErro = new JLabel("Por favor, insira um valor maior que 0");
		lblErro.setForeground(new Color(255, 0, 0));
		lblErro.setBounds(470, 322, 250, 15);
		lblErro.setVisible(false);
		frame.getContentPane().add(lblErro);

		JLabel lblErro3 = new JLabel("Por favor, insira valores nos campos");
		lblErro3.setForeground(new Color(255, 0, 0));
		lblErro3.setBounds(350, 290, 250, 15);
		lblErro3.setVisible(false);
		frame.getContentPane().add(lblErro3);
		JButton btnIniciar = new JButton("Iniciar");
		btnIniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					tam = Integer.parseInt(tfTam.getText());
					if(tam<=0)
						lblErro.setVisible(true);
					else{
						frame.dispose();
						GerenciadorDeMemoria gm = new AlgoritmoMF(tam);
						TelaConfigEscalonador tce = new TelaConfigEscalonador(gm);
						tce.getFrame().setLocation(300, 7);
						tce.getFrame().setVisible(true);
					}
				} catch (Exception e1) {
					lblErro3.setVisible(true);
				}

			}
		});
		btnIniciar.setBounds(405, 358, 70, 25);
		frame.getContentPane().add(btnIniciar);
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
}
