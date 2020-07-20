package myplugin.generator;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import freemarker.template.TemplateException;
import myplugin.generator.fmmodel.FMMicroservice;
import myplugin.generator.fmmodel.FMModel;
import myplugin.generator.options.GeneratorOptions;

/**
 * Generiše po jedan fajl za svaki mikroservis.
 * 
 * Primer gde je korisno: pom.xml, application.yml, MapperCatalogue
 * Potrebno samo podesiti kontekst tako da ima odgovarajuće
 * atribute.
 */
public abstract class PerMicroserviceGenerator extends BasicGenerator {

	public PerMicroserviceGenerator(GeneratorOptions generatorOptions) {
		super(generatorOptions);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void generate() throws IOException {		
		
		for(FMMicroservice microservice: FMModel.getInstance().getMicroservices()) {
			//spremi foldere kako treba
			try {
				//postavi naziv mikroservisa za koji se trenutno radi generisanje
				microserviceName = microservice.getName();
				//proveri da li postoji template i generiši folder za čuvanje
				super.generate();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
			
			//generisi datoteku
			Writer out;				
			try {
				// parametar 1 je cime se menja {0} u imenu datoteke
				// parametar 2 je naziv paketa, ovo je da bismo lakse mogli, npr u modelu imati
				// podpakete
				out = getWriter("", "");
				Map<String, Object> context = new HashMap<String, Object>();;
				if (out != null) {
					prepareContext(microservice, context);						
					getTemplate().process(context, out);
					out.flush();
				}
			} catch (TemplateException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
		}
	}
	
	public void prepareContext(FMMicroservice microservice, Map<String, Object> context) {
		context.clear();
	}
}
