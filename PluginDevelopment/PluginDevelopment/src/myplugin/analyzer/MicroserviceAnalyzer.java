package myplugin.analyzer;

import java.util.List;

import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Package;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Property;
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Stereotype;

import myplugin.generator.fmmodel.FMMicroservice;
import myplugin.utils.Constants;

public class MicroserviceAnalyzer {
	
	public static FMMicroservice analyzeMicroservice(Package ownedPackage) {		
		FMMicroservice fmMicroservice = new FMMicroservice(ownedPackage.getID(), ownedPackage.getName());
		
		Stereotype stereotype = StereotypesHelper.getAppliedStereotypeByString(ownedPackage, Constants.microserviceIdentifier);		
		if (stereotype != null) {			
			List<Property> tags = stereotype.getOwnedAttribute();
			for (Property tag : tags) {
				createProperty(tag, fmMicroservice, ownedPackage, stereotype);

			}
		}
		return fmMicroservice;
	}

	private static void createProperty(Property tag, FMMicroservice fmMicroservice, Package ownedPackage,
			Stereotype stereotype) {

		String tagName = tag.getName();

		// preuzimanje vrednosti tagova
		List<?> values = StereotypesHelper.getStereotypePropertyValue(ownedPackage, stereotype, tag.getName());

		// if tag has tag values
		if (values.size() > 0) {
			switch (tagName) {
			case FMMicroservice.dbUrlField:
				if(values.get(0) instanceof String) {
					String dbUrl = (String)values.get(0);
					fmMicroservice.setDbUrl(dbUrl);
				}
				break;
			case FMMicroservice.dbUsernameField:
				if(values.get(0) instanceof String) {
					String dbUsername = (String)values.get(0);
					fmMicroservice.setDbUsername(dbUsername);
				}
				break;
			case FMMicroservice.dbPasswordField:
				if(values.get(0) instanceof String) {
					String dbPassword= (String)values.get(0);
					fmMicroservice.setDbPassword(dbPassword);
				}
				break;
			}
		}
		
	}
	
	

}
