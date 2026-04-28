import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class JanelaPrincipal extends JFrame {
    // venda
    public JTextField campoNomeVenda = new JTextField(15);
    public JTextField campoQntdVenda = new JTextField(5); 
    public JButton botaoVenda = new JButton("Confirmar Venda");

    // cadastro
    public JTextField campoNomeCadastro = new JTextField(15);
    public JTextField campoPrecoCadastro = new JTextField(15);
    public JTextField campoQntdCadastro = new JTextField(15);
    public JButton botaoCadastro = new JButton("Cadastrar Produto");

    // tabela
    private JTable tabelaEstoque;
    private DefaultTableModel modeloTabela;
    public JButton botaoListar = new JButton("Atualizar Estoque");

    public Loja loja;

    public JanelaPrincipal(Loja loja) {
        this.loja = loja;

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) {}

        Font fontePadrao = new Font("SansSerif", Font.PLAIN, 14);
        UIManager.put("Label.font", fontePadrao);
        UIManager.put("Button.font", fontePadrao);
        UIManager.put("TextField.font", fontePadrao);
        UIManager.put("Table.font", fontePadrao);

        setTitle("Sistema de Gerenciamento de Loja");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane abas = new JTabbedPane();

        JPanel abaVenda = new JPanel(new GridBagLayout());
        abaVenda.setBorder(new EmptyBorder(30, 30, 30, 30));
        GridBagConstraints gbcVenda = new GridBagConstraints();
        gbcVenda.insets = new Insets(10, 10, 10, 10);
        gbcVenda.fill = GridBagConstraints.HORIZONTAL;

        gbcVenda.gridx = 0; gbcVenda.gridy = 0;
        abaVenda.add(new JLabel("Produto:"), gbcVenda);
        gbcVenda.gridx = 1; abaVenda.add(campoNomeVenda, gbcVenda);
        
        gbcVenda.gridx = 0; gbcVenda.gridy = 1;
        abaVenda.add(new JLabel("Quantidade:"), gbcVenda);
        gbcVenda.gridx = 1; abaVenda.add(campoQntdVenda, gbcVenda);
        
        gbcVenda.gridx = 0; gbcVenda.gridy = 2; gbcVenda.gridwidth = 2;
        botaoVenda.setBackground(new Color(100, 150, 250));
        botaoVenda.setForeground(Color.WHITE);
        abaVenda.add(botaoVenda, gbcVenda);

        JPanel abaCadastro = new JPanel(new GridBagLayout());
        abaCadastro.setBorder(new EmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 8, 8, 8);

        gbc.gridx = 0; gbc.gridy = 0; abaCadastro.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1; abaCadastro.add(campoNomeCadastro, gbc);
        gbc.gridx = 0; gbc.gridy = 1; abaCadastro.add(new JLabel("Preço:"), gbc);
        gbc.gridx = 1; abaCadastro.add(campoPrecoCadastro, gbc);
        gbc.gridx = 0; gbc.gridy = 2; abaCadastro.add(new JLabel("Quantidade Inicial:"), gbc);
        gbc.gridx = 1; abaCadastro.add(campoQntdCadastro, gbc);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        abaCadastro.add(botaoCadastro, gbc);

        JPanel abaListagem = new JPanel(new BorderLayout(10, 10));
        abaListagem.setBorder(new EmptyBorder(15, 15, 15, 15));
        modeloTabela = new DefaultTableModel(new String[]{"Nome", "Preço (R$)", "Estoque"}, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tabelaEstoque = new JTable(modeloTabela);
        abaListagem.add(new JScrollPane(tabelaEstoque), BorderLayout.CENTER);
        abaListagem.add(botaoListar, BorderLayout.SOUTH);

        abas.addTab("Venda", abaVenda);
        abas.addTab("Cadastro", abaCadastro);
        abas.addTab("Estoque", abaListagem);
        add(abas);
        
        configurarEventos();
    }

    public void configurarEventos() {
        botaoVenda.addActionListener(e -> {
            try {
                int qntd = Integer.parseInt(campoQntdVenda.getText());
                double resultado = loja.venderProduto(campoNomeVenda.getText(), qntd);
                if(resultado == -1) JOptionPane.showMessageDialog(this, "Produto não encontrado.");
                else if(resultado == -2) JOptionPane.showMessageDialog(this, "Estoque insuficiente!");
                else {
                    JOptionPane.showMessageDialog(this, "Venda realizada! Total: R$ " + resultado);
                    atualizarTabela();
                }
            } catch(NumberFormatException ex) { JOptionPane.showMessageDialog(this, "Quantidade inválida!"); }
        });

        botaoCadastro.addActionListener(e -> {
            try {
                String nome = campoNomeCadastro.getText();
                double preco = Double.parseDouble(campoPrecoCadastro.getText());
                int qntd = Integer.parseInt(campoQntdCadastro.getText());
                
                Produto p = loja.buscarProduto(nome);
                if (p != null) {
                    p.quantidade += qntd;
                    p.preco = preco;
                    JOptionPane.showMessageDialog(this, "Produto atualizado!");
                } else {
                    loja.cadastrarProduto(new Produto(nome, preco, qntd));
                    JOptionPane.showMessageDialog(this, "Novo produto cadastrado!");
                }
                campoNomeCadastro.setText(""); campoPrecoCadastro.setText(""); campoQntdCadastro.setText("");
                atualizarTabela();
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Erro nos dados."); }
        });

        botaoListar.addActionListener(e -> atualizarTabela());
    }

    private void atualizarTabela() {
        modeloTabela.setRowCount(0);
        for(Produto p : loja.estoque) {
            modeloTabela.addRow(new Object[]{p.nome, String.format("%.2f", p.preco), p.quantidade});
        }
    }
}