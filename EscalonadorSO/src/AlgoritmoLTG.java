import java.awt.Color;
import java.util.ArrayList;

public class AlgoritmoLTG extends Escalonador {
	ArrayList<Processo> aptos = new ArrayList<Processo>();
	ArrayList<Processo> terminados = new ArrayList<Processo>();
	ArrayList<Core> cores = new ArrayList<Core>();
	GerenciadorDeMemoria gm;
	public AlgoritmoLTG(int qCores, int qProcIni, GerenciadorDeMemoria gm) {
		super();
		this.gm=gm;
		for (int i = 0; i < qCores; i++) {
			Core c = new Core();
			cores.add(c);
			addCores(c);
		}

		for (int k = 0; k < qProcIni; k++) {
			Processo p = new Processo(this);
			p.getPanel().setBackground(new Color(0, 0, 80));
			if (aptos.size() > 0) {
				boolean flag = false;
				for (int i = 0; i < aptos.size(); i++) {
					if (p.getDeadline() < aptos.get(i).getDeadline()) {
						aptos.add(i, p);
						flag = true;
						break;
					}
				}
				if (flag == false) {
					aptos.add(p);
				}
			} else {
				aptos.add(p);
			}
			escalonar();
			for (Processo a : aptos) {
				addAptos(a);
			}
			
		}
	}

	@Override
	public void addProcess() {
		Processo p = new Processo(this);
		if (aptos.size() > 0) {
			boolean flag = false;
			for (int i = 0; i < aptos.size(); i++) {
				if (p.getDeadline() < aptos.get(i).getDeadline()) {
					aptos.add(i, p);

					flag = true;
					break;
				}
			}
			if (flag == false) {
				aptos.add(p);

			}
		} else {
			aptos.add(p);

		}
		escalonar();
		removerAptos();
		for (Processo a : aptos) {
			addAptos(a);
		}
	}

	@Override
	public void escalonar() {
		synchronized (this) {
			for (int i = 0; i < cores.size(); i++) {
				if (!cores.get(i).haveProcess()) {
					if (!aptos.isEmpty()) {
						cores.get(i).setProcesso(aptos.get(0));
						removeCores();
						reAddCores();
						//readdCores();
						aptos.remove(0);
						repintar();
					} 
			}
			}
		}
		
	}

	private void reAddCores() {
		for (Core c : cores) {
			addCores(c);
		}

	}

	public void terminar(Core c) {
		terminados.add(c.getProcesso());
		addTerminados(c.getProcesso());
		removerCore(c);
		escalonar();
	}

	public void dlineZero(Processo p) {
		aptos.remove(p);
		p.abortar();
		terminados.add(p);
		removeAptos(p);
		addTerminados(p);
		repintar();
	}

		
}
