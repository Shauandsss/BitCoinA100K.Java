import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;

public class FuncoesdeMenu {

    static ControladorPessoa Criador = new ControladorPessoa();
    static ControladorAcao AcoesP = new ControladorAcao();
    final static Scanner Scan = new Scanner(System.in);
    final static Scanner ScanInt = new Scanner(System.in);
    static int MatriculaLogada = 0;

    public static boolean Login() throws IOException, InterruptedException {
        System.out.print("Para realizar o login insira:\nMatricula: ");
        int matricula = FuncoesdeMenu.pegarInt();
        Pessoa Login = Criador.pegarLogin(matricula);
        if (Login != null) {
            System.out.print("Senha(4 primeiros digitos do CPF): ");
            String senhaInput = Scan.nextLine();
            String temp = Long.toString(Login.cpf);
            String senha = temp.substring(0, 4);
            if (senha.equals(senhaInput)) {
                System.out.println("Login efetuado com sucesso! " + Login.nome + " seja bem vindo(a)");
                MatriculaLogada = matricula;
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
        String digitado = "";
        digitado = Scan.nextLine();
        return Integer.parseInt(digitado);
            
    }
    public static double pegarDouble() {
        String digitado = "";
        digitado = Scan.nextLine();
        return Integer.parseInt(digitado);
    }

    public static void acoes(int opcao) throws IOException, InterruptedException {
        
        if (opcao == 5)
            gerarMensagem(1);
        if ((opcao < 1) || (opcao > 5))
            gerarMensagem(2);

        if (opcao == 1) { // Inserir
            System.out.println("Deseja adicionar uma pessoa ou uma ação ? \n1 - Pessoa\n2 - Ação");
            int segopc = pegarInt();
                if((segopc < 1) || (segopc > 2)){
                    gerarMensagem(2);
                    acoes(1);
                }
                if(segopc == 1){
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
                } else {
                    FuncoesdeMenu.limparTela();
                    System.out.println("Para adicionar nova ação insira: ");
                    System.out.print("Código: ");
                    String CodAcao = Scan.nextLine();
                    // criar validador
                    System.out.print("Preço médio: ");
                    double Pm = pegarDouble();
                    System.out.print("Quantidade: ");
                    int Qtnd = pegarInt();
                    Pessoa X = Criador.pegarPessoa(MatriculaLogada);
                    Criador.alterarPessoasComAcao(X.matricula, X.nome, X.cpf,AcoesP.adicionarAcoesAPessoa(CodAcao, Pm, Qtnd, X.Acoes));
                    System.out.println(" " + X.nome + ", " + Qtnd + " cotas de " + CodAcao + " foram adicionadas a sua carteira com preço médio de: " + Pm + "\n "
                    + " Deseja adicionar mais ações a sua carteira ? 1 - Sim | 2 - Não ");

                    int Opc = pegarInt();
                    if((Opc < 1) || (Opc > 2)){
                        System.out.println("Opcão invalida, retornando ao menu! \n");
                        return; 
                    }
                    if(Opc == 1){
                        acoes(1);
                    }
                    else
                        return;
 
                }
        } 

        if (opcao == 2) { // Alterar
            limparTela();
            System.out.println("Deseja alterar uma pessoa ou uma ação ? \n1 - Pessoa\n2 - Ação");
            int segopc = FuncoesdeMenu.pegarInt();
            if((segopc < 1) || (segopc > 2)){
                gerarMensagem(2);
                acoes(2);
            }
            if(segopc == 1){
                System.out.println("Para alterar uma pessoa insira matricula: ");
                System.out.print("Matricula:  ");
                String smatricula = Scan.nextLine();
                int matricula = pegarInt();
                Pessoa p = Criador.pegarPessoa(matricula);
                System.out.println("Para alterar o cadastro do(a) " + p.nome + " insira: ");
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
            } else {
                List<Acao> AcaoTemp;
                AcaoTemp = new ArrayList<>();
                FuncoesdeMenu.limparTela();
                System.out.println("Para alterar uma ação insira: ");
                System.out.print("Código: ");
                String CodAcao = Scan.nextLine();
                // criar validador
                System.out.print("Preço médio: ");
                double Pm = pegarDouble();
                System.out.print("Quantidade: ");
                int Qntd = pegarInt();
                Pessoa X = Criador.pegarPessoa(MatriculaLogada);
                AcaoTemp = AcoesP.alterarAcoes(CodAcao, Pm, Qntd, X.Acoes);
                Criador.alterarPessoasComAcao(X.matricula,X.nome,X.cpf, AcaoTemp);
                System.out.println(" " + X.nome + ", " + Qntd + " cotas de " + CodAcao + " foram alteradas na sua carteira \n " +
                " com preço médio de: " + Pm + "\n"
                + " Deseja alterar mais ações a sua carteira ? 1 - Sim | 2 - Não ");
                int Opc = pegarInt();
                if((Opc < 1) || (Opc > 2)){
                    System.out.println("Opcão invalida, retornando ao menu! \n");
                    return; 
                }
                if(Opc == 1)
                    acoes(2);
                else
                    return;
            }   
        } 
        
        if (opcao == 3) { // Deletar
            limparTela();
            System.out.println("Deseja deletar uma pessoa ou uma ação ? \n1 - Pessoa\n2 - Ação");
            int segopc = FuncoesdeMenu.pegarInt();
            if((segopc < 1) || (segopc > 2)){
                gerarMensagem(2);
                acoes(3);
            }
            if(segopc == 1){
                System.out.println("Para deletar uma pessoa insira matricula: ");
                System.out.print("Matricula:  ");
                int matricula = pegarInt();
                Pessoa R = Criador.pegarPessoa(matricula);
                if(R != null){
                    System.out.println("Você tem certeza que deseja remover o(a) "+ R.nome+" ? 1 - Sim | 2 - Não: " );
                    int resposta = pegarInt();
                    if(resposta == 1){
                        Criador.removerPessoas(matricula);
                        System.out.println("Pessoa deletada com sucesso!");
                    } else {
                        System.out.println("Pessoa não removida");
                    }
                } else {
                    int xopcao = pegarInt();
                    if(xopcao == 1){
                        FuncoesdeMenu.acoes(3);
                        return;
                    } else if (xopcao == 2) {
                        questaoRetorno(1, 3);
                        return;
                    }
                }
            } else {
                FuncoesdeMenu.limparTela();
                System.out.println("Para deletar uma ação insira: ");
                System.out.print("Código: ");
                String CodAcao = Scan.nextLine();
                // criar validador
                Pessoa X = Criador.pegarPessoa(MatriculaLogada);
                Acao Z = AcoesP.pegarAcao(CodAcao, X.Acoes);
                Criador.alterarPessoasComAcao(X.matricula, X.nome, X.cpf,AcoesP.removerAcoes(CodAcao, X.Acoes));
                System.out.println(" " + X.nome + ", " + Z.Quantidade + " cotas de " + Z.Cod + " foram excluídas na sua carteira com preço médio de: " + Z.Pm + "\n "
                + " Deseja excluir mais ações a sua carteira ? 1 - Sim | 2 - Não ");
                int Opc = pegarInt();
                if((Opc < 1) || (Opc > 2)){
                    System.out.println("Opcão invalida, retornando ao menu! \n");
                    return; 
                }
                if(Opc == 1)
                    acoes(3);
                else
                    return;
            } 
        } 

        if (opcao == 4){ // Consultar
            System.out.println("Deseja consultar uma pessoa ou uma ação ? \n1 - Pessoa\n2 - Ação");
            int segopc = FuncoesdeMenu.pegarInt();
            if((segopc < 1) || (segopc > 2)){
                gerarMensagem(2);
                acoes(4);
            }
            if(segopc == 1){
                System.out.println("1 - Consultar individualmente\n2 - Visualizar todos usuarios: ");   
                int yopcao = FuncoesdeMenu.pegarInt();
                if((yopcao != 1) && (yopcao != 2)){
                    gerarMensagem(2);
                    acoes(4);
                }
                if(yopcao == 1){
                    System.out.println("Digite Matricula: ");
                    System.out.print("Digite: ");
                    int matricula = pegarInt();  
                    limparTela();
                    Pessoa p = Criador.pegarPessoa(matricula);
                    if(p != null){
                        System.out.println("Matricula: " + p.matricula + "\nNome: " + p.nome + "\nCPF: " + p.cpf);
                        questaoRetorno(1, 4);
                    } else {
                        int xopcao = pegarInt();
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
            } else {
                FuncoesdeMenu.limparTela();
                System.out.println("1 - Consultar individualmente\n2 - Visualizar todos as ações: ");
                int teropc = FuncoesdeMenu.pegarInt();
                if((teropc < 1) || (teropc > 2)){
                    gerarMensagem(2);
                    acoes(4);
                }
                if(teropc == 1){
                    System.out.println("Para consultar uma ação insira: ");
                    System.out.print("Código: ");
                    String CodAcao = Scan.nextLine();
                    // criar validador
                    Pessoa X = Criador.pegarPessoa(MatriculaLogada);
                    Acao Z = AcoesP.pegarAcao(CodAcao, X.Acoes);
                    System.out.println(" " + X.nome + ", " + Z.Quantidade + " cotas de " + Z.Cod + " foram excluídas na sua carteira com preço médio de: " + Z.Pm + "\n _"
                    + " Deseja excluir mais ações a sua carteira ? 1 - Sim | 2 - Não ");
                    int Opc = pegarInt();
                    if((Opc < 1) || (Opc > 2)){
                        System.out.println("Opcão invalida, retornando ao menu! \n");
                        return; 
                    }
                    if(Opc == 1)
                        acoes(4);
                    else
                        return;
                } else {
                    Pessoa X = Criador.pegarPessoa(MatriculaLogada);
                    AcoesP.mostrarPessoas(X.Acoes);
                }      
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
