import java.util.Date;

public class Acao implements Comparable<Acao>{
    String Cod;
    Date Data;
    double Valor;
    double Pm;
    int Quantidade;
    int pontoGraf;

    public Acao(String Cod, double Valor, Date Data, int pontoGraf){
        this.Cod = Cod;
        this.Valor = Valor; 
        this.Data = Data;
        this.pontoGraf = pontoGraf;
    }

    public Acao(String Cod, double Pm, int Quantidade) {
        this.Cod = Cod;
        this.Pm = Pm; 
        this.Quantidade = Quantidade;
    }

    public double getValor() {
        return Valor;
    }

    @Override
    public int compareTo(Acao outraAcao) {
        if(this.Valor < outraAcao.getValor())
            return -1;
        else if(this.Valor > outraAcao.getValor())
            return 1;
        return 0;
    }

}
