package meteogaliza.modelo.enums;


// Clase anidada para valores entre 0 y 100
class EstadoArbitrario implements EstadoUtils {
    private final int valor;

    public EstadoArbitrario(int valor) {
        this.valor = valor;
    }

    @Override
    public int getCodigo() {
        return -1;
    }

    @Override
    public String getNome() {
        return String.valueOf(valor);
    }
}