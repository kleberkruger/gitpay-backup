package br.ufms.gitpay;

import java.util.Map;

public class ContaPagamento extends ContaCorrente implements InterfaceContaInvestimento {

    protected ContaPagamento(Usuario<? extends Pessoa> usuario) {
        super(usuario);
    }

    @Override
    public void investir(Investimento investimento, double valor) {

    }

    @Override
    public Map<Investimento, Double> getRendimentos() {
        return null;
    }
}
