import java.awt.Color;
import java.util.ArrayList;



public class AlgoritmoMF extends GerenciadorDeMemoria {
	
	boolean primeiro=true;
	public AlgoritmoMF(int tM) {
		super(tM);
		
		
	}

	@Override
	public Bloco alocar(Processo p, int requisicao) {
		if (primeiro && super.tamanhoMemoria >= p.getMemoria()) {
			primeiro = false;
			Bloco b = new Bloco(tamanhoMemoria); 
			blist.add(b);
			blistlivre.add(b);
			esc.getpBlocos().add(b.getPanel());
			atualizarLabels(b);
	
		}
		
			
		
		Bloco bloco = null; 
		if (!blistlivre.isEmpty()) {
			for (Bloco b : blistlivre) {
				if (b.getTamanho() >= p.getMemoria()) {
					if (bloco == null) {
						bloco = b;
					} else if (b.getTamanho() < bloco.getTamanho()) {
						bloco = b;
					}
				}
			}
		}	
			if(bloco!=null){
				bloco.setProc(p);
				p.alocado=true;
				bloco.setEspacoUsado(p.getMemoria());
				blistlivre.remove(bloco);
				blistusado.add(bloco);
				p.blocoList.add(bloco);
				memoriaUsada+=p.getMemoria();
				atualizarLabels(bloco);
				split(bloco, p.getMemoria());
				
				return bloco;
			}
			return null;
	}

	private Bloco split(Bloco b, int requisicao) { 
		int aux = b.getTamanho() - requisicao; 
		b.setTamanho(requisicao); 
		Bloco novoBloco = new Bloco(aux);
		 
		if(blist.indexOf(b) + 1 >= blist.size()){ 
			blist.add(novoBloco);
		}else{
			blist.add(blist.indexOf(b) + 1, novoBloco);			
		}
		esc.getpBlocos().add(novoBloco.getPanel(),esc.getpBlocos().getComponentZOrder(b.getPanel())); 
		blistlivre.add(novoBloco);
		atualizarLabels(novoBloco);
		atualizarLabels(b);
		
		return novoBloco;
	}

	private synchronized Bloco merge(Bloco inicial, Bloco anterior) {
		if (blistlivre.contains(inicial) && inicial != null) {

			Bloco esq = getBlocoEsquerda(inicial);
			
			if (esq != null && (esq != anterior || anterior == null) ) { 

				join(merge(esq, inicial),inicial);
			}

			Bloco dir = getBlocoDireita(inicial);
			
			if (dir != null && (dir != anterior || anterior == null)) {

				join( merge(dir, inicial) ,inicial );
			}

			
			
			return inicial;
		}
		return null;
	}

	public synchronized Bloco join(Bloco b1, Bloco b2){

		if (b1 != null && b2 != null) {
			
			
			blistlivre.remove(b1); 
			blist.remove(b1);
			esc.getpBlocos().remove(b1.getPanel());
			esc.getpBlocos().revalidate();
			esc.getpBlocos().repaint();
			
			b2.setTamanho(b1.getTamanho() + b2.getTamanho());
			b2.getPanel().revalidate();
			b2.getPanel().repaint();
			return b2;

		}
		
		
		return null;
	}

	@Override
	public void desalocar(Processo p) {
		for (Bloco b : p.blocoList) {
			b.setProc(null);
			p.alocado=false;
			b.setEspacoUsado(0);
			blistusado.remove(b);
			blistlivre.add(b);
			merge(b, null);
			atualizarLabels(b);
		}
		p.blocoList.clear();
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
	
	public  Bloco getBlocoDireita(Bloco b) {
		int index = blist.indexOf(b);
		if (index + 1 < blist.size()) {
			return blist.get(index + 1);
		}
		return null;
	}

	public  Bloco getBlocoEsquerda(Bloco b) {
		int index = blist.indexOf(b);
		if (index - 1 >= 0) {
			return blist.get(index - 1);
		}
		return null;
	}

}
