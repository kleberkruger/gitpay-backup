package br.ufms.gitpay;

public class ContaCorrente extends ContaBancaria {

    private double limite;

    protected ContaCorrente(Usuario<? extends Pessoa> usuario) {
        super(usuario);
    }

    @Override
    public void sacar(double valor) {
        if (valor > saldo + limite) {
            throw new IllegalArgumentException("Saldo insuficiente");
        }
        this.saldo -= valor;

        this.transacoes.add(new Transacao(TipoTransacao.SAQUE, valor));
    }

    public double getLimite() {
        return limite;
    }

    public void setLimite(double limite) {
        this.limite = limite;
    }
}