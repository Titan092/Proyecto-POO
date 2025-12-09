package etsisi.upm.es;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.products.Category; // Asegúrate de tener acceso a tus modelos

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class JavaFXGuiDemo extends Application {

    // Referencia al controlador lógico existente
    private CommandHandler commandHandler;

    // Componentes de la UI
    private FlowPane productsContainer; // Panel para las tarjetas
    private TextArea terminalOutput;
    private TextField commandInput;
    private TextArea ticketView;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Inicializar lógica
        commandHandler = new CommandHandler();
        // Nota: commandHandler.init() en tu código original devuelve void o String
        // según la versión. Asumiremos que imprime a consola o devuelve texto.
        // Aquí capturamos la salida inicial simulada.
        String initMsg = "Welcome to the ticket module App (JavaFX Mode).\n";

        // --- LAYOUT PRINCIPAL ---
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        // ---------------------------------------------------------
        // 1. PANEL IZQUIERDO: CATÁLOGO VISUAL (ScrollPane + FlowPane)
        // ---------------------------------------------------------
        productsContainer = new FlowPane();
        productsContainer.setHgap(15);
        productsContainer.setVgap(15);
        productsContainer.setPadding(new Insets(15));
        productsContainer.setStyle("-fx-background-color: #f4f4f4;");

        ScrollPane scrollProducts = new ScrollPane(productsContainer);
        scrollProducts.setFitToWidth(true); // Las tarjetas se ajustan al ancho
        scrollProducts.setPannable(true);
        scrollProducts.setPrefWidth(750);
        scrollProducts.setStyle("-fx-background-color: transparent;");

        // Título del panel
        Label lblCatalogo = new Label("Catálogo Visual de Productos");
        lblCatalogo.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        lblCatalogo.setPadding(new Insets(0,0,10,0));

        VBox leftPane = new VBox(10, lblCatalogo, scrollProducts);
        VBox.setVgrow(scrollProducts, Priority.ALWAYS); // Ocupar todo el alto

        // ---------------------------------------------------------
        // 2. PANEL DERECHO: GESTIÓN Y TERMINAL
        // ---------------------------------------------------------
        SplitPane rightSplit = new SplitPane();
        rightSplit.setOrientation(javafx.geometry.Orientation.VERTICAL);
        rightSplit.setDividerPositions(0.4); // 40% arriba, 60% abajo

        // 2a. Pestañas de Información
        TabPane tabPane = new TabPane();

        // Tab Ticket
        ticketView = new TextArea();
        ticketView.setEditable(false);
        ticketView.setStyle("-fx-font-family: 'Monospaced'; -fx-font-size: 12px;");
        Tab tabTicket = new Tab("Ticket Actual", ticketView);
        tabTicket.setClosable(false);

        // Tab Usuarios (Ejemplo)
        TextArea userView = new TextArea("Lista de usuarios conectada al UserService...");
        userView.setEditable(false);
        Tab tabUsers = new Tab("Usuarios / Cajas", userView);
        tabUsers.setClosable(false);

        tabPane.getTabs().addAll(tabTicket, tabUsers);

        // 2b. Terminal Integrada
        VBox terminalBox = new VBox(5);
        terminalBox.setPadding(new Insets(5));
        terminalBox.setStyle("-fx-border-color: #cccccc; -fx-border-radius: 5;");

        Label lblTerminal = new Label("Terminal de Comandos");
        lblTerminal.setStyle("-fx-font-weight: bold;");

        Button btnLoadFile = new Button("📂 Cargar Script");
        btnLoadFile.setOnAction(e -> loadScriptAction(primaryStage));

        HBox terminalHeader = new HBox(10, lblTerminal, new Region(), btnLoadFile);
        HBox.setHgrow(terminalHeader.getChildren().get(1), Priority.ALWAYS); // Espaciador

        terminalOutput = new TextArea();
        terminalOutput.setEditable(false);
        // Estilo "Hacker" para la terminal
        terminalOutput.setStyle("-fx-control-inner-background: black; -fx-text-fill: #00ff00; -fx-font-family: 'Monospaced';");
        terminalOutput.setText(initMsg);

        commandInput = new TextField();
        commandInput.setPromptText("Escribe un comando (ej: ticket print) y pulsa Enter...");
        commandInput.setStyle("-fx-background-color: #333; -fx-text-fill: white; -fx-font-family: 'Monospaced';");
        commandInput.setOnAction(e -> executeCommand(commandInput.getText())); // Al pulsar Enter

        terminalBox.getChildren().addAll(terminalHeader, terminalOutput, commandInput);
        VBox.setVgrow(terminalOutput, Priority.ALWAYS);

        rightSplit.getItems().addAll(tabPane, terminalBox);

        // Añadir paneles al root
        root.setCenter(leftPane);
        root.setRight(rightSplit);

        // --- INICIALIZACIÓN DE DATOS DEMO ---
        // Ejecutamos algunos comandos invisibles para poblar el sistema
        commandHandler.init(); // Inicializa servicios internos
        executeCommand("prod add 1 \"Libro JavaFX\" BOOK 30");
        executeCommand("prod add 2 \"Camiseta POO\" CLOTHES 15");
        executeCommand("prod addMeeting 3 \"Hackathon\" 5 2025-12-01 100");
        executeCommand("prod addFood 4 \"Pizza Party\" 10 2025-12-01 50");

        refreshProductCards(); // Dibujar las tarjetas

        // Configurar Escena
        Scene scene = new Scene(root, 1200, 800);
        primaryStage.setTitle("Sistema TPV - Proyecto POO (JavaFX)");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Procesa el comando contra la lógica de negocio y actualiza la GUI
     */
    private void executeCommand(String input) {
        if (input == null || input.trim().isEmpty()) return;

        terminalOutput.appendText("\ntUPM> " + input + "\n");
        commandInput.clear();

        // Llamada a tu lógica (adaptado a tu estructura)
        String[] args = input.split(" ");
        // Usamos applyCommand. Nota: Tu CommandHandler actual devuelve un objeto Command.
        // Asumimos que Command tiene un método getMessage() con el resultado.
        var cmd = commandHandler.applyCommand(args);

        if (cmd != null) {
            String response = cmd.getMessage();
            if (response != null) {
                terminalOutput.appendText(response + "\n");

                // Actualizar vistas si es necesario
                if (input.startsWith("prod")) refreshProductCards();
                if (input.startsWith("ticket")) {
                    // Si el comando devolvió info del ticket, la mostramos en el panel lateral
                    if(response.startsWith("Ticket :")) {
                        ticketView.setText(response);
                    }
                }
            }
        } else {
            terminalOutput.appendText("Comando no reconocido.\n");
        }
    }

    /**
     * Carga un fichero de texto y ejecuta línea por línea
     */
    private void loadScriptAction(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Script de Entrada");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos de Texto", "*.txt"));
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            try (Scanner scanner = new Scanner(file)) {
                terminalOutput.appendText("--- INICIO SCRIPT: " + file.getName() + " ---\n");
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    if (!line.trim().isEmpty() && !line.startsWith("echo")) {
                        executeCommand(line);
                    }
                }
                terminalOutput.appendText("--- FIN SCRIPT ---\n");
            } catch (FileNotFoundException ex) {
                terminalOutput.appendText("Error: Archivo no encontrado.\n");
            }
        }
    }

    /**
     * Dibuja las tarjetas de productos en el FlowPane
     */
    private void refreshProductCards() {
        productsContainer.getChildren().clear();

        // AQUÍ CONECTAS CON TUS DATOS REALES
        // Como 'productService' es privado en tu CommandHandler,
        // estoy usando datos dummy para la demo visual.
        // Para que funcione real: Haz publico productService en CommandHandler
        // for (var prod : commandHandler.productService.getProducts().values()) { ... }
        for (var entry : commandHandler.productService.getProducts().entrySet()) {
            var prod = entry.getValue();
            // Lógica para determinar el tipo visual (color)
            String type = "Standard";
            if (prod.getClass().getSimpleName().equals("Food")) type = "Food";
            if (prod.getClass().getSimpleName().equals("Event")) type = "Event";
            if (prod.getClass().getSimpleName().equals("Custom")) type = "Custom";


        }

        // --- DATOS SIMULADOS PARA DEMO VISUAL ---
        addProductCard(1, "Libro JavaFX", "BOOK", 30.0, "Standard");
        addProductCard(2, "Camiseta POO", "CLOTHES", 15.0, "Standard");
        addProductCard(3, "Hackathon 2025", "MEETING", 5.0, "Event");
        addProductCard(4, "Pizza Party", "FOOD", 10.0, "Food");
        addProductCard(5, "Sudadera Custom", "CLOTHES", 45.0, "Custom");
    }

    private void addProductCard(int id, String name, String category, double price, String type) {
        // Estructura de la Tarjeta (VBox)
        VBox card = new VBox(10);
        card.setPadding(new Insets(15));
        card.setPrefWidth(200);
        card.setPrefHeight(180);
        card.setAlignment(Pos.CENTER_LEFT);

        // Estilos CSS según el tipo de producto
        String baseStyle = "-fx-background-radius: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 5, 0, 0, 2);";
        String colorStyle = switch (type) {
            case "Food" -> "-fx-background-color: #98FB98;"; // Verde Pastel
            case "Event" -> "-fx-background-color: #FFD700;"; // Dorado
            case "Custom" -> "-fx-background-color: #DDA0DD;"; // Ciruela
            default -> "-fx-background-color: #87CEFA;";      // Azul Cielo
        };
        card.setStyle(baseStyle + colorStyle);

        // Contenido
        Label lblType = new Label(type.toUpperCase());
        lblType.setFont(Font.font("System", FontWeight.BOLD, 10));
        lblType.setTextFill(Color.web("#555555"));

        Label lblName = new Label(name);
        lblName.setFont(Font.font("System", FontWeight.BOLD, 16));
        lblName.setWrapText(true);

        Label lblCat = new Label(category);
        lblCat.setStyle("-fx-background-color: rgba(255,255,255,0.5); -fx-padding: 3 8; -fx-background-radius: 5;");

        Label lblPrice = new Label(String.format("%.2f €", price));
        lblPrice.setFont(Font.font("System", FontWeight.BOLD, 14));

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        Button btnAdd = new Button("Añadir al Ticket");
        btnAdd.setMaxWidth(Double.MAX_VALUE); // Botón ancho completo
        btnAdd.setStyle("-fx-background-color: rgba(0,0,0,0.1); -fx-cursor: hand;");
        btnAdd.setOnAction(e -> {
            // Acción rápida: Escribe el comando en la terminal input para que el usuario complete cantidad
            commandInput.setText("ticket add <TICKET_ID> <CASH_ID> " + id + " 1");
            commandInput.requestFocus();
            commandInput.selectRange(11, 32); // Seleccionar los placeholders para fácil edición
        });

        card.getChildren().addAll(lblType, lblName, lblCat, spacer, lblPrice, btnAdd);

        productsContainer.getChildren().add(card);
    }
}
