package myplugin.analyzer;

import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Enumeration;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.EnumerationLiteral;

import myplugin.generator.fmmodel.FMEnumeration;
import myplugin.generator.fmmodel.FMMicroservice;

public class EnumAnalyzer {
	public static FMEnumeration analyzeEnumeration(FMMicroservice microservice, Enumeration enumeration, String packageName) throws AnalyzeException {
		if(enumeration.getName().trim() == null) {
			throw new AnalyzeException("Enumerations must have names!");
		}
		FMEnumeration fmEnumeration = new FMEnumeration(enumeration.getID(), enumeration.getName(), packageName, microservice);
		for(EnumerationLiteral literal: enumeration.getOwnedLiteral()) {
			fmEnumeration.getValues().add(literal.getName());
		}	
		return fmEnumeration;
	}
}
