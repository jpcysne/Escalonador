import java.awt.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.Map.Entry;
import java.util.TreeMap;		
  		  
public class AlgoritmoQF extends GerenciadorDeMemoria {		 


	private ArrayList<Bloco> maisChamados = new ArrayList<Bloco>();
	private ArrayList<Processo> pro = new ArrayList<Processo>();
	private HashMap<Integer, Integer> listaQF = new HashMap<Integer, Integer>();
	private HashMap<ArrayList<Bloco>, Integer> hashQF = new HashMap<ArrayList<Bloco>, Integer>(); 

	private int numRequisisao = 0;
	private int frequenciaProcesso;


	public AlgoritmoQF(int tM) {	
		super(tM);

	}		  	  		  
	@Override		  	
	public Bloco alocar(Processo p, int requisicao) {	

		if(listaQF.containsKey(requisicao)){
			AddMap(requisicao);
		}

		//se tiver blocos livres
		if(blistlivre.isEmpty()){		
			for(Bloco b: blistlivre){		
				if(b.getTamanho() >= requisicao){		
					b.setProc(p);		
					b.setEspacoUsado(requisicao);		
					blistlivre.remove(b);		
					blistusado.add(b);		
					p.blocoList.add(b);		
					atualizarLabels(b);
					maisChamados.add(b);
					NumRequisisoes(requisicao);
					return b;		
				}

			}		

		}else{		
			//se não tiver blocos livres, cria um novo, caso tenha espaço;		
			if(tamanhoMemoria-memoriaUsada>requisicao){		
				Bloco bl = new Bloco(requisicao);		
				esc=p.escalonador;		
				blist.add(bl);		
				bl.setProc(p);		
				bl.setEspacoUsado(requisicao);		
				blistusado.add(bl);		
				p.blocoList.add(bl);		
				memoriaUsada+=bl.getTamanho();		
				esc.addBloco(bl);		
				atualizarLabels(bl);

				NumRequisisoes(requisicao);
				return bl;		
			}		
		}
		return null;
	}

	@Override		  	
	public void desalocar(Processo p) {		  	 		
		for(Bloco b: p.blocoList){		 
			b.setProc(null);		
			b.setEspacoUsado(0);		
			blistusado.remove(b);		
			blistlivre.add(b);		
			atualizarLabels(b);		
		}		
		p.blocoList.clear();		

	}	

	public void NumRequisisoes(int tamanho){
		numRequisisao = numRequisisao + 1;
		AddMap(tamanho);
		if(numRequisisao == 20){
			numRequisisao = 0;
			Maiores();
		}
	}

	public void AddMap(int tamanho){
		if(listaQF.isEmpty()){
			listaQF.put(1,tamanho);
		}else{
			boolean achou=false;
			for(Entry<Integer, Integer> valor : listaQF.entrySet()){
				if(valor.getKey()==tamanho){
					valor.setValue(valor.getValue()+1);
					achou=true;
				}

			}
			if(!achou){
				listaQF.put(1,tamanho);
			}
		}

	}

	public int[] Maiores() {
		int i = 0;
		int[] maiores = new int[3];
		Map<Integer, Integer> map = new TreeMap<Integer, Integer>(listaQF);

		for (Entry<Integer, Integer> valor : map.entrySet()) {
			maiores[i] = valor.getKey();
			i++;
			if (i == 3) {
				break;
			}
		}
		return maiores;
	}

	public void atualizarLabels(Bloco b){		
		if(b.getProc()==null){		
			b.lblPIDValue.setText("livre");		
		}else{		
			b.lblPIDValue.setText(b.getProc().pId + "");		
		}		
		b.lblEValue.setText(b.getEspacoUsado()+" / "+b.getTamanho());		
		b.getPanel().revalidate();		
		b.getPanel().repaint();		
	}


}