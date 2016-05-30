import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

public class Core extends Thread {
	JPanel panel;
	private Processo processo = null;
	boolean correr=true;
	public Core() {
		panel = new JPanel();
		panel.setPreferredSize(new Dimension(110, 58));
		panel.setBackground(Color.DARK_GRAY);
		start();
	}

	public synchronized void setProcesso(Processo p) {
		processo = p;
		p.setDeadline(-1);
		p.getLblDLine().setVisible(false);
		p.getLblDLineValue().setVisible(false);
		panel.add(p.getPanel());
		panel.revalidate();
		panel.repaint();
		if(this.isAlive())this.notify();
	}

	public JPanel getPanel() {
		return panel;
	}

	
	public void removerProcesso() {
		processo = null;
		panel.removeAll();
		panel.setPreferredSize(new Dimension(110, 58));
		panel.setBackground(Color.DARK_GRAY);
		panel.revalidate();
		panel.repaint();
	}

	public Processo getProcesso() {
		return this.processo;
	}

	public boolean haveProcess() {
		if (processo == null)
			return false;
		return true;
	}

	public void terminarProcesso() {
		processo.terminar(this);
		removerProcesso();
	}

	@Override
	public void run() {
		// try {
		// wait();
		// } catch (InterruptedException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }
		while (correr) {
			if (processo != null) {
				if (processo.getTempoRest() > 0) {
					processo.diminuirTRest();
					//processo.novaRequisicao();
				} else {
					terminarProcesso();

				}
				if (processo != null) {
					if (processo.getQuantum() > 0) {
						processo.diminuirQuantum();
						if (processo.getQuantum() == 0) {
							processo.quantumZero(this);
						}
					}
				}
				try {
					sleep(1000);
					panel.revalidate();
					panel.repaint();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				try {
					synchronized (this) {
						wait();
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
