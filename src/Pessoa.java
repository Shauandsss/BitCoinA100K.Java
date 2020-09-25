import java.util.List;
public class Pessoa {
    int matricula;
    String nome;
    long cpf;
    List<Acao> Acoes;

    public Pessoa(int matricula, String nome, long cpf, List<Acao> Acoes){
        this.matricula = matricula;
        this.nome = nome;
        this.cpf = cpf;  
        this.Acoes = Acoes;
    }
}
