import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;

public class ControladorAcao {
	public static List<Acao> AcoesUsuario;
    public static List<Acao> AcoesHistorico;
    final static Scanner Scan = new Scanner(System.in);
    Interacaotxt ItTxt = new Interacaotxt();
    static DecimalFormat df = new DecimalFormat("0.00");

    public ControladorAcao() {
        AcoesUsuario = new ArrayList<>();
        AcoesHistorico = new ArrayList<>();
    }

    public static List<Acao> adicionarAcoesAPessoa(final int Matricula, final String Cod, final double Pm,
            final int quantidade, final List<Acao> AcoesUsuario) throws IOException {
        AcoesUsuario.add(new Acao(Cod, Pm, quantidade));
        Interacaotxt.adicionaTxt("C:/Users/Administrator/Documents/GitHub/BitCoinA100K.Java/Txt's/PessoaAcao.txt",
                Matricula + ";" + Cod + ";" + Pm + ";" + quantidade + ";", false);
        return AcoesUsuario;
    }

    public static List<Acao> adicionarAcoesAPessoa(final int Matricula, final String Cod, final double Pm,
            final int quantidade) throws IOException, InterruptedException {
        AcoesUsuario.add(new Acao(Cod, Pm, quantidade));
        return AcoesUsuario;
    }

    public static String adicionarAcoesAHistorico(final String Cod, final double Valor, final Date Data)
            throws IOException {
        AcoesHistorico.add(new Acao(Cod, Valor, Data, 0));
        return Cod;
    }

    public List<Acao> alterarAcoes(final int Matricula, final String codAcao, final double Pm, final int Qntd,
            final List<Acao> Acoes) throws IOException, InterruptedException {
        removerAcoes(Matricula, codAcao, Acoes);
        adicionarAcoesAPessoa(Matricula, codAcao, Pm, Qntd, Acoes);
        return Acoes;
    }

    public static List<Acao> removerAcoes(final int Matricula, final String codAcao, final List<Acao> AcoesX)
            throws IOException, InterruptedException {
        int index = 0;
        for (final Acao p : AcoesX) {
            if (p.Cod.equals(codAcao)) {
                Interacaotxt.removeTxt("C:/Users/Administrator/Documents/GitHub/BitCoinA100K.Java/Txt's/PessoaAcao.txt",
                        Matricula + ";" + p.Cod + ";" + p.Pm + ";" + p.Quantidade + ";");
                break;
            }
            index++;
        }
        AcoesX.remove(index);
        return AcoesX;
    }

    public Acao pegarAcao(final String codAcao, final List<Acao> AcoesX) throws IOException, InterruptedException {
        for (final Acao p : AcoesX) {
            if (p.Cod.equals(codAcao)) {
                return p;
            }
        }
        System.out.println("Ação inexistente, retornando ao menu...");

        return null;
    }
    public List pegarAcaoHistorico(final String codAcao) throws IOException, InterruptedException {
        int vFlag = 0;
        final List acoesGrafico = new ArrayList<>();
        for (final Acao p : AcoesHistorico) {
            if (p.Cod.equals(codAcao)) {
                acoesGrafico.add(p);
                vFlag = 1;
            }
        }
        if (vFlag == 1)
            return acoesGrafico;
        else
            System.out.println("Ação inexistente, retornando ao menu...");
            return null;

        
    }

