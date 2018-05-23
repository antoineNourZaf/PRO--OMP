package model;

public class MediaExtracted {

    private String id;
    private String titre;
    private String artiste;
    private String format;
    private String typeMedia;
    private String duree;
    private String cheminAcces;
    private String album;
    private String annee;
    private String image;


    public MediaExtracted(String id,String titre,String format,String artiste,String duree,String album,String annee,String image,String typeMedia,String cheminAcces){
        this.id = id;
        this.titre=titre;
        this.format=format;
        this.artiste=artiste;
        this.duree=duree;
        this.album = album;
        this.annee = annee;
        this.image=image;
        this.cheminAcces=cheminAcces;
        this.typeMedia=typeMedia;

    }

    public String getTitre(){
        return titre;
    }

    public String getId(){
        return id;
    }

    public String getAlbum(){
        return album;
    }

    public String getAnnee(){
        return annee;
    }
    public String getArtiste(){
        return artiste;
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

    public String getDuree(){
        return duree;
    }

    public String getImage(){
        return image;
    }
}
