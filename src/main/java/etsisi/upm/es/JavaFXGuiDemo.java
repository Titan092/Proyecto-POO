package etsisi.upm.es;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class JavaFXGuiDemo extends Application {

    // Lógica de negocio (Backend)
    private CommandHandler commandHandler;

    // Componentes inyectados desde el FXML
    @FXML private FlowPane productsContainer;
    @FXML private TextArea ticketView;
    @FXML private TextArea terminalOutput;
    @FXML private TextField commandInput;

    // --- 1. ENTRY POINT (LAUNCHER) ---
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Cargar el FXML
        Scene scene;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view.fxml"));
            Parent root = loader.load();

            // Cargar el CSS
            scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        }catch (Exception e) {
            System.out.println("Error en la carga de archivos FXML/CSS: " + e.getMessage());
            return;
        }
        primaryStage.setTitle("Sistema TPV - Proyecto POO (FXML + CSS)");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // --- 2. LÓGICA DEL CONTROLADOR ---

    /**
     * Se ejecuta automáticamente al cargar el FXML.
     * Aquí inicializamos la lógica de negocio.
     */
    @FXML
    public void initialize() {
        commandHandler = new CommandHandler();
        commandHandler.init(); // Inicializar servicios

        printToTerminal("Welcome to the Shop App (FXML Mode).");

        // Cargar datos de prueba iniciales
        runInternalCommand("prod add 1 \"Libro Java\" BOOK 25");
        runInternalCommand("prod addFood 2 \"Pizza\" 10 2025-12-01 50");
        runInternalCommand("prod addMeeting 3 \"Tech Conf\" 5 2025-12-01 100");

        refreshProductCards();
    }

    /**
     * Evento al pulsar Enter en el campo de texto
     */
    @FXML
    private void onCommandEntered() {
        String input = commandInput.getText();
        if (input.trim().isEmpty()) return;

        printToTerminal("tUPM> " + input);
        runInternalCommand(input);
        commandInput.clear();
    }

    /**
     * Evento del botón "Cargar Script"
     */
    @FXML
    private void loadScriptAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Texto", "*.txt"));
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            try (Scanner scanner = new Scanner(file)) {
                printToTerminal("--- SCRIPT: " + file.getName() + " ---");
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    if (!line.trim().isEmpty() && !line.startsWith("echo")) {
                        runInternalCommand(line);
                    }
                }
                printToTerminal("--- FIN SCRIPT ---");
            } catch (FileNotFoundException e) {
                printToTerminal("Error: Archivo no encontrado.");
            }
        }
    }

    // --- MÉTODOS AUXILIARES ---

    private void runInternalCommand(String cmdLine) {
        String[] args = cmdLine.split(" ");
        // Llamada al backend
        var cmd = commandHandler.applyCommand(args);

        if (cmd != null && cmd.getMessage() != null) {
            printToTerminal(cmd.getMessage());

            // Actualizar UI si es necesario
            if (cmdLine.startsWith("prod")) refreshProductCards();
            if (cmdLine.startsWith("ticket") && cmd.getMessage().startsWith("Ticket :")) {
                ticketView.setText(cmd.getMessage());
            }
        } else {
            printToTerminal("Comando no reconocido o sin salida.");
        }
    }

    private void printToTerminal(String msg) {
        terminalOutput.appendText(msg + "\n");
    }

    /**
     * Genera las tarjetas visuales dinámicamente.
     * Simula el acceso a datos (aquí deberías usar tu getter real de ProductService)
     */
    private void refreshProductCards() {
        productsContainer.getChildren().clear();

        // -- SIMULACIÓN DE DATOS (Conecta esto con commandHandler.getProductService()...) --
        createCard(1, "Libro Java", "BOOK", "25.0 €", "card-standard");
        createCard(2, "Pizza Carbonara", "FOOD", "10.0 €", "card-food");
        createCard(3, "Conferencia Tech", "MEETING", "5.0 €", "card-event");
        createCard(4, "Sudadera UPM", "CLOTHES", "40.0 €", "card-custom");
    }

    private void createCard(int id, String name, String category, String price, String cssClass) {
        VBox card = new VBox(5);
        card.getStyleClass().addAll("product-card", cssClass);

        Label lblType = new Label(category);
        lblType.getStyleClass().add("card-category");

        Label lblName = new Label(name);
        lblName.getStyleClass().add("card-title");
        lblName.setWrapText(true);

        Label lblPrice = new Label(price);
        lblPrice.getStyleClass().add("card-price");

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        Button btnAdd = new Button("Añadir al Ticket");
        btnAdd.setMaxWidth(Double.MAX_VALUE);
        btnAdd.getStyleClass().add("card-button");
        btnAdd.setOnAction(e -> {
            commandInput.setText("ticket add <ID_TICKET> <ID_CAJA> " + id + " 1");
            commandInput.requestFocus();
        });

        card.getChildren().addAll(lblType, lblName, spacer, lblPrice, btnAdd);
        productsContainer.getChildren().add(card);
    }
}