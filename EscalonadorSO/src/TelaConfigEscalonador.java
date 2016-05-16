import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class TelaConfigEscalonador {

	private JFrame frame;
	private GerenciadorDeMemoria gm;
	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	private JTextField txtTrabalhoDeSo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaConfigEscalonador window = new TelaConfigEscalonador();
					window.frame.setLocation(300, 0);
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
	public TelaConfigEscalonador(GerenciadorDeMemoria gm) {
		this.gm=gm;
		initialize();
	}
	public TelaConfigEscalonador() {
		initialize();
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setForeground(new Color(255, 255, 255));
		frame.getContentPane().setBackground(new Color(139, 0, 0));
		frame.getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("LTG");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
				TelaConfigLTG cltg = new TelaConfigLTG(gm);
				cltg.getFrame().setLocation(300, 7);
				cltg.getFrame().setVisible(true);				
			}
		});
		btnNewButton.setBounds(325, 290, 175, 25);
		frame.getContentPane().add(btnNewButton);
		
		txtTrabalhoDeSo = new JTextField();
		txtTrabalhoDeSo.setEditable(false);
		txtTrabalhoDeSo.setBounds(325, 225, 175, 30);
		txtTrabalhoDeSo.setHorizontalAlignment(SwingConstants.CENTER);
		txtTrabalhoDeSo.setFont(new Font("Tahoma", Font.PLAIN, 22));
		txtTrabalhoDeSo.setForeground(new Color(0, 128, 0));
		txtTrabalhoDeSo.setBackground(new Color(139, 0, 0));
		txtTrabalhoDeSo.setText("Trabalho de SO");
		frame.getContentPane().add(txtTrabalhoDeSo);
		txtTrabalhoDeSo.setColumns(20);
		
		JButton btnFilaComRoundrobin = new JButton("Fila com Round-Robin");
		btnFilaComRoundrobin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
				TelaConfigRR crr = new TelaConfigRR(gm);
				crr.getFrame().setLocation(300, 7);
				crr.getFrame().setVisible(true);
			}
		});
		btnFilaComRoundrobin.setBounds(325, 335, 175, 23);
		frame.getContentPane().add(btnFilaComRoundrobin);
		
		JButton btnIntervalbased = new JButton("Interval-Based");
		btnIntervalbased.setBounds(325, 375, 175, 25);
		frame.getContentPane().add(btnIntervalbased);
		frame.setBounds(100, 100, 800, 720);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
	}
}
