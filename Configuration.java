/** Définition de constantes utilisées par le programme.
 * @author Thomas Raimbault
 * @version 2023/01
 */

public final class Configuration {

	/* Légende */

	// Légende au format CSV pour correspondance entre : 
	// (1) caractère dans fichier texte à charger/enregistrer,
	// (2) entier dans la Matrice (pour la manipulation des données),
	// (3) image dans la fenêtre grahique.
	public static String LEGENDE_FICHIER_CSV 	= "legende.csv";
	public static String LEGENDE_SEPARATEUR 	= ",";

	
	/* Fenêtre */ 

	// titre de la fenêtre
	public static String TITRE = "Carte";

	// taille pour chaque image (image carrée, largeur = hauteur)
	public static int IMAGE_TAILLE = 50; // en pixels

	// si image inexistante ou inconnue depuis la légende
	public static String IMAGE_INCONNUE = "images/inconnu.jpg";

	// zones de messages
	public static int LARGEUR_INFORMATION_DROITE = 300; // en pixels
	public static int HAUTEUR_INFORMATION_BAS    = 30;  // en pixels

	// espacement entre les éléments si graphiquement trop compactés
	public static int ESPACEMENT_ENTRE_LIGNES = 0;   // en pixels
	public static int ESPACEMENT_ENTRE_COLONNES = 0; // en pixels

	// ajustement si composants graphiques trop compactés
	public static int AJUSTEMENT_HAUTEUR = 0; // en pixels
	public static int AJUSTEMENT_LARGEUR = 0; // en pixels

	// décalage au démarrage par rapport au bord supérieur gauche de l'écran (non applicable avec Replit)
	public static int DECALAGE_HAUT   = 0; // en pixels
	public static int DECALAGE_GAUCHE = 0; // en pixels

	
}
