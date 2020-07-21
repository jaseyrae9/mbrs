package myplugin.analyzer;

import java.util.Iterator;
import java.util.List;

import com.nomagic.uml2.ext.jmi.helpers.ModelHelper;
import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Property;
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Stereotype;

import myplugin.generator.fmmodel.FMClass;
import myplugin.generator.fmmodel.FMMicroservice;
import myplugin.generator.fmmodel.FMProperty;
import myplugin.utils.Constants;

public class ClassAnalyzer {
	
	//TODO: Provera da li persistant klasa ima kljuc
	public static FMClass analyzeClass(FMMicroservice microservice, Class magicClass, String packageName) throws AnalyzeException {
		if(magicClass.getName() == null) {
			throw new AnalyzeException("Classes must have names!");
		}
		
		FMClass fmClass = new FMClass(magicClass.getID(), magicClass.getName(), packageName, microservice);
		
		//ucitati tagove perizistente klase
		Stereotype stereotype = StereotypesHelper.getAppliedStereotypeByString(magicClass,Constants.persistentEntityIdentifier);		
		if (stereotype != null) {
			fmClass.setPersistant(true);
			List<Property> tags = stereotype.getOwnedAttribute();
			for (Property tag : tags) {
				createProperty(tag, fmClass, magicClass, stereotype);
			}
		}
		
		// Pronalazenje svojstava klase
		Iterator<Property> iterator = ModelHelper.attributes(magicClass);
		while (iterator.hasNext()) {
		Property p = iterator.next();
			FMProperty property = PropertyAnalyzer.createPropertyData(p);			
			fmClass.getProperties().add(property);
		}	
		
		return fmClass;
	}

	private static void createProperty(Property tag, FMClass fmClass, Class magicClass, Stereotype stereotype) {
		String tagName = tag.getName();

		// preuzimanje vrednosti tagova
		List<?> values = StereotypesHelper.getStereotypePropertyValue(magicClass, stereotype, tagName);

		// if tag has tag values
		if (values.size() > 0) {
			switch (tagName) {
			case FMClass.tableNameField:
				if(values.get(0) instanceof String) {
					String tableName = (String)values.get(0);
					fmClass.setTableName(tableName);
				}
				break;
			case FMClass.schemeField:
				if(values.get(0) instanceof String) {
					String scheme = (String)values.get(0);
					fmClass.setScheme(scheme);
				}
				break;
			case FMClass.generateCreateField:
				if(values.get(0) instanceof Boolean) {
					Boolean generateCreate= (Boolean)values.get(0);
					fmClass.setGenerateCreate(generateCreate);
				}
				break;
			case FMClass.generateUpdateField:
				if(values.get(0) instanceof Boolean) {
					Boolean generateUpdate= (Boolean)values.get(0);
					fmClass.setGenerateUpdate(generateUpdate);
				}
				break;
			case FMClass.generateDeleteField:
				if(values.get(0) instanceof Boolean) {
					Boolean generateDelete= (Boolean)values.get(0);
					fmClass.setGenerateDelete(generateDelete);
				}
				break;
			case FMClass.generateReadOneField:
				if(values.get(0) instanceof Boolean) {
					Boolean generateReadOne= (Boolean)values.get(0);
					fmClass.setGenerateReadOne(generateReadOne);
				}
				break;
			case FMClass.generateReadAllField:
				if(values.get(0) instanceof Boolean) {
					Boolean generateReadAll= (Boolean)values.get(0);
					fmClass.setGenerateReadAll(generateReadAll);
				}
				break;
			}
		}
	}
}
