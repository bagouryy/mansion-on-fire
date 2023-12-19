import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Furfeux{

    Terrain terrain;
    Joueur joueur;
    private static final String JDBC_URL = "jdbc:sqlite:results.db";


    public Furfeux(String f) {
        this.terrain = new Terrain(f); // Initialise le terrain à partir d'un fichier de description
        this.joueur = terrain.getJoueur(); // Obtient le joueur à partir du terrain
    }

    public void tour() { // Méthode pour effectuer un tour du jeu
        // Parcourt toutes les cases du terrain
        for (int i = 1; i < terrain.getHauteur() - 1; i++) {
            for (int j = 1; j < terrain.getLargeur() - 1; j++) {
                // Si la case est traversable, applique la propagation du feu à ses voisines
                if(terrain.getCase(i,j) instanceof CaseTraversable){
                    ((CaseTraversable) terrain.getCase(i,j)).brule(terrain.getVoisinesTraversables(i,j));
                }
            }
        }
        joueur.burn(joueur.getCase().getChaleur());  // Applique les dégâts au joueur en fonction de la chaleur de sa case actuelle
    }

    public boolean partieFinie() {
        return joueur.estSortie() || joueur.getResistance() <= 0;
    }
    // Méthode pour vérifier si la partie est terminée

    public static void main(String[] args) {
        int tempo = 100;
        JFrame frame = new JFrame("Furfeux");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Start start = new Start(frame);
        frame.getContentPane().add(start);
        frame.pack();
        frame.setVisible(true);


        while (frame.isVisible()) {
            try {
                // Sleep for 0.5 seconds (500 milliseconds)
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        String user = start.getName();
        System.out.println(user);

        int diff = start.getDifficulty();
        System.out.println(diff);
        Furfeux jeu;
        if(diff == 3) {
            jeu = new Furfeux("terrains/new.txt");
        }else if(diff == 1){
            jeu = new Furfeux("terrains/manoir.txt");
        }else{
            throw new IllegalArgumentException("Choose a difficulty");
        }
        FenetreJeu graphic = new FenetreJeu(jeu.terrain, user);
        AtomicInteger bucketTimer = new AtomicInteger();
        long startTime = System.currentTimeMillis();
        Timer timer = new Timer(tempo, e -> {
            jeu.tour();
            graphic.updateGUI();
            graphic.repaint();
            bucketTimer.getAndIncrement();
            if(bucketTimer.get() == 50){
                jeu.getJoueur().giveBuckets();
                bucketTimer.set(0);
            }
            if (jeu.partieFinie()) {
                long endTime = System.currentTimeMillis();
                long time = endTime - startTime;
                graphic.ecranFinal(Math.max(0, jeu.joueur.getResistance()),time);
                //sql.INSERT INTO
                long min = (time / (1000 * 60));
                long sec = (time / 1000) % 60;
                long ms = time % 1000;
                if(jeu.joueur.getResistance() > 0) {
                    insertIntoDatabase(user, min, sec, ms, graphic.getMario(), jeu.joueur.getResistance());
                }
                ((Timer)e.getSource()).stop();
            }
        });
//        timer = new Timer(tempo, e -> );
        timer.start();
    }

    private Joueur getJoueur() {
        return joueur;
    }

    private static void insertIntoDatabase(String username, long min, long sec, long ms, boolean mario, int health) {
        try {
            // Load the JDBC driver
            Class.forName("org.sqlite.JDBC");

            // Establish a connection
            Connection connection = DriverManager.getConnection(JDBC_URL);

            if (connection != null && !connection.isClosed()) {
                System.out.println("Connection successful.");
                // Perform further operations or queries here
            } else {
                System.out.println("Connection failed.");
            }

            // Create an INSERT SQL statement
            String insertSql = "INSERT INTO scores (username, time_minutes, time_seconds , time_ms, health, mario) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertSql);

            // Set values for the placeholders
            preparedStatement.setString(1, username);
            preparedStatement.setLong(2, min);
            preparedStatement.setLong(3, sec);
            preparedStatement.setLong(4, ms);
            preparedStatement.setInt(5,health);
            preparedStatement.setBoolean(6,mario);



            // Execute the INSERT statement
            int rowsAffected = preparedStatement.executeUpdate();

            // Check if the insertion was successful
            if (rowsAffected > 0) {
                System.out.println("Insertion into database successful.");
            } else {
                System.out.println("Insertion into database failed.");
            }

            // Close resources
            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }



}
