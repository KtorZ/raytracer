/** 
 * Representer une image au format Portable PixMap ASCII. 
 * @author Matthias Benkort 
 * @version 1.1
 */
package raytracing.scene;

import raytracing.scene.Color;
import raytracing.scene.positionable.ViewPoint;
import java.io.*;

public class ImagePPM{

    /** Taille de l'image
     * indice 0 : nombre de Lignes
     * indice 1 : nombre de Colonnes */
    private int size[] = new int[2];

    /** La valeur maximale de reference pour l'intensite */
    private int intensityMaxScale;

    /** Matrice des couleurs de l'ecran */
    private Color[][] colorMap;

    /** Initialiser une image ppm a partir de sa taille et de sa valeur max de reference pour l'intensite.
     * @param size La taille de l'image
     * 	indice 0 : nombre de lignes
     * 	indice 1 : nombre de colonnes
     * @param iMax Valeur maximale de reference pour l'intensite.
     * @throws IllegalArgumentException cf Setters.
     */
    public ImagePPM(int size[], int iMax){
	this.setSize(size);
	this.setIntensityMaxScale(iMax);
    }

    /** Ajouter une couleur a la matrice des couleurs.
     * @param i Ligne de la matrice
     * @param j Colonne de la matrice
     * @param c Couleur à ajouter.
     * @param sum Mettre a true pour ajouter les composantes de la couleur a celle deja presente dans 
     * la matrice au meme emplacement s'il y a lieu.
     * @throws IllegalArgumentException Lorsque i et j ne sont pas dans le tableau ou lorsque la couleur est null
     */
    public void addColor(int i, int j, Color c, boolean sum){
	if(i < 0 || i >= this.size[0] || j < 0|| j >= this.size[1])
	    throw new IllegalArgumentException("Impossible d'ajouter couleur hors de la grille");
			
	if(c == null)
	    throw new IllegalArgumentException("Couleur invalide");
		
	if(sum && colorMap[i][j] == null)
	    throw new IllegalArgumentException("Impossible de logAjouter couleur : case du tableau non initialisee");
			
	if(sum){
	    this.colorMap[i][j].logAdd(c);
	}else{
	    this.colorMap[i][j] = c;
	}
    }

    /** Obtenir la taille de l'image.
     * @return La taille de l'image.
     */
    public int[] getSize(){
	return this.size;
    }

    /** Obtenir la valeur max de reference pour l'intensite.
     * @return La valeur max de l'intensite.
     */
    public int getIntensityMaxScale(){
	return this.intensityMaxScale;
    }

    /** Obtenir le tableau des couleurs.
     * @return Le tableau des couleurs.
     */
    public Color[][] getColorMap(){
	return this.colorMap;
    }

    /** Definir la taille de l'image
     * @param size La taille de l'image.
     * indice 0 : nombre de lignes
     * indice 1 : nombre de colonnes
     * @throws IllegalArgumentException Lorsque la taille de size est incorrecte, et que les valeurs ne pas sont strict. positives
     */
    public void setSize(int[] size){
	if(size.length != 2)
	    throw new IllegalArgumentException("Taille invalide");
			
	if(size[0] <= 0 || size[1] <= 0)
	    throw new IllegalArgumentException("Hauteur ou largeur incorrecte");
			
	this.size = size;
	this.colorMap = new Color[size[0]][size[1]];
    }

    /** Definir la valeur max de reference pour l'intensite.
     * @param iMax La valeur max de reference pour l'intensite.
     * @throws IllegalArgumentException Lorsque !(0 < iMax <= 255) 
     */
    public void setIntensityMaxScale(int iMax){
	if(iMax <= 0 || iMax > 255)
	    throw new IllegalArgumentException("Valeur max de reference pour l'intensite invalide");
			
	this.intensityMaxScale = iMax;
    }
	
    /** Enregistrer l'image dans un fichier ppm a la racine de l'application.
     * Genere un fichier XXX.ppm ou XXX est le string associe au point de vue.
     * @param vp Point de vue associe a l'image donnant son nom au fichier genere.
     * @throws IllegalArgumentException Lorsque vp est null
     */
    public void exportPPM(ViewPoint vp){
	if(vp == null)
	    throw new IllegalArgumentException("Point de vue invalide");
			
	try{
	    String fileName = vp + "";
	    fileName = fileName.substring(0, fileName.length() - 5) + ".ppm";
	    FileWriter fWrit = new FileWriter(new File(fileName));
			
	    //Ecriture de l'en-tete.
	    fWrit.write("P3\n"+this.size[1]+" "+this.size[0]+"\n"+this.intensityMaxScale+"\n");
			
	    //Ecriture de l'image.
	    int pixel[];
	    for(int i = 0; i < this.size[0]; i++){
		for(int j = 0; j < this.size[1]; j++){
		    pixel = this.colorMap[i][j].getPixel();
		    fWrit.write(pixel[0]+" "+pixel[1]+" "+pixel[2]);
		    if(j != this.size[1] - 1)
			fWrit.write(" ");
		}
		fWrit.write("\n");					
	    }			
	    fWrit.close();
	}catch(IndexOutOfBoundsException e){
	    throw new RuntimeException("Oops... L'image a ete generee n'importe comment.\n"+
				       "Impossible de l'exporter");
	}catch(IOException e){
	    throw new RuntimeException("Hopa ! Erreur lors de l'exportation.");
	}catch(Exception e){
	    throw new RuntimeException("Hopa ! Erreur lors de l'exportation.");			
	}		
    }


}
