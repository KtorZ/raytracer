/** 
 * Representer un ecran a travers lequel s'effectue l'observation.
 * @author Matthias Benkort
 * @version 2.0
 */
package raytracing.scene.positionable;

import raytracing.scene.geometry.*;

public class Screen{
    
    /** Triplet de point servant a definir l'ecran.  Dans l'ordre :
     * coin supérieur gauche, coin supérieur droit, coin inférieur
     * gauche. */
    private Point corners[] = new Point[3];
    
    /** Vecteurs de translation, horizontal et vertical. */
    private Vector delta[] = new Vector[2];
    
    /** Resolution de l'ecran.
     * indice 0 : resolution verticale = nb de lignes.
     * indice 1 : resolution horizontale = nb de colonnes. */
    private int resolution[] = new int[2];
    
    /** Initialiser un ecran depuis 3 de ses coins, ie: 3 points non
     * alignes.
     * @param corners Coins de l'ecran.
     * @param resVerti Resolution verticale de l'ecran.
     * @param redHoriz Resolution horizontale de l'ecran.
     * @throws IllegalArgumentException cf Setters
     */
    public Screen(Point corners[], int resHoriz, int resVerti){
	this.setResolutionI(resVerti);
	this.setResolutionJ(resHoriz);
	this.setCorners(corners);
		
	this.delta[0] = Geometry.realProduct(new Vector(corners[0],corners[2]), 1.0/(resVerti-1.0));
	this.delta[1] = Geometry.realProduct(new Vector(corners[0],corners[1]), 1.0/(resHoriz-1.0));
    }
    
    /** Obtenir le point correspondant a un certain point du
     * quadrillage delimite par l'ecran.
     * @return Le point de coordonnee (i,j) sur le quadrillage.
     * @param i ligne du point sur le quadrillage.
     * @param j colonne du point sur le quadrillage.
     * @throws IllegalArgumentException Lorsque non (0 <= x < xResol)
     *  ou non (0 <= y < yResol)
     */
    public Point getPointAt(int i, int j){
	if(i < 0 || j < 0 || 
	   i >= this.resolution[0] || 
	   j >= this.resolution[1])
	    throw new IllegalArgumentException("Selection d'un point" +
					       " en dehors de l'ecran");
		
	Point requestedPoint = new Point(
					 this.corners[0].getX(),
					 this.corners[0].getY(),
					 this.corners[0].getZ()
					 );
		
	for(int k = 0; k < i; k++){
	    requestedPoint = Geometry.translate(requestedPoint, this.delta[0]);
	}
		
	for(int l = 0; l < j; l++){
	    requestedPoint = Geometry.translate(requestedPoint, this.delta[1]);
	}
		
	return requestedPoint;
    }
    
    /** Obtenir la resolution verticale de l'ecran.
     * @return La resolution verticale.
     */
    public int getResolutionI(){
	return this.resolution[0];
    }
    
    /** Obtenir la resolution horizontale de l'ecran.
     * @return La resolution horizontale.
     */
    public int getResolutionJ(){
	return this.resolution[1];    
    }
    
    /** Definir un coin de l'ecran.
     * @param id Numero du coin a definir.
     * @param corner Point definissant le coin.
     * @throws IllegalArgumentException Lorsque le point est null
     */
    public void setCorner(int id, Point corner){
	if(corner == null)
	    throw new IllegalArgumentException("Point non initialise");
		
	this.corners[id] = corner;
    }
    
    /** Definir les 3 coins de l'ecran depuis un triplet de points.
     * @param corners Triplet de coin
     * @throws IllegalArgumentException Lorsque l'un des points est
     *	null OU le tableau ne contient pas 3 points OU les points sont
     *	alignes.
     */
    public void setCorners(Point corners[]){
	if(corners.length != 3)
	    throw new IllegalArgumentException("Nombre de coins invalide");
			
	Vector det = Geometry.vectorialProduct(
					       new Vector(corners[0], corners[1]),
					       new Vector(corners[1], corners[2])
					       );
		
	if(det.isZero())
	    throw new IllegalArgumentException("3 points alignes" +
					       " ne forment pas un plan");
		
	for(int i = 0; i<3; i++){
	    this.setCorner(i, corners[i]);
	}
    }
    
    /** Definir la resolution verticale de l'ecran.
     * @param i Resolution verticale.
     * @throws IllegalArgumentException Lorsque la résolution est
     * incorrecte.
     */
    public void setResolutionI(int i){	
	if(i <= 0)
	    throw new IllegalArgumentException("Resolution verticale invalide");
		
	this.resolution[0] = i;
    }
    
    /** Definir la resolution horizontale de l'ecran.
     * @param j Resolution horizontale de l'ecran.
     * @throws IllegalArgumentException Lorsque la résolution est
     * incorrecte.
     */
    public void setResolutionJ(int j){
	if(j <= 0)
	    throw new IllegalArgumentException("Resolution horizontale invalide");
		
	this.resolution[1] = j;    
    }
    
    
    
}
