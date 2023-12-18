/** Main code
 * @author Lazare Plisson-Arcos
 * @version 2023/01
 */



public class Main {

public static void main(String[] args) throws Exception {
  
  System.out.print("Number of fireghters: " );
  int pompier = Console.saisirEntier();

  System.out.println("What type of wind do you want?");
  System.out.println("No wind, press 1");  
  System.out.println("North wind, press 2");
  System.out.println("East wind, press 3");
  System.out.println("South wind, press 4");
  System.out.println("West wind, press 5");
	int choixVent = Console.saisirEntier();
		if (choixVent ==1){
			System.out.println("No wind");
		}
		if (choixVent ==2){
			System.out.println("North wind");
		}
    if (choixVent ==3){
			System.out.println("East wind");
		}
		if (choixVent ==4){
			System.out.println("South wind");
		}
		if (choixVent ==5){
			System.out.println("West wind");
		} 
 

  System.out.println("On which map do you want to play? (from 1 to 5)");
  int mapChoice = Console.saisirEntier();
  int[][] carte = Outils.chargerCarte("carte0"+mapChoice+".txt");    
  int[][] newcarte = new int[carte.length][carte[0].length];
  copier(carte, newcarte);

  System.out.print("Enter the time it will take for firefighters to arrive: " );
  int reactivite = Console.saisirEntier();

  System.out.print("Choose the mode of action: " );
  System.out.println("Manual, press 1");  
  System.out.println("Automatic, press 2");
  int choix = Console.saisirEntier();
  
  
  
  FenetreGraphique.initialiserFenetre(carte);
  FenetreGraphique.activerClicSouris(true);

 
  for (int temps=0; temps<20; temps++){
    int pointPompier = pompier;
    ActualiseCarte(carte);
    if (temps>=reactivite ) { 
      while (pointPompier>0) {
        if (choix==1) {
          pointPompier = action(carte, newcarte, pointPompier);
        }
        if (choix==2) {
          pointPompier = actionAuto(carte, newcarte, pointPompier);
        }
        System.out.println(pointPompier);
          
        
        if (choix==1) {
         
          System.out.println("Avez-vous fini ?");
  		    System.out.println("Si oui, tapez 1");	
          System.out.println("Si non, continuez en tapant 2");
      		int fin = Console.saisirEntier();
      		if (fin==1){ break;}
            else {}   
        }
        
               
      }
    }
    
    afficherMatriceEnConsole(newcarte);
    for (int ligne=1; ligne < carte.length-1; ligne++) {  
      for (int colonne=1; colonne < carte[0].length-1; colonne++) {
        newcarte[ligne][colonne] = actualisation(carte,ligne,colonne, choixVent);  
      }
    } 
//calculate the number of fire on the map
    int nbrefeu=0;
    for (int i = 0; i < carte.length; i++) {
      for (int j = 0; j < carte[i].length; j++) {
       
    if (carte[i][j]==4||carte[i][j]==5||carte[i][j]==8||carte[i][j]==11||carte[i][j]==9||carte[i][j]==10||carte[i][j]==22||carte[i][j]==27||carte[i][j]==23||carte[i][j]==26||carte[i][j]==24||carte[i][j]==25||carte[i][j]==14||carte[i][j]==19||carte[i][j]==15||carte[i][j]==18||carte[i][j]==16||carte[i][j]==17||carte[i][j]==31||carte[i][j]==32||carte[i][j]==33||carte[i][j]==34||carte[i][j]==37||carte[i][j]==38||carte[i][j]==39||carte[i][j]==40||carte[i][j]==41||carte[i][j]==42||carte[i][j]==43||carte[i][j]==44){

          nbrefeu++;
        }
      }
      }

        System.out.print("The number of burned vegetation is: ");
		System.out.println(nbrefeu);

      copier(newcarte,carte);
      // Or here
      // Refreshing the cell to make it visible
      // Display the remaining points after each refresh
      System.out.println("Continue the simulation");
    	
      Console.appuyerSurEntrer();
    }

  
    }
    
