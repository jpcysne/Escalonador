import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.Color;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TelaConfigLTG {

	private JFrame frame;
	private JTextField tfQntCores;
	private JTextField tfQntProc;
	private int qntCores;
	private int qntProc;
	GerenciadorDeMemoria gm;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaConfigLTG window = new TelaConfigLTG();
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
	public TelaConfigLTG() {
		initialize();
	}

	public TelaConfigLTG(GerenciadorDeMemoria gm) {
		// TODO Auto-generated constructor stub
		this.gm=gm;
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

		JLabel lblFilaDePrioridade = new JLabel("Least Time to Go");
		lblFilaDePrioridade.setForeground(new Color(0, 128, 0));
		lblFilaDePrioridade.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblFilaDePrioridade.setBackground(new Color(139, 0, 0));
		lblFilaDePrioridade.setBounds(360, 225, 135, 25);
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

		tfQntCores = new JTextField("");
		tfQntCores.setBounds(420, 320, 40, 20);
		frame.getContentPane().add(tfQntCores);
		tfQntCores.setColumns(10);

		tfQntProc = new JTextField("");
		tfQntProc.setColumns(10);
		tfQntProc.setBounds(420, 360, 40, 20);
		frame.getContentPane().add(tfQntProc);

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
		JButton btnIniciar = new JButton("Iniciar");
		btnIniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					qntCores = Integer.parseInt(tfQntCores.getText());
					qntProc = Integer.parseInt(tfQntProc.getText());
					if (qntCores < 1 || qntCores > 64) {
						lblErro.setVisible(true);
					} else if (qntProc <= 0) {
						lblErro2.setVisible(true);
					} else {
						frame.dispose();
						Escalonador esc = new AlgoritmoLTG(qntCores, qntProc,gm);
						esc.getFrame().setLocation(300, 7);
						esc.getFrame().setVisible(true);
					}
				} catch (Exception e1) {
					lblErro3.setVisible(true);
				}

			}
		});
		btnIniciar.setBounds(405, 400, 70, 25);
		frame.getContentPane().add(btnIniciar);
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
}
