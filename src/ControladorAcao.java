import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class ControladorAcao {
    public ArrayList<Pessoa> pessoas;

    public ControladorAcao() {
        this.pessoas = new ArrayList<>();
    }

    public boolean atualizar() throws IOException, InterruptedException {
        String path;
        // Date hoje = new Date(System.currentTimeMillis());
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
