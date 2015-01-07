/** 
 * Representer un materiau possedant des proprietes optiques classiques.
 * @author Matthias Benkort, Nicolas Gaborit
 * @version 2.0
 */
package raytracing.scene.positionable.element;

import raytracing.scene.Color;
import raytracing.scene.geometry.Geometry;

public class Material{

    /** Couleur du materiau pour une lumiere ambiante = 1 - Ka*/
    private Color cAmbient;

    /** Couleur du materiau pour une lumiere diffuse = 1 - Kd */
    private Color cDiffuse;

    /** Coefficient speculaire. € [0,1] */
    private double kSpecular;

    /** Brillance du materiau >= 1 */
    private double brightness;

    /** Indice de refraction du materiau*/
    private double iRefraction; 

    /** Couleur de transmission */
    private Color cTransmission;

    /** Couleur de reflexion */
    private Color cReflection;
	

    /** Initialiser un materiau a l'aide de sa couleurs, et de ses divers coefficients.
     * @param color La couleur du materiau.
     * @param cAmbient Couleur ambiante
     * @param cDiffuse Couleur diffuse
     * @param ks Reflectance
     * @param n brillance
     * @param cTrans Coefficient de transmission.
     * @param CReflex Coefficient de reflection.
     * @param iRef Indice de refraction.
     * @throws IllegalArgumentException  Cf Setters
     */
    public Material(Color cAmbient, Color cDiffuse, double ks, double n, Color cTrans, Color cReflex, double iRef){
	this.setCAmbient(cAmbient);
	this.setCDiffuse(cDiffuse);
	this.setKSpecular(ks);
	this.setBrightness(n);
	this.setCTransmission(cTrans);
	this.setCReflection(cReflex);
	this.setIRefraction(iRef);
    }

    /** Obtenir la couleur ambiante.
     * @return La couleur ambiante.
     */
    public Color getCAmbient(){
	return this.cAmbient;
    }

    /** Obtenir la couleur diffuse.
     * @return La couleur diffuse.
     */
    public Color getCDiffuse(){
	return this.cDiffuse;
    }

    /** Obtenir la reflectance du materiau.
     * @return La reflectance
	
     */
    public double getKSpecular(){
	return this.kSpecular;
    }

    /** Obtenir la brillance du materiau.
     * @return la brillance du materiau.
     */
    public double getBrightness(){
	return this.brightness;
    }

    /** Obtenir la couleur de transmission du materiau.
     *@return La couleur de transmission.
     */
    public Color getCTransmission(){
	return this.cTransmission;
    }

    /** Obtenir la couleur de reflexion du materiau.
     * @return La couleur de reflexion.
     */
    public Color getCReflection(){
	return this.cReflection;
    }

    /** Obtenir l'indice de refraction de l'objet.
     * @return L'indice de refraction.
     */
    public double getIRefraction(){
	return this.iRefraction;
    }

    /** Definir la couleur ambiante du materiau.
     * @param cAmbient La couleur ambiante du materiau.
     * @throws IllegalArgumentException Lorsque la couleur est null
     */
    public void setCAmbient(Color cAmbient){
	if(cAmbient == null)
	    throw new IllegalArgumentException("La couleur ambiante est invalide.");
		
	this.cAmbient = cAmbient;
    }

    /** Definir le coefficient d'absorption ambiant.
     * @param cDifuse La couleur diffuse du materiau.
     * @throws IllegalArgumentException Lorsque le coefficient n'appartient pas a [0,1]
     */
    public void setCDiffuse(Color cDiffuse){
	if(cDiffuse == null)
	    throw new IllegalArgumentException("La couleur diffuse est invalide");
		
	this.cDiffuse = cDiffuse;
    }

    /** Definir la reflectance du materiau.
     * @param ks La reflectance.
     * @throws IllegalArgumentException Lorsque le coefficient n'appartient pas a [0,1]
     */
    public void setKSpecular(double ks){
	if(ks < 0 || ks  > 1)
	    throw new IllegalArgumentException("Reflectance invalide");		
			
	this.kSpecular = ks;
    }

    /** Definir la brillance du materiau.
     * @param n La brillance du materiau.
     * @throws IllegalArgumentException Lorsque le coefficient est < 1
     */
    public void setBrightness(double n){
	if(n < 1)
	    throw new IllegalArgumentException("Brillance invalide");
		
	this.brightness = n;
    }

    /** Definir le coefficient de transmission du materiau.
     * @param kt Coefficient de transmission du materiau.
     * @throws IllegalArgumentException Lorsque l'argument est null
     */
    public void setCTransmission(Color kt){
	if(kt == null)
	    throw new IllegalArgumentException("Coefficient de transmission invalide");

	this.cTransmission = kt;
    }

    /** Definir le coefficient de reflection du materiau.
     * @param kr Coefficient de reflection du materiau.
     * @throws IllegalArgumentException Lorsque l'argument est null
     */
    public void setCReflection(Color kr){
	if(kr == null)
	    throw new IllegalArgumentException("Le coefficient de reflection invalide");

	this.cReflection = kr;		
    }

    /** Definir l'indice de refraction de l'objet 
     * @param iRef Indice de refraction du materiau
     * @throws IllegalArgumentException Lorsque l'indice est strictement inferieur a 1
     */
    public void setIRefraction(double iRef){
	if(iRef < 1)
	    throw new IllegalArgumentException("Indice de refraction invalide");

	this.iRefraction = iRef;
    }

    /** @see Object */
    public String toString(){
	String materialStr = "";
	double eps = Geometry.EPSILON;

        //Platre
	if(Math.abs(kSpecular-0.05) < eps && Math.abs(brightness-100) < eps && cTransmission.isEqual(new Color(0,0,0)) && cReflection.isEqual(new Color(0,0,0)) && Math.abs(iRefraction-1) < eps){
	    materialStr = "Plâtre";
	    //Métal
        }else if(Math.abs(kSpecular-0.9) < eps && Math.abs(brightness-75) < eps && cTransmission.isEqual(new Color(0,0,0)) && cReflection.isEqual(new Color(0.75,0.75,0.75)) && Math.abs(iRefraction-1) < eps){
            materialStr = "Métal";
	    //Verre
        }else if(Math.abs(kSpecular-0.2) < eps && Math.abs(brightness-125) < eps && cTransmission.isEqual(new Color(0.95,0.95,0.95)) && cReflection.isEqual(new Color(0.15,0.15,0.15)) && Math.abs(iRefraction-1.5) < eps){
            materialStr = "Verre";
	    //Plastique
        }else if(Math.abs(kSpecular-0.05) < eps && Math.abs(brightness-80) < eps && cTransmission.isEqual(new Color(0.98,0.98,0.98)) && cReflection.isEqual(new Color(0.02,0.02,0.02)) && Math.abs(iRefraction-1.02) < eps){
            materialStr = "Plastique";			
        }else{
            materialStr = "Materiau personnalise";
        }
        return materialStr;
    }
    

}
