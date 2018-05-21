package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProjetOMP {

    private static Connection mConnection;
    private static String url = "jdbc:mysql://localhost:3306/zik?user=root&password=bdrbdrbdr";


    public static void connexion() {
        try {
            mConnection = DriverManager.getConnection(url);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }

    public static ResultSet search(String nomRecherche,String categorieActuelle) {

        return null;
    }

    public static ResultSet searchInArtiste(String nomRecherche,String categorieActuelle) {
        Statement statement;
        //PreparedStatement preparedStatement = null;
        try {


            statement = mConnection.createStatement();

            String sql = " SELECT *"
                    +" FROM " + categorieActuelle +
                    " WHERE nom = \""+ nomRecherche + "\";";

            ResultSet result = statement.executeQuery(sql);

            return result;

        } catch (Exception e) {
            System.out.println("erreur query1");
            return null;
        }

    }
    public static ResultSet searchInAlbums(String nomRecherche,String categorieActuelle) {
        Statement statement;
        //PreparedStatement preparedStatement = null;
        try {


            statement = mConnection.createStatement();

            String sql = " SELECT *"
                    +" FROM " + categorieActuelle +
                    " WHERE titre = \""+ nomRecherche + "\";";

            ResultSet result = statement.executeQuery(sql);

            return result;

        } catch (Exception e) {
            System.out.println("erreur query1");
            return null;
        }

    }public static ResultSet searchInPlaylist(String nomRecherche,String categorieActuelle) {
        Statement statement;
        //PreparedStatement preparedStatement = null;
        try {


            statement = mConnection.createStatement();

            String sql = " SELECT *"
                    +" FROM " + categorieActuelle +
                    " WHERE nomPlaylist = \""+ nomRecherche + "\";";

            ResultSet result = statement.executeQuery(sql);

            return result;

        } catch (Exception e) {
            System.out.println("erreur query1");
            return null;
        }

    }

    public static void insererPisteDansPistes(String nomTitre,String artiste){
        Statement statement;
        //PreparedStatement preparedStatement = null;
        try {


            statement = mConnection.createStatement();

            String sql = " INSERT"
                    +" INTO piste" +
                    " VALUES (\"" + nomTitre + "\",\""+ artiste + "\");";

            statement.executeQuery(sql);


        } catch (Exception e) {
            System.out.println("erreur query1");
        }

    }



}