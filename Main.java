import java.util.*;
import javax.swing.*;

public class Main {
    public static void main(String[] args){
        Loja loja1 = new Loja("Loja 1");

        javax.swing.SwingUtilities.invokeLater(() -> {
            JanelaPrincipal janela = new JanelaPrincipal(loja1);
            janela.setVisible(true);
        });   
    }
}
