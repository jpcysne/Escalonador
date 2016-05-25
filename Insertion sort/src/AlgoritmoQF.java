import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class AlgoritmoQF extends GerenciadorDeMemoria {

	private HashMap<Integer, Integer> listaQF = new HashMap<Integer, Integer>();
	private int numRequisisao = 0;
	HashMap<Integer, ArrayList<Bloco>> maiores = new HashMap<Integer,ArrayList<Bloco>>();
	private int qntL, qntR;

	public AlgoritmoQF(int tM, int qntL, int qntR) {
		super(tM);
		this.qntL = qntL;
		this.qntR = qntR;
	}

	@Override
	public Bloco alocar(Processo p, int requisicao) {
		if (!maiores.isEmpty()) {
			for (Entry<Integer, ArrayList<Bloco>> m : maiores.entrySet()) {
				if (!m.getValue().isEmpty()) {
					if (m.getKey() == requisicao) {
						Bloco aux=m.getValue().get(0);
						aux.setProc(p);
						blistusado.add(aux);
						NumRequisicoes(requisicao);
						m.getValue().remove(0);
						return aux;
					}
				}
			}
		}
		if (!blistlivre.isEmpty()) {
			for (Bloco b : blistlivre) {
				if (b.getTamanho() >= requisicao) {
					b.setProc(p);
					b.setEspacoUsado(requisicao);
					blistlivre.remove(b);
					blistusado.add(b);
					p.blocoList.add(b);
					atualizarLabels(b);

					NumRequisicoes(requisicao);
					return b;
				}

			}

		} else {
			if (tamanhoMemoria - memoriaUsada > requisicao) {
				Bloco bl = new Bloco(requisicao);
				esc = p.escalonador;
				blist.add(bl);
				bl.setProc(p);
				bl.setEspacoUsado(requisicao);
				blistusado.add(bl);
				p.blocoList.add(bl);
				memoriaUsada += bl.getTamanho();
				esc.addBloco(bl);
				atualizarLabels(bl);
				
				NumRequisicoes(requisicao);
				return bl;
			}
		}

		return null;

	}

	@Override
	public void desalocar(Processo p) {
		for (Bloco b : p.blocoList) {
			b.setProc(null);
			b.setEspacoUsado(0);
			blistusado.remove(b);
			blistlivre.add(b);
			atualizarLabels(b);
		}
		p.blocoList.clear();

	}

	public void NumRequisicoes(int tamanho) {
		numRequisisao = numRequisisao + 1;
		AddMap(tamanho);
		if (numRequisisao == qntR) {
			numRequisisao = 0;
			Maiores();
		}
	}

	public synchronized void AddMap(int tamanho) {
		if (listaQF.isEmpty()) {
			listaQF.put(tamanho, 1);
		} else {
			boolean achou = false;
			for (Entry<Integer, Integer> valor : listaQF.entrySet()) {
				if (valor.getKey() == tamanho) {
					valor.setValue(valor.getValue() + 1);
					achou = true;
				}

			}
			if (!achou) {
				listaQF.put(tamanho, 1);
			}
		}

	}

	public void Maiores() {
		esc.removeTam();
		HashMap<Integer, Integer> map = listaQF
				.entrySet()
				.stream()
				.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
				.collect(Collectors.toMap(Map.Entry::getKey,
								Map.Entry::getValue, (e1, e2) -> e1,
								LinkedHashMap::new));
		 
		int[] tamanhos = new int[qntL];
		int i = 0;
		for (Entry<Integer, Integer> valor : map.entrySet()) {
			tamanhos[i] = valor.getKey();
			i++;
			if (i == qntL)
				break;
		}
		maiores.clear();
		for (int j = 0; j < qntL; j++) {
			maiores.put(map.get(tamanhos[j]), new ArrayList<Bloco>());
			esc.addTam(tamanhos[j]);
		}
		for(int k=0; k<blistlivre.size(); k++){
			for(Entry<Integer,ArrayList<Bloco>> m: maiores.entrySet()){
				if(blistlivre.get(k).getTamanho()==m.getKey()){
					m.getValue().add(blistlivre.get(k));
					blistlivre.remove(blistlivre.get(k));
				}
			}
		}
	}

	public void atualizarLabels(Bloco b) {
		if (b.getProc() == null) {
			b.lblPIDValue.setText("livre");
		} else {
			b.lblPIDValue.setText(b.getProc().id + "");
		}
		b.lblEValue.setText(b.getEspacoUsado() + " / " + b.getTamanho());
		b.getPanel().revalidate();
		b.getPanel().repaint();
	}

}