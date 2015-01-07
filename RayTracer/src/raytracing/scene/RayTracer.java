/** 
 * Calculer les composantes ambiante, diffuse, speculaire, reflechie et
 * transmise du modele de Blinn-Phong en effectuant des lancer des
 * rayons sur une scene composé d'element.  
 * @author  Nicolas Gaborit, Matthias Benkort, Maxime Herve
 * @version 1.5
 */
package raytracing.scene;

import raytracing.scene.Color;
import raytracing.scene.Ray;
import raytracing.scene.positionable.*;
import raytracing.scene.positionable.element.*;
import raytracing.scene.geometry.*;
import raytracing.scene.geometry.Vector;
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.Stack;
import java.util.EmptyStackException;


abstract public class RayTracer{

    /** Obtenir la composante ambiante du modele de Blinn-Phong a
     * partir d'une liste d'elements, d'une lumiere ambiante et d'un
     * rayon.
     * @param elements La liste d'elements a considerer.
     * @param ambientLight La source de lumiere ambiante.
     * @param ray Le rayon a lancer.
     * @return La couleur de la composante ambiante
     */
    public static Color getAmbientComp(ArrayList<Element> elements, 
				       Color ambientLight, Ray ray){
		
	//Recherche du point d'intersection
	Hashtable<Point, Element> intersectedElement = getClosestInter(elements, ray);
	Point intersection = intersectedElement.keys().nextElement();
	Element elInter = intersectedElement.get(intersection);

	//Calcul de la composante associee a ce point.
	return ambientComp(elInter, intersection, ambientLight);
    }

    /** Obtenir la composante diffuse du modele de Blinn-Phong a
     * partir d'une liste d'element, de source de lumiere ponctuelles
     * et d'un rayon.
     * @param elements Liste d'elements consideres.
     * @param spotlights Liste des sources de lumieres considerees.
     * @param ray Le rayon a lancer.
     * @return La couleur de la composante diffuse
     */
    public static Color getDiffuseComp(ArrayList<Element> elements, 
				       ArrayList<SpotLight> spotLights, Ray ray){
		
	//Recherche du point d'intersection
	Hashtable<Point, Element> intersectedElement = getClosestInter(elements, ray);
	Point intersection = intersectedElement.keys().nextElement();
	Element elInter = intersectedElement.get(intersection);		
			
	// Obtenir la liste des lumières visibles
	ArrayList<SpotLight> visibleSpots;
	visibleSpots = getVisibleSpots(elements, spotLights, ray, elInter, intersection);
						
	// Calcul de la composante associee 
	return diffuseComp(elements, visibleSpots, elInter, intersection);
    }

    /** Obtenir la composante speculaire du modele de Blinn-Phong a
     * partir d'une liste d'element, d'une liste de sources de lumiere
     * ponctuelles, et d'un rayon.
     * @param elements Liste d'element consideres.
     * @param spotLights Liste des sources de lumiere considerees.
     * @param ray Le rayon a lancer.
     * @return La couleur de la composante spéculaire
     */
    public static Color getSpecularComp(ArrayList<Element> elements, 
					ArrayList<SpotLight> spotLights, Ray ray){
		
	//Recherche du point d'intersection		
	Hashtable<Point, Element> intersectedElement = getClosestInter(elements, ray);
	Point intersection = intersectedElement.keys().nextElement();
	Element elInter = intersectedElement.get(intersection);

	// Obtenir la liste des lumières visibles
	ArrayList<SpotLight> visibleSpots;
	visibleSpots = getVisibleSpots(elements, spotLights, ray, elInter, intersection);
			
	// Calcul de la composante associee 
	return specularComp(elements, visibleSpots, elInter, intersection, ray);
    }
    
