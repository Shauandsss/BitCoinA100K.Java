/*
    Desenvolvido por Shauan Gustavo dos Santos e Henrique Cavalli
    Ciência da Computação 2° Período
    Prof° José Toniazzo
*/
public class App {
    public static void main(final String[] args) throws Exception {
        Interacaotxt ItTxt = new Interacaotxt();


        
/*        ControladorAcao Acoes = new ControladorAcao();
        Acoes.procuraeSalvaArquivos();
        Acoes.atualizar(); */
 
        FuncoesdeMenu.limparTela();
        System.out.println("Bem vindo ao sistema!");
        ItTxt.objetificaçãoTxt("C:/Users/Administrator/Documents/GitHub/BitCoinA100K.Java/Txt's/Pessoa.txt", 1);
        int sair = 0;
        while(sair == 0){
            if(FuncoesdeMenu.Login()){
                while(true){     
                    FuncoesdeMenu.mostrarMenu();
                    int opcao = FuncoesdeMenu.pegarInt();
                    FuncoesdeMenu.limparTela();
                    FuncoesdeMenu.acoes(opcao);
                    if(opcao == 5) break;
                }
            } else {
                System.out.println("Gostaria de realizar uma nova tentativa de login ? 1 - Sim | 2 - Não");
                final int xopcao = FuncoesdeMenu.pegarInt();
                if(xopcao == 2){
                    sair = 1;
                    break;
                } 
                FuncoesdeMenu.limparTela();
            }
        }

    }
}