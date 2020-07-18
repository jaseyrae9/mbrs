package myplugin.analyzer;

import java.util.Iterator;

import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Enumeration;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Package;

import myplugin.generator.fmmodel.FMMicroservice;
import myplugin.generator.fmmodel.FMModel;

/**
 * Model Analyzer takes necessary metadata from the MagicDraw model and puts it
 * in the intermediate data structure (@see myplugin.generator.fmmodel.FMModel)
 * optimized for code generation using freemarker. Model Analyzer now takes
 * metadata only for ejb code generation
 * 
 * @ToDo: Enhance (or completely rewrite) myplugin.generator.fmmodel classes and
 *        Model Analyzer methods in order to support GUI generation.
 */

public class ModelAnalyzer {
	// root model package
	private Package root;

	// java root package for generated code
	private String filePackage;

	public ModelAnalyzer(Package root, String filePackage) {
		super();
		this.root = root;
		this.filePackage = filePackage;
	}

	public Package getRoot() {
		return root;
	}

	public void prepareModel() throws AnalyzeException {
		/** @ToDo: Spremiti model za novu upotrebu, dakle očistiti sve liste itd... */
		FMModel.getInstance().getMicroservices().clear();
		processPackage(root, filePackage, null);
	}

	private void processPackage(Package pack, String packageOwner, FMMicroservice microservice)
			throws AnalyzeException {
		// Recursive procedure that extracts data from package elements and stores it in
		// the intermediate data structure

		if (pack.getName() == null)
			throw new AnalyzeException("Packages must have names!");

		String packageName = packageOwner;
		if (pack != root) {
			packageName += "." + pack.getName();
		}

		if (pack.hasOwnedElement()) {

			/** @ToDo: Obrada paketa, po potrebi pozvati rekurizivno za podpakete */
			for (Iterator<Element> it = pack.getOwnedElement().iterator(); it.hasNext();) {
				Element ownedElement = it.next();

				if (microservice != null) {
					// analiziramo mikroservis da nadjemo klase i enumeracije
					if (ownedElement instanceof Enumeration) {
						Enumeration enumeration = (Enumeration) ownedElement;
						microservice.getEnumerations().add(EnumAnalyzer.analyzeEnumeration(enumeration));
					}
				} else {
					// pretrazujemo glavni paket da bismo nasli one pakete koji su mikroservisi
					if (ownedElement instanceof Package) {
						Package ownedPackage = (Package) ownedElement;
						if (StereotypesHelper.getAppliedStereotypeByString(ownedPackage, "Microservice") != null) {
							FMMicroservice fmMicroservice = new FMMicroservice(ownedPackage.getName());
							FMModel.getInstance().getMicroservices().add(fmMicroservice);

							// pozvati rekurzivnu analizu
							processPackage(ownedPackage, packageName, fmMicroservice);
						}
					}
				}
			}
		}
	}

}