    /** Obtenir la composante reflechie et refractee du modele de
     * Blinn-Phong a partir d'une liste d'elements, d'une liste de
     * sources de lumiere ponctuelles, d'une source de lumiere
     * ambiante, d'un rayon, d'un indice de réfraction et d'un nombre
     * d'itérations
     * @param elements Liste d'elements consideres
     * @param spotLights Liste de sources de lumieres considerees
     * @param ambientLight Source de lumiere ambiante.
     * @param ray Rayon a lancer.
     * @param iRefs Pile d'indice de refraction des milieux rencontrés
     * @param infinityColor Couleur de l'infini.
     * @param nbIterations Limite le nombre de deviations considerees.
     * @return La couleur de la composante réfléchie et réfractée
     */
    public static Color getRandRComp(ArrayList<Element> elements,
				     ArrayList<SpotLight> spotLights, Color ambientLight, Ray ray,
				     Stack<Double> iRefs, Color infinityColor, int nbIterations){
		
	Color colorResult = new Color(0, 0, 0); // Pas la couleur de l'infini
		
	if(nbIterations != 0) {
	    //Duplication des piles pour ne pas que la refraction perturbe la reflexion.
	    @SuppressWarnings("unchecked")
		Stack<Double> iRefsT = (Stack<Double>)iRefs.clone(); 
	    @SuppressWarnings("unchecked")
		Stack<Double> iRefsR = (Stack<Double>)iRefs.clone(); 
				
	    // Recherche de l'intersection.
	    Hashtable<Point, Element> intersectedElement = getClosestInter(elements, ray);
	    Point intersection = intersectedElement.keys().nextElement();
	    Element elInter = intersectedElement.get(intersection);	

	    // Calculer le rayon réflechi
	    Ray rayReflec = elInter.getReflected(ray, intersection);
				
	    // Calculer le rayon refracté
	    Ray rayRefrac = elInter.getRefracted(ray, intersection, iRefsT);
				
	    // Obtenir les composantes ambiantes, diffuses et spéculaires
	    // correspondant au rayon refléchi.
	    Color cReflec = infinityColor;
	    try{
		cReflec = NonRAndRComps(elements, spotLights, ambientLight, rayReflec);

		// Obtenir la composante réfléchie dans la direction
		// du rayon réfléchi, avec un nbIteration décrémenté
		// et un nouvel iRef pour la réfraction
		cReflec.logAdd(getRandRComp(elements, spotLights, ambientLight, 
					    rayReflec, iRefs, infinityColor, 
					    (nbIterations - 1))
			       );
			
	    }catch(NoIntersectionException e){}
				
	    // Sommer logarithmiquement les composantes et appliquer les
	    // bons coefficients
	    cReflec = cReflec.multiply(elInter.getMaterial().getCReflection());
	    colorResult.logAdd(cReflec);
				
	    // Obtenir les composantes ambiantes, diffuses et spéculaires
	    // correspondant au rayon transmis.
	    Color cRefrac = infinityColor;
	    try{
		cRefrac = NonRAndRComps(elements, spotLights, ambientLight, rayRefrac);

		// Obtenir la composante réfléchie dans la direction
		// du rayon refracté, avec un nbIteration décrémenté
		// et un nouvel iRef pour la réfraction
		cRefrac.logAdd(getRandRComp(elements, spotLights, ambientLight, 
					    rayRefrac, iRefsT, infinityColor, 
					    (nbIterations - 1))
			       );	   
							
	    }catch(NoIntersectionException e){}
				
	    // Sommer logarithmiquement les composantes et appliquer les
	    // bons coefficients
	    cRefrac = cRefrac.multiply(elInter.getMaterial().getCTransmission());	
	    colorResult.logAdd(cRefrac);
	}
	return colorResult;
    }

