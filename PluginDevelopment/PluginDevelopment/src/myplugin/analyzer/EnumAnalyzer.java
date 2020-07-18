package myplugin.analyzer;

import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Enumeration;

import myplugin.generator.fmmodel.FMEnumeration;

public class EnumAnalyzer {
	public static FMEnumeration analyzeEnumeration(Enumeration enumeration) {
		FMEnumeration fmEnumeration = new FMEnumeration(enumeration.getName());
		return fmEnumeration;
	}
}