  //end of executed function
  
//algorithm for doing watering in the most efficient way possible
static int actionAuto (int[][] carte, int[][] newcarte, int pointPompier) {
  Console.appuyerSurEntrer();
  int[] res = auto(carte);
  int ligne = res[0];
  int colonne = res[1];
  
  FenetreGraphique.encadrerElement(ligne,colonne, Couleur.ROUGE, 1);
  if (Puissance(carte, ligne, colonne)>0) {
    System.out.println("watering");
    pointPompier = Arrosage(carte, newcarte, ligne, colonne, pointPompier);}
  else {
    System.out.println("end of possible actions");
    pointPompier = 0;
  }
  copier(newcarte, carte);
  FenetreGraphique.rafraichirElement(ligne, colonne);
  System.out.println("action completed");   
  
  return(pointPompier); 
  
}
//select what to water
static int[] auto (int[][] carte) {
  int ligne_priorite = 0;
  int colonne_priorite = 0;
  int valeur = 0;
  int valeurTest = 0;
  for (int ligne=1; ligne < carte.length-1; ligne++) {  
    for (int colonne=1; colonne < carte[0].length-1; colonne++) {
      valeurTest = valoriser(carte, ligne, colonne);
      if (valeur<valeurTest) {
        valeur=valeurTest;
        ligne_priorite = ligne;
        colonne_priorite = colonne;     
      }
    }
  } 
  int[] res = {ligne_priorite, colonne_priorite};
  return(res);
}
 // how we select what to water automatically 
static int valoriser (int[][] carte, int ligne, int colonne) {
  int valeur = 0;

  // We assign the highest value to burning dwellings, as these are the type of objects to prioritize for watering.
  // Next are the trees, then the bushes, then the grass.
  // Anything that is not on fire, and anything that cannot or will no longer burn, is given a value of 0.
  // There is no point in watering these.
  if (carte[ligne][colonne] == 4) {valeur = 1;}
	  
  if (carte[ligne][colonne] == 8) {valeur = 2;}
  if (carte[ligne][colonne] == 9) {valeur = 2;}

  if (carte[ligne][colonne] == 14) {valeur = 3;}
  if (carte[ligne][colonne] == 15) {valeur = 3;}
  if (carte[ligne][colonne] == 16) {valeur = 3;}

  if (carte[ligne][colonne] == 22) {valeur = 4;}
  if (carte[ligne][colonne] == 23) {valeur = 4;}
  if (carte[ligne][colonne] == 24) {valeur = 4;}

  if (carte[ligne][colonne] == 31) {valeur = 4;}
  if (carte[ligne][colonne] == 32) {valeur = 4;}

  if (carte[ligne][colonne] == 37) {valeur = 4;}
  if (carte[ligne][colonne] == 38) {valeur = 4;}
  if (carte[ligne][colonne] == 39) {valeur = 4;}
  if (carte[ligne][colonne] == 40) {valeur = 4;}

  return valeur; 
}


// This function creates a copy of the map without linking them by assigning the same memory space 
// (as would be the case with newCarte = map).
// We need to have a buffer map so that propagations that occur at the same time 
// do not create additional propagations within the same timeframe.
// Indeed, without this, a flame on cell A could spread to its neighboring cell B, 
// which could then spread to its neighbor C, all in the same turn (since fire management is done cell by cell).
static void copier(int[][] carte, int[][] newcarte){ 
    for (int i = 0; i < carte.length; i++) {
        for (int j = 0; j < carte[i].length; j++) {
            newcarte[i][j] = carte[i][j];
        }
    }
  }
  
// Allows displaying the current state of a cell on the graphical interface.
static void ActualiseCarte (int[][] cartes){
  for (int ligne=1; ligne < cartes.length-1; ligne++) {  
    for (int colonne=1; colonne < cartes[0].length-1; colonne++) {
      FenetreGraphique.rafraichirElement(ligne, colonne);
      FenetreGraphique.supprimerCadreElement(ligne, colonne);
    }
  }
}


static int Inflammabilite (int[][] carte, int ligne, int colonne) {
  int Seuil = -1; // This represents all objects that are already on fire; they will inevitably continue to burn.
    
  //if seuil =0, then the object can't burn
  if (carte[ligne][colonne] == 0) {
    Seuil = 0;
    return Seuil; }
  if (carte[ligne][colonne] == 1) {
    Seuil = 0;
    return Seuil; }
  if (carte[ligne][colonne] == 2) {
    Seuil = 0;
    return Seuil; }
  if (carte[ligne][colonne] == 6) {
    Seuil = 0;
    return Seuil; }
  if (carte[ligne][colonne] == 12) {
    Seuil = 0;
    return Seuil; }
  if (carte[ligne][colonne] == 20) {
    Seuil = 0;
    return Seuil; }
  if (carte[ligne][colonne] == 28) {
    Seuil = 0;
    return Seuil; }
  if (carte[ligne][colonne] == 29) {
    Seuil = 0;
    return Seuil; }
  if (carte[ligne][colonne] == 35) {
    Seuil = 0;
    return Seuil; }
  if (carte[ligne][colonne] == 45) {
    Seuil = 0;
    return Seuil; }
  
  if (carte[ligne][colonne] == 3) {
    Seuil = 1;
    return Seuil; }
  if (carte[ligne][colonne] == 7) {
    Seuil = 2;
    return Seuil; }
  if (carte[ligne][colonne] == 13) {
    Seuil = 3;
    return Seuil; }
  if (carte[ligne][colonne] == 21 ) {
    Seuil = 3;
    return Seuil; }
  if (carte[ligne][colonne] == 30 ) {
    Seuil = 3;                           // mobil home
    return Seuil; }
  if (carte[ligne][colonne] == 36 ) {    
    Seuil = 3;                          //tour
    return Seuil; }
  return (Seuil);
}

static int PuissanceAlentour (int[][] tableau, int ligne, int colonne, int choixVent) {
  int sommeVoisinsVent =0;
  if (choixVent ==1){
    sommeVoisinsVent = PuissanceSansVent(tableau, ligne, colonne);
  }
  if (choixVent ==2){
    sommeVoisinsVent = PuissanceAlentourNord(tableau, ligne, colonne);
  }
  if (choixVent ==3){
    sommeVoisinsVent = PuissanceAlentourEst(tableau, ligne, colonne);
  }
  if (choixVent ==4){
    sommeVoisinsVent = PuissanceAlentourSud(tableau, ligne, colonne);
  }
  if (choixVent ==5){
    sommeVoisinsVent = PuissanceAlentourOuest(tableau, ligne, colonne);
  }
  return(sommeVoisinsVent);
}

// Sum of the powers around.
static int PuissanceSansVent (int[][] tableau, int ligne, int colonne) {
  int sommeVoisins = 0;
  sommeVoisins += Puissance(tableau, ligne-1, colonne);
  sommeVoisins += Puissance(tableau, ligne-1, colonne-1);
  sommeVoisins += Puissance(tableau, ligne-1, colonne+1);
  sommeVoisins += Puissance(tableau, ligne+1, colonne);
  sommeVoisins += Puissance(tableau, ligne+1, colonne-1);
  sommeVoisins += Puissance(tableau, ligne+1, colonne+1);
  sommeVoisins += Puissance(tableau, ligne,colonne-1);
  sommeVoisins += Puissance(tableau, ligne,colonne+1);
  // System.out.println("Sum of the values of neighboring cells: " + sumOfNeighbors);
  return sommeVoisins;
}

static int PuissanceAlentourNord (int[][] tableau, int ligne, int colonne){
	int sommeVoisinsN = 0;
	sommeVoisinsN += Puissance(tableau, ligne-1, colonne-1);
	sommeVoisinsN += Puissance(tableau, ligne-1, colonne);
	sommeVoisinsN += Puissance(tableau, ligne-1, colonne+1);
	sommeVoisinsN += Puissance(tableau, ligne, colonne-1);
	sommeVoisinsN += Puissance(tableau, ligne, colonne+1);

//To avoid going out of bounds, you need to have a safety margin of 2 cells with respect to the line. If the margin is greater than 2, then you can calculate the total number of cells around it.
  if (ligne >= 2) {
    sommeVoisinsN += Puissance(tableau, ligne-2, colonne-1);
	  sommeVoisinsN += Puissance(tableau, ligne-2, colonne);
	  sommeVoisinsN += Puissance(tableau, ligne-2, colonne+1);
  }
	return sommeVoisinsN;
}

static int PuissanceAlentourEst (int[][] tableau, int ligne, int colonne){
	int sommeVoisinsE = 0;
	sommeVoisinsE += Puissance(tableau, ligne-1, colonne);
	sommeVoisinsE += Puissance(tableau, ligne-1, colonne+1);
	sommeVoisinsE += Puissance(tableau, ligne, colonne+1);
	sommeVoisinsE += Puissance(tableau, ligne+1, colonne);
	sommeVoisinsE += Puissance(tableau, ligne+1, colonne+1);
	
// To avoid going out of bounds, you need to have a safety margin of 2 cells with respect to the column. The column +2 should be less than the size of the array - 1 because indexing starts at 0.
if (colonne +2 <= tableau[0].length-1) {
    //System.out.println(colonne);
    sommeVoisinsE += Puissance(tableau, ligne-1, colonne+2);
  	sommeVoisinsE += Puissance(tableau, ligne+1, colonne+2);
    sommeVoisinsE += Puissance(tableau, ligne, colonne+2);
  }
	return sommeVoisinsE;
}


static int PuissanceAlentourSud (int[][] tableau, int ligne, int colonne){
	int sommeVoisinsS = 0;
	sommeVoisinsS += Puissance(tableau, ligne, colonne-1);
	sommeVoisinsS += Puissance(tableau, ligne, colonne+1);
	sommeVoisinsS += Puissance(tableau, ligne+1, colonne-1);
	sommeVoisinsS += Puissance(tableau, ligne+1, colonne);
	sommeVoisinsS += Puissance(tableau, ligne+1, colonne+1);
	
// To avoid going out of bounds, you need to have a safety margin of 2 cells with respect to the row. The row +2 should be less than the size of the array - 1 because indexing starts at 0.
if (ligne +2 <= tableau.length-1) {
  	sommeVoisinsS += Puissance(tableau, ligne+2, colonne-1);
  	sommeVoisinsS += Puissance(tableau, ligne+2, colonne);
  	sommeVoisinsS += Puissance(tableau, ligne+2, colonne+1);
  }
	return sommeVoisinsS;
}

static int PuissanceAlentourOuest (int[][] tableau, int ligne, int colonne){
	int sommeVoisinsO = 0;
	sommeVoisinsO += Puissance(tableau, ligne-1, colonne-1);
	sommeVoisinsO += Puissance(tableau, ligne-1, colonne);
	sommeVoisinsO += Puissance(tableau, ligne, colonne-1);
	sommeVoisinsO += Puissance(tableau, ligne+1, colonne-1);
	sommeVoisinsO += Puissance(tableau, ligne+1, colonne);

// To avoid going out of bounds, you should have a safety margin of 2 cells with respect to the column. If it's greater than 2, then you can calculate the total number of cells around it.  if (colonne >= 2) {
  sommeVoisinsO += Puissance(tableau, ligne, colonne-2);
	sommeVoisinsO += Puissance(tableau, ligne-1, colonne-2);
  sommeVoisinsO += Puissance(tableau, ligne+1, colonne-2);
  }
	return sommeVoisinsO;
}
	
	
	