    /** Obtenir la somme logarithmique des quatres composantes du
     * modele de Blinn-Phong a partir d'une liste d'elements, d'une
     * liste de sources de lumiere ponctuelles, d'une source de
     * lumiere ambiante, d'un rayon, d'un indice de réfraction et d'un
     * nombre d'itérations
     * @param elements Liste d'elements consideres
     * @param spotLights Liste de sources de lumieres considerees
     * @param ambientLight Source de lumiere ambiante.
     * @param ray Rayon a lancer.
     * @param iRefs Pile d'indice de refraction des milieux rencontrés
     * @param infinityColor Couleur de l'infini.
     * @param nbIterations Limite le nombre de deviations considerees.
     * @return La couleur de la composante réfléchie et réfractée
     */
    public static Color getAllComp(ArrayList<Element> elements,
				   ArrayList<SpotLight> spotLights,
				   Color ambientLight, Ray ray,
				   Stack<Double> iRefs, Color infinityColor,
				   int nbIterations){
	
	Color colorResult = new Color(0, 0, 0); // Pas la couleur de l'infini
	//Duplication des piles pour ne pas que la refraction perturbe la reflexion.
	@SuppressWarnings("unchecked")
	    Stack<Double> iRefsT = (Stack<Double>)iRefs.clone(); 
	@SuppressWarnings("unchecked")
	    Stack<Double> iRefsR = (Stack<Double>)iRefs.clone(); 

	// Recherche de l'intersection.
	Hashtable<Point, Element> intersectedElement = getClosestInter(elements, ray);
	Point intersection = intersectedElement.keys().nextElement();
	Element elInter = intersectedElement.get(intersection);	

	// Recherche des lumières visibles
	ArrayList<SpotLight> visibleSpots;
	visibleSpots = getVisibleSpots(elements, spotLights, ray, elInter, intersection);
	// Ajouter les trois premières composantes
	if (!(elInter.getMaterial().getCAmbient().isEqual(new Color(0, 0, 0)))) {
	    colorResult.logAdd(ambientComp(elInter, intersection, ambientLight));
	}
	if (!(elInter.getMaterial().getCDiffuse().isEqual(new Color(0, 0, 0)))) {
	    colorResult.logAdd(diffuseComp(elements, visibleSpots, elInter, intersection));
	}
	if (!(elInter.getMaterial().getKSpecular() < Geometry.EPSILON)) {
	    colorResult.logAdd(specularComp(elements, visibleSpots, elInter, intersection, ray));
	}
	if(nbIterations != 0) {
	    // S'occuper des reflexion refractions.
	    if (!(elInter.getMaterial().getCReflection().isEqual(new Color(0, 0, 0)))) {
		// Calculer le rayon réflechi
		Ray rayReflec = elInter.getReflected(ray, intersection);
		// Obtenir la composante réfléchie dans la direction
		// du rayon réfléchi, avec un nbIteration décrémenté
		// et un nouvel iRef pour la réfraction
		Color cReflec = infinityColor;
		try{
		    cReflec = (getAllComp(elements, spotLights, ambientLight, 
					  rayReflec, iRefs, infinityColor, 
					  (nbIterations - 1)));
		} catch(NoIntersectionException e){}
		// Sommer logarithmiquement les composantes et appliquer les
		// bons coefficients
		cReflec = cReflec.multiply(elInter.getMaterial().getCReflection());
		colorResult.logAdd(cReflec);
	    }
				
	    if (!(elInter.getMaterial().getCTransmission().isEqual(new Color(0, 0, 0)))) {
		// Calculer le rayon refracté
		Ray rayRefrac = elInter.getRefracted(ray, intersection, iRefsT);
		// Obtenir la composante réfléchie dans la direction
		// du rayon refracté, avec un nbIteration décrémenté
		// et un nouvel iRef pour la réfraction
		Color cRefrac = infinityColor;
		try{
		    cRefrac = (getAllComp(elements, spotLights, ambientLight, 
					  rayRefrac, iRefsT, infinityColor,(nbIterations - 1))
			       );
		} catch(NoIntersectionException e){}
		// Sommer logarithmiquement les composantes et appliquer les
		// bons coefficients
		cRefrac = cRefrac.multiply(elInter.getMaterial().getCTransmission());	
		colorResult.logAdd(cRefrac);
	    }
	}
	return colorResult;
    }

    /** Obtenir l'intersection d'un rayon avec un élément la plus
     * proche. Lève une exception si aucun objet n'est rencontré.
     * @param elements La liste d'elements a considerer.
     * @param ray Le rayon a lancer.
     * @return Une Hashtable contenant l'association (point
     * d'intersection / element intersecté le plus proche)
     * @throws NoIntersectionException Lorsqu'aucun n'objet n'est
     * intersecté
     */
    private static Hashtable<Point, Element> getClosestInter(ArrayList<Element> elements, Ray ray){

	Hashtable<Point, Element> intersectedElement = new Hashtable<Point, Element>();
	Element elInter = null;
	Point interMin = null;
	Point intersection = null;
	double dist;
	double distMin = Double.POSITIVE_INFINITY;

	for(Element e : elements){
	    try{
		intersection = e.getIntersection(ray);
		// L'intersection est-elle plus proche que celles
		// précédemment calculées ?
		dist = Geometry.distance(intersection, ray.getSource());
		if (dist < distMin) {
		    elInter = e;
		    interMin = intersection;
		    distMin = dist;
		} else{}
	    }catch(NoIntersectionException exc){}
	}

	if(interMin == null)
	    throw new NoIntersectionException("Couleur de l'infini");
			
	intersectedElement.put(interMin, elInter);
	return intersectedElement;
    }

