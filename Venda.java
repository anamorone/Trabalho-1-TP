public class Venda {
    public int quantidadeVendida;
    public double valorTotal;
    public Produto produto;

    public Venda(Produto produto, int quantidadeVendida){
        this.produto = produto;
        this.quantidadeVendida = quantidadeVendida;
    }

    public double calcularTotal(){
        valorTotal = produto.preco * quantidadeVendida;
        return valorTotal;
    }

    public void exibirVenda(){
        System.out.println("Produto " + produto.nome);
        System.out.println("Quantidade: " + quantidadeVendida);
        System.out.println("Valor total: R$ " + valorTotal);
    }
}
