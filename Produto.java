public class Produto {
    public String nome;
    public double preco;
    public int quantidade;

    public Produto(String nome, double preco, int quantidade){
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
    }


    public void exibirDados(){
        System.out.println("Nome: " + nome + "\nPreço: " + preco + "\nQuantidade em estoque: " + quantidade);
    }

    public void diminuirEstoque(int quantidade2){
        if(quantidade2 > quantidade){
            System.out.println("Estoque insuficiente");
        }else {
            quantidade = quantidade - quantidade2;
        }
        
    }
}