    /** Obtenir la liste des lumières visibles depuis un point
     * d'intersection à partir d'une liste d'éléments
     * @param elements La liste d'elements a considerer.
     * @param spotLights La liste des lumières de la scene
     * @param incRay Le rayon incident 
     * @param elInter L'element intersecté 
     * @param intersection le point d'intersection
     * @return La liste des lumières visible
     * @throws NoIntersectionException Lorsqu'aucun n'objet n'est
     * intersecté
     */
    private static ArrayList<SpotLight> getVisibleSpots(ArrayList<Element> elements,
							ArrayList<SpotLight> spotLights, Ray incRay, Element elInter, Point intersection){

	ArrayList<SpotLight> visibleSpots = new ArrayList<SpotLight>();
	Ray ray;
	boolean visible;
	Point intersVisible;
	double incRayScalNormal;
	double spotRayScalNormal;

	for(SpotLight spot : spotLights){
	    // Est-elle visible ?
	    visible = true;
			
	    //Traiter le cas ou le spot est égal au point
	    //d'intersection sinon creation de vecteur nul
	    if(!intersection.isEqual(spot.getPosition())) {
		ray = new Ray(intersection, new Vector(intersection, spot.getPosition()));
					
		for(Element e : elements){
		    try{
			incRayScalNormal = Geometry.scalarProduct(incRay.getSupport(), elInter.getNormal(intersection));
			spotRayScalNormal = Geometry.scalarProduct(ray.getSupport(), elInter.getNormal(intersection));
			visible = incRayScalNormal*spotRayScalNormal < 0;

			intersVisible = e.getIntersection(ray);
			visible = visible && Geometry.distance(intersVisible, ray.getSource()) > Geometry.distance(ray.getSource(), spot.getPosition());
			break;
		    }catch(NoIntersectionException exc){}
		}
	    }

	    if(visible){
		visibleSpots.add(spot);
	    }
	}
	return visibleSpots;
    }


    /** Obtenir la somme logarithmique des composantes ambiante,
     * diffuse et spéculaire du modèle de Blinn-Phong dans la
     * direction d'un rayon à partir d'une liste d'elements, d'une
     * liste de sources de lumiere ponctuelles et d'une source de
     * lumiere ambiante.
     * @param elements Liste d'elements consideres
     * @param spotLights Liste de sources de lumieres considerees
     * @param ambientLight Source de lumiere ambiante.
     * @param ray Le rayon considéré
     * @return La couleur résultante des trois premières composantes
     * @throws NoIntersectionException si aucune intersection n'est trouvée
     */
    private static Color NonRAndRComps(ArrayList<Element> elements,
				       ArrayList<SpotLight> spotLights, Color ambientLight, Ray ray) {
				       
	Color colorResult = new Color(0, 0, 0);

	// Obtenir l'intersection la plus proche
	Hashtable<Point, Element> intersectedElement = getClosestInter(elements, ray);

	// Il n'y a qu'une entrée dans intersectedElement
	Point intersection = intersectedElement.keys().nextElement();
	Element elInter = intersectedElement.get(intersection);

	// Obtenir la liste des lumières visibles
	ArrayList<SpotLight> visibleSpots;
	visibleSpots = getVisibleSpots(elements, spotLights, ray, elInter, intersection);

	colorResult = ambientComp(elInter, intersection, ambientLight);
	colorResult.logAdd(diffuseComp(elements, visibleSpots, elInter, intersection));
	colorResult.logAdd(specularComp(elements, visibleSpots, elInter, intersection, ray));
	return colorResult;
    }

    /** Obtenir la composante ambiante a partir d'un point
     * d'intersection, d'un élément intersecté et d'une lumière
     * ambiante.
     * @param elInter L'élément intersecté
     * @param intersection Le point d'intersection
     * @param ambientLight Source de lumiere ambiante.
     * @return La couleur de la composante ambiante
     */
    private static Color ambientComp(Element elInter,
				     Point intersection, Color ambientLight){
	
	return elInter.getMaterial().getCAmbient().multiply(ambientLight);
    }

