import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.text.DecimalFormat; 

public class ControladorAcao {
    public ArrayList<Pessoa> pessoas;

    
    public ControladorAcao() {
        this.pessoas = new ArrayList<>();
    }

    public boolean atualizar() throws IOException, InterruptedException{
        String path;
        path = "C:/Users/Administrator/Documents/GitHub/BitCoinA100K.Java/COTAHIST_D21092020.txt";
        BufferedReader buffRead = new BufferedReader(new FileReader(path));
        DecimalFormat df = new DecimalFormat("#,###.00");
        String linha = "";
        linha = buffRead.readLine();
        String valor = "";
        String valordeci = "";
		while (true) {
			if (linha != null) {
                String subLinha = linha.substring(12,20);
                System.out.print("Código da Ação: " + subLinha);
                valor = linha.substring(63,68);
                valordeci = linha.substring(68,70);
                if (valor != null && !valor.isEmpty() && !valor.trim().isEmpty()){
                    valor = valor + "." + valordeci;
                    double value = Double.parseDouble(valor);
                    String sValue = String.valueOf(value);
                    System.out.println("   Valor: " + sValue);
                }
                //String sValor = String.valueOf(value);
                //System.out.println("   Valor: " + sValor);

               // System.out.println("Valor: " + sValor);
			} else
				break;
			linha = buffRead.readLine();
		}
		buffRead.close();
        return true;
    }
}
