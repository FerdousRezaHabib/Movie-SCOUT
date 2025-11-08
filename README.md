# 🎬 Movie Browser

A modern JavaFX desktop application for browsing, searching, and managing your personal movie watchlist. Built with a clean, responsive interface and persistent MySQL storage.

![Java](https://img.shields.io/badge/Java-24-orange)
![JavaFX](https://img.shields.io/badge/JavaFX-17+-blue)
![MySQL](https://img.shields.io/badge/MySQL-Compatible-green)

## ✨ Features

- **🎭 Rich Movie Catalog** - Browse movies with posters, genres, runtime, cast, and ratings
- **🔍 Smart Search & Filter** - Find movies by title or filter by genre
- **📝 Detailed Movie Views** - Comprehensive information for each movie
- **❤️ Persistent Watch List** - Add/remove movies with local database storage
- **🌙 Theme Support** - Toggle between Light and Dark modes
- **💾 Local Database** - MySQL backend for data persistence

## 🚀 Quick Start

### Prerequisites

- **Java 24** (JDK 24)
- **JavaFX 17+** (on module path)
- **MySQL Server** (or compatible database)
- **MySQL Connector/J**

### Installation

1. **Clone & Setup**
   ```bash
   git clone <repository-url>
   cd movie-browser
   ```

2. **Database Setup**
   ```sql
   CREATE DATABASE movie_browser;
   USE movie_browser;
   
   -- Create movies table
   CREATE TABLE movies (
       id INT PRIMARY KEY AUTO_INCREMENT,
       title VARCHAR(255) NOT NULL,
       genre VARCHAR(100),
       runtime INT,
       release_year INT,
       director VARCHAR(255),
       cast TEXT,
       rating DECIMAL(3,1),
       poster_url VARCHAR(500),
       description TEXT
   );
   
   -- Create watchlist table
   CREATE TABLE watchlist (
       id INT PRIMARY KEY AUTO_INCREMENT,
       movie_id INT,
       added_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
       FOREIGN KEY (movie_id) REFERENCES movies(id)
   );
   ```

3. **Configure Database**  
   Update credentials in `src/application/DatabaseManager.java` if needed:
   ```java
   private static final String URL = "jdbc:mysql://localhost:3306/movie_browser";
   private static final String USER = "your_username";
   private static final String PASSWORD = "your_password";
   ```

### Running the Application

#### IDE (Eclipse Recommended)
1. Open project in Eclipse
2. Ensure JavaFX libraries are configured on module path
3. Verify MySQL connector JAR path in `.classpath`
4. Run main class: `application.Main`

#### Command Line
**Windows:**
```cmd
set PATH_TO_FX="C:\path\to\javafx\lib"
set MYSQL_JAR="C:\path\to\mysql-connector.jar"

javac -d bin --module-path %PATH_TO_FX% --add-modules javafx.controls,javafx.fxml src\application\*.java src\module-info.java

java --module-path %PATH_TO_FX%;%MYSQL_JAR% --add-modules javafx.controls,javafx.fxml -cp bin;%MYSQL_JAR% application.Main
```

**macOS/Linux:**
```bash
export PATH_TO_FX="/path/to/javafx/lib"
export MYSQL_JAR="/path/to/mysql-connector.jar"

javac -d bin --module-path $PATH_TO_FX --add-modules javafx.controls,javafx.fxml src/application/*.java src/module-info.java

java --module-path $PATH_TO_FX:$MYSQL_JAR --add-modules javafx.controls,javafx.fxml -cp bin:$MYSQL_JAR application.Main
```

## 🏗️ Project Structure

```
src/
├── application/
│   ├── Main.java                 # Application entry point
│   ├── MainController.java       # Main browser controller
│   ├── DetailController.java     # Movie detail view controller
│   ├── WatchListController.java  # Watchlist management
│   ├── DatabaseManager.java      # Database operations
│   ├── Movie.java               # Movie data model
│   ├── main-movie-browser.fxml  # Main layout
│   ├── detail-movie-view.fxml   # Detail view layout
│   ├── watch-list-view.fxml     # Watchlist layout
│   ├── styles.css               # Light theme
│   └── dark-styles.css          # Dark theme
└── module-info.java             # Java module descriptor
```

## 🎨 Theming

Switch between themes seamlessly:
- **Light Theme**: `styles.css`
- **Dark Theme**: `dark-styles.css`

Theme switching is implemented in `MainController.java`.

## 🔧 Technical Details

### Key Components

- **Controllers**: Handle UI interactions and business logic
- **DatabaseManager**: Manages all MySQL operations
- **Movie Model**: Data representation for movies
- **FXML Layouts**: Define the application's user interface
- **CSS Styling**: Customizable appearance and themes

### Database Operations

```java
// Example operations from DatabaseManager
List<Movie> getAllMovies();
List<Movie> searchMovies(String query);
List<Movie> getMoviesByGenre(String genre);
void addToWatchlist(int movieId);
void removeFromWatchlist(int movieId);
List<Movie> getWatchlist();
boolean isInWatchlist(int movieId);
```

## 🐛 Troubleshooting

### Common Issues

1. **Missing MySQL Driver**
   - Ensure connector JAR is present
   - Update path in `.classpath` if needed

2. **JavaFX Runtime Errors**
   - Confirm JavaFX SDK is on module path
   - Verify `javafx.controls` and `javafx.fxml` modules are added

3. **FXML Loading Errors**
   - Check controller declarations in FXML files match package/class names
   - All controllers are in `application` package

4. **Database Connection Issues**
   - Verify MySQL server is running
   - Check credentials in `DatabaseManager.java`
   - Ensure database `movie_browser` exists

### Module Configuration

The project uses Java modules. Key dependencies in `module-info.java`:
```java
requires javafx.controls;
requires javafx.fxml;
requires java.sql;
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📝 Notes

- Project is configured as a modular Java application
- `.classpath` references local MySQL connector JAR - update for your environment
- Sample seed data SQL available upon request

## 📄 License

This project is open source and available under the [MIT License](LICENSE).

---
