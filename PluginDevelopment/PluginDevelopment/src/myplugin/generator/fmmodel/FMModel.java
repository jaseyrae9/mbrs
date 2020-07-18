package myplugin.generator.fmmodel;

import java.util.ArrayList;
import java.util.List;

/** FMModel: Singleton class. This is intermediate data structure that keeps metadata
 * extracted from MagicDraw model. Data structure should be optimized for code generation
 * using freemarker
 */

public class FMModel {	
	
	//....
	/** @ToDo: Add lists of other elements, if needed */
	private List<FMMicroservice> microservices = new ArrayList<FMMicroservice>();
	
	private FMModel() {}
	
	private static FMModel model;
	
	public static FMModel getInstance() {
		if (model == null) {
			model = new FMModel();			
		}
		return model;
	}

	public List<FMMicroservice> getMicroservices() {
		return microservices;
	}

	public void setMicroservices(List<FMMicroservice> microservices) {
		this.microservices = microservices;
	}
}
