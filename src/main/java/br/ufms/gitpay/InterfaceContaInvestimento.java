package br.ufms.gitpay;

import java.util.Map;

public interface InterfaceContaInvestimento extends InterfaceContaBancaria {

    void investir(Investimento investimento, double valor);

    Map<Investimento, Double> getRendimentos();
}
