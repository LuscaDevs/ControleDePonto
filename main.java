import javax.swing.SwingUtilities;

//Main
public class main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                PontoEletronico pontoEletronico = new PontoEletronico();
                pontoEletronico.exibirInterface();
            }
        });
    }
}
