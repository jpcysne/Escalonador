
import java.util.ArrayList;


public abstract class GerenciadorDeMemoria {
	ArrayList<Bloco> blist = new ArrayList<Bloco>();
	
	ArrayList<Bloco> blistlivre = new ArrayList<Bloco>();
	ArrayList<Bloco> blistusado = new ArrayList<Bloco>();
	Escalonador esc;
	int tamanhoMemoria, memoriaUsada;
	public abstract Bloco alocar(Processo p);
	public abstract void desalocar(Processo p);
	public GerenciadorDeMemoria(int tM){
		tamanhoMemoria=tM;
	}

}
