package model;

public class MediaExtracted {

    private String nom;
    private String artiste;
    private String genre;
    private String format;
    private String typeMedia;
    private String cheminAcces;

    public MediaExtracted(String nom,String artiste,String genre,String format,String typeMedia,String cheminAcces){
        this.nom=nom;
        this.artiste=artiste;
        this.genre=genre;
        this.format=format;
        this.typeMedia=typeMedia;
        this.cheminAcces=cheminAcces;
    }

    public String getNom(){
        return nom;
    }

    public String getArtiste(){
        return artiste;
    }

    public String getGenre(){
        return genre;
    }

    public String getFormat(){
        return format;
    }

    public String getCheminAcces(){
        return cheminAcces;
    }

    public String getTypeMedia(){
        return typeMedia;
    }
}
