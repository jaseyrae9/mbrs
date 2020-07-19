package myplugin.analyzer;

import java.util.Iterator;

import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Enumeration;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Package;

import myplugin.generator.fmmodel.FMMicroservice;
import myplugin.generator.fmmodel.FMModel;
import myplugin.utils.Constants;

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
		/** @ToDo: Spremiti model za novu upotrebu, dakle ocistiti sve liste itd... */
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
		if (pack != root && !microservice.getName().equals(pack.getName())) {
			packageName += "." + pack.getName();
		}

		if (pack.hasOwnedElement()) {

			/** @ToDo: Obrada paketa, po potrebi pozvati rekurizivno za podpakete */
			for (Iterator<Element> it = pack.getOwnedElement().iterator(); it.hasNext();) {
				Element ownedElement = it.next();

				if (microservice == null) {
					// pretrazujemo glavni paket da bismo nasli one pakete koji su mikroservisi
					if (ownedElement instanceof Package) {
						Package ownedPackage = (Package) ownedElement;
						if (StereotypesHelper.getAppliedStereotypeByString(ownedPackage, Constants.microserviceIdentifier) != null) {
							FMMicroservice fmMicroservice = MicroserviceAnalyzer.analyzeMicroservice(ownedPackage);
							FMModel.getInstance().getMicroservices().add(fmMicroservice);
							// pozvati rekurzivnu analizu
							processPackage(ownedPackage, "", fmMicroservice);
						}
					}					
				} else {
					//nasli smo mikroservis, analiziramo njega i podpakete da bismo nasli klase i enumeracije
					if (ownedElement instanceof Package) {
						Package ownedPackage = (Package) ownedElement;
						processPackage(ownedPackage, packageName, microservice);
					}
					if (ownedElement instanceof Enumeration) {
						Enumeration enumeration = (Enumeration) ownedElement;
						microservice.getEnumerations().add(EnumAnalyzer.analyzeEnumeration(enumeration, packageName));
					} else if (ownedElement instanceof Class) {
						Class magicClass = (Class) ownedElement;
						microservice.getClasses().add(ClassAnalyzer.analyzeClass(magicClass));
					}
				}
			}
		}
	}

}
