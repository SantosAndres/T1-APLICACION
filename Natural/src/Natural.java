import java.util.ArrayList;
import java.util.List;

/* CLASE NATURAL */

public class Natural {

    private List<Integer> lista;

    public Natural() {
        this.lista = new ArrayList<>();
    }

    public void insertar(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Solo se permiten números naturales (n >= 0).");
        }
        lista.add(n);
    }

    public int obtener(int posicion) {
        validarPosicion(posicion);
        return lista.get(posicion);
    }

    public void eliminar(int posicion) {
        validarPosicion(posicion);
        lista.remove(posicion);
    }

    public int cantidad() {
        return lista.size();
    }

    public long sumar() {
        long suma = 0;
        for (int n : lista) {
            suma += n;
        }
        return suma;
    }

    public List<Integer> pares() {
        List<Integer> resultado = new ArrayList<>();
        for (int n : lista) {
            if (esPar(n)) {
                resultado.add(n);
            }
        }
        return resultado;
    }

    public List<Integer> impares() {
        List<Integer> resultado = new ArrayList<>();
        for (int n : lista) {
            if (esImpar(n)) {
                resultado.add(n);
            }
        }
        return resultado;
    }

    public List<Integer> primos() {
        List<Integer> resultado = new ArrayList<>();
        for (int n : lista) {
            if (esPrimo(n)) {
                resultado.add(n);
            }
        }
        return resultado;
    }

    public int mayor() {
        if (lista.isEmpty()) {
            throw new IllegalStateException("La colección está vacía.");
        }
        int max = lista.get(0);
        for (int n : lista) {
            if (n > max) {
                max = n;
            }
        }
        return max;
    }

    public int menor() {
        if (lista.isEmpty()) {
            throw new IllegalStateException("La colección está vacía.");
        }
        int min = lista.get(0);
        for (int n : lista) {
            if (n < min) {
                min = n;
            }
        }
        return min;
    }

    public List<Integer> listar() {
        return new ArrayList<>(lista);
    }

    private void validarPosicion(int posicion) {
        if (posicion < 0 || posicion >= lista.size()) {
            throw new IndexOutOfBoundsException(
                "Posición inválida. Debe estar entre 0 y " + (lista.size() - 1) + ".");
        }
    }


    public static long invertir(int n) {
        int resto;
        long invertido = 0;
        n = Math.abs(n);
        while (n > 0) {
            resto = n % 10;
            invertido = invertido * 10 + resto;
            n = n / 10;
        }
        return invertido;
    }

    public static boolean esCapicua(int n) {
        return n >= 0 && n == invertir(n);
    }

    public static boolean esPar(int n) {
        return n % 2 == 0;
    }

    public static boolean esImpar(int n) {
        return !esPar(n);
    }

    public static boolean esPrimo(int n) {
        if (n < 2) {
            return false;
        }
        if (n == 2) {
            return true;
        }
        if (n % 2 == 0) {
            return false;
        }
        for (int i = 3; (long) i * i <= n; i += 2) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static String aBinario(int n) {
        return aBaseN(n, 2);
    }

    public static String aOctal(int n) {
        return aBaseN(n, 8);
    }

    public static String aHexadecimal(int n) {
        return aBaseN(n, 16);
    }

    public static String aBaseN(int n, int base) {
        if (base < 2 || base > 36) {
            throw new IllegalArgumentException("La base debe estar entre 2 y 36.");
        }
        if (n == 0) {
            return "0";
        }
        final String digitos = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder();
        int valor = n;
        while (valor > 0) {
            int residuo = valor % base;
            sb.append(digitos.charAt(residuo));
            valor /= base;
        }
        return sb.reverse().toString();
    }

    public static String aRomano(int n) {
        if (n <= 0 || n > 3999) {
            throw new IllegalArgumentException("Solo se admiten números entre 1 y 3999 para números romanos.");
        }
        int[] valores = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] simbolos = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        StringBuilder sb = new StringBuilder();
        int resto = n;
        for (int i = 0; i < valores.length; i++) {
            while (resto >= valores[i]) {
                resto -= valores[i];
                sb.append(simbolos[i]);
            }
        }
        return sb.toString();
    }

    public static String aLiteral(int n) {
        return ConversorLiteral.convertir(n);
    }
}
