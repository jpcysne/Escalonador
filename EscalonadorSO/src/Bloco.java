import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JLabel;


public class Bloco {
	static int pbl = 0 ;
	private int tamanho;
	private int espacoUsado;
	private JPanel panel;
	private Processo proc;
	JLabel lblPIDValue;
	JLabel lblEValue;
	
	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */
	public Bloco(int tamanho){
		this.tamanho=tamanho;
		initialize();
		pbl++;
	}
	public Processo getProc() {
		return proc;
	}
	public void setProc(Processo proc) {
		this.proc = proc;
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
		panel.setPreferredSize(new Dimension(129, 52));
		panel.setLayout(null);
		
		JLabel lblBlocoId = new JLabel("BlocoID =");
		lblBlocoId.setForeground(new Color(0, 255, 0));
		lblBlocoId.setBounds(5, 2, 56, 15);
		panel.add(lblBlocoId);
		
		JLabel lblBIDValue = new JLabel(pbl+" ");
		lblBIDValue.setForeground(new Color(0, 255, 0));
		lblBIDValue.setBounds(62, 2, 25, 14);
		panel.add(lblBIDValue);
		
		JLabel lblTamTotal = new JLabel("Espa\u00E7o =");
		lblTamTotal.setForeground(new Color(0, 255, 0));
		lblTamTotal.setBounds(5, 33, 56, 15);
		panel.add(lblTamTotal);
		
		
		lblEValue = new JLabel(espacoUsado+" / "+tamanho);
		lblEValue.setForeground(new Color(0, 255, 0));
		lblEValue.setBounds(62, 33, 65, 14);
		panel.add(lblEValue);
		
		JLabel lblPid = new JLabel("ProcID = ");
		lblPid.setForeground(Color.GREEN);
		lblPid.setBounds(5, 18, 56, 14);
		panel.add(lblPid);
		
		if(proc==null){
			lblPIDValue = new JLabel("livre");
		}else{
			lblPIDValue = new JLabel(proc.pId-1+"");
		}
		lblPIDValue.setForeground(new Color(0, 255, 0));
		lblPIDValue.setBounds(62, 18, 46, 14);
		panel.add(lblPIDValue);
	}
	public int getEspacoUsado() {
		return espacoUsado;
	}
	public void setEspacoUsado(int espacoUsado) {
		this.espacoUsado = espacoUsado;
	}

	
}
