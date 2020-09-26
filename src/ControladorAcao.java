import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class ControladorAcao {
	public List<Acao> AcoesUsuario;
    public List<Acao> AcoesHistorico;
    final static Scanner Scan = new Scanner(System.in);
    Interacaotxt ItTxt = new Interacaotxt();

    public ControladorAcao() {
        this.AcoesUsuario = new ArrayList<>();
        this.AcoesHistorico = new ArrayList<>();
    }

    public List<Acao> adicionarAcoesAPessoa(int Matricula ,String Cod, double Pm, int quantidade, List<Acao> AcoesUsuario) throws IOException {
        AcoesUsuario.add(new Acao(Cod, Pm, quantidade));
        ItTxt.adicionaTxt("C:/Users/Administrator/Documents/GitHub/BitCoinA100K.Java/Txt's/PessoaAcao.txt", Matricula + ";" + Cod + ";" + Pm + ";" + quantidade + ";");
        return AcoesUsuario;
    }

    public String adicionarAcoesAHistorico(String Cod, int Valor, String Data) {
        this.AcoesUsuario.add(new Acao(Cod, Valor, Data));
        return Cod;
    }

    public List<Acao> alterarAcoes(String codAcao, double Pm, int Qntd, List<Acao> Acoes) throws IOException, InterruptedException {
        removerAcoes(codAcao, Acoes);
        Acoes.add(new Acao(codAcao, Pm, Qntd));
        return Acoes;
    }

    public List<Acao> removerAcoes(String codAcao, List<Acao> AcoesX) throws IOException, InterruptedException {
        int index = 0;
        for (Acao p : AcoesX){
            if(p.Cod.equals(codAcao)){
                break;
            }
            index ++;
        }
        AcoesX.remove(index);
        return AcoesX;
    }

    public Acao pegarAcao(String codAcao, List<Acao> AcoesX) throws IOException, InterruptedException {
        for (Acao p : AcoesX){
            if(p.Cod.equals(codAcao)){
                return p;
            }
        }
        System.out.println("Ação inexistente, retornando ao menu...");

        return null;
    }
    
    public Acao mostrarAcao(List<Acao> Acoes) throws IOException, InterruptedException {
        FuncoesdeMenu.limparTela();
        System.out.println("|| ============================================ ||");
        System.out.println("|| Cód Ação | Quantidade:     |  Preço Médio:   || ");
        System.out.println("|| ============================================ ||");
        for (Acao p : Acoes){
            int x = p.Cod.length();
            String cod = p.Cod;
            for(int i = 0;i < (7 - x);i++){
                cod = cod + " ";
            }
            String Qunt = String.valueOf(p.Quantidade);
            int z = Qunt.length();
            for(int i = 0;i < (12 - z);i++){
                Qunt = Qunt + " ";
            }

            String Pm = String.valueOf(p.Pm);
            int y = Pm.length();
            for(int i = 0;i < (10 - y);i++){
                Pm = Pm + " ";
            }
            
            System.out.print("|| "+ cod + "  |  " + Qunt + "   |   " + Pm + "    || \n");
        }
        System.out.println("|| ============================================ ||");
        return null;
    }



    public boolean atualizar() throws IOException, InterruptedException {
        String path;
        for (int i = 0; i < 30; i++) {
            Date hoje = new Date(System.currentTimeMillis() - i * 86400000);
            SimpleDateFormat hojeformat = new SimpleDateFormat("ddMMyyyy");
            System.out.println(hojeformat.format(hoje));
            String sHoje = String.valueOf(hojeformat.format(hoje));
            path = "C:/Users/Administrator/Documents/GitHub/BitCoinA100K.Java/Cotações/COTAHIST_D" + hojeformat.format(hoje)
                    + ".txt";
            BufferedReader buffRead = new BufferedReader(new FileReader(path));
            String valordeci = "", acao = "", valor = "", linha = "";
            linha = buffRead.readLine();
            while (true) {
                if (linha != null) {
                    String subLinha = linha.substring(12, 20);
                    valor = linha.substring(63, 68);
                    valordeci = linha.substring(68, 70);
                    if (valor != null && !valor.isEmpty() && !valor.trim().isEmpty()) {
                        valor = valor + "." + valordeci;
                        double value = Double.parseDouble(valor);
                        String sValue = String.valueOf(value);
                        acao = acao + subLinha + ";" + sValue + ";" + sHoje.substring(0, 2) + "/"
                                + sHoje.substring(2, 4) + "/" + sHoje.substring(4, 8) + ";\n";
                    }
                } else
                    break;
                linha = buffRead.readLine();
            }
            buffRead.close();
            gravaArquivo(acao, sHoje);
        }
        return true;
    }

    public boolean gravaArquivo(String acao, String sHoje) throws IOException, InterruptedException {
        FileWriter arq = new FileWriter(
                "C:/Users/Administrator/Documents/GitHub/BitCoinA100K.Java/Cotações/Cotação" + sHoje + ".txt");
        PrintWriter gravarArq = new PrintWriter(arq);
        gravarArq.print(acao);

        arq.close();
        gravarArq.close();

        return true;
    }

    public boolean procuraeSalvaArquivos() throws IOException, InterruptedException, URISyntaxException {
            java.awt.Desktop.getDesktop().browse(new java.net.URI("http://bvmf.bmfbovespa.com.br/InstDados/SerHist/COTAHIST_D21092020.ZIP"));

        return true;
    }
}