//fonction defining power fire
static int Puissance (int[][] carte, int ligne, int colonne) {
  int feu = 0;  
    
  if (carte[ligne][colonne] == 4) {feu = 1;}
  if (carte[ligne][colonne] == 5) {feu = 1;}
  if (carte[ligne][colonne] == 8) {feu = 1;}
  if (carte[ligne][colonne] == 11) {feu = 1;}
  if (carte[ligne][colonne] == 14) {feu = 1;}
  if (carte[ligne][colonne] == 19) {feu = 1;}
  if (carte[ligne][colonne] == 22) {feu = 1;}
  if (carte[ligne][colonne] == 27) {feu = 1;}
  if (carte[ligne][colonne] == 31) {feu = 1;}
  if (carte[ligne][colonne] == 34) {feu = 1;}
  if (carte[ligne][colonne] == 37) {feu = 1;}
  if (carte[ligne][colonne] == 44) {feu = 1;}

  if (carte[ligne][colonne] == 9) {feu = 2;}
  if (carte[ligne][colonne] == 10) {feu = 2;}
  if (carte[ligne][colonne] == 15) {feu = 2;}
  if (carte[ligne][colonne] == 18) {feu = 2;}
  if (carte[ligne][colonne] == 23) {feu = 2;}
  if (carte[ligne][colonne] == 26) {feu = 2;}
  if (carte[ligne][colonne] == 32) {feu = 2;}
  if (carte[ligne][colonne] == 33) {feu = 2;}
  if (carte[ligne][colonne] == 38) {feu = 2;}
  if (carte[ligne][colonne] == 43) {feu = 2;}
    
  if (carte[ligne][colonne] == 16) {feu = 3;}
  if (carte[ligne][colonne] == 17) {feu = 3;}
  if (carte[ligne][colonne] == 24) {feu = 3;}
  if (carte[ligne][colonne] == 25) {feu = 3;}
  if (carte[ligne][colonne] == 39) {feu = 3;}
  if (carte[ligne][colonne] == 42) {feu = 3;}

  if (carte[ligne][colonne] == 40) {feu = 4;}
  if (carte[ligne][colonne] == 41) {feu = 4;}
  
    return feu;
}

