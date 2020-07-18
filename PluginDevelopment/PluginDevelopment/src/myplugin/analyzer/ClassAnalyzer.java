package myplugin.analyzer;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Property;
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Stereotype;

import myplugin.generator.fmmodel.FMClass;

public class ClassAnalyzer {
	public static FMClass analyzeClass(Class magicClass) {
		FMClass fmClass = new FMClass();
		fmClass.setName(magicClass.getName());

		// ovo mozda prebaciti u neku drugu klasu / funkciju
		Stereotype stereotype = StereotypesHelper.getAppliedStereotypeByString(magicClass, "Presistent Entity");
		JOptionPane.showMessageDialog(null, "Analiziranje klase ." + magicClass.getName());
		if (stereotype != null) {
			JOptionPane.showMessageDialog(null, "Pronasli stereotip  ." + magicClass.getName());
			List<Property> tags = stereotype.getOwnedAttribute();
			for (Property tag : tags) {
				String tagName = tag.getName();
				// preuzimanje vrednosti tagova
				List<?> values = StereotypesHelper.getStereotypePropertyValue(magicClass, stereotype, tag.getName());

				// if tag has tag values
				if (values.size() > 0) {
					List<String> tagValues = new ArrayList<String>();
					
					for(Object val: values) {
	            		String newValue;
	            		if(val instanceof String) {
	            			newValue = "\"" + val + "\"";
	            		} else {
	            			newValue = val.toString();
	            		}
	            		tagValues.add(newValue);
	        			JOptionPane.showMessageDialog(null, "Tag " + tagName + " value " + newValue);
	            	}
				}

			}

			return fmClass;
		}
		return fmClass;
	}
}
