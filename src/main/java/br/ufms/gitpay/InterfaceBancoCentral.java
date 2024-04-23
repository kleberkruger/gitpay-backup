package br.ufms.gitpay;

public interface InterfaceBancoCentral {

    void registrarBanco(Banco banco);

    void registrarPix(String chave, ContaBancaria conta);

    void excluirPix(String chave);

    Banco getBanco(int codigo);

    ContaBancaria getConta(int codigoBanco, NumeroBancario numero);

    ContaBancaria getConta(String pix);

    void depositar(int codigoBanco, NumeroBancario numeroConta, int valor);

    void transferir(ContaBancaria contaOrigem, int codigoBancoDestino, NumeroBancario numeroContaDestino, int valor);
}
