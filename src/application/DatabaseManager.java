package application;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/movie_browser";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static List<Movie> getAllMovies() {
        List<Movie> movies = new ArrayList<>();
        String query = "SELECT * FROM movies";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                movies.add(createMovieFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }

    public static List<Movie> searchMovies(String searchTerm) {
        List<Movie> movies = new ArrayList<>();
        String query = "SELECT * FROM movies WHERE title LIKE ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, "%" + searchTerm + "%");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                movies.add(createMovieFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }

    public static List<Movie> getMoviesByGenre(String genre) {
        List<Movie> movies = new ArrayList<>();
        String query = "SELECT * FROM movies WHERE genre = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, genre);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                movies.add(createMovieFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }

    public static List<Movie> getWatchlist() {
        List<Movie> movies = new ArrayList<>();
        String query = "SELECT m.* FROM movies m JOIN watchlist w ON m.id = w.movie_id";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                movies.add(createMovieFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }

    public static void addToWatchlist(int movieId) {
        String query = "INSERT INTO watchlist (movie_id) VALUES (?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, movieId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeFromWatchlist(int movieId) {
        String query = "DELETE FROM watchlist WHERE movie_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, movieId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean isInWatchlist(int movieId) {
        String query = "SELECT COUNT(*) FROM watchlist WHERE movie_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, movieId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static Movie createMovieFromResultSet(ResultSet rs) throws SQLException {
        return new Movie(
            rs.getInt("id"),
            rs.getString("title"),
            rs.getString("genre"),
            rs.getInt("year"),
            rs.getString("duration"),
            rs.getDouble("rating"),
            rs.getString("plot"),
            rs.getString("poster_url"),
            rs.getString("cast")
        );
    }
}