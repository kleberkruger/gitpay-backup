package br.ufms.gitpay.util

import br.ufms.gitpay.*
import kotlin.random.Random

fun gerarClientes(
    codigoBanco: Int, qtdPessoaFisica: Int, qtdPessoaJuridica: Int
): MutableList<ContaBancaria> {

    val contasGeradas: MutableList<ContaBancaria> = mutableListOf()

    BancoCentral.INSTANCE.bancos[codigoBanco]?.let { banco: Banco ->
        repeat(qtdPessoaFisica) {
            contasGeradas.add(banco.criarConta(gerarClientePessoaFisica()))
        }
        repeat(qtdPessoaJuridica) {
            contasGeradas.add(banco.criarConta(gerarClientePessoaJuridica()))
        }
    }
    return contasGeradas
}

private fun gerarClientePessoaFisica(): Usuario<PessoaFisica> {
    val nome = gerarNomeAleatorio()
    val cpf = gerarCPF()
    return Usuario(PessoaFisica(nome, cpf), gerarSenhaAleatoria())
}

private fun gerarClientePessoaJuridica(): Usuario<PessoaJuridica> {
    val nome = gerarNomeEmpresaAleatorio()
    val cnpj = gerarCNPJ()
    return Usuario(PessoaJuridica(nome, cnpj), gerarSenhaAleatoria())
}

private fun gerarNomeAleatorio(): String {
    val nomes = listOf(
        "João",
        "Maria",
        "Pedro",
        "Ana",
        "Carlos",
        "Juliana",
        "Marcos",
        "Laura",
        "Fernanda",
        "Rafael",
        "Mariana",
        "Lucas",
        "Patrícia",
        "Gabriel",
        "Renata",
        "Matheus",
        "Aline",
        "Gustavo",
        "Camila",
        "Diego",
        "Bianca",
        "Felipe",
        "Carolina",
        "Rodrigo",
        "Isabela",
        "Leandro",
        "Amanda",
        "Thiago",
        "Vanessa",
        "Guilherme"
    )
    val sobrenomes = listOf(
        "Silva",
        "Santos",
        "Oliveira",
        "Pereira",
        "Costa",
        "Rodrigues",
        "Almeida",
        "Lima",
        "Ferreira",
        "Martins",
        "Souza",
        "Carvalho",
        "Araújo",
        "Cardoso",
        "Rocha",
        "Barbosa",
        "Gomes",
        "Alves",
        "Monteiro",
        "Correia",
        "Dias",
        "Cavalcanti",
        "Castro",
        "Nascimento",
        "Mendes",
        "Sousa",
        "Peixoto",
        "Farias",
        "Lopes",
        "Nunes"
    )
    val nome = nomes.random()
    val sobrenome = sobrenomes.random()
    return "$nome $sobrenome"
}

private fun gerarNomeEmpresaAleatorio(): String {
    val palavras = listOf(
        "Tech",
        "Soluções",
        "Consultoria",
        "Desenvolvimento",
        "Inovação",
        "Digital",
        "Serviços",
        "Global",
        "Sistemas",
        "Comércio",
        "Estratégia",
        "Softwares"
    )
    val prefixos = listOf(
        "Alpha", "Beta", "Delta", "Omega", "Prime", "Mega", "Hyper", "First", "Global", "Net", "Bright", "Future"
    )
    val sufixos = listOf("Ltda", "S/A", "Soluções", "Enterprise", "e Associados", "e Cia", "e Filhos")
    val nome = prefixos.random() + " " + palavras.random() + " " + sufixos.random()
    return nome
}

private fun gerarCPF(): String {
    val random = Random.Default
    val cpf = StringBuilder()
    repeat(9) {
        cpf.append(random.nextInt(0, 9))
    }
    val digito1 = calcularDigitoVerificador(cpf.toString())
    cpf.append(digito1)
    val digito2 = calcularDigitoVerificador(cpf.toString())
    cpf.append(digito2)
    return cpf.toString()
}

private fun calcularDigitoVerificador(cpfParcial: String): Int {
    var soma = 0
    var peso = cpfParcial.length + 1
    for (digito in cpfParcial) {
        soma += Integer.parseInt(digito.toString()) * peso
        peso--
    }
    val resto = soma % 11
    return if (resto < 2) 0 else 11 - resto
}

private fun gerarCNPJ(): String {
    val random = Random.Default
    val cnpjSemDV = (1..12).map { random.nextInt(0, 9) }.joinToString("")
    val digito1 = calcularDigitoVerificadorCNPJ(cnpjSemDV)
    val digito2 = calcularDigitoVerificadorCNPJ(cnpjSemDV + digito1)
    return "$cnpjSemDV$digito1$digito2"
}

private fun calcularDigitoVerificadorCNPJ(cnpjParcial: String): Int {
    val pesos = intArrayOf(6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2)
    var soma = 0
    for (i in 0 until cnpjParcial.length) {
        soma += cnpjParcial[i].toString().toInt() * pesos[i]
    }
    val resto = soma % 11
    return if (resto < 2) 0 else 11 - resto
}

private fun gerarSenhaAleatoria(): String {
    val caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
    return (1..8).map { caracteres.random() }.joinToString("")
}