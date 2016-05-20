public class AlgoritmoMF extends GerenciadorDeMemoria {
	Bloco superbloco;
	public AlgoritmoMF(int tM) {
		super(tM);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Bloco alocar(Processo p) {
		if (!blist.isEmpty()) {
			for (Bloco b : blist) {
				if (b.getTamanho() == p.getMemoria()) {
					b.setProc(p);
					b.setEspacoUsado(p.getMemoria());
					blistlivre.remove(b);
					blistusado.add(b);
					p.blocoList.add(b);
					atualizarLabels(b);
					return b;
				} else {
					if (b.getEspacoUsado() > p.getMemoria()) {
						Bloco bl = split(b, p.getMemoria());
						b.setProc(p);
						b.setEspacoUsado(p.getMemoria());
						blistusado.add(bl);
						p.blocoList.add(bl);
						atualizarLabels(bl);
						return b;
					}else{
						if(superbloco.getTamanho()>p.getMemoria()){
							
							Bloco bl = split(superbloco, p.getMemoria());
							b.setProc(p);
							b.setEspacoUsado(p.getMemoria());
							blistusado.add(bl);
							p.blocoList.add(bl);
							atualizarLabels(bl);
							return b;
						}
					}
				}
			}
		}
		return null;
	}

	private Bloco split(Bloco b, int requisicao) {
		int aux=b.getTamanho()-requisicao;
		b.setTamanho(aux);
		Bloco bl = new Bloco(requisicao);
		if(blist.indexOf(b)==0){
			blist.add(0,bl);
		}else
		blist.add(blist.indexOf(b)-1,bl);
		
		return bl;
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

	public void atualizarLabels(Bloco b) {
		if (b.getProc() == null) {
			b.lblPIDValue.setText("livre");
		} else {
			b.lblPIDValue.setText(b.getProc().pId - 1 + "");
		}
		b.lblEValue.setText(b.getEspacoUsado() + " / " + b.getTamanho());
		b.getPanel().revalidate();
		b.getPanel().repaint();
	}

}
