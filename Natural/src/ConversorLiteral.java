
/* CONVERTIR UN NUMERO A LITERAL */

public class ConversorLiteral {

    private static final String[] UNIDADES = {
        "", "uno", "dos", "tres", "cuatro", "cinco", "seis", "siete", "ocho", "nueve"
    };

    private static final String[] DIEZ_A_DIECINUEVE = {
        "diez", "once", "doce", "trece", "catorce", "quince",
        "dieciséis", "diecisiete", "dieciocho", "diecinueve"
    };

    private static final String[] DECENAS = {
        "", "", "veinte", "treinta", "cuarenta", "cincuenta",
        "sesenta", "setenta", "ochenta", "noventa"
    };

    private static final String[] CENTENAS = {
        "", "ciento", "doscientos", "trescientos", "cuatrocientos", "quinientos",
        "seiscientos", "setecientos", "ochocientos", "novecientos"
    };

    private ConversorLiteral() {}

    public static String convertir(int n) {
        if (n < 0 || n > 999_999_999) {
            throw new IllegalArgumentException("Solo se admiten números entre 0 y 999,999,999.");
        }
        if (n == 0) {
            return "cero";
        }

        int millones = n / 1_000_000;
        int miles = (n % 1_000_000) / 1_000;
        int resto = n % 1_000;

        StringBuilder sb = new StringBuilder();

        if (millones > 0) {
            if (millones == 1) {
                sb.append("un millón");
            } else {
                sb.append(convertirGrupo(millones)).append(" millones");
            }
        }

        if (miles > 0) {
            if (sb.length() > 0) {
                sb.append(" ");
            }
            if (miles == 1) {
                sb.append("mil");
            } else {
                sb.append(convertirGrupo(miles)).append(" mil");
            }
        }

        if (resto > 0) {
            if (sb.length() > 0) {
                sb.append(" ");
            }
            sb.append(convertirGrupo(resto));
        }

        return sb.toString().trim();
    }

    /* CONVERTIR DE 1 A 999 */
    
    private static String convertirGrupo(int n) {
        StringBuilder sb = new StringBuilder();

        int centena = n / 100;
        int restoDecenas = n % 100;

        if (centena > 0) {
            if (n == 100) {
                return "cien";
            }
            sb.append(CENTENAS[centena]);
        }

        if (restoDecenas > 0) {
            if (sb.length() > 0) {
                sb.append(" ");
            }
            sb.append(convertirDecenas(restoDecenas));
        }

        return sb.toString();
    }

    /* CONVERTIR DE 1 A 99 */
    
    private static String convertirDecenas(int n) {
        if (n < 10) {
            return UNIDADES[n];
        }
        if (n < 20) {
            return DIEZ_A_DIECINUEVE[n - 10];
        }
        int decena = n / 10;
        int unidad = n % 10;
        if (decena == 2 && unidad > 0) {

            return "veinti" + (unidad == 3 ? "trés" : unidadEspecial(unidad));
        }
        if (unidad == 0) {
            return DECENAS[decena];
        }
        return DECENAS[decena] + " y " + UNIDADES[unidad];
    }

    private static String unidadEspecial(int unidad) {
        switch (unidad) {
            case 1: return "uno";
            case 2: return "dós";
            case 6: return "séis";
            default: return UNIDADES[unidad];
        }
    }
}
