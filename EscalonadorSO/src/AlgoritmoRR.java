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

	public AlgoritmoRR(int qCores, int qProcIni, int Quantum,
			GerenciadorDeMemoria gm) {
		super(gm);
		quantum = Quantum;
		this.gm = gm;
		this.gm.esc=this;
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
	public synchronized void addProcess() {

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
	public synchronized void escalonar() {

		for (int i = 0; i < cores.size(); i++) {
			if (!cores.get(i).haveProcess()) {
				boolean pare = false;
				while (!pare
					&& (!aptos.isEmpty() || !aptos2.isEmpty()
								|| !aptos3.isEmpty() || !aptos4.isEmpty())) {
					switch (lastQ) {
					case 0:
						if (!aptos.isEmpty()) {
							if (!aptos.get(0).alocado) {
							
								gm.alocar(aptos.get(0),aptos.get(0).getMemoria());
							}
							if (aptos.get(0).alocado) {
								aptos.get(0).setQuantum(4 * quantum);
								aptos.get(0).lblQuantumValue.setText(aptos.get(
										0).getQuantum()
										+ "s");
								aptos.get(0).lblQuantumValue.setVisible(true);
								aptos.get(0).lblQuantum.setVisible(true);
								cores.get(i).setProcesso(aptos.get(0));
								//removeCores();
								//reAddCores();
								repintar();
								aptos.remove(0);
								pare = true;
								nextQ();
								return;
							} else {
								abortarMemoria(aptos.get(0), aptos);
								nextQ();
								return;
							}
						}
					case 1:
						if (!aptos2.isEmpty()) {
							if (!aptos2.get(0).alocado) {
								gm.alocar(aptos2.get(0),aptos2.get(0).getMemoria());
							}
							if (aptos2.get(0).alocado) {
								aptos2.get(0).setQuantum(3 * quantum);
								aptos2.get(0).lblQuantumValue.setText(aptos2
										.get(0).getQuantum() + "s");
								aptos2.get(0).lblQuantumValue.setVisible(true);
								aptos2.get(0).lblQuantum.setVisible(true);
								cores.get(i).setProcesso(aptos2.get(0));
								//removeCores();
								//reAddCores();
								repintar();
								aptos2.remove(0);

								pare = true;
								nextQ();
								return;
							} else {
								abortarMemoria(aptos2.get(0), aptos2);
								nextQ();
								return;
							}
						}
					case 2:
						if (!aptos3.isEmpty()) {
							if (!aptos3.get(0).alocado) {
								gm.alocar(aptos3.get(0),aptos3.get(0).getMemoria());
							}
							if (aptos3.get(0).alocado) {
								aptos3.get(0).setQuantum(2 * quantum);
								aptos3.get(0).lblQuantumValue.setText(aptos3
										.get(0).getQuantum() + "s");
								aptos3.get(0).lblQuantumValue.setVisible(true);
								aptos3.get(0).lblQuantum.setVisible(true);
								cores.get(i).setProcesso(aptos3.get(0));
								//removeCores();
								//reAddCores();
								repintar();
								aptos3.remove(0);

								pare = true;
								nextQ();
								return;
							} else {
								abortarMemoria(aptos3.get(0), aptos3);
								nextQ();
								return;
							}
						}
					case 3:
						if (!aptos4.isEmpty()) {
							if (!aptos4.get(0).alocado) {
								gm.alocar(aptos4.get(0),aptos4.get(0).getMemoria());
							}
							if (aptos4.get(0).alocado) {
								aptos4.get(0).setQuantum(quantum);
								aptos4.get(0).lblQuantumValue.setText(aptos4
										.get(0).getQuantum() + "s");
								aptos4.get(0).lblQuantumValue.setVisible(true);
								aptos4.get(0).lblQuantum.setVisible(true);
								cores.get(i).setProcesso(aptos4.get(0));
								//removeCores();
								//reAddCores();
								repintar();
								aptos4.remove(0);
								pare = true;
								nextQ();
								return;
							} else {
								abortarMemoria(aptos4.get(0), aptos4);
								nextQ();
								return;
							}
						}
						
					}
				}
			}
		}
	}


	@Override
	public void terminar(Core c) {
		terminados.add(c.getProcesso());
		addTerminados(c.getProcesso());
		repintarCore(c);
		gm.desalocar(c.getProcesso());
		repintar();
		escalonar();
	}

	public void quantumZero(Core c) {
		switch(c.getProcesso().getPrioridade()){
		case 0: aptos.add(c.getProcesso());
			break;
		case 1: aptos2.add(c.getProcesso());
			break;
		case 2: aptos3.add(c.getProcesso()); 
			break;
		case 3: aptos4.add(c.getProcesso());
			break;
		}
		addAptos(c.getProcesso());
		c.removerProcesso();
		repintarCore(c);
		repintar();
		escalonar();
	}
	public void abortarMemoria(Processo p){
		p.abortarMemoria();
		terminados.add(p);
		addTerminados(p);
		repintar();
	}
	public void abortarMemoria(Processo p, ArrayList<Processo> aptos){
		p.abortarMemoria();
		terminados.add(p);
		removeAptos(p);
		addTerminados(p);
		aptos.remove(p);
		repintar();
	}
	public void abortarEscalonado(Processo p) {
		for (Core c : cores) {
			if(c.getProcesso()!=null)
			if (c.getProcesso().equals(p)) {
				terminados.add(c.getProcesso());
				addTerminados(c.getProcesso());
				gm.desalocar(c.getProcesso());
				c.removerProcesso();
				repintar();
				escalonar();
				break;
			}
		}
	}
	private void nextQ(){
		lastQ++;
		if(lastQ==4){
			lastQ=0;
		}
	}
}
