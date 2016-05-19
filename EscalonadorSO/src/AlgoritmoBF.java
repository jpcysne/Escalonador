public class AlgoritmoBF extends GerenciadorDeMemoria {

	public AlgoritmoBF(int tM) {
		super(tM);

	}

	@Override
	public Bloco alocar(Processo p) {
		//se tiver blocos livres
		if (!blistlivre.isEmpty()) {
			Bloco melhor = null;
			for (Bloco b : blistlivre) {
				if(b.getTamanho()==p.getMemoria()){
				b.setProc(p);
				b.setEspacoUsado(p.getMemoria());
				blistlivre.remove(b);
				blistusado.add(b);
				p.blocoList.add(b);
				atualizarLabels(b);
				return b;
				}
				if(b.getTamanho() > p.getMemoria()){
					melhor=b;
					break;
				}
			}
			//se chegou aqui nulo, nenhum bloco da lisa de livres cabia o processo;
			if(melhor==null){
				//se tiver espaço na memoria, vai criar um novo bloco;
				if(tamanhoMemoria-memoriaUsada>p.getMemoria()){
					Bloco bl = new Bloco(p.getMemoria());
					esc=p.escalonador;
					blist.add(bl);
					bl.setProc(p);
					bl.setEspacoUsado(p.getMemoria());
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
				if(b.getTamanho()==p.getMemoria()){
					b.setProc(p);
					b.setEspacoUsado(p.getMemoria());
					blistlivre.remove(b);
					blistusado.add(b);
					p.blocoList.add(b);
					atualizarLabels(b);
					return b;
				}
			
				if (melhor.getTamanho()> b.getTamanho()&& b.getTamanho() > p.getMemoria()) {
					melhor = b;
				}
			}
			melhor.setProc(p);
			melhor.setEspacoUsado(p.getMemoria());
			blistlivre.remove(melhor);
			blistusado.add(melhor);
			p.blocoList.add(melhor);
			atualizarLabels(melhor);
			return melhor;
			}
		//se não tiver blocos livres, cria um novo, caso tenha espaço;
		else{
			if(tamanhoMemoria-memoriaUsada>p.getMemoria()){
				Bloco bl = new Bloco(p.getMemoria());
				esc=p.escalonador;
				blist.add(bl);
				bl.setProc(p);
				bl.setEspacoUsado(p.getMemoria());
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
			
		b.lblPIDValue.setText(b.getProc().pId+"");
		}
		b.lblEValue.setText(b.getEspacoUsado()+" / "+b.getTamanho());
		b.getPanel().revalidate();
		b.getPanel().repaint();
		
	}
}
