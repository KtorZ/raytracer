/** 
 * Representer une couleur selon le modele RGB.
 * @author Matthias Benkort & Nicolas Gaborit
 * @version 1.2
 */

package raytracing.scene;

import raytracing.scene.geometry.Geometry;

public class Color{

    /** Composante verte de la couleur. */
    private double greenComp;

    /** Composante rouge de la couleur. */
    private double redComp;

    /** Composante bleue de la couleur. */
    private double blueComp;

    /** Initialiser une couleur a partir de ses 3 composantes.
     * @param greenComp La composante verte.
     * @param redComp La composante rouge.
     * @param blueComp La composante bleue.
     * @throws IllegalArgumentException
     */
    public Color(double redComp, double greenComp,  double blueComp){
	this.setRedComp(redComp);
	this.setGreenComp(greenComp);
	this.setBlueComp(blueComp);
    }

    /** Obtenir la valeur des composantes d'un pixel sur un modele
     * RGB, c-a-d une repartition des valeurs entre 0 et 255.
     * @return La valeur des trois composantes dans un tableau d'entier.
     */
    public int[] getPixel(){
	return new int[]{ 
	    (int)(255*this.redComp),
	    (int)(255*this.greenComp),
	    (int)(255*this.blueComp)
	};
    }

    /** Log-ajouter les composantes d'une autre couleur a la couleur existante
     * @param La couleur a ajouter
     * @throws IllegalArgumentException Lorsque la couleur est null
     */
    public void logAdd(Color color){
	if(color == null)
	    throw new IllegalArgumentException("Couleur invalide");
			
	this.redComp = 1 - (1-this.redComp)*(1 - color.redComp);
	this.greenComp = 1 - (1-this.greenComp)*(1 - color.greenComp);
	this.blueComp = 1 - (1-this.blueComp)*(1 - color.blueComp);
    }
	

    /** Appliquer un coefficient (ratio) compris entre 0 et 1 aux
     * composantes d'une couleur.
     * @param r le ratio à appliquer
     * @return La couleur multipliee
     * @throws IllegalArgumentException Lorsque le ratio est hors de [0;1]
     */
    public Color ratio(double r){
	if(r < 0 || r > 1)
	    throw new IllegalArgumentException("Ratio invalide");

	return new Color(this.redComp*r, this.greenComp*r, this.blueComp*r);
    }

    /** Multiplie une couleur par une autre, composante par composante.
     * @param color la couleur à multiplier
     * @return La couleur multipliee
     * @throws IllegalArgumentException Lorsque la couleur est null
     */
    public Color multiply(Color color){
	if(color == null)
	    throw new IllegalArgumentException("Couleur invalide");
		
	return new Color(this.redComp*color.redComp, this.greenComp*color.greenComp, this.blueComp*color.blueComp);
    }

    /** Obtenir la composante verte de la couleur.
     * @return La composante verte
     */
    public double getGreenComp(){
	return this.greenComp;
    }

    /** Obtenir la composante rouge de la couleur.
     * @return La composante rouge.
     */
    public double getRedComp(){
	return this.redComp;
    }

    /** Obtenir la composante bleue de la couleur.
     * @return La composante bleue.
     */
    public double getBlueComp(){
	return this.blueComp;
    }

    /** Definir la composante verte de la couleur.
     * @param greenComp La composante verte
     * @throws IllegalArgumentException Lorsque !(0 <= greenComp <= 1)
     */
    public void setGreenComp(double greenComp){
	if(greenComp < 0 || greenComp > 1)
	    throw new IllegalArgumentException("Composante verte invalide");
		
	this.greenComp = greenComp;
    }

    /** Definir la composante rouge de la couleur.
     * @param redComp La composante rouge.
     * @throws IllegalArgumentException Lorsque !(0 <= redComp <= 1)
     */
    public void setRedComp(double redComp){
	if(redComp < 0 || redComp > 1)
	    throw new IllegalArgumentException("Composante rouge invalide");
		
	this.redComp = redComp;
    }

    /** Definir la composante bleue de la couleur.
     * @param blueCompt La composante bleue.
     * @throws IllegalArgumentException Lorsque !(0 <= blueComp <= 1)
     */
    public void setBlueComp(double blueComp){
	if(blueComp < 0 || blueComp > 1)
	    throw new IllegalArgumentException("Composante bleue invalide");
			
	this.blueComp = blueComp;
    }

    /** Indique si une couleur est égale à une autre à epsilon près.
     * @param c la couleur à comparer.
     * @return Le booléen d'égalité
     */
    public boolean isEqual(Color c){
	double rGap = Math.abs(c.getRedComp() - this.redComp);
	double gGap = Math.abs(c.getGreenComp() - this.greenComp);
	double bGap = Math.abs(c.getBlueComp() - this.blueComp);
	
	return (rGap < Geometry.EPSILON &&
		gGap < Geometry.EPSILON && 
		bGap < Geometry.EPSILON);
    }
    
    /** @see Object */
    public String toString(){
	return "("+redComp+", "+greenComp+", "+blueComp+")";
    }


}
