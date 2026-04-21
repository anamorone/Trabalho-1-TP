import javax.swing.*;
import java.awt.*;   

public class JanelaPrincipal extends JFrame{
    public JTextField campoNomeVenda = new JTextField(15);
    public JButton botaoVenda = new JButton("Vender");

    public JTextField campoNomeCadastro = new JTextField(15);
    public JTextField campoPrecoCadastro = new JTextField(10);
    public JTextField campoQntdCadastro = new JTextField(10);
    public JButton botaoCadastro = new JButton("Cadastrar produto");

    public Loja loja;

    public JanelaPrincipal(Loja loja){
        this.loja = loja;

        setTitle("Trabalho 1 - Loja");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JTabbedPane abas = new JTabbedPane();

        JPanel abaVenda = new JPanel();
        abaVenda.add(new JLabel("Nome do produto: "));
        abaVenda.add(campoNomeVenda);
        abaVenda.add(botaoVenda);

        JPanel abaCadastro = new JPanel();
        abaCadastro.setLayout(new GridLayout(4, 2));
        abaCadastro.add(new JLabel("Nome: "));
        abaCadastro.add(campoNomeCadastro);
        abaCadastro.add(new JLabel("Preço: "));
        abaCadastro.add(campoPrecoCadastro);
        abaCadastro.add(new JLabel("Quantidade: "));
        abaCadastro.add(campoQntdCadastro);
        abaCadastro.add(botaoCadastro);


        abas.addTab("Vendas", abaVenda);
        abas.addTab("Cadastro", abaCadastro);
        add(abas);
        
        configurarEventos();


    }

    public void configurarEventos(){
        botaoVenda.addActionListener(e -> {
            String nome = campoNomeVenda.getText();
            int qntd = 1;

            double resultado = loja.venderProduto(nome, qntd);

            if(resultado == -1){
                JOptionPane.showMessageDialog(this, "Produto não encontrado.");

            }else if(resultado == -2){
                JOptionPane.showMessageDialog(this, "Estoque insuficiente!");

            }else {
                JOptionPane.showMessageDialog(this, "Venda realizada! Total: R$; " + resultado);
            }
        });

        botaoCadastro.addActionListener(e -> {
            try{
                String nome = campoNomeCadastro.getText();
                double preco = Double.parseDouble(campoPrecoCadastro.getText());
                int qntd = Integer.parseInt(campoQntdCadastro.getText());
                
                loja.cadastrarProduto(new Produto(nome, preco, qntd));
                JOptionPane.showMessageDialog(this, "Produto cadastrado com sucesso");

                campoNomeCadastro.setText("");
                campoPrecoCadastro.setText("");
                campoQntdCadastro.setText("");
            } catch (NumberFormatException ex){
                JOptionPane.showMessageDialog(this, "Erro: Verifique se preço e quantidade são números válidos.");
            }
        });

    }

}
