import java.awt.Color;
import java.util.ArrayList;

public class AlgoritmoLTG extends Escalonador {
	ArrayList<Processo> aptos = new ArrayList<Processo>();
	ArrayList<Processo> terminados = new ArrayList<Processo>();
	ArrayList<Core> cores = new ArrayList<Core>();
	

	public AlgoritmoLTG(int qCores, int qProcIni, GerenciadorDeMemoria gm) {
		super(gm);
		this.gm = gm;
		this.gm.esc=this;
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
	public synchronized void addProcess() {
		Processo p = new Processo(this);
		if (aptos.size() > 0) {
			boolean flag = false;
			for (int i = 0; i < aptos.size(); i++) {
				if (p.getDeadline() < aptos.get(i).getDeadline()) {
					aptos.add(i, p);
					addAptos(p,i);
					flag = true;
					break;
				}
			}
			if (flag == false) {
				aptos.add(p);
				addAptos(p);
			}
		} else {
			aptos.add(p);
			addAptos(p);

		}
		escalonar();
		
//		removerAptos();
//		for (Processo a : aptos) {
//			addAptos(a);
//		}
	}

	@Override
	public synchronized void escalonar() {

		for (int i = 0; i < cores.size(); i++) {
			if (!cores.get(i).haveProcess()) {
				if (!aptos.isEmpty()) {
					Bloco b=gm.alocar(aptos.get(0), aptos.get(0).getMemoria());
					if (b!=null) {
						cores.get(i).setProcesso(aptos.get(0));
						aptos.remove(0);
						repintar();
					} else {
						abortarMemoria(aptos.get(0));
					}
				}else{
					break;
				}
			}
		}
	}

	

	public void terminar(Core c) {
		terminados.add(c.getProcesso());
		addTerminados(c.getProcesso());
		repintarCore(c);
		gm.desalocar(c.getProcesso());
		escalonar();
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

	public void dlineZero(Processo p) {
		p.abortar();
		terminados.add(p);
		removeAptos(p);
		addTerminados(p);
		aptos.remove(p);
		repintar();
	}
	public void abortarMemoria(Processo p){
		p.abortarMemoria();
		terminados.add(p);
		removeAptos(p);
		addTerminados(p);
		
		aptos.remove(p);
		repintar();
	}
}
