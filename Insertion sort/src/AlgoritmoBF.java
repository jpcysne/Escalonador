public class AlgoritmoBF extends GerenciadorDeMemoria {

	public AlgoritmoBF(int tM) {
		super(tM);

	}

	@Override
	public Bloco alocar(Processo p,int requisicao) {
		//se tiver blocos livres
		if (!blistlivre.isEmpty()) {
			Bloco melhor = null;
			for (Bloco b : blistlivre) {
				if(b.getTamanho()==requisicao){
				b.setProc(p);
				p.alocado=true;
				b.setEspacoUsado(requisicao);
				blistlivre.remove(b);
				blistusado.add(b);
				p.blocoList.add(b);
				atualizarLabels(b);
				return b;
				}
				if(b.getTamanho() > requisicao){
					melhor=b;
					break;
				}
			}
			//se chegou aqui nulo, nenhum bloco da lisa de livres cabia o processo;
			if(melhor==null){
				//se tiver espaço na memoria, vai criar um novo bloco;
				if(tamanhoMemoria-memoriaUsada>requisicao){
					Bloco bl = new Bloco(requisicao);
					esc=p.escalonador;
					blist.add(bl);
					bl.setProc(p);
					p.alocado=true;
					bl.setEspacoUsado(requisicao);
					blistusado.add(bl);
					memoriaUsada+=bl.getTamanho();
					esc.addBloco(bl);
					p.blocoList.add(bl);
					atualizarLabels(bl);
					return bl;
				}
				//senão, não conseguiu alocar o processo;
				else{					
					return null;
				}
			}
			//achou um bloco que cabe, vai procurar se tem um melhor;
			for (Bloco b : blistlivre) {
				if(b.getTamanho()==requisicao){
					b.setProc(p);
					p.alocado=true;
					b.setEspacoUsado(requisicao);
					blistlivre.remove(b);
					blistusado.add(b);
					p.blocoList.add(b);
					atualizarLabels(b);
					return b;
				}
			
				if (melhor.getTamanho()> b.getTamanho()&& b.getTamanho() > requisicao) {
					melhor = b;
				}
			}
			melhor.setProc(p);
			p.alocado=true;
			melhor.setEspacoUsado(requisicao);
			blistlivre.remove(melhor);
			blistusado.add(melhor);
			p.blocoList.add(melhor);
			atualizarLabels(melhor);
			return melhor;
			}
		//se não tiver blocos livres, cria um novo, caso tenha espaço;
		else{
			if(tamanhoMemoria-memoriaUsada>requisicao){
				Bloco bl = new Bloco(requisicao);
				esc=p.escalonador;
				blist.add(bl);
				bl.setProc(p);
				p.alocado=true;
				bl.setEspacoUsado(requisicao);
				blistusado.add(bl);
				p.blocoList.add(bl);
				memoriaUsada+=bl.getTamanho();
				esc.addBloco(bl);
				atualizarLabels(bl);
				return bl;
			}
		}
		//não tinha memoria livre;
		return null;
	}

	public void desalocar(Processo p) {
		//checa na lista de blocos usados todos que o processo é o que vai ser desalocado;
		for(Bloco b: p.blocoList){
				b.setProc(null);
				p.alocado=false;
				b.setEspacoUsado(0);
				blistusado.remove(b);
				blistlivre.add(b);
				atualizarLabels(b);
		}
		p.blocoList.clear();
	}
	public void atualizarLabels(Bloco b){
		if(b.getProc()==null){
			b.lblPIDValue.setText("livre");
		}else{
		b.lblPIDValue.setText(b.getProc().id+"");
		}
		b.lblEValue.setText(b.getEspacoUsado()+" / "+b.getTamanho());
		b.getPanel().revalidate();
		b.getPanel().repaint();
	}
}
