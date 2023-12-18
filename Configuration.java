// Definition of constants used by the program.

public final class Configuration {

	  /* Legend */

    // CSV legend for correspondence between:
    // (1) character in the text file to load/save,
    // (2) integer in the Matrix (for data manipulation),
    // (3) image in the graphical window.
	public static String LEGENDE_FICHIER_CSV 	= "legende.csv";
	public static String LEGENDE_SEPARATEUR 	= ",";

	
	/* Window */ 

	// Window title
	public static String TITRE = "Carte";

	// Size for each image (square image, width = height)
	public static int IMAGE_TAILLE = 50; // en pixels

	// If the image is missing or unknown according to the legend
	public static String IMAGE_INCONNUE = "images/inconnu.jpg";

	// Message areas
	public static int LARGEUR_INFORMATION_DROITE = 300; // in pixels
	public static int HAUTEUR_INFORMATION_BAS    = 30;  // in pixels

	// Spacing between elements if graphically too compact
	public static int ESPACEMENT_ENTRE_LIGNES = 0;   // in pixels
	public static int ESPACEMENT_ENTRE_COLONNES = 0; // in pixels

	// Adjustment if graphical components are too compact
	public static int AJUSTEMENT_HAUTEUR = 0; // in pixels
	public static int AJUSTEMENT_LARGEUR = 0; // in pixels

	// Offset at startup relative to the top-left corner of the screen (not applicable with Replit)
	public static int DECALAGE_HAUT   = 0; // in pixels
	public static int DECALAGE_GAUCHE = 0; // in pixels

	
}
