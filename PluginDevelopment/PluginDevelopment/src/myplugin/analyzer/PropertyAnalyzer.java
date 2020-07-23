package myplugin.analyzer;

import java.util.List;

import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.EnumerationLiteral;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Property;
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Stereotype;

import myplugin.generator.fmmodel.FMCascadeType;
import myplugin.generator.fmmodel.FMFetchType;
import myplugin.generator.fmmodel.FMGeneratorType;
import myplugin.generator.fmmodel.FMLinkedProperty;
import myplugin.generator.fmmodel.FMPeristantProperty;
import myplugin.generator.fmmodel.FMProperty;
import myplugin.generator.fmmodel.FMType;
import myplugin.generator.options.ProjectOptions;
import myplugin.generator.options.TypeMapping;
import myplugin.utils.Constants;

public class PropertyAnalyzer {
	public static FMProperty createPropertyData(Property property) throws AnalyzeException {
		String propertyName = property.getName();
		if (propertyName.trim() == null) {
			throw new AnalyzeException("Properties must have names!");
		}

		int lower = property.getLower();
		int upper = property.getUpper();

		String typeName = property.getType().getName();
		String typePackage = "";

		TypeMapping typeMapping = getDataType(typeName);
		boolean isUml = false;
		String defaultValue = "";
		if (typeMapping != null) {
			typeName = typeMapping.getDestType();
			typePackage = typeMapping.getLibraryName();
			isUml = true;
			defaultValue = typeMapping.getDefaultValue();
		}
		FMType type = new FMType(property.getType().getID(), typeName, typePackage, isUml, false, false, defaultValue);

		String visibility = property.getVisibility().toString();
		FMProperty fmProperty = new FMProperty(property.getID(), propertyName, type, visibility, lower, upper);

		//ucitati abstract tagove
		Boolean isPersistant = StereotypesHelper.hasStereotypeOrDerived(property, Constants.abstractPropertyIdentifier);
		if (isPersistant) {
			fmProperty.setPersistant(true);
			setAbstractProperties(property, fmProperty);
		}

		// ucitati tagove za perzistente atribute
		Stereotype persistentPropertyStereotype = StereotypesHelper.getAppliedStereotypeByString(property,
				Constants.persistentPropertyIdentifier);
		if (persistentPropertyStereotype != null) {	
			fmProperty = setPersistantPropertyData(property, fmProperty, persistentPropertyStereotype);
		}

		// ucitati tagove za linked atribute
		Stereotype linkedPropertyStereotype = StereotypesHelper.getAppliedStereotypeByString(property,
				Constants.linkedPropertyIdentifier);
		if (linkedPropertyStereotype != null) {
			fmProperty = setLinkedPropertyData(property, fmProperty, linkedPropertyStereotype);
		}
		return fmProperty;
	}

	private static FMProperty setPersistantPropertyData(Property property, FMProperty fmProperty,
			Stereotype stereotype) {
		FMPeristantProperty persistantProperty = new FMPeristantProperty(fmProperty);
		List<Property> tags = stereotype.getOwnedAttribute();
		for (Property tag : tags) {
			createPersistantProperty(tag, property, fmProperty, stereotype, persistantProperty);
		}

		return persistantProperty;
	}

	private static FMProperty setLinkedPropertyData(Property property, FMProperty fmProperty, Stereotype stereotype) {
		FMLinkedProperty linkedProperty = new FMLinkedProperty(fmProperty);
		List<Property> tags = stereotype.getOwnedAttribute();
		for (Property tag : tags) {
			createLinkedProperty(tag, property, fmProperty, stereotype, linkedProperty);
		}
		return linkedProperty;
	}
	
	private static void setAbstractProperties(Property property, FMProperty fmProperty) {
		//da li da se kreira getter
		List<?> values = StereotypesHelper.getStereotypePropertyValue(property, Constants.abstractPropertyIdentifier, FMProperty.createGetterField);
		if (values.size() > 0) {
			if (values.get(0) instanceof Boolean) {
				Boolean createGetter = (Boolean) values.get(0);
				fmProperty.setCreateGetter(createGetter);
			}
		}
		//da li da se kreira setter
		values = StereotypesHelper.getStereotypePropertyValue(property, Constants.abstractPropertyIdentifier, FMProperty.createSetterField);
		if (values.size() > 0) {
			if (values.get(0) instanceof Boolean) {
				Boolean createSetter = (Boolean) values.get(0);
				fmProperty.setCreateSetter(createSetter);
			}
		}
		//columnName
		values = StereotypesHelper.getStereotypePropertyValue(property, Constants.abstractPropertyIdentifier, FMProperty.columnNameField);
		if (values.size() > 0) {
			if (values.get(0) instanceof String) {
				String columnName = (String) values.get(0);
				fmProperty.setColumnName(columnName);
			}
		}
	}
	
