/** Factorisation du pannel de materiau personnalise
 * @author Matthias Benkort
 * @version 1.0
 */
package raytracing.ihm;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Hashtable;
import raytracing.scene.positionable.element.Material;
import raytracing.scene.Color;

public class PanelMaterial extends JPanel{
	
    private JPanel panelCoeffMaterial;
    private JTextField ks;
    private JTextField krR, krG, krB;
    private JTextField ktR, ktG, ktB;
    private JTextField n, iRef;
    private JComboBox materialList;

    public PanelMaterial(){
	super(new BorderLayout());
        this.initPanel(null);
    }
    
    public PanelMaterial(Material material){
	super(new BorderLayout());
        this.initPanel(material);
        if(material.toString().equals("Plâtre")){
            this.materialList.setSelectedIndex(0);
        }else if(material.toString().equals("Métal")){
            this.materialList.setSelectedIndex(1);
        }else if(material.toString().equals("Verre")){
            this.materialList.setSelectedIndex(2);
        }else if(material.toString().equals("Plastique")){
            this.materialList.setSelectedIndex(3);
        }else{
            this.materialList.setSelectedIndex(4);
        }
    }

    private void initPanel(Material material){
	this.setBorder(BorderFactory.createTitledBorder("Choix du matériau"));
	this.initMaterialList();
	this.add(this.materialList);
			
	//Ajout du materiau personnalise
	panelCoeffMaterial = new JPanel();
	panelCoeffMaterial.setVisible(false);
	this.add(panelCoeffMaterial, BorderLayout.SOUTH);

	panelCoeffMaterial.setLayout(new BoxLayout(panelCoeffMaterial, BoxLayout.PAGE_AXIS));
	panelCoeffMaterial.setBorder(BorderFactory.createMatteBorder(0, 3, 0, 0, java.awt.Color.GRAY));

	//Demander le coefficient speculaire
	JPanel panelSpec = new JPanel(new BorderLayout()); 
	panelSpec.add(new JLabel("    Reflectance : "), BorderLayout.WEST);
	JPanel panelKs = new JPanel(new FlowLayout());
	ks = new JTextField(3);
					
	if(material!=null)
	    ks.setText(""+material.getKSpecular());
					
	panelKs.add(ks);
	panelSpec.add(panelKs, BorderLayout.EAST);

	//Demander le coefficient de brillance
	JPanel panelBri = new JPanel(new BorderLayout()); 
	panelBri.add(new JLabel("    Brillance : "), BorderLayout.WEST);
	JPanel panelN =  new JPanel(new FlowLayout());
	n = new JTextField(3);
					
	if(material!=null)
	    n.setText(""+material.getBrightness());
					
	panelN.add(n);
	panelBri.add(panelN, BorderLayout.EAST);

	//Demander le coefficient de reflection
	JPanel panelReflec = new JPanel(new BorderLayout()); 
	panelReflec.add(new JLabel("    Coeff. de réflection : "), BorderLayout.WEST);
	JPanel panelKr = new JPanel(new FlowLayout());
	krR = new JTextField(3);
	krG = new JTextField(3);
	krB = new JTextField(3);
					
	if(material != null){
	    Color kr = material.getCReflection();
	    krR.setText(""+kr.getRedComp());
	    krG.setText(""+kr.getGreenComp());
	    krB.setText(""+kr.getBlueComp());
	}
					
	panelKr.add(new JLabel("R :"));
	panelKr.add(krR);
	panelKr.add(new JLabel("V :"));
	panelKr.add(krG);
	panelKr.add(new JLabel("B :"));
	panelKr.add(krB);
	panelReflec.add(panelKr, BorderLayout.EAST);

	//Demander le coefficient de transmission
	JPanel panelTransm = new JPanel(new BorderLayout()); 
	panelTransm.add(new JLabel("    Coeff. de transmission : "), BorderLayout.WEST);
	JPanel panelKt = new JPanel(new FlowLayout());
	ktR = new JTextField(3);
	ktG = new JTextField(3);
	ktB = new JTextField(3);
					
	if(material != null){
	    Color kt = material.getCTransmission();
	    ktR.setText(""+kt.getRedComp());
	    ktG.setText(""+kt.getGreenComp());
	    ktB.setText(""+kt.getBlueComp());
	}
					
	panelKt.add(new JLabel("R :"));
	panelKt.add(ktR);
	panelKt.add(new JLabel("V :"));
	panelKt.add(ktG);
	panelKt.add(new JLabel("B :"));
	panelKt.add(ktB);
	panelTransm.add(panelKt, BorderLayout.EAST);

	//Demander l'indice de refraction
	JPanel panelRefrac = new JPanel(new BorderLayout()); 
	panelRefrac.add(new JLabel("    Indice de réfraction : "), BorderLayout.WEST);
	JPanel panelIRef =  new JPanel(new FlowLayout());
	iRef = new JTextField(3);
					
	if(material != null)
	    iRef.setText(""+material.getIRefraction());
					
	panelIRef.add(iRef);
	panelRefrac.add(panelIRef, BorderLayout.EAST);

	//Ajouter les elements au panneau
	panelCoeffMaterial.add(panelSpec);
	panelCoeffMaterial.add(panelBri);
	panelCoeffMaterial.add(panelReflec);
	panelCoeffMaterial.add(panelTransm);
	panelCoeffMaterial.add(panelRefrac);
    }

