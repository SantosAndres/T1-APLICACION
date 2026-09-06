import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

/* INTERFAZ GRAFICA */ 


public class NaturalGUI extends JFrame {

    private final Natural natural = new Natural();

    /* PESTAÑA COLLECCION */
    
    private JTextField campoValor;
    private JTextField campoPosicion;
    private DefaultListModel<Integer> modeloLista;
    private JList<Integer> listaVisual;
    private JTextArea areaResultadoColeccion;

    /* PESTAÑA ENTEROS */
    
    private JTextField campoNumero;
    private JTextField campoBase;
    private JTextArea areaResultadoEnteros;

    public NaturalGUI() {
        super("Clase Natural - Prueba de Funcionalidad");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(820, 560);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Colección de Naturales", crearPanelColeccion());
        tabs.addTab("Operaciones sobre Enteros", crearPanelEnteros());

        setContentPane(tabs);
    }

  /* PRIMERA PESTAÑA*/
    
    private JPanel crearPanelColeccion() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        /* PANEL SUPERIOR */ 
        
        JPanel entradas = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        campoValor = new JTextField(10);
        campoPosicion = new JTextField(10);

        JButton btnInsertar = new JButton("Insertar");
        JButton btnObtener = new JButton("Obtener");
        JButton btnEliminar = new JButton("Eliminar");