	private static void createLinkedProperty(Property tag, Property property, FMProperty fmProperty,
			Stereotype stereotype, FMLinkedProperty linkedProperty) {

		Property referencingProperty = property.getOpposite();
		int upper = referencingProperty.getUpper();
		int lower = referencingProperty.getLower();
		String name = referencingProperty.getName();

		String typeName = referencingProperty.getType().getName();
		String typePackage = "";

		TypeMapping typeMapping = getDataType(typeName);
		boolean isUml = false;
		String defaultValue = "";

		if (typeMapping != null) {
			typeName = typeMapping.getDestType();
			typePackage = typeMapping.getLibraryName();
			isUml = true;
			defaultValue = typeMapping.getDefaultValue();

		}
		FMType type = new FMType(property.getType().getID(), typeName, typePackage, isUml, false, false, defaultValue);

		FMProperty p = new FMProperty(referencingProperty.getID(), name, type,
				referencingProperty.getVisibility().toString(), lower, upper);
		linkedProperty.setOppositeEnd(new FMLinkedProperty(p));
		String tagName = tag.getName();

		// preuzimanje vrednosti taga
		List<?> values = StereotypesHelper.getStereotypePropertyValue(property, stereotype, tagName);

		// ako tag ima vrednosti
		if (values.size() > 0) {
			switch (tagName) {			
			case FMLinkedProperty.mappedByField:
				if (values.get(0) instanceof String) {
					String mappedBy = (String) values.get(0);
					linkedProperty.setMappedBy(mappedBy);
				}
				break;
			case FMLinkedProperty.optionalField:
				if (values.get(0) instanceof Boolean) {
					Boolean optional = (Boolean) values.get(0);
					linkedProperty.setOptional(optional);
				}
				break;
			case FMLinkedProperty.orphanRemovalField:
				if (values.get(0) instanceof Boolean) {
					Boolean orphanRemoval = (Boolean) values.get(0);
					linkedProperty.setOrphanRemoval(orphanRemoval);
				}
				break;
			case FMLinkedProperty.fetchField:
				if (values.get(0) instanceof EnumerationLiteral) {
					EnumerationLiteral enumLiteral = (EnumerationLiteral) values.get(0);
					String enumString = enumLiteral.getName();
					FMFetchType fetchType = FMFetchType.valueOf(enumString);
					linkedProperty.setFetch(fetchType);
				}
				break;
			case FMLinkedProperty.cascadeField:
				if (values.get(0) instanceof EnumerationLiteral) {
					EnumerationLiteral enumLiteral = (EnumerationLiteral) values.get(0);
					String enumString = enumLiteral.getName();
					FMCascadeType cascadeType = FMCascadeType.valueOf(enumString);
					linkedProperty.setCascade(cascadeType);
				}
				break;
			}
		}
	}

	private static void createPersistantProperty(Property tag, Property property, FMProperty fmProperty,
			Stereotype stereotype, FMPeristantProperty persistantProperty) {
		String tagName = tag.getName();

		// preuzimanje vrednosti tagova
		List<?> values = StereotypesHelper.getStereotypePropertyValue(property, stereotype, tagName);

		// ako tag ima vrednosti
		if (values.size() > 0) {
			switch (tagName) {
			case FMPeristantProperty.isKeyField:
				if (values.get(0) instanceof Boolean) {
					Boolean isKey = (Boolean) values.get(0);
					persistantProperty.setKey(isKey);
				}
				break;
			case FMPeristantProperty.isUniqueField:
				if (values.get(0) instanceof Boolean) {
					Boolean isUnique = (Boolean) values.get(0);
					persistantProperty.setUnique(isUnique);
				}
				break;
			case FMPeristantProperty.lengthField:
				if (values.get(0) instanceof Integer) {
					Integer length = (Integer) values.get(0);
					persistantProperty.setLength(length);
				}
				break;
			case FMPeristantProperty.precisionField:
				if (values.get(0) instanceof Integer) {
					Integer precision = (Integer) values.get(0);
					persistantProperty.setPrecision(precision);
				}
				break;
			case FMPeristantProperty.scaleField:
				if (values.get(0) instanceof Integer) {
					Integer scale = (Integer) values.get(0);
					persistantProperty.setScale(scale);
				}
				break;
			case FMPeristantProperty.generatorField:
				if (values.get(0) instanceof EnumerationLiteral) {
					EnumerationLiteral enumLiteral = (EnumerationLiteral) values.get(0);
					String enumString = enumLiteral.getName();
					FMGeneratorType generator = FMGeneratorType.valueOf(enumString);
					persistantProperty.setGenerator(generator);
				}
				break;
			}
		}
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
