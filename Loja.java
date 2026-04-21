import java.util.*;

public class Loja {
    public String nomeLoja;
    public ArrayList<Produto> estoque = new ArrayList<>();

    public Loja(String nomeLoja){
        this.nomeLoja = nomeLoja;
    }

    public void cadastrarProduto(Produto produto){
        estoque.add(produto);
    }

    public void listarProdutos(){
        for(int i = 0; i < estoque.size(); i++){
            System.out.println(estoque.get(i));
        }
    }

    public Produto buscarProduto(String nome){
        for(int i = 0; i < estoque.size(); i++){
            if(estoque.get(i).nome.equals(nome)){
                return estoque.get(i);
            }
        }
        return null;
    }

    public double venderProduto(String nome, int quantidade){

        Produto p = buscarProduto(nome);

        if(p == null){
            return -1;
        }

        if(p.quantidade < quantidade){
            return -2;
        }

        p.diminuirEstoque(quantidade);
        double valorTotal = p.preco * quantidade;
        return valorTotal;

    }
}
