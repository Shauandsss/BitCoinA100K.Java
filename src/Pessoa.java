import java.util.List;
public class Pessoa implements Comparable<Pessoa>{
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

    public int getMatricula() {
        return matricula;
    }

    @Override
    public int compareTo(Pessoa outraPessoa) {
        if(this.matricula < outraPessoa.getMatricula())
            return -1;
        else if(this.matricula > outraPessoa.getMatricula())
            return 1;
        return 0;
    }
}
