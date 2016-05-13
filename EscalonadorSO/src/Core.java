import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

public class Core extends Thread {
	JPanel panel;
	private Processo processo = null;

	public Core() {
		panel = new JPanel();
		panel.setPreferredSize(new Dimension(110, 52));
		panel.setBackground(Color.DARK_GRAY);
		start();
	}

	public synchronized void setProcesso(Processo p) {
		processo = p;
		p.setDeadline(-1);
		p.getLblDLine().setVisible(false);
		p.getLblDLineValue().setVisible(false);
		panel = p.getPanel();
		panel.revalidate();
		panel.repaint();
		this.notify();
	}

	public JPanel getPanel() {
		return panel;
	}

	public void setPanel(JPanel panel) {
		this.panel = panel;
	}

	public void removerProcesso() {
		processo = null;
		panel = new JPanel();
		panel.setPreferredSize(new Dimension(110, 52));
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
		while (true) {
			if (haveProcess()) {
				if (processo.getTempoRest() > 0) {
					processo.diminuirTRest();

				} else {
					terminarProcesso();

				}
				if (haveProcess()) {
					if (processo.getQuantum() >= 0) {
						processo.diminuirQuantum();
						if (processo.getQuantum() == 0) {
							processo.quantumZero(this);
						}
					}
				}
				try {
					sleep(1000);
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
