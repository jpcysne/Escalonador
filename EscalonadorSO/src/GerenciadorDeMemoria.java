
import java.util.ArrayList;


public abstract class GerenciadorDeMemoria {
	ArrayList<Bloco> blist = new ArrayList<Bloco>();
	Escalonador esc;
	int tamanhoMemoria;
	public abstract void alocar();
	public abstract void desalocar();
	public GerenciadorDeMemoria(int tM){
		tamanhoMemoria=tM;
	}

}