    private void initMaterialList(){
	this.materialList = new JComboBox();

	Material platre = new Material(
				       new Color(0,0,0), //Ambiante
				       new Color(0,0,0), //Diffuse
				       0.05, //Reflectance
				       100, //Brillance
				       new Color(0,0,0), //Transparence
				       new Color(0,0,0), //Reflex
				       1
				       );
	Material metal = new Material(
				      new Color(0,0,0), //Ambiante
				      new Color(0,0,0), //Diffuse
				      0.9, //Reflectance
				      75, //Brillance
				      new Color(0,0,0), //Transparence
				      new Color(0.75,0.75,0.75), //Reflex
				      1
				      );
        Material verre = new Material(
				      new Color(0,0,0), //Ambiante
				      new Color(0,0,0), //Diffuse
				      0.2, //Reflectance
				      125, //Brillance
				      new Color(0.95,0.95,0.95), //Transparence
				      new Color(0.15,0.15,0.15), //Reflex
				      1.5
				      );
		
        Material plastique = new Material(
					  new Color(0,0,0), //Ambiante
					  new Color(0,0,0), //Diffuse
					  0.05, //Reflectance
					  80, //Brillance
					  new Color(0.98,0.98,0.98), //Transparence
					  new Color(0.02,0.02,0.02), //Reflex
					  1.02
					  );
        Material perso = new Material(
				      new Color(0, 0, 0), //Ambiante
				      new Color(0, 0, 0), //Diffuse
				      0, //Reflectance
				      1, //Brillance
				      new Color(0, 0, 0), //Transparence
				      new Color(0, 0, 0), //Reflex
				      1
				      );          
        this.materialList.addItem(platre);
        this.materialList.addItem(metal);
        this.materialList.addItem(verre);
        this.materialList.addItem(plastique);
        this.materialList.addItem(perso);
        
    }

    /** Obtenir le materiau selectionne
     * @param La couleur ambiante selectionnee
     * @param La couleur diffuse selectionnee
     * @return Le materiau selectionne
     */
    public Material getMaterial(Color cAmbient, Color cDiffuse){
        Material material = null;
        if(this.materialList.getSelectedItem().toString().equals("Materiau personnalise")){
            Color cReflec = new Color(Double.parseDouble(krR.getText()), Double.parseDouble(krG.getText()), Double.parseDouble(krB.getText()));
            Color cTransm = new Color(Double.parseDouble(ktR.getText()), Double.parseDouble(ktG.getText()), Double.parseDouble(ktB.getText()));
            material = new Material(
				    cAmbient,
				    cDiffuse,
				    Double.parseDouble(ks.getText()),
				    Double.parseDouble(n.getText()),
				    cTransm,
				    cReflec,
				    Double.parseDouble(iRef.getText())
				    );            
        }else{
            Material materialSelected = (Material)this.materialList.getSelectedItem();
			
            //Modification de la couleur pour coller a la transparence.
	    material = new Material(cAmbient,
				    cDiffuse, 
				    materialSelected.getKSpecular(),
				    materialSelected.getBrightness(),
				    materialSelected.getCTransmission(),
				    materialSelected.getCReflection(),
				    materialSelected.getIRefraction()
				    );
        }
        return material;              
    }

    public JPanel getPanelCoeffMaterial(){
	return this.panelCoeffMaterial;
    }

    public JComboBox getMaterialList(){
	return this.materialList;
    }

}

