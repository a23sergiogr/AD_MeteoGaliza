package meteogaliza.enums;

public enum VariableMeteoroloxica {
    CIELO("ceo"), LLUVIA("pchoiva"), TEMPERATURA_MAXIMA("tmaxFranxa"), TEMPERATURA_MINIMA("tminFranxa"), VIENTO("vento");

    private String variableMeteoroloxica;
    VariableMeteoroloxica(String variableMeteoroloxica){
        this.variableMeteoroloxica = variableMeteoroloxica;
    }

    public String getNome() {
        return variableMeteoroloxica;
    }
}