        gbc.gridx = 0; gbc.gridy = 0; entradas.add(new JLabel("Valor a insertar:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; entradas.add(campoValor, gbc);
        gbc.gridx = 2; gbc.gridy = 0; entradas.add(btnInsertar, gbc);

        gbc.gridx = 0; gbc.gridy = 1; entradas.add(new JLabel("Posición (obtener/eliminar):"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; entradas.add(campoPosicion, gbc);
        gbc.gridx = 2; gbc.gridy = 1; entradas.add(btnObtener, gbc);
        gbc.gridx = 3; gbc.gridy = 1; entradas.add(btnEliminar, gbc);

        /* PANEL CENTRAL */
        
        modeloLista = new DefaultListModel<>();
        listaVisual = new JList<>(modeloLista);
        listaVisual.setBorder(BorderFactory.createTitledBorder("Elementos en la colección"));
        JScrollPane scrollLista = new JScrollPane(listaVisual);
        scrollLista.setPreferredSize(new Dimension(200, 0));

        /* PANEL DE OPERACIONES */ 
        
        JPanel operaciones = new JPanel(new GridLayout(0, 1, 5, 5));
        operaciones.setBorder(BorderFactory.createTitledBorder("Operaciones"));
        JButton btnCantidad = new JButton("Cantidad");
        JButton btnSumar = new JButton("Sumar");
        JButton btnPares = new JButton("Pares");
        JButton btnImpares = new JButton("Impares");
        JButton btnPrimos = new JButton("Primos");
        JButton btnMayor = new JButton("Mayor");
        JButton btnMenor = new JButton("Menor");
        operaciones.add(btnCantidad);
        operaciones.add(btnSumar);
        operaciones.add(btnPares);
        operaciones.add(btnImpares);
        operaciones.add(btnPrimos);
        operaciones.add(btnMayor);
        operaciones.add(btnMenor);

        /* PANEL DE RESULTADOS */
        
        areaResultadoColeccion = new JTextArea(6, 20);
        areaResultadoColeccion.setEditable(false);
        JScrollPane scrollResultado = new JScrollPane(areaResultadoColeccion);
        scrollResultado.setBorder(BorderFactory.createTitledBorder("Resultado"));

        JPanel centro = new JPanel(new BorderLayout(10, 10));
        centro.add(scrollLista, BorderLayout.WEST);
        centro.add(operaciones, BorderLayout.CENTER);
        centro.add(scrollResultado, BorderLayout.SOUTH);

        panel.add(entradas, BorderLayout.NORTH);
        panel.add(centro, BorderLayout.CENTER);

        /* EVENTOS */
        
        btnInsertar.addActionListener(e -> {
            try {
                int valor = Integer.parseInt(campoValor.getText().trim());
                natural.insertar(valor);
                modeloLista.addElement(valor);
                mostrarColeccion("Se insertó " + valor + " correctamente.");
                campoValor.setText("");
            } catch (NumberFormatException ex) {
                mostrarErrorColeccion("Ingresa un número entero válido.");
            } catch (IllegalArgumentException ex) {
                mostrarErrorColeccion(ex.getMessage());
            }
        });

        btnObtener.addActionListener(e -> {
            try {
                int pos = Integer.parseInt(campoPosicion.getText().trim());
                int valor = natural.obtener(pos);
                mostrarColeccion("Elemento en la posición " + pos + ": " + valor);
            } catch (NumberFormatException ex) {
                mostrarErrorColeccion("Ingresa una posición válida.");
            } catch (IndexOutOfBoundsException ex) {
                mostrarErrorColeccion(ex.getMessage());
            }
        });

        btnEliminar.addActionListener(e -> {
            try {
                int pos = Integer.parseInt(campoPosicion.getText().trim());
                int valor = natural.obtener(pos);
                natural.eliminar(pos);
                modeloLista.remove(pos);
                mostrarColeccion("Se eliminó el elemento " + valor + " de la posición " + pos + ".");
            } catch (NumberFormatException ex) {
                mostrarErrorColeccion("Ingresa una posición válida.");
            } catch (IndexOutOfBoundsException ex) {
                mostrarErrorColeccion(ex.getMessage());
            }
        });

        btnCantidad.addActionListener(e ->
            mostrarColeccion("Cantidad de elementos: " + natural.cantidad()));

        btnSumar.addActionListener(e -> {
            if (natural.cantidad() == 0) {
                mostrarErrorColeccion("La colección está vacía.");
            } else {
                mostrarColeccion("Suma total: " + natural.sumar());
            }
        });

        btnPares.addActionListener(e ->
            mostrarColeccion("Pares: " + formatearLista(natural.pares())));

        btnImpares.addActionListener(e ->
            mostrarColeccion("Impares: " + formatearLista(natural.impares())));

        btnPrimos.addActionListener(e ->
            mostrarColeccion("Primos: " + formatearLista(natural.primos())));

        btnMayor.addActionListener(e -> {
            try {
                mostrarColeccion("Mayor: " + natural.mayor());
            } catch (IllegalStateException ex) {
                mostrarErrorColeccion(ex.getMessage());
            }
        });

        btnMenor.addActionListener(e -> {
            try {
                mostrarColeccion("Menor: " + natural.menor());
            } catch (IllegalStateException ex) {
                mostrarErrorColeccion(ex.getMessage());
            }
        });

        return panel;
    }

    private String formatearLista(List<Integer> lista) {
        if (lista.isEmpty()) {
            return "(ninguno)";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lista.size(); i++) {
            sb.append(lista.get(i));
            if (i < lista.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    private void mostrarColeccion(String mensaje) {
        areaResultadoColeccion.setText(mensaje);
    }

    private void mostrarErrorColeccion(String mensaje) {
        areaResultadoColeccion.setText("Error: " + mensaje);
    }

/* SEGUNDA PESTAÑA */
    
    private JPanel crearPanelEnteros() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel entrada = new JPanel(new FlowLayout(FlowLayout.LEFT));
        campoNumero = new JTextField(10);
        entrada.add(new JLabel("Número:"));
        entrada.add(campoNumero);
        entrada.add(new JLabel("   Base N (solo para 'Convertir a Base N'):"));
        campoBase = new JTextField(4);
        entrada.add(campoBase);

        JPanel botones = new JPanel(new GridLayout(0, 3, 5, 5));
        botones.setBorder(BorderFactory.createTitledBorder("Operaciones"));

        JButton btnInvertir = new JButton("Invertir");
        JButton btnCapicua = new JButton("¿Es Capicúa?");
        JButton btnPar = new JButton("¿Es Par?");
        JButton btnImpar = new JButton("¿Es Impar?");
        JButton btnPrimo = new JButton("¿Es Primo?");
        JButton btnBinario = new JButton("A Binario");
        JButton btnOctal = new JButton("A Octal");
        JButton btnHexadecimal = new JButton("A Hexadecimal");
        JButton btnBaseN = new JButton("A Base N");
        JButton btnRomano = new JButton("A Romano");
        JButton btnLiteral = new JButton("A Literal");

        botones.add(btnInvertir);
        botones.add(btnCapicua);
        botones.add(btnPar);
        botones.add(btnImpar);
        botones.add(btnPrimo);
        botones.add(btnBinario);
        botones.add(btnOctal);
        botones.add(btnHexadecimal);
        botones.add(btnBaseN);
        botones.add(btnRomano);
        botones.add(btnLiteral);

        areaResultadoEnteros = new JTextArea(8, 20);
        areaResultadoEnteros.setEditable(false);
        JScrollPane scrollResultado = new JScrollPane(areaResultadoEnteros);
        scrollResultado.setBorder(BorderFactory.createTitledBorder("Resultado"));

        panel.add(entrada, BorderLayout.NORTH);
        panel.add(botones, BorderLayout.CENTER);
        panel.add(scrollResultado, BorderLayout.SOUTH);

        btnInvertir.addActionListener(e -> ejecutarOperacionEntero(n ->
            "Invertir(" + n + ") = " + Natural.invertir(n)));

        btnCapicua.addActionListener(e -> ejecutarOperacionEntero(n ->
            n + " es capicúa: " + Natural.esCapicua(n)));

        btnPar.addActionListener(e -> ejecutarOperacionEntero(n ->
            n + " es par: " + Natural.esPar(n)));

        btnImpar.addActionListener(e -> ejecutarOperacionEntero(n ->
            n + " es impar: " + Natural.esImpar(n)));

        btnPrimo.addActionListener(e -> ejecutarOperacionEntero(n ->
            n + " es primo: " + Natural.esPrimo(n)));

        btnBinario.addActionListener(e -> ejecutarOperacionEntero(n ->
            n + " en binario = " + Natural.aBinario(n)));

        btnOctal.addActionListener(e -> ejecutarOperacionEntero(n ->
            n + " en octal = " + Natural.aOctal(n)));

        btnHexadecimal.addActionListener(e -> ejecutarOperacionEntero(n ->
            n + " en hexadecimal = " + Natural.aHexadecimal(n)));

        btnBaseN.addActionListener(e -> {
            try {
                int numero = Integer.parseInt(campoNumero.getText().trim());
                int base = Integer.parseInt(campoBase.getText().trim());
                if (numero < 0) {
                    mostrarErrorEnteros("Ingresa un número natural (>= 0).");
                    return;
                }
                String resultado = Natural.aBaseN(numero, base);
                mostrarEnteros(numero + " en base " + base + " = " + resultado);
            } catch (NumberFormatException ex) {
                mostrarErrorEnteros("Ingresa un número y una base válidos (la base es un entero, ej. 5).");
            } catch (IllegalArgumentException ex) {
                mostrarErrorEnteros(ex.getMessage());
            }
        });

        btnRomano.addActionListener(e -> ejecutarOperacionEntero(n ->
            n + " en romano = " + Natural.aRomano(n)));

        btnLiteral.addActionListener(e -> ejecutarOperacionEntero(n ->
            n + " en literal = " + Natural.aLiteral(n)));

        return panel;
    }

    /* INTEFAZ DE ERRORES */ 
    
    private interface OperacionEntero {
        String aplicar(int n);
    }

    private void ejecutarOperacionEntero(OperacionEntero operacion) {
        try {
            int numero = Integer.parseInt(campoNumero.getText().trim());
            if (numero < 0) {
                mostrarErrorEnteros("Ingresa un número natural (>= 0).");
                return;
            }
            mostrarEnteros(operacion.aplicar(numero));
        } catch (NumberFormatException ex) {
            mostrarErrorEnteros("Ingresa un número entero válido.");
        } catch (IllegalArgumentException ex) {
            mostrarErrorEnteros(ex.getMessage());
        }
    }

    private void mostrarEnteros(String mensaje) {
        areaResultadoEnteros.setText(mensaje);
    }

    private void mostrarErrorEnteros(String mensaje) {
        areaResultadoEnteros.setText("Error: " + mensaje);
    }
}
