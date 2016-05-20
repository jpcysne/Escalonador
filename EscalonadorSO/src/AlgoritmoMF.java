import java.awt.Color;
import java.util.ArrayList;



public class AlgoritmoMF extends GerenciadorDeMemoria {
	
	boolean primeiro=true;
	public AlgoritmoMF(int tM) {
		super(tM);
		
		
	}

	@Override
	public Bloco alocar(Processo p) {
		
		if (primeiro && super.tamanhoMemoria >= p.getMemoria()) {
			primeiro = false;
			Bloco b = new Bloco(tamanhoMemoria); //SUPER BLOC!!!
			blist.add(b);
			blistlivre.add(b);
			esc.getpBlocos().add(b.getPanel());
			atualizarLabels(b);
	
		}
		
			
		//se tiver blocos livres
		//System.out.println( p.getId()+" ->"+p.getMemoria());
		Bloco melhor = null; // O BLOCO LIVRE VAI SER O PRIMEIRO!!! O SUPER BLOCO!
		if (!blistlivre.isEmpty()) {
			for (Bloco b : blistlivre) {
				if (b.getTamanho() >= p.getMemoria()) {
					if (melhor == null) {
						melhor = b;
					} else if (b.getTamanho() < melhor.getTamanho()) {
						melhor = b;
					}
				}
			}
		}	
			if(melhor!=null){
				melhor.setProc(p);
				melhor.setEspacoUsado(p.getMemoria());
				blistlivre.remove(melhor);
				blistusado.add(melhor);
				p.blocoList.add(melhor);
				memoriaUsada+=p.getMemoria();
				atualizarLabels(melhor);
				System.out.println(p.getId()+" Adicionado"); // VOU ADD O PROCESSO NELE NORMALMENTE, MAS EU VOU CRIAR UM BLOCO 
				//NOVO COM A FOLGA DELE
				//COM A FRAGMENTACAO DELE
				split(melhor, p.getMemoria());
				
				return melhor;
			}
			/*
			else{
				
				//se chegou aqui nulo, nenhum bloco da lisa de livres cabia o processo;
				//se tiver espaço na memoria, vai criar um novo bloco;
				
				if(tamanhoMemoria-memoriaUsada>p.getMemoria()){
					Bloco bl = new Bloco(p.getMemoria());
					esc=p.escalonador;
					blist.add(bl);
					bl.setProc(p);
					bl.setEspacoUsado(p.getMemoria());
					blistusado.add(bl);
					esc.addBloco(bl);
					p.blocoList.add(bl);
					memoriaUsada+=p.getMemoria();
					atualizarLabels(bl);
					System.out.println(p.getId()+" Adicionado");
					return bl;
				}else{ //senão, não conseguiu alocar o processo;					
					return null;
				}
			}
			*/
			return null;
	}

	private Bloco split(Bloco b, int requisicao) { //LOL
		int aux = b.getTamanho() - requisicao; //folga
		b.setTamanho(requisicao); // voce ja errou aqui neh? voce setou o tamanho do bloco que vai ser divido pela folga?
		//tamanho desse bloco é a requisição
		//A folga vai ser o novo bloco, folga é o que sobra
		//
		Bloco novoBloco = new Bloco(aux);
		
		//ORGANIZAR na lista 
		if(blist.indexOf(b) + 1 >= blist.size()){ //CASO o elemento que vai tomar um split seja o utimo, dai eu add no final
			blist.add(novoBloco);
		}else{
			blist.add(blist.indexOf(b) + 1, novoBloco);			 //se nao eu add no lado direito dele
		}
		
		//organizar na interface grafica
		esc.getpBlocos().add(novoBloco.getPanel(),esc.getpBlocos().getComponentZOrder(b.getPanel())); //ISSO AQUI VAI ADD o BLOCO PANEL
		//DO lado do bloco que foi separado 
		
		blistlivre.add(novoBloco);
		atualizarLabels(novoBloco);
		atualizarLabels(b);
		
		return novoBloco;
	}
	

	private synchronized Bloco merge(Bloco inicial, Bloco anterior) {
		System.out.println("AAAAAAAAA");
		if (blistlivre.contains(inicial) && inicial != null) {

			Bloco esq = getBlocoEsquerda(inicial);
			
			if (esq != null && (esq != anterior || anterior == null) ) { // se anterio é null é pq é o primeiro da arvore 

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

		//dir inicial
	public synchronized Bloco join(Bloco b1, Bloco b2){

		if (b1 != null && b2 != null) {
			
			
			System.out.println("JOIN " + b1.lblPIDValue.getText() + " + " + b2.lblPIDValue.getText());
			blistlivre.remove(b1); 
			super.blist.remove(b1);
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
	
	private void merge(Bloco b) {
		int total=0;
		if (blist.lastIndexOf(b) + 1 < blist.size()) {
			if(blist.get(blist.indexOf(b)+1).getProc()==null){
				total+=blist.get(blist.indexOf(b)+1).getTamanho();
				blistlivre.remove(blist.get(blist.indexOf(b)+1));
				blist.remove(blist.get(blist.indexOf(b)+1));
			}
			if (blistlivre.lastIndexOf(b) + 1 < blistlivre.size())
			merge(blistlivre.get(blistlivre.indexOf(b)+1));
		}
		if (blist.indexOf(b) != 0) {
			if(blist.get(blist.indexOf(b)-1).getProc()==null){
				total+=blist.get(blist.indexOf(b)-1).getTamanho();
				blistlivre.remove(blist.get(blist.indexOf(b)-1));
				blist.remove(blist.get(blist.indexOf(b)-1));
			}
			if(blistlivre.indexOf(b)!=0)
			merge(blistlivre.get(blistlivre.indexOf(b)-1));
		}
		b.setTamanho(total+b.getTamanho());
		atualizarLabels(b);
	}

	@Override
	public void desalocar(Processo p) {
		for (Bloco b : p.blocoList) {
			b.setProc(null);
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
