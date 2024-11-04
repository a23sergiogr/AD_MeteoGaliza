package meteogaliza.enums;

public interface EstadoUtils{
    int getCodigo();
    String getNome();

    //Implementación de Estado Non Dispoñible
    EstadoUtils NON_DISPONHIBLE = new EstadoUtils() {
        @Override
        public int getCodigo() {
            return -9999;
        }

        @Override
        public String getNome() {
            return "Non dispoñible";
        }

        @Override
        public String toString() {
            return getNome();
        }
    };

    static <E extends EstadoUtils> String getNomePorCodigo(E[] estados, int codigo) {
        for (E estado : estados) {
            if (estado.getCodigo() == codigo) {
                return estado.getNome();
            }
        }
        throw new IllegalArgumentException("Código non válido: " + codigo);
    }

    static <E extends EstadoUtils> int getCodigoPorNome(E[] estados, String nome) {
        for (E estado : estados) {
            if (estado.getNome().equalsIgnoreCase(nome)) {
                return estado.getCodigo();
            }
        }
        throw new IllegalArgumentException("Nome non válido: " + nome);
    }

    static <E extends EstadoUtils> E getEstadoPorCodigo(E[] estados, int codigo) {
        for (E estado : estados) {
            if (estado.getCodigo() == codigo) {
                return estado;
            }
        }
        throw new IllegalArgumentException("Código no válido: " + codigo);
    }

    // Método para obtener una instancia arbitraria
    static EstadoUtils ofValorArbitrario(int valor) {
        return new EstadoArbitrario(valor);
    }
}

