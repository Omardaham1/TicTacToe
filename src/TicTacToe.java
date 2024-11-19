import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class TicTacToe extends Application {

    private Button[][] buttons = new Button[5][5];
    private boolean isXturn = true;
    private Label label;

    @Override
    public void start(Stage primaryStage) {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                buttons[i][j] = new Button();
                buttons[i][j].setPrefSize(50, 50);
                buttons[i][j].setOnAction(e -> handleButtonClick((Button) e.getSource()));
                gridPane.add(buttons[i][j], j, i);
            }
        }

        label = new Label("Player X's turn");

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(gridPane);
        borderPane.setBottom(label);
        BorderPane.setAlignment(label, Pos.CENTER);

        Scene scene = new Scene(borderPane, 300, 350);
        primaryStage.setTitle("TicTacToe");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleButtonClick(Button button) {
        if (button.getText().isEmpty()) {
            if (isXturn) {
                button.setText("X");
                label.setText("Player O's turn");
            } else {
                button.setText("O");
                label.setText("Player X's turn");
            }
            isXturn = !isXturn;
            checkWinner();
        }
    }

    private void checkWinner() {
        // Check rows
        for (int i = 0; i < 5; i++) {
            String val = buttons[i][0].getText();
            if (!val.isEmpty() && isConsecutive(buttons[i], val)) {
                gameOver(val);
                return;
            }
        }

        // Check columns
        for (int i = 0; i < 5; i++) {
            String val = buttons[0][i].getText();
            if (!val.isEmpty() && isConsecutive(getColumn(buttons, i), val)) {
                gameOver(val);
                return;
            }
        }

        // Check diagonals
        String val1 = buttons[0][0].getText();
        if (!val1.isEmpty() && isConsecutive(getDiagonal1(buttons), val1)) {
            gameOver(val1);
            return;
        }

        String val2 = buttons[0][4].getText();
        if (!val2.isEmpty() && isConsecutive(getDiagonal2(buttons), val2)) {
            gameOver(val2);
            return;
        }
    }

    private boolean isConsecutive(Button[] buttons, String val) {
        for (int i = 0; i < buttons.length - 4; i++) {
            if (!buttons[i].getText().equals(val) ||
                    !buttons[i + 1].getText().equals(val) ||
                    !buttons[i + 2].getText().equals(val) ||
                    !buttons[i + 3].getText().equals(val) ||
                    !buttons[i + 4].getText().equals(val)) {
                return false;
            }
        }
        return true;
    }

    private Button[] getColumn(Button[][] buttons, int col) {
        Button[] column = new Button[5];
        for (int i = 0; i < 5; i++) {
            column[i] = buttons[i][col];
        }
        return column;
    }

    private Button[] getDiagonal1(Button[][] buttons) {
        Button[] diagonal = new Button[5];
        for (int i = 0; i < 5; i++) {
            diagonal[i] = buttons[i][i];
        }
        return diagonal;
    }

    private Button[] getDiagonal2(Button[][] buttons) {
        Button[] diagonal = new Button[5];
        for (int i = 0; i < 5; i++) {
            diagonal[i] = buttons[i][4 - i];
        }
        return diagonal;
    }

    private void gameOver(String winner) {
        label.setText("Player " + winner + " wins!");
        for (Button[] row : buttons) {
            for (Button button : row) {
                button.setDisable(true);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}