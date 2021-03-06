import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;

public class ControladorPessoa {//classe para manipulação de pessoas
    public static List<Pessoa> pessoas;

    public ControladorPessoa() {
        ControladorPessoa.pessoas = new ArrayList<>();
    }

    public int quantidadePessoas() {
        return ControladorPessoa.pessoas.size();
    }

    public int adicionarPessoas(String nome, long cpf) throws IOException { // Nova pessoa
        ArrayList<Acao> Vazio = new ArrayList<>();
        Pessoa p = ControladorPessoa.pessoas.get((ControladorPessoa.pessoas.size()) - 1);
        int matricula = p.matricula + 1;
        ControladorPessoa.pessoas.add(new Pessoa(matricula, nome, cpf, Vazio));

        Interacaotxt.adicionaTxt("C:/Users/Administrator/Documents/GitHub/BitCoinA100K.Java/Txt's/Pessoa.txt",
                matricula + ";" + nome + ";" + cpf + ";", false);//booleano para descobrir se vai limpar o arquivo ou não
        return matricula;
    }

    public static int adicionarPessoas(int matricula, String nome, long cpf) throws IOException { // Objetifica Txt, carregar pro sistema
        ArrayList<Acao> Vazio = new ArrayList<>();   
        pessoas.add(new Pessoa(matricula, nome, cpf, Vazio));
        return matricula;
    }

    public static int adicionarPessoas(int matricula, String nome, long cpf, List<Acao>Acoes) throws IOException { // Alterar Pessoa
        ControladorPessoa.pessoas.add(new Pessoa(matricula, nome, cpf, Acoes));
        Interacaotxt.adicionaTxt("C:/Users/Administrator/Documents/GitHub/BitCoinA100K.Java/Txt's/Pessoa.txt",
                matricula + ";" + nome + ";" + cpf + ";", false);
        return matricula;
    }

    public static boolean removerPessoas(int matricula) throws IOException, InterruptedException {
        int index = 0;
        int matriculaRemover = 0;
        String nome = "";
        long cpf = 0;
        for (Pessoa p : pessoas) {
            if (p.matricula == matricula) {
                matriculaRemover = p.matricula;
                nome = p.nome;
                cpf = p.cpf;
                break;
            }
            index++;
        }
        Interacaotxt.removeTxt("C:/Users/Administrator/Documents/GitHub/BitCoinA100K.Java/Txt's/Pessoa.txt",
                matriculaRemover + ";" + nome + ";" + cpf + ";");
        ControladorPessoa.pessoas.remove(index);
        return true;
    }

    public static int alterarPessoas(int matricula, String nome, long cpf, List<Acao> Acoes) throws IOException, InterruptedException {
        removerPessoas(matricula);
        adicionarPessoas(matricula, nome,cpf, Acoes);
        return matricula;
    }



    public static Pessoa pegarPessoa(int matricula) throws IOException, InterruptedException {
        for (Pessoa p : pessoas){
            if(p.matricula==matricula){
                return p;
            }
        }
        System.out.println("Matrícula inexistente, insira outra");
        System.out.println("1 - Informar outra matrícula \n2 - Retornar ao Menu");
        return null;
    }
    
    public Pessoa pegarLogin(int matricula) throws IOException, InterruptedException {
        for (Pessoa p : pessoas){
            if(p.matricula==matricula){
                return p;
            }
        }
        return null;
    }
    
    public Pessoa mostrarPessoas() throws IOException, InterruptedException {
        FuncoesdeMenu.limparTela();
        Collections.sort(pessoas);
        System.out.println("|| =================================================== ||");
        System.out.println("|| Matrícula  |  Nome:                |  CPF:          ||");
        System.out.println("|| =================================================== ||");
        for (Pessoa p : pessoas){
            int x = p.nome.length();
            String nome = p.nome;
            String aMatricula = String.valueOf(p.matricula);
            int A = aMatricula.length();
            for(int i = 0;i < (2 - A);i++){
                aMatricula = aMatricula + " ";
            }
            for(int i = 0;i < (20 - x);i++){
                nome = nome + " ";
            }
            
            System.out.print("|| "+ aMatricula + "         |  " + nome + " | ");
            String temp = Long.toString(p.cpf);  
            System.out.println(imprimeCPF(temp) + " ||");
        }
        System.out.println("|| =================================================== ||\n");
        return null;
    }

    public static boolean isCPF(String CPF) {
        // considera-se erro CPF's formados por uma sequencia de numeros iguais
        if (CPF.equals("00000000000") || CPF.equals("11111111111") ||
            CPF.equals("22222222222") || CPF.equals("33333333333") ||
            CPF.equals("44444444444") || CPF.equals("55555555555") ||
            CPF.equals("66666666666") || CPF.equals("77777777777") ||
            CPF.equals("88888888888") || CPF.equals("99999999999") ||
            (CPF.length() != 11))
            return(false);
          
        char dig10, dig11;
        int sm, i, r, num, peso;
        try {
        // Calculo do 1o. Digito Verificador
            sm = 0;
            peso = 10;
            for (i=0; i<9; i++) {              
        // converte o i-esimo caractere do CPF em um numero:
        // por exemplo, transforma o caractere '0' no inteiro 0         
        // (48 eh a posicao de '0' na tabela ASCII)         
            num = (int)(CPF.charAt(i) - 48); 
            sm = sm + (num * peso);
            peso = peso - 1;
            }
          
            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else dig10 = (char)(r + 48); // converte no respectivo caractere numerico
          
        // Calculo do 2o. Digito Verificador
            sm = 0;
            peso = 11;
            for(i=0; i<10; i++) {
            num = (int)(CPF.charAt(i) - 48);
            sm = sm + (num * peso);
            peso = peso - 1;
            }
          
            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                 dig11 = '0';
            else dig11 = (char)(r + 48);
          
        // Verifica se os digitos calculados conferem com os digitos informados.
            if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
                 return(true);
            else return(false);
                } catch (InputMismatchException erro) {
                return(false);
            }
        }

    public static String imprimeCPF(String CPF) {
        return(CPF.substring(0, 3) + "." + CPF.substring(3, 6) + "." +
        CPF.substring(6, 9) + "-" + CPF.substring(9, 11));
    }

}



/*
public static int alterarPessoasComAcao(int matricula,String nome,long cpf, List<Acao> Acoes) throws IOException, InterruptedException {
    removerPessoas(matricula);
    adicionarPessoas(matricula, nome, cpf, Acoes);
    return matricula;
}*/