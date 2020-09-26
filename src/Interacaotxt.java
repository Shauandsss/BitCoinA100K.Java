import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


public class Interacaotxt {
    String Diretorio;
    String Conteudo;
    String NomeArq;
    List<Acao> Acoes;

    public boolean gravaTxt(final String Diretorio, final String Conteudo) throws IOException {
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

    public String buscaTxt(final String Diretorio, final String Conteudo) throws IOException {
        String conteudo = "";
        final File file = new File(Diretorio);
        try {
            final FileReader reader = new FileReader(file);
            final BufferedReader input = new BufferedReader(reader);
            String linha;
            while ((linha = input.readLine()) != null) {
                conteudo = conteudo + linha + "\n";
            }
            input.close();
        } catch (final IOException ioe) {
            System.out.println(ioe);
        }
        return conteudo;
    }

    public String alteraTxt(final String Diretorio, final String linhaAntiga, final String linhaNova) throws IOException {
        String conteudo = "";
        final File file = new File(Diretorio);
        try {
            final FileReader reader = new FileReader(file);
            final BufferedReader input = new BufferedReader(reader);
            String linha;
            while ((linha = input.readLine()) != null) {
                if(linha != linhaAntiga) {
                    conteudo = conteudo + linha + "\n";
                }    
            }
            input.close();
        } catch (final IOException ioe) {
            System.out.println(ioe);
        }
        gravaTxt(Diretorio, Conteudo + linhaNova + "\n");   
        return conteudo;
    }

    public String adicionaTxt(final String Diretorio, final String linhaNova) throws IOException {
        String conteudo = "";
        final File file = new File(Diretorio);
        try {
            final FileReader reader = new FileReader(file);
            final BufferedReader input = new BufferedReader(reader);
            String linha;
            while ((linha = input.readLine()) != null) {  
                    conteudo = conteudo + linha;  
            }
            
            input.close();
        } catch (final IOException ioe) {
            System.out.println(ioe);
        }
        System.out.println(conteudo + " Final " + linhaNova);
        gravaTxt(Diretorio, conteudo     + "\n" + linhaNova + "\n");   
        return conteudo;
    }

}