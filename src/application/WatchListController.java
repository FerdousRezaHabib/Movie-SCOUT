
package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class WatchListController implements Initializable {
    @FXML private TilePane watchListTilePane;
    @FXML private Label emptyLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadWatchList();
    }

    public void loadWatchList() {
        watchListTilePane.getChildren().clear();
        List<Movie> watchList = DatabaseManager.getWatchlist();
        
        if (watchList.isEmpty()) {
            emptyLabel.setVisible(true);
            emptyLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: #636e72;");
            return;
        }
        
        emptyLabel.setVisible(false);
        
        for (Movie movie : watchList) {
            VBox card = createWatchListCard(movie);
            watchListTilePane.getChildren().add(card);
        }
    }

    private VBox createWatchListCard(Movie movie) {
        VBox card = new VBox(10);
        card.getStyleClass().add("watchlist-card");
        card.setPrefSize(200, 300);
        
        // Movie poster
        ImageView posterView = new ImageView();
        try {
            Image image = new Image(movie.getPosterUrl(), true);
            posterView.setImage(image);
        } catch (Exception e) {
            posterView.setImage(new Image("https://via.placeholder.com/200x300/dfe6e9/b2bec3?text=No+Poster"));
        }
        posterView.setFitWidth(200);
        posterView.setFitHeight(250);
        posterView.setPreserveRatio(false);
        
        // Movie title
        Label titleLabel = new Label(movie.getTitle());
        titleLabel.getStyleClass().add("watchlist-title");
        titleLabel.setWrapText(true);
        titleLabel.setMaxWidth(180);
        
        // Remove button
        Button removeButton = new Button("Remove");
        removeButton.getStyleClass().add("remove-button");
        removeButton.setOnAction(_ -> {
            DatabaseManager.removeFromWatchlist(movie.getId());
            loadWatchList(); // Refresh the list
        });
        
        card.getChildren().addAll(posterView, titleLabel, removeButton);
        return card;
    }
}


