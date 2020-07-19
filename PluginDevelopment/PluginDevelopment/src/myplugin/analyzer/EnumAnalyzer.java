package myplugin.analyzer;

import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Enumeration;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.EnumerationLiteral;

import myplugin.generator.fmmodel.FMEnumeration;

public class EnumAnalyzer {
	public static FMEnumeration analyzeEnumeration(Enumeration enumeration, String packageName) throws AnalyzeException {
		if(enumeration.getName() == null) {
			throw new AnalyzeException("Enumerations must have names!");
		}
		FMEnumeration fmEnumeration = new FMEnumeration(enumeration.getName(), packageName);
		for(EnumerationLiteral literal: enumeration.getOwnedLiteral()) {
			fmEnumeration.getValues().add(literal.getName());
		}	
		return fmEnumeration;
	}
}
