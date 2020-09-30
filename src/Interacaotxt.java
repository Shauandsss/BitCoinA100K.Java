import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Interacaotxt {
    String Diretorio;
    String Conteudo;
    String NomeArq;
    List<Acao> Acoes;
    ControladorPessoa Pessoa = new ControladorPessoa();

    public static boolean gravaTxt(final String Diretorio, final String Conteudo) throws IOException {
        final FileWriter arq = new FileWriter(Diretorio);
        final PrintWriter gravarArq = new PrintWriter(arq);
        try {
            gravarArq.print(Conteudo);
            arq.close();
            gravarArq.close();
        } catch (final IOException ioe) {
            // except error return false
            return false;
        }
        return true;
    }

    public static String alteraTxt(final String Diretorio, final String linhaAntiga, final String linhaNova)
            throws IOException {
        String conteudo = "";
        final File file = new File(Diretorio);
        try {
            final FileReader reader = new FileReader(file);
            final BufferedReader input = new BufferedReader(reader);
            String linha;
            while ((linha = input.readLine()) != null) {
                if (linha != linhaAntiga) {
                    conteudo = conteudo + linha + "\n";
                }
            }
            input.close();
        } catch (final IOException ioe) {
            System.out.println(ioe);
        }
        gravaTxt(Diretorio, conteudo + linhaNova + "\n");
        return conteudo;
    }

    public static String adicionaTxt(final String Diretorio, final String linhaNova, final boolean limparArquivo)
            throws IOException {
        String conteudo = "";
        if (limparArquivo)
            gravaTxt(Diretorio, conteudo);

        final File file = new File(Diretorio);
        try {
            final FileReader reader = new FileReader(file);
            final BufferedReader input = new BufferedReader(reader);
            String linha;
            while ((linha = input.readLine()) != null) {
                if (!linha.equals("")) {
                    conteudo = conteudo + "\n" + linha;
                }
            }

            input.close();
        } catch (final IOException ioe) {
            System.out.println(ioe);
        }
        gravaTxt(Diretorio, conteudo + "\n" + linhaNova + "\n");
        return conteudo;
    }

    public static String removeTxt(final String Diretorio, final String linhaRemover) throws IOException {
        String conteudo = "";
        String linha = "";
        final File file = new File(Diretorio);
        try {
            final FileReader reader = new FileReader(file);
            final BufferedReader input = new BufferedReader(reader);

            while ((linha = input.readLine()) != null) {
                if (!linha.equals(linhaRemover)) {
                    conteudo = conteudo + linha + "\n";
                }

            }

            input.close();
        } catch (final IOException ioe) {
            System.out.println(ioe);
        }
        adicionaTxt(Diretorio, conteudo + "\n", true);
        return conteudo;
    }

    public boolean objetificaçãoTxt(final String Diretorio, final int TipoObjeto)
            throws IOException, ParseException, InterruptedException {
        final File file = new File(Diretorio);
        try {
            final FileReader reader = new FileReader(file);
            final BufferedReader input = new BufferedReader(reader);
            String linha;
            while ((linha = input.readLine()) != null) {
                if (!linha.equals("")) {
                    if (TipoObjeto == 1) { // Pessoa
                            // Matricula;     Nome; CPF;

                        int x = linha.indexOf(";");
                        final String matricula = linha.substring(0, x);
                        linha = linha.substring(x + 1, linha.length());
                        x = linha.indexOf(";");
                        final String nome = linha.substring(0, x);
                        linha = linha.substring(x + 1, linha.length());
                        x = linha.indexOf(";");
                        final String cpf = linha.substring(0, x);
                        final int iMatricula = Integer.parseInt(matricula);
                        final long lCpf = Long.parseLong(cpf);
                        ControladorPessoa.adicionarPessoas(iMatricula, nome, lCpf);
                    }
                    if (TipoObjeto == 2) { // Ação Historico
                        int x = linha.indexOf(";");
                        final String codAcao = linha.substring(0, x);
                        linha = linha.substring(x + 1, linha.length());
                        x = linha.indexOf(";");
                        final String Pm = linha.substring(0, x);
                        linha = linha.substring(x + 1, linha.length());
                        x = linha.indexOf(";");
                        final String Data = linha.substring(0, x);
                        final double dPm = Double.parseDouble(Pm);
                        final Date date = new SimpleDateFormat("dd/MM/yyyy").parse(Data);
                        ControladorAcao.adicionarAcoesAHistorico(codAcao, dPm, date);
                    }
                    if (TipoObjeto == 3) { // Ação Compra
                        int x = linha.indexOf(";");
                        final String matricula = linha.substring(0, x);
                        final int iMatricula = Integer.parseInt(matricula);
                        linha = linha.substring(x + 1, linha.length());

                        x = linha.indexOf(";");
                        final String codAcao = linha.substring(0, x);
                        linha = linha.substring(x + 1, linha.length());

                        x = linha.indexOf(";");
                        final String Pm = linha.substring(0, x);
                        final double dPm = Double.parseDouble(Pm);
                        linha = linha.substring(x + 1, linha.length());

                        x = linha.indexOf(";");
                        final String quantidade = linha.substring(0, x);
                        final int sQuantidade = Integer.parseInt(quantidade);
                        linha = linha.substring(x + 1, linha.length());

                        final Pessoa X = ControladorPessoa.pegarPessoa(iMatricula);
                        ControladorPessoa.alterarPessoas(iMatricula,X.nome,X.cpf, ControladorAcao.adicionarAcoesAPessoa(iMatricula, codAcao, dPm, sQuantidade));
                        
                    }
                } 
            }
            
            input.close();
        } catch (final IOException ioe) {
            System.out.println(ioe);
        } 
        return true;
    }

}
