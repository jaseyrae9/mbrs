package myplugin.generator;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import freemarker.template.TemplateException;
import myplugin.generator.fmmodel.FMClass;
import myplugin.generator.fmmodel.FMMicroservice;
import myplugin.generator.fmmodel.FMModel;
import myplugin.generator.options.GeneratorOptions;


/**
 * Prolazi kroz sve mikroservise. Za svaku perzistentu klasu
 * koju mikroservis ima poziva generisanje templejta.
 * 
 * Pogodno za repozitorijume, servise, kontrolere, dto, mapere.
 * Potrebno samo podesiti kontekst tako da ima odgovarajuće
 * atribute.
 */
public abstract class PerClassGenerator extends BasicGenerator {

	public PerClassGenerator(GeneratorOptions generatorOptions) {
		super(generatorOptions);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void generate() throws IOException {

		for (FMMicroservice microservice : FMModel.getInstance().getMicroservices()) {
			// spremi foldere kako treba
			try {
				// postavi naziv mikroservisa za koji se trenutno radi generisanje
				microserviceName = microservice.getName();
				// proveri da li postoji template i generisi folder za cuvanje
				super.generate();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
			
			for (FMClass fmClass : microservice.getPersistantClasses()) {
				// generisi datoteku
				Writer out;				
				try {
					// parametar 1 je cime se menja {0} u imenu datoteke
					// parametar 2 je naziv paketa, ovo je da bismo lakse mogli, npr u modelu imati
					// podpakete
					out = getWriter(fmClass.getName(), fmClass.getTypePackage());
					Map<String, Object> context = new HashMap<String, Object>();;
					if (out != null) {
						prepareContext(fmClass, context);						
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
	}
	
	public void prepareContext(FMClass fmClass, Map<String, Object> context) {
		context.clear();
	}

}