    public static boolean somarPm(final int Matricula, final String codAcao, final List<Acao> AcoesX)
            throws IOException, InterruptedException {
        final List<Acao> AcoesRemover = new ArrayList<>();
        int quantidade = 0;
        double Pm = 0;
        for (final Acao p : AcoesX) {
            if (p.Cod.equals(codAcao)) {
                AcoesRemover.add(new Acao(codAcao, p.Pm, p.Quantidade));
            }
        }

        for (final Acao p : AcoesRemover) {
            Interacaotxt.removeTxt("C:/Users/Administrator/Documents/GitHub/BitCoinA100K.Java/Txt's/PessoaAcao.txt",
                    Matricula + ";" + p.Cod + ";" + p.Pm + ";" + p.Quantidade + ";");
            quantidade = quantidade + p.Quantidade;
            Pm = Pm + p.Pm;
            removerAcoes(Matricula, codAcao, AcoesX);
        }
        final String PmDf = df.format(Pm / AcoesRemover.size());
        Pm = Double.parseDouble(PmDf);
        Interacaotxt.adicionaTxt("C:/Users/Administrator/Documents/GitHub/BitCoinA100K.Java/Txt's/PessoaAcao.txt",
                Matricula + ";" + codAcao + ";" + Pm + ";" + quantidade + ";", false);
        adicionarAcoesAPessoa(Matricula, codAcao, Pm, quantidade);
        return true;
    }

    public static Acao mostrarAcao(final List<Acao> Acoes) throws IOException, InterruptedException {
        FuncoesdeMenu.limparTela();
        System.out.println("|| ============================================ ||");
        System.out.println("|| Cód Ação | Quantidade:     |  Preço Médio:   || ");
        System.out.println("|| ============================================ ||");
        for (final Acao p : Acoes) {
            final int x = p.Cod.length();
            String cod = p.Cod;
            for (int i = 0; i < (7 - x); i++) {
                cod = cod + " ";
            }
            String Qunt = String.valueOf(p.Quantidade);
            final int z = Qunt.length();
            for (int i = 0; i < (12 - z); i++) {
                Qunt = Qunt + " ";
            }

            String Pm = String.valueOf(p.Pm);
            final int y = Pm.length();
            for (int i = 0; i < (10 - y); i++) {
                Pm = Pm + " ";
            }

            System.out.print("|| " + cod + "  |  " + Qunt + "   |   " + Pm + "    || \n");
        }
        System.out.println("|| ============================================ ||\n");
        return null;
    }

