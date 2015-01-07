package raytracing.test;

import raytracing.scene.*;
import raytracing.scene.geometry.*;
import raytracing.scene.positionable.*;
import raytracing.scene.positionable.element.*;
import java.util.ArrayList;
public class Test2{
    public static void main(String args[]){
	Material matTrans, matRefl, matOpaq;
	ViewPoint vp;
	Sphere s;
	Screen scr;
	SpotLight sp;

		// // La scène
		Scene scene = new Scene(new Color(0.2, 0.2, 0.2), new Color(0,0,0), 1, 5, "scene"); //Lumiere ambiante1
			
		// Les matériaux		
		Color colTransAbs  = new Color(0.02,0.02,0.02);
		matTrans = new Material(
			colTransAbs, //Coefficient d'absorption ambiante
			colTransAbs, //Coefficient d'absorption diffuse
			0, //Coefficient de reflectance
			1, //Coefficient de brillance
			new Color(0.98, 0.98, 0.98),// Coefficient de transmission
			new Color(0,0,0),//Coefficient de reflexion
			1.33);
			
		Color colReflAbs  = new Color(0.1,0.25,0.1);
		matRefl = new Material(
			colReflAbs, //Coefficient d'absorption ambiante
			colReflAbs, //Coefficient d'absorption diffuse
			0.8, //Coefficient de reflectance
			75, //Coefficient de brillance
			new Color(0, 0, 0),// Coefficient de transmission
			new Color(0.9,0.9,0.9),//Coefficient de reflexion
			1);	
			
		Color colOpaqAbs  = new Color(0.6,0.23,0.23);
		matOpaq = new Material(
			colOpaqAbs, //Coefficient d'absorption ambiante
			colOpaqAbs, //Coefficient d'absorption diffuse
			0.5, //Coefficient de reflectance
			150, //Coefficient de brillance
			new Color(0,0,0),// Coefficient de transmission
			new Color(0,0,0),//Coefficient de reflexion
			1);	
			
		// Les éléments
		s = new Sphere(new Point(0,0,0), 1, "s", matTrans);
		scene.addElement(s);
	
		s = new Sphere(new Point(0.75,0,3), 0.5, "s", matOpaq);
		scene.addElement(s);	
		
		s = new Sphere(new Point(0,0,0), 0.25, "s", matOpaq);
		scene.addElement(s);
		
		s = new Sphere(new Point(-1.5,0,0), 0.5, "s", matRefl);
		scene.addElement(s);	
		
		s = new Sphere(new Point(-1.25,1,0), 0.5, "s", matRefl);
		scene.addElement(s);	
	
		// Les sources de lumières
		sp = new SpotLight(new Point(10, 10, -10), new Color(1, 1, 1), "SpotLight 1");
		scene.addSpotLight(sp);
		
		sp = new SpotLight(new Point(10, 10, 10), new Color(1, 1, 1), "SpotLight 1");
		scene.addSpotLight(sp);	
		
		sp = new SpotLight(new Point(10, 10, 10), new Color(1, 1, 1), "SpotLight 1");
		scene.addSpotLight(sp);
		
		sp = new SpotLight(new Point(0, 0.65, -0.5), new Color(1, 1, 1), "SpotLight 1");
		scene.addSpotLight(sp);		
		
		sp = new SpotLight(new Point(-10, 10, 10), new Color(1, 1, 1), "SpotLight 1");
		scene.addSpotLight(sp);		
		// Les points de vue
		scr = new Screen(new Point[]{new Point(-3, 3, -1), new Point(3,3,-1), new Point(-3, -3, -1)}, //Premier point comme origine
				 800, //Nombre de colonnes = nb de points en horizontal
				 800); //Nombre de lignes = nb de points en vertical
				
		vp = new ViewPoint(new Point(0, 0, -150), scr, "Vp1"); //Source, ecran, nom	
		scene.addViewPoint(vp);	
    }
}
