import java.util.Scanner;
import java.io.IOException;

public class FuncoesdeMenu {

    static ControladorPessoa Criador = new ControladorPessoa();
    final static Scanner Scan = new Scanner(System.in);

    public static boolean Login() throws IOException, InterruptedException {
        System.out.print("Para realizar o login insira:\nMatricula: ");
        int matricula = FuncoesdeMenu.pegarInt();
        Pessoa Login = Criador.pegarLogin(matricula);
        if(Login != null){
            System.out.print("Senha(4 primeiros digitos do CPF): ");
            String senhaInput = Scan.nextLine();
            String temp = Long.toString(Login.cpf);    
            String senha = temp.substring(0,4);      
            if(senha.equals(senhaInput)){
                System.out.println("Login efetuado com sucesso! " + Login.nome + " seja bem vindo(a)");
                return true;
            } else {
                System.out.println("Senha Invalída");
                return false;
            }
        } else {
            System.out.println("Matrícula não localizada, insira outra");
            if(Login()){
                return true;
            } else {
                return false;
            }
        }
    }

    public static void mostrarMenu() {
        System.out.println("1 - Inserir");
        System.out.println("2 - Alterar");
        System.out.println("3 - Deletar");
        System.out.println("4 - Consultar");
        System.out.println("5 - Sair");
    }

    public static void limparTela() throws IOException, InterruptedException {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    }

    public static void adicionarVariaspessoas() {
        Criador.adicionarVariaspessoas();
    }

    public static int pegarInt() {
            String n = Scan.nextLine();
            if(n == null){
                return 0;
            } else {
                int i = Integer.parseInt(n);
                return i;
            }
    }

    public static void acoes(int opcao) throws IOException, InterruptedException {
        if (opcao == 5)
            gerarMensagem(1);
        if ((opcao < 1) || (opcao > 5))
            gerarMensagem(2);

        if (opcao == 1) { // Inserir
            System.out.println("Para adicionar nova pessoa insira: ");
            System.out.print("Nome: ");
            String nome = Scan.nextLine();
            limparTela();
            System.out.print("CPF(somente números): ");
            String scpf = Scan.nextLine();
            boolean iscpf = ControladorPessoa.isCPF(scpf);
            if(iscpf){
                long cpf = Long.parseLong(scpf);
                Pessoa p = Criador.pegarPessoa(Criador.adicionarPessoas(nome, cpf));
                limparTela();
                System.out.println("Pessoa inserida!\nMatricula: " + p.matricula + "\nNome: " + p.nome + "\nCPF: " + p.cpf);
                questaoRetorno(1, 1);
            } else {
                gerarMensagem(3);
                questaoRetorno(1, 1);     
            }

        } 

        if (opcao == 2) { // Alterar
            limparTela();
            System.out.println("Para alterar uma pessoa insira matricula: ");
            System.out.print("Matricula:  ");
            String smatricula = Scan.nextLine();
            int matricula;
			Pessoa p = Criador.pegarPessoa((matricula = Integer.parseInt(smatricula)));
            System.out.println("Para alterar o cadastro do(a) "+p.nome+" insira: ");
            System.out.print("Nome: ");  
            String nome = Scan.nextLine();
            limparTela();
            System.out.print("CPF(somente números): ");
            String scpf = Scan.nextLine();
            boolean iscpf = ControladorPessoa.isCPF(scpf);
            if(iscpf){
                long cpf = Long.parseLong(scpf);    
                Criador.pegarPessoa(Criador.alterarPessoas(matricula, nome, cpf));
                questaoRetorno(1, 2);
            } else {
                gerarMensagem(3);
                questaoRetorno(1, 2);               
            }
        } 
        
        if (opcao == 3) { // Deletar
            limparTela();
            System.out.println("Para deletar uma pessoa insira matricula: ");
            System.out.print("Matricula:  ");
            int matricula = FuncoesdeMenu.pegarInt();
            Pessoa R = Criador.pegarPessoa(matricula);
            if(R != null){
                System.out.println("Você tem certeza que deseja remover o(a) "+ R.nome+" ? 1 - Sim | 2 - Não: " );
                int resposta = FuncoesdeMenu.pegarInt();
                if(resposta == 1){
                    Criador.removerPessoas(matricula);
                    System.out.println("Pessoa deletada com sucesso!");
                } else {
                    System.out.println("Pessoa não removida");
                }
            } else {
                int xopcao = FuncoesdeMenu.pegarInt();
                if(xopcao == 1){
                    FuncoesdeMenu.acoes(3);
                    return;
                } else if (xopcao == 2) {
                    questaoRetorno(1, 3);
                    return;
                }
            }
        } 


        if (opcao == 4){ // Consultar
            System.out.println("1 - Consultar individualmente\n2 - Visualizar todos usuarios: ");   
            int yopcao = FuncoesdeMenu.pegarInt();
            if((yopcao != 1) && (yopcao != 2)){
                gerarMensagem(2);
                acoes(4);
            }
            if(yopcao == 1){
                System.out.println("Digite Matricula: ");
                System.out.print("Digite: ");
                String smatricula = Scan.nextLine();
                int matricula = Integer.parseInt(smatricula);  
                limparTela();
                Pessoa p = Criador.pegarPessoa(matricula);
                if(p != null){
                    System.out.println("Matricula: " + p.matricula + "\nNome: " + p.nome + "\nCPF: " + p.cpf);
                    questaoRetorno(1, 4);
                } else {
                    String sopcao = Scan.nextLine();
                    int xopcao = Integer.parseInt(sopcao);
                    if(xopcao == 1){
                        FuncoesdeMenu.acoes(4);
                        return;
                    } else if (xopcao == 2) {
                        return;
                    }
                }
            } else if (yopcao == 2){
                Criador.mostrarPessoas();
                questaoRetorno(1, 4);
            }
        } 

    }

    public static void gerarMensagem(int cod){
        if(cod == 1) System.out.println("Obrigado por usar o nosso sistema! \n");
        if(cod == 2) System.out.println("Opcão invalida, informe novamente! \n");
        if(cod == 3) System.out.println("CPF Inválido! \n");
    }

    public static void questaoRetorno(int cod, int acao) throws IOException, InterruptedException {
    String tipoAcao = "";
        if(acao == 1) tipoAcao = "Inserir";
        if(acao == 2) tipoAcao = "Alterar";
        if(acao == 3) tipoAcao = "Deletar";
        if(acao == 4) tipoAcao = "Consultar";
        
        if(cod == 1){
            System.out.println("\n1 - " + tipoAcao + " outra matrícula\n2 - Retornar ao Menu");
            String sopcao = Scan.nextLine();
            int xopcao = Integer.parseInt(sopcao);
            limparTela();  
            if(xopcao == 1){
                if(acao == 1) acoes(1);
                if(acao == 2) acoes(2);
                if(acao == 3) acoes(3);
                if(acao == 4) acoes(4);
                return;
            }
            if(xopcao == 2){
                return;
            }

        }
        return;
    }

}