    public static Acao mostrarValorizacao(final String codAcao) throws IOException, InterruptedException {
        final SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        List <Acao> AcoesHistoricoValorizacao = new ArrayList<>();
        FuncoesdeMenu.limparTela();
        String Conteudo = "";
        System.out.println("|| ============================================ ||");
        System.out.println("|| Cód Ação  | Dia:          |  Preço Médio:    ||");
        System.out.println("|| ============================================ ||");
        for (final Acao p : AcoesHistorico) {
            if (p.Cod.trim().equals(codAcao)) {
                AcoesHistoricoValorizacao.add(p);
                final int x = p.Cod.length();
                String cod = p.Cod;
                for (int i = 0; i < (7 - x); i++) {
                    cod = cod + " ";
                }

                String Data = String.valueOf(formato.format(p.Data));
                final int z = Data.length();
                for (int i = 0; i < (9 - z); i++) {
                    Data = Data + " ";
                }

                String Pm = String.valueOf(p.Valor);
                final int y = Pm.length();
                for (int i = 0; i < (10 - y); i++) {
                    Pm = Pm + " ";
                }

                Conteudo = Conteudo + "|| " + cod + "  |  " + Data + "   |  R$ " + Pm + "   || \n";
            }
        }
        System.out.print(Conteudo);
        System.out.println("|| ============================================ ||\n");

        final Acao acaoMax = (Acao) Collections.max(AcoesHistoricoValorizacao);
        final Acao acaoMin = (Acao) Collections.min(AcoesHistoricoValorizacao);

        final double AlturaMax = acaoMax.Valor * 1.10;
        final double AlturaMin = acaoMin.Valor;
        System.out.println(AlturaMin + " --------------");
        final int vetor[][] = new int[18][100];
        int pontoGraf = 0;
        double Multiplo = 0;
        for (double i = 0; pontoGraf  <= 16; i = i + 0.10){
            pontoGraf = (int) ((acaoMax.Valor - AlturaMin) * ((AlturaMax * (((AlturaMax*i)-AlturaMin)/18)) / AlturaMin));
            Multiplo = i;
        }    

        for (final Acao p : AcoesHistoricoValorizacao) {
            if (p.Cod.trim().equals(codAcao)) {
                    pontoGraf = (int) ((p.Valor - AlturaMin) * ((AlturaMax * (((AlturaMax * Multiplo)-AlturaMin)/18)) / AlturaMin));
                    p.pontoGraf = pontoGraf;
                    //System.out.println("((" + p.Valor + " - " + AlturaMin + ") * ((" + AlturaMax + "*(((" + AlturaMax + "*" + Multiplo +")-" + AlturaMin + ")/17))/" + AlturaMin); 
                    System.out.println(formato.format(p.Data) + "-" + pontoGraf);   
            }    
        }

        for (int y = 0; y < 17 ; y++){ 
            for (int x = 0; x < 100 ; x++){ 
                vetor[y][x] = 4;
            }    
            System.out.print("\n");
        }
        int coluna = 0, direcao = 0;
        int conteudo[] = new int [6];
        for (int j = 0;j < AcoesHistoricoValorizacao.size() - 1; j++) {
            if(AcoesHistoricoValorizacao.get(j).getpontoGraf() > AcoesHistoricoValorizacao.get(j + 1).getpontoGraf()){
                direcao = 1;
                conteudo[0] = 0;
                conteudo[1] = 0;
                conteudo[2] = 0;
                conteudo[3] = 0;
                conteudo[4] = 0;
                conteudo[5] = 0;
                if (AcoesHistoricoValorizacao.get(j).getpontoGraf() < 16){
                    conteudo[1] = 1;
                    conteudo[2] = 1;
                    conteudo[3] = 1;
                    conteudo[4] = 1;
                    conteudo[5] = 1;
                }
                if (AcoesHistoricoValorizacao.get(j).getpontoGraf() < 15){
                    conteudo[2] = 2;
                    conteudo[3] = 2;
                    conteudo[4] = 2;
                    conteudo[5] = 2;
                }
                if (AcoesHistoricoValorizacao.get(j).getpontoGraf() < 14){
                    conteudo[3] = 3;
                    conteudo[4] = 3;
                    conteudo[5] = 3;
                }
                if (AcoesHistoricoValorizacao.get(j).getpontoGraf() < 13){
                    conteudo[4] = 4;
                    conteudo[5] = 4;          
                }
                if (AcoesHistoricoValorizacao.get(j).getpontoGraf() < 12)
                    conteudo[5] = 5;
                    
            } else if (AcoesHistoricoValorizacao.get(j).getpontoGraf() < AcoesHistoricoValorizacao.get(j + 1).getpontoGraf()){
                direcao = -1;
                direcao = 1;
                conteudo[0] = 0;
                conteudo[1] = 0;
                conteudo[2] = 0;
                conteudo[3] = 0;
                conteudo[4] = 0;
                conteudo[5] = 0;
                if (AcoesHistoricoValorizacao.get(j).getpontoGraf() > 1){
                    conteudo[1] = -1;
                    conteudo[2] = -1;
                    conteudo[3] = -1;
                    conteudo[4] = -1;
                    conteudo[5] = -1;
                }
                if (AcoesHistoricoValorizacao.get(j).getpontoGraf() > 2){
                    conteudo[2] = -2;
                    conteudo[3] = -2;
                    conteudo[4] = -2;
                    conteudo[5] = -2;
                }
                if (AcoesHistoricoValorizacao.get(j).getpontoGraf() > 3){
                    conteudo[3] = -3;
                    conteudo[4] = -3;
                    conteudo[5] = -3;
                }
                if (AcoesHistoricoValorizacao.get(j).getpontoGraf() > 4){
                    conteudo[4] = -4;
                    conteudo[5] = -4;          
                }
                if (AcoesHistoricoValorizacao.get(j).getpontoGraf() > 5)
                    conteudo[5] = -5;
            } else { 
                direcao = 0;
            }
            System.out.println(coluna + " --- " + AcoesHistoricoValorizacao.get(j).getpontoGraf());
            vetor[AcoesHistoricoValorizacao.get(j).getpontoGraf() + conteudo[5]][coluna] = direcao;   
            vetor[AcoesHistoricoValorizacao.get(j).getpontoGraf() + conteudo[4]][coluna + 1] = direcao;
            vetor[AcoesHistoricoValorizacao.get(j).getpontoGraf() + conteudo[3]][coluna + 2] = direcao;
            
            
            vetor[AcoesHistoricoValorizacao.get(j).getpontoGraf() + conteudo[2]][coluna + 3] = direcao;
            vetor[AcoesHistoricoValorizacao.get(j).getpontoGraf() + conteudo[1]][coluna + 4] = direcao;
            vetor[AcoesHistoricoValorizacao.get(j).getpontoGraf() + conteudo[0]][coluna + 5] = direcao;
            coluna = coluna + 6;
            
        }
        
        for (int y = 0; y < 17 ; y++){ 
            for (int x = 0; x < 100 ; x++){ 
                if(vetor[y][x] == -1)
                    System.out.print("\\");
                else if(vetor[y][x] == 1)
                    System.out.print("/");
                else if(vetor[y][x] == 0)
                    System.out.print("-");
                else
                    System.out.print(" ");
                }    
            System.out.print("\n");
        }

        

        return null;
    }
   

