package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML private TextField searchField;
    @FXML private FlowPane moviesFlowPane;
    @FXML private ComboBox<String> genreComboBox;
    @FXML private Button watchListButton;
    @FXML private ToggleButton themeToggle;

    private boolean darkMode = false;
    private static final double SCENE_WIDTH = 1000;
    private static final double SCENE_HEIGHT = 700;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize genre filter with colorful genres
        genreComboBox.getItems().addAll("All", "Action", "Adventure", "Animation", 
                                      "Comedy", "Crime", "Documentary", "Drama", 
                                      "Fantasy", "Horror", "Mystery", "Romance", 
                                      "Sci-Fi", "Thriller");
        genreComboBox.setValue("All");
        
        // Set prompt text with style
        searchField.setPromptText("🔍 Search movies...");
        
        // Load all movies initially
        loadMovies(DatabaseManager.getAllMovies());
        
        // Set up event handlers
        searchField.textProperty().addListener((_, _, newValue) -> {
            if (newValue.isEmpty()) {
                loadMovies(DatabaseManager.getAllMovies());
            } else {
                loadMovies(DatabaseManager.searchMovies(newValue));
            }
        });
        
        genreComboBox.setOnAction(_ -> {
            String selectedGenre = genreComboBox.getValue();
            if (selectedGenre.equals("All")) {
                loadMovies(DatabaseManager.getAllMovies());
            } else {
                loadMovies(DatabaseManager.getMoviesByGenre(selectedGenre));
            }
        });
        
        watchListButton.setOnAction(_ -> openWatchList());
        themeToggle.setOnAction(_ -> toggleTheme());
    }

    private void loadMovies(List<Movie> movies) {
        moviesFlowPane.getChildren().clear();
        
        if (movies.isEmpty()) {
            Label emptyLabel = new Label("No movies found");
            emptyLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: #636e72;");
            moviesFlowPane.getChildren().add(emptyLabel);
            return;
        }
        
        for (Movie movie : movies) {
            VBox movieCardContainer = new VBox();
            movieCardContainer.getStyleClass().add("movie-card-container");
            movieCardContainer.getChildren().add(createMovieCard(movie));
            moviesFlowPane.getChildren().add(movieCardContainer);
        }
    }

    private VBox createMovieCard(Movie movie) {
        VBox card = new VBox(8);
        card.getStyleClass().add("movie-card");
        card.setPrefSize(200, 320);
        card.setAlignment(Pos.CENTER);
        
        // Movie poster with centered alignment
        ImageView posterView = new ImageView();
        try {
            Image image = new Image(movie.getPosterUrl(), true);
            posterView.setImage(image);
        } catch (Exception e) {
            posterView.setImage(new Image("https://via.placeholder.com/200x300/dfe6e9/b2bec3?text=No+Poster"));
        }
        posterView.setFitWidth(200);
        posterView.setFitHeight(300);
        posterView.setPreserveRatio(false);
        
        // Movie title with centered alignment
        Label titleLabel = new Label(movie.getTitle());
        titleLabel.getStyleClass().add("movie-title");
        titleLabel.setWrapText(true);
        titleLabel.setMaxWidth(180);
        titleLabel.setAlignment(Pos.CENTER);
        
        // Rating with centered alignment
        HBox ratingBox = new HBox(5);
        ratingBox.setAlignment(Pos.CENTER);
        Label ratingLabel = new Label(String.format("★ %.1f", movie.getRating()));
        ratingLabel.getStyleClass().add("movie-rating");
        ratingBox.getChildren().add(ratingLabel);
        
        // Buttons container with centered alignment
        HBox buttonsBox = new HBox(10);
        buttonsBox.setAlignment(Pos.CENTER);
        
        // Details button
        Button detailsButton = new Button("Details");
        detailsButton.getStyleClass().add("details-button");
        detailsButton.setOnAction(_ -> showMovieDetails(movie));
        
        // Watch later button
        Button watchLaterButton = new Button();
        watchLaterButton.getStyleClass().add("watch-later-button");
        if (DatabaseManager.isInWatchlist(movie.getId())) {
            watchLaterButton.setText("✓ Saved");
        } else {
            watchLaterButton.setText("+ Watch Later");
        }
        watchLaterButton.setOnAction(_ -> toggleWatchLater(movie, watchLaterButton));
        
        buttonsBox.getChildren().addAll(detailsButton, watchLaterButton);
        card.getChildren().addAll(posterView, titleLabel, ratingBox, buttonsBox);
        return card;
    }

    private void toggleWatchLater(Movie movie, Button watchLaterButton) {
        if (DatabaseManager.isInWatchlist(movie.getId())) {
            DatabaseManager.removeFromWatchlist(movie.getId());
            watchLaterButton.setText("+ Watch Later");
        } else {
            DatabaseManager.addToWatchlist(movie.getId());
            watchLaterButton.setText("✓ Saved");
        }
    }

    private void showMovieDetails(Movie movie) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("detail-movie-view.fxml"));
            Parent root = loader.load();
            
            DetailController controller = loader.getController();
            controller.setMovie(movie);
            
            Stage stage = new Stage();
            stage.setScene(new Scene(root, SCENE_WIDTH, SCENE_HEIGHT));
            
            // Apply current theme to new window
            if (darkMode) {
                stage.getScene().getStylesheets().add(getClass().getResource("dark-styles.css").toExternalForm());
            } else {
                stage.getScene().getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
            }
            
            stage.setTitle(movie.getTitle());
            stage.getIcons().add(new Image("https://cdn-icons-png.flaticon.com/512/3176/3176272.png"));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openWatchList() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("watch-list-view.fxml"));
            Parent root = loader.load();
            
            WatchListController controller = loader.getController();
            controller.loadWatchList();
            
            Stage stage = new Stage();
            stage.setScene(new Scene(root, SCENE_WIDTH, SCENE_HEIGHT));
            
            // Apply current theme to new window
            if (darkMode) {
                stage.getScene().getStylesheets().add(getClass().getResource("dark-styles.css").toExternalForm());
            } else {
                stage.getScene().getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
            }
            
            stage.setTitle("My Watch List");
            stage.getIcons().add(new Image("https://cdn-icons-png.flaticon.com/512/3176/3176272.png"));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void toggleTheme() {
        darkMode = !darkMode;
        Scene scene = moviesFlowPane.getScene();
        
        if (darkMode) {
            scene.getStylesheets().remove(getClass().getResource("styles.css").toExternalForm());
            scene.getStylesheets().add(getClass().getResource("dark-styles.css").toExternalForm());
            themeToggle.setText("☀️ Light Mode");
        } else {
            scene.getStylesheets().remove(getClass().getResource("dark-styles.css").toExternalForm());
            scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
            themeToggle.setText("🌙 Dark Mode");
        }
    }
}

