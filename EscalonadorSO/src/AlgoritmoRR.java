import java.awt.Color;
import java.util.ArrayList;

public class AlgoritmoRR extends Escalonador {
	ArrayList<Processo> aptos = new ArrayList<Processo>();
	ArrayList<Processo> aptos2 = new ArrayList<Processo>();
	ArrayList<Processo> aptos3 = new ArrayList<Processo>();
	ArrayList<Processo> aptos4 = new ArrayList<Processo>();
	ArrayList<Processo> terminados = new ArrayList<Processo>();
	ArrayList<Core> cores = new ArrayList<Core>();
	private int lastQ;
	private int quantum;
	private int qCores;

	public AlgoritmoRR(int qCores, int qProcIni, int Quantum) {
		super();
		this.qCores = qCores;
		quantum = Quantum;
		for (int i = 0; i < qCores; i++) {
			Core c = new Core();
			cores.add(c);
			addCores(c);
		}
		for (int i = 0; i < qProcIni; i++) {
			Processo p = new Processo(this);
			switch (p.getPrioridade()) {
			case 0:
				p.getPanel().setBackground(new Color(0, 0, 80));
				aptos.add(p);
				addAptos(p);
				break;
			case 1:
				p.getPanel().setBackground(new Color(55, 0, 80));
				aptos2.add(p);
				addAptos(p);
				break;
			case 2:
				p.getPanel().setBackground(new Color(80, 0, 0));
				aptos3.add(p);
				addAptos(p);
				break;
			case 3:
				p.getPanel().setBackground(new Color(80, 0, 55));
				aptos4.add(p);
				addAptos(p);
				break;
			}
		}
		escalonar();
	}

	@Override
	public void addProcess() {

		Processo p = new Processo(this);
		switch (p.getPrioridade()) {
		case 0:
			aptos.add(p);
			addAptos(p);
			break;
		case 1:
			aptos2.add(p);
			addAptos(p);
			break;
		case 2:
			aptos3.add(p);
			addAptos(p);
			break;
		case 3:
			aptos4.add(p);
			addAptos(p);
			break;

		}
		escalonar();
	}

	@Override
	public void escalonar() {
		for (int i = 0; i < cores.size(); i++) {
			if (!cores.get(i).haveProcess()) {
				switch (lastQ%4) {
				case 0:
					if (!aptos.isEmpty()) {
						aptos.get(0).setQuantum(4 * quantum);
						aptos.get(0).lblQuantumValue.setText(aptos.get(0).getQuantum() + "s");
						aptos.get(0).lblQuantumValue.setVisible(true);
						aptos.get(0).lblQuantum.setVisible(true);
						cores.get(i).setProcesso(aptos.get(0));
						removeCores();
						reAddCores();
						aptos.remove(0);
					}
					break;
				case 1:
					if (!aptos2.isEmpty()) {
						aptos2.get(0).setQuantum(2 * quantum);
						aptos2.get(0).lblQuantumValue.setText(aptos2.get(0).getQuantum() + "s");
						aptos2.get(0).lblQuantumValue.setVisible(true);
						aptos2.get(0).lblQuantum.setVisible(true);
						cores.get(i).setProcesso(aptos2.get(0));
						removeCores();
						reAddCores();
						aptos2.remove(0);
					}
					break;
				case 2:
					if (!aptos3.isEmpty()) {
						aptos3.get(0).setQuantum(3 * quantum);
						aptos3.get(0).lblQuantumValue.setText(aptos3.get(0).getQuantum() + "s");
						aptos3.get(0).lblQuantumValue.setVisible(true);
						aptos3.get(0).lblQuantum.setVisible(true);
						cores.get(i).setProcesso(aptos.get(0));
						removeCores();
						reAddCores();
						aptos3.remove(0);
					}
					break;
				case 3:
					if (!aptos4.isEmpty()) {
						aptos4.get(0).setQuantum(quantum);
						aptos4.get(0).lblQuantumValue.setText(aptos4.get(0).getQuantum() + "s");
						aptos4.get(0).lblQuantumValue.setVisible(true);
						aptos4.get(0).lblQuantum.setVisible(true);
						cores.get(i).setProcesso(aptos4.get(0));
						removeCores();
						reAddCores();
						aptos4.remove(0);
					}
					break;
				}
				lastQ++;
			} else
				break;
		}
	}

	private void reAddCores() {
		for (Core c : cores) {
			addCores(c);
		}

	}

	@Override
	public void terminar(Core c) {
		terminados.add(c.getProcesso());
		addTerminados(c.getProcesso());
		removerCore(c);
		escalonar();
	}

	public void quantumZero(Core c) {
		aptos.add(c.getProcesso());
		addAptos(c.getProcesso());
		c.removerProcesso();
		removerCore(c);
		escalonar();
	}

}