//function changing the state of an object based on whether it should burn or not.static int actualisation(int[][] cartes, int ligne, int colonne, int choixVent) {
    int puissanceAutour = PuissanceAlentour(cartes, ligne, colonne, choixVent);
    int seuil = Inflammabilite (cartes, ligne, colonne);
    int var = cartes[ligne][colonne];
    if (seuil == -1)   //A threshold of -1 indicates that the object was already on fire, so it will continue to burn.
      {var +=1;}
    if (seuil > 0  && (puissanceAutour>=seuil)) //If the object is flammable and its threshold is exceeded, then it burns.
      {var +=1;}
    return var;
}

//change case state after watering
static int Arrosage (int[][] carte, int[][] newcarte, int ligne, int colonne, int pointPompier) {
  if (pointPompier>0) { 
    
    if (carte[ligne][colonne] == 4) {newcarte[ligne][colonne] = 3;}
    if (carte[ligne][colonne] == 5) {newcarte[ligne][colonne] = 6;}
    if (carte[ligne][colonne] == 8) {newcarte[ligne][colonne] = 7;}
    if (carte[ligne][colonne] == 11) {newcarte[ligne][colonne] = 12;}
    if (carte[ligne][colonne] == 14) {newcarte[ligne][colonne] = 13;}
    if (carte[ligne][colonne] == 19) {newcarte[ligne][colonne] = 20;}
    if (carte[ligne][colonne] == 22) {newcarte[ligne][colonne] = 21;}
    if (carte[ligne][colonne] == 27) {newcarte[ligne][colonne] = 28;}
    if (carte[ligne][colonne] == 9) {newcarte[ligne][colonne] = 8;}
    if (carte[ligne][colonne] == 10) {newcarte[ligne][colonne] = 11;}
    if (carte[ligne][colonne] == 15) {newcarte[ligne][colonne] = 14;}
    if (carte[ligne][colonne] == 18) {newcarte[ligne][colonne] = 19;}
    if (carte[ligne][colonne] == 23) {newcarte[ligne][colonne] = 22;}
    if (carte[ligne][colonne] == 26) {newcarte[ligne][colonne] = 27;}
    if (carte[ligne][colonne] == 16) {newcarte[ligne][colonne] = 15;}
    if (carte[ligne][colonne] == 17) {newcarte[ligne][colonne] = 18;}
    if (carte[ligne][colonne] == 24) {newcarte[ligne][colonne] = 23;}
    if (carte[ligne][colonne] == 25) {newcarte[ligne][colonne] = 26;}
    if (carte[ligne][colonne] == 31) {newcarte[ligne][colonne] = 30;}
    if (carte[ligne][colonne] == 32) {newcarte[ligne][colonne] = 31;}
    if (carte[ligne][colonne] == 33) {newcarte[ligne][colonne] = 34;}
    if (carte[ligne][colonne] == 34) {newcarte[ligne][colonne] = 35;}
    if (carte[ligne][colonne] == 37) {newcarte[ligne][colonne] = 36;}
    if (carte[ligne][colonne] == 38) {newcarte[ligne][colonne] = 37;}
    if (carte[ligne][colonne] == 39) {newcarte[ligne][colonne] = 38;}
    if (carte[ligne][colonne] == 40) {newcarte[ligne][colonne] = 39;}
    if (carte[ligne][colonne] == 41) {newcarte[ligne][colonne] = 42;}
    if (carte[ligne][colonne] == 42) {newcarte[ligne][colonne] = 43;}
    if (carte[ligne][colonne] == 43) {newcarte[ligne][colonne] = 44;}
    if (carte[ligne][colonne] == 44) {newcarte[ligne][colonne] = 45;}
    //consomme un point pompier
    
    pointPompier = pointPompier - 1;}
    
  return(pointPompier);
}