    /** Obtenir la composante diffuse a partir d'un point
     * d'intersection, d'un élément intersecté et d'une liste de
     * sources de lumière visibles.
     * @param elements Liste d'elements consideres
     * @param spotLights Liste des sources de lumières visibles depuis
     * le point d'intersection
     * @param elInter L'élément intersecté
     * @param intersection Le point d'intersection
     * @return La couleur de la composante diffuse
     */
    private static Color diffuseComp(ArrayList<Element> elements,
				     ArrayList<SpotLight> visibleSpots, Element elInter, Point intersection){

	Color colorResult = new Color(0, 0, 0);
	Color contrib;
	Ray raySpot;
		
	// Calculer la contribution de chacune
	double cosAngle;
	for (SpotLight spot : visibleSpots){
	    //Traiter le cas ou le spot est égal au point
	    //d'intersection (sinon creation de vecteur nul)
	    if(!intersection.isEqual(spot.getPosition())){
		raySpot = new Ray(intersection, new Vector(intersection, spot.getPosition()));

		// On calcule le cosinus de l'angle que fait la lumière
		// avec la normale au point d'intersection
		cosAngle = Math.abs(Geometry.scalarProduct(raySpot.getSupport(), 
							   elInter.getNormal(intersection)));

		//Cas ou les vecteurs ne sont pas tout a fait unitaire ( imprécision double )
		cosAngle = (cosAngle>1) ? 1 : cosAngle;

	    }else{
		cosAngle = 1;
	    }
	    contrib = spot.getColor().ratio(cosAngle);
	    colorResult.logAdd(contrib);
	}

	// Appliquer le coefficient d'absorption de la lumière diffuse
	colorResult = colorResult.multiply(elInter.getMaterial().getCDiffuse());
	return colorResult;
    }


    /** Obtenir la composante spéculaire a partir d'un point
     * d'intersection, d'un élément intersecte, d'une liste de
     * sources de lumière visibles, et d'un rayon incident.
     * @param elements Liste d'elements consideres
     * @param visibleSpots Liste des sources de lumières visibles
     * depuis le point d'intersection
     * @param elInter L'élément intersecté
     * @param intersection Le point d'intersection
     * @param ray Le rayon incident 
     * @return La couleur de la composante spéculaire
     */
    private static Color specularComp(ArrayList<Element> elements,
				      ArrayList<SpotLight> visibleSpots, Element elInter, 
				      Point intersection, Ray ray){

	Color colorResult = new Color(0, 0, 0);
	Color contrib;
	Ray raySpot;
			   
	// Calculer la contribution de chacune
	double cosAngle;
	for(SpotLight spot : visibleSpots){
	    //Traiter le cas ou le spot est égal au point
	    //d'intersection (sinon creation de vecteur nul)
	    if(!intersection.isEqual(spot.getPosition())){
		raySpot = new Ray(intersection, new Vector(intersection, spot.getPosition()));
		// On calcule le cosinus de l'angle que fait la
		// lumière avec le rayon réfléchi.
		Ray reflected = elInter.getReflected(ray, intersection);
		cosAngle = Math.max(0, Geometry.scalarProduct(reflected.getSupport(),
							      raySpot.getSupport()));

		//Cas ou les vecteurs ne sont pas tout a fait unitaire (imprécision double)
		cosAngle = (cosAngle>1) ? 1 : cosAngle;

	    }else{
		cosAngle = 1;
	    }
	    Material matter = elInter.getMaterial();
	    // Calculer la contribution
			   
	    contrib =spot.getColor().ratio(Math.min(1, Math.pow(cosAngle, matter.getBrightness())));

	    // Appliquer le coefficient W(l) = Math.min(1,(1 - cos(l)))*matter.getKSpecular()
	    // avec l = Math.max(0, Geometry.scalarProduct(raySpot.getSupport(), elInter.getNormal(intersection)));
	    contrib = contrib.ratio(matter.getKSpecular());

	    // Ajout a la lumiere totale
	    colorResult.logAdd(contrib);
	}
	return colorResult;
    }
}
