package br.ufms.gitpay;

public class Usuario<P extends Pessoa> {

    private final P dados;
    private String senha;

    public Usuario(P dados, String senha) {
        this.dados = dados;
        this.senha = senha;
    }

    public P getDados() {
        return dados;
    }

    public String getLogin() {
        return dados instanceof PessoaFisica ? ((PessoaFisica) dados).getCpf() : ((PessoaJuridica) dados).getCnpj();
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
