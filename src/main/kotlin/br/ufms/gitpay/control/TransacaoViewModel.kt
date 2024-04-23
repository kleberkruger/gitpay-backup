package br.ufms.gitpay.control

import br.ufms.gitpay.BancoCentral
import br.ufms.gitpay.ContaBancaria
import br.ufms.gitpay.NumeroBancario

class TransacaoViewModel {

    private val bancoCentral = BancoCentral.INSTANCE

    fun buscarConta(banco: Int, numero: NumeroBancario): ContaBancaria {
        return bancoCentral.getConta(banco, numero)
    }

    fun buscarConta(chavePix: String): ContaBancaria {
        return bancoCentral.getConta(chavePix)
    }
}