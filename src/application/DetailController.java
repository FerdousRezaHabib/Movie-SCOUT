package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
//import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;

public class DetailController {
    @FXML private ImageView posterImage;
    @FXML private Label titleLabel;
    @FXML private Label yearLabel;
    @FXML private Label genreLabel;
    @FXML private Label durationLabel;
    @FXML private Label ratingLabel;
    @FXML private Label castLabel;
    @FXML private Label plotLabel;
    @FXML private Button watchLaterButton;
    @FXML private VBox mainContainer;

    private Movie movie;

    public void setMovie(Movie movie) {
        this.movie = movie;
        updateUI();
    }

    private void updateUI() {
        mainContainer.setAlignment(Pos.TOP_CENTER);
        
        try {
            posterImage.setImage(new Image(movie.getPosterUrl()));
        } catch (Exception e) {
            posterImage.setImage(new Image("https://via.placeholder.com/300x450/dfe6e9/b2bec3?text=No+Poster"));
        }
        
        titleLabel.setText(movie.getTitle());
        titleLabel.setAlignment(Pos.CENTER);
        yearLabel.setText(String.valueOf(movie.getYear()));
        genreLabel.setText(movie.getGenre());
        durationLabel.setText(movie.getDuration());
        ratingLabel.setText(String.format("★ %.1f/10", movie.getRating()));
        castLabel.setText(movie.getCast());
        plotLabel.setText(movie.getPlot());
        
        if (DatabaseManager.isInWatchlist(movie.getId())) {
            watchLaterButton.setText("✓ In Watch List");
        } else {
            watchLaterButton.setText("+ Add to Watch List");
        }
    }

    @FXML
    private void handleWatchLater() {
        if (DatabaseManager.isInWatchlist(movie.getId())) {
            DatabaseManager.removeFromWatchlist(movie.getId());
            watchLaterButton.setText("+ Add to Watch List");
        } else {
            DatabaseManager.addToWatchlist(movie.getId());
            watchLaterButton.setText("✓ In Watch List");
        }
    }
}

