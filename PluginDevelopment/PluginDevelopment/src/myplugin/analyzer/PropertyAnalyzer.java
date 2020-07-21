package myplugin.analyzer;

import java.util.List;

import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Property;

import myplugin.generator.fmmodel.FMProperty;
import myplugin.generator.fmmodel.FMType;
import myplugin.generator.options.ProjectOptions;
import myplugin.generator.options.TypeMapping;
import myplugin.utils.Constants;

public class PropertyAnalyzer {
	public static FMProperty createPropertyData(Property property) throws AnalyzeException {
		String propertyName = property.getName();
		if(propertyName == null) {
			throw new AnalyzeException("Properties must have names!");
		}
		
		
		int lower = property.getLower();
		int upper = property.getUpper();

		String typeName = property.getType().getName();
		String typePackage = "";

		TypeMapping typeMapping = getDataType(typeName);
		boolean isUml = false;
		if (typeMapping != null) {
			typeName = typeMapping.getDestType();
			typePackage = typeMapping.getLibraryName();
			isUml = true;
		}
		FMType type = new FMType(property.getType().getID(), typeName, typePackage, isUml, false, false);

		String visibility = property.getVisibility().toString();
		FMProperty fmProperty = new FMProperty(property.getID(), propertyName, type, visibility, lower, upper);
		
		//ucitati tagove za perzistente atribute
		boolean  stereotype = StereotypesHelper.hasStereotypeOrDerived(property,Constants.perssitentPropertyIdentifier);		
		if (stereotype) {
			fmProperty.setPersistant(true);
			//TODO: ucitati vrednosti tagova
		}
		
		return fmProperty;
	}

	private static TypeMapping getDataType(String umlDataType) {
		List<TypeMapping> typeMappings = ProjectOptions.getProjectOptions().getTypeMappings();
		for (TypeMapping typeMapping : typeMappings) {
			if (typeMapping.getuMLType().equals(umlDataType)) {
				return typeMapping;
			}
		}
		return null;
	}

}