// Change the state of a cell after clearing it.
// Check if there are enough points left and deduct a number of points corresponding to double the maximum firepower of that cell.static int Defrichage (int[][] carte, int[][] newcarte, int ligne, int colonne, int         pointPompier) {       
  if (carte[ligne][colonne] == 3 & pointPompier>=2) 
    {newcarte[ligne][colonne] = 0;
    pointPompier -= 2;}
  if (carte[ligne][colonne] == 7 & pointPompier>=4) 
    {newcarte[ligne][colonne] = 0;
    pointPompier -= 4;}
  if (carte[ligne][colonne] == 13 & pointPompier>=6) 
    {newcarte[ligne][colonne] = 0;
    pointPompier -= 6;}
  if (carte[ligne][colonne] == 21 & pointPompier>=6) 
    {newcarte[ligne][colonne] = 0;
    pointPompier -= 6;}
  if (carte[ligne][colonne] == 30 & pointPompier>=4) 
    {newcarte[ligne][colonne] = 0;
    pointPompier -= 4;}
  if (carte[ligne][colonne] == 36 & pointPompier>=8) 
    {newcarte[ligne][colonne] = 0;
    pointPompier -= 8;}
  return(pointPompier);
}

static int action (int[][] carte, int[][] newcarte, int pointPompier) {
  System.out.println("Which action do you want to do ?");
	Console.appuyerSurEntrer();
	int ligne = FenetreGraphique.dernierIndexLigneClicSouris();
	int colonne = FenetreGraphique.dernierIndexColonneClicSouris();
	if( ligne != -1 && colonne != -1) { // si clic ok
		FenetreGraphique.encadrerElement(ligne,colonne, Couleur.ROUGE, 1);
	}
  if (Puissance(carte, ligne, colonne)>0) {
    System.out.println("watering");
    pointPompier = Arrosage(carte, newcarte, ligne, colonne, pointPompier);
  }
  else {
    System.out.println("clearVegetation");
    pointPompier = Defrichage(carte, newcarte, ligne, colonne, pointPompier);
  }
  copier(newcarte, carte);
  FenetreGraphique.rafraichirElement(ligne, colonne);
  System.out.println("action completed");   
  return(pointPompier);
}
  
  

  
// Display a matrix of integers in a raw manner in the console.
// Preconditions: Provide a non-null matrix of integers. ("Matrix" <=> the same number of columns in each row)
// The first index corresponds to rows, and the second index corresponds to columns.
static void afficherMatriceEnConsole(int[][] matrice) {
	// line by line
	for(int lig = 0 ; lig < matrice.length ; lig++) {
		// column by column
		for(int col = 0 ; col < matrice[0].length ; col++) {
			// countain of a case
			//System.out.print(matrice[lig][col]);
			System.out.print(" ");
		}
		System.out.println();
	}
}


	
}