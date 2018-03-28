package Exceptions;

public class LibraryNotFoundException extends Exception {

   public LibraryNotFoundException() {
   }

   @Override
   public String getMessage() {
      return "Le fichier bibliotheque n'as pas été trouvé ou est manquant ";
   }

   
   
    
}
