import java.util.ArrayList;

public class AlgoritmoQF extends GerenciadorDeMemoria {

	private ArrayList<Bloco> maisChamados;
	private int numRequisisao = 0;
	
	public AlgoritmoQF(int tM) {
		super(tM);
		
	}

	@Override
	public Bloco alocar(Processo p) {
		//se tiver blocos livres
		if(blistlivre.isEmpty()){
			for(Bloco b: blistlivre){
				if(b.getTamanho() == p.getMemoria()){
					b.setProc(p);
					b.setEspacoUsado(p.getMemoria());
					blistlivre.remove(b);
					blistusado.add(b);
					p.blocoList.add(b);
					atualizarLabels(b);
					return b;
					}
				}
		
			}else{
				//se não tiver blocos livres, cria um novo, caso tenha espaço;
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