    public static boolean isAcao(final String codAcao) {
        final File file = new File(
                "C:/Users/Administrator/Documents/GitHub/BitCoinA100K.Java/Txt's/CotacaoHistorica.txt");
        try {
            final FileReader reader = new FileReader(file);
            final BufferedReader input = new BufferedReader(reader);
            String linha;
            while ((linha = input.readLine()) != null) {
                if (!linha.equals("")) {
                    final int x = linha.indexOf(";");
                    final String cod = linha.substring(0, x);
                    if (cod.trim().equals(codAcao)) {
                        return true;
                    }
                }
            }
            input.close();
        } catch (final IOException ioe) {
            System.out.println(ioe);
        }

        return false;
    }

    public boolean atualizar() throws IOException, InterruptedException {
        String path;
        for (int i = 0; i < 30; i++) {
            final Date hoje = new Date(System.currentTimeMillis() - i * 86400000);
            final SimpleDateFormat hojeformat = new SimpleDateFormat("ddMMyyyy");
            System.out.println(hojeformat.format(hoje));
            final String sHoje = String.valueOf(hojeformat.format(hoje));
            path = "C:/Users/Administrator/Documents/GitHub/BitCoinA100K.Java/Txt's/ZIP/COTAHIST_D"
                    + hojeformat.format(hoje) + ".txt";
            final BufferedReader buffRead = new BufferedReader(new FileReader(path));
            String valordeci = "", acao = "", valor = "", linha = "";
            linha = buffRead.readLine();
            while (true) {
                if (linha != null) {
                    final String subLinha = linha.substring(12, 20);
                    valor = linha.substring(63, 67);
                    valordeci = linha.substring(67, 69);
                    if (valor != null && !valor.isEmpty() && !valor.trim().isEmpty()) {
                        valor = valor + "." + valordeci;
                        final double value = Double.parseDouble(valor);
                        final String sValue = String.valueOf(value);
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

    public boolean gravaArquivo(final String acao, final String sHoje) throws IOException, InterruptedException {
        final FileWriter arq = new FileWriter(
                "C:/Users/Administrator/Documents/GitHub/BitCoinA100K.Java/Txt's/ZIP/" + sHoje + ".txt");
        final PrintWriter gravarArq = new PrintWriter(arq);
        gravarArq.print(acao);

        arq.close();
        gravarArq.close();

        return true;
    }

    public boolean procuraeSalvaArquivos() throws IOException, InterruptedException, URISyntaxException {
            java.awt.Desktop.getDesktop().browse(new java.net.URI("http://bvmf.bmfbovespa.com.br/InstDados/SerHist/COTAHIST_D21092020.ZIP"));

        return true;
    }

    public void montaGrafico(){




    }
}
