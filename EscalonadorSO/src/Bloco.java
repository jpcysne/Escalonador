import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JLabel;


public class Bloco {
	static int pbl = 0 ;
	private int tamanho;
	private int espacoUsado;
	private JPanel panel;
	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */
	public Bloco(int tamanho){
		this.tamanho=tamanho;
		pbl++;
		initialize();
	}
	public int getTamanho() {
		return tamanho;
	}
	public void setTamanho(int tamanho) {
		this.tamanho = tamanho;
	}
	public JPanel getPanel() {
		return panel;
	}
	public void setPanel(JPanel panel) {
		this.panel = panel;
	}
	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize(){
		panel = new JPanel();
		panel.setBackground(new Color(47, 79, 79));
		panel.setPreferredSize(new Dimension(110, 52));
		panel.setLayout(null);
		
		JLabel lblBlocoId = new JLabel("BlocoID =");
		lblBlocoId.setForeground(new Color(0, 255, 0));
		lblBlocoId.setBounds(5, 2, 68, 15);
		panel.add(lblBlocoId);
		
		JLabel lblPIDValue = new JLabel(pbl+" ");
		lblPIDValue.setForeground(new Color(0, 255, 0));
		lblPIDValue.setBounds(72, 2, 46, 14);
		panel.add(lblPIDValue);
		
		JLabel lblTamTotal = new JLabel("Tam. Total =");
		lblTamTotal.setForeground(new Color(0, 255, 0));
		lblTamTotal.setBounds(5, 18, 68, 15);
		panel.add(lblTamTotal);
		
		JLabel lblTTValue = new JLabel(tamanho+" ");
		lblTTValue.setForeground(Color.GREEN);
		lblTTValue.setBounds(72, 18, 46, 14);
		panel.add(lblTTValue);
		
		JLabel EspUsado = new JLabel("Esp. Usado = ");
		EspUsado.setForeground(new Color(0, 255, 0));
		EspUsado.setBounds(5, 33, 72, 14);
		panel.add(EspUsado);
		
		JLabel lblEUValue = new JLabel(espacoUsado+" ");
		lblEUValue.setForeground(new Color(0, 255, 0));
		lblEUValue.setBounds(72, 33, 46, 14);
		panel.add(lblEUValue);
	}
}
