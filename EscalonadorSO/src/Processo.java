import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Processo extends Thread {

	private JPanel panel;
	private Random r = new Random();
	private int tempoExec = r.nextInt(17) + 4;
	private int tempoRest = tempoExec;
	private int prioridade = r.nextInt(4);
	private int quantum = -1;
	private int deadline = r.nextInt(17) + 4;
	static int pId = 0;
	JLabel lblTempoRest;
	JLabel lblTempoRestValue;
	Escalonador escalonador;
	JLabel lblQuantumValue;
	JLabel lblQuantum;
	JLabel lblDLineValue;
	JLabel lblDLine;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */
	public Processo(Escalonador esc) {
		escalonador = esc;
		initialize();
		pId++;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		panel = new JPanel();
		panel.setBackground(new Color(0, 0, 128));
		switch (prioridade) {
		case 0:
			panel.setBackground(new Color(0, 0, 128));
			break;
		case 1:
			panel.setBackground(new Color(55, 0, 128));
			break;
		case 2:
			panel.setBackground(new Color(128, 0, 0));
			break;
		case 3:
			panel.setBackground(new Color(128, 0, 55));
			break;
		}
		panel.setPreferredSize(new Dimension(110, 52));
		panel.setLayout(null);

		JLabel lblPid = new JLabel("PID = ");
		lblPid.setBounds(5, 2, 35, 10);
		lblPid.setForeground(Color.GREEN);
		panel.add(lblPid);

		JLabel lblTempoTotal = new JLabel("Tempo Total=");
		lblTempoTotal.setBounds(5, 14, 80, 10);
		lblTempoTotal.setForeground(Color.GREEN);
		panel.add(lblTempoTotal);

		JLabel lblPIDValue = new JLabel(pId + " ");
		lblPIDValue.setBounds(45, 2, 44, 10);
		lblPIDValue.setForeground(Color.GREEN);
		panel.add(lblPIDValue);

		JLabel lblTempovalue = new JLabel(tempoExec + "s");
		lblTempovalue.setBounds(85, 14, 24, 10);
		lblTempovalue.setForeground(Color.GREEN);
		panel.add(lblTempovalue);

		lblTempoRestValue = new JLabel(tempoRest + "s");
		lblTempoRestValue.setBounds(85, 26, 24, 10);
		lblTempoRestValue.setForeground(Color.GREEN);
		panel.add(lblTempoRestValue);

		lblTempoRest = new JLabel("Tempo Rest=");
		lblTempoRest.setBounds(5, 26, 80, 10);
		lblTempoRest.setForeground(Color.GREEN);
		panel.add(lblTempoRest);

		lblDLine = new JLabel("DLine = ");
		lblDLine.setBounds(5, 38, 45, 10);
		lblDLine.setForeground(Color.GREEN);
		lblDLine.setVisible(false);
		panel.add(lblDLine);

		lblDLineValue = new JLabel(deadline + "s");
		lblDLineValue.setBounds(50, 38, 44, 10);
		lblDLineValue.setForeground(Color.GREEN);
		lblDLineValue.setVisible(false);
		panel.add(lblDLineValue);

		lblQuantum = new JLabel("Quantum = ");
		lblQuantum.setBounds(5, 38, 70, 10);
		lblQuantum.setForeground(Color.GREEN);
		lblQuantum.setVisible(false);
		panel.add(lblQuantum);

		lblQuantumValue = new JLabel(quantum + "s");
		lblQuantumValue.setBounds(80, 38, 44, 10);
		lblQuantumValue.setForeground(Color.GREEN);
		lblQuantumValue.setVisible(false);
		panel.add(lblQuantumValue);

		if (escalonador instanceof AlgoritmoRR) {
			try {
				deadline = -1;
				lblQuantum.setVisible(true);
				lblQuantumValue.setVisible(true);
				start();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			
		} else if (escalonador instanceof AlgoritmoLTG) {
			try {
				quantum = -1;
				lblDLine.setVisible(true);
				lblDLineValue.setVisible(true);
				start();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}

	public JLabel getLblQuantumValue() {
		return lblQuantumValue;
	}

	public void setLblQuantumValue(JLabel lblQuantumValue) {
		this.lblQuantumValue = lblQuantumValue;
	}

	public int getTempoRest() {
		return tempoRest;
	}

	public void setTempoRest(int tempoRest) {
		this.tempoRest = tempoRest;
	}

	public int getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(int prioridade) {
		this.prioridade = prioridade;
	}

	public int getQuantum() {
		return quantum;
	}

	public void setQuantum(int quantum) {
		this.quantum = quantum;
	}

	public int getDeadline() {
		return deadline;
	}

	public void setDeadline(int deadline) {
		this.deadline = deadline;

	}

	public void diminuirTRest() {
		this.tempoRest--;
		lblTempoRestValue.setText(tempoRest + "s");
		lblTempoRestValue.repaint();
	}

	public void diminuirDLine() {
		this.deadline--;
		lblDLineValue.revalidate();
		lblDLineValue.repaint();
	}

	public void diminuirQuantum() {
		this.quantum--;
		lblQuantumValue.setText(quantum+"s");
		lblQuantumValue.revalidate();
		lblQuantumValue.repaint();
	}

	public void terminar(Core c) {
		panel.setBackground(Color.BLACK);
		escalonador.terminar(c);
	}

	public void abortar() {
		panel.setBackground(Color.GRAY);
	}

	@Override
	public void run() {
		while ( deadline >= 0) {
			if (deadline > 0) {
				diminuirDLine();
				lblDLineValue.setText(deadline + "s");
				escalonador.repintar();
			} else {
				try {
					synchronized (this) {
						diminuirDLine();
						((AlgoritmoLTG) escalonador).dlineZero(this);
					}
					
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				}
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public JLabel getLblDLineValue() {
		return lblDLineValue;
	}

	public void setLblDLineValue(JLabel lblDLineValue) {
		this.lblDLineValue = lblDLineValue;
	}

	public JLabel getLblDLine() {
		return lblDLine;
	}

	public void setLblDLine(JLabel lblDLine) {
		this.lblDLine = lblDLine;
	}

	public JPanel getPanel() {
		// TODO Auto-generated method stub
		return panel;
	}
	public void quantumZero(Core c){
		((AlgoritmoRR) escalonador).quantumZero(c);
	}
}
