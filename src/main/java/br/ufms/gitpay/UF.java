package br.ufms.gitpay;

public enum UF {

    AC, AL, AM, AP, BA, CE, DF, ES, GO, MA, MG, MS, MT, PA, PB, PE, PI, PR, RJ, RN, RO, RR, RS, SC, SE, SP, TO;

    public String getCapital() {
        return switch (this) {
            case AC -> "Rio Branco" ;
            case AL -> "Maceió" ;
            case AM -> "Manaus" ;
            case AP -> "Macapá" ;
            case BA -> "Salvador" ;
            case CE -> "Fortaleza" ;
            case DF -> "Brasília" ;
            case ES -> "Vitória" ;
            case GO -> "Goiânia" ;
            case MA -> "São Luís" ;
            case MG -> "Belo Horizonte" ;
            case MS -> "Campo Grande" ;
            case MT -> "Cuiabá" ;
            case PA -> "Belém" ;
            case PB -> "João Pessoa" ;
            case PR -> "Curitiba" ;
            case PE -> "Recife" ;
            case PI -> "Teresina" ;
            case RJ -> "Rio de Janeiro" ;
            case RN -> "Natal" ;
            case RS -> "Porto Alegre" ;
            case RO -> "Porto Velho" ;
            case RR -> "Boa Vista" ;
            case SC -> "Florianópolis" ;
            case SP -> "São Paulo" ;
            case SE -> "Aracaju" ;
            case TO -> "Palmas" ;
        };
    }

    public String getNomeEstado() {
        return switch (this) {
            case AC -> "Acre" ;
            case AL -> "Alagoas" ;
            case AM -> "Amazonas" ;
            case AP -> "Amapá" ;
            case BA -> "Bahia" ;
            case CE -> "Ceará" ;
            case DF -> "Distrito Federal" ;
            case ES -> "Espírito Santo" ;
            case GO -> "Goiás" ;
            case MA -> "Maranhão" ;
            case MG -> "Minas Gerais" ;
            case MS -> "Mato Grosso do Sul" ;
            case MT -> "Mato Grosso" ;
            case PA -> "Pará" ;
            case PB -> "Paraíba" ;
            case PR -> "Paraná" ;
            case PE -> "Pernambuco" ;
            case PI -> "Piauí" ;
            case RJ -> "Rio de Janeiro" ;
            case RN -> "Rio Grande do Norte" ;
            case RS -> "Rio Grande do Sul" ;
            case RO -> "Rondônia" ;
            case RR -> "Roraima" ;
            case SC -> "Santa Catarina" ;
            case SP -> "São Paulo" ;
            case SE -> "Sergipe" ;
            case TO -> "Tocantins" ;
        };
    }
}
