public class Acao {
    String Cod;
    String Data;
    int Valor;
    double Pm;
    int Quantidade;
    public Acao(String Cod, int Valor, String Data){
        this.Cod = Cod;
        this.Valor = Valor; 
        this.Data = Data;
    }
    public Acao(String Cod, double Pm, int Quantidade) {
        this.Cod = Cod;
        this.Pm = Pm; 
        this.Quantidade = Quantidade;
    }
}
