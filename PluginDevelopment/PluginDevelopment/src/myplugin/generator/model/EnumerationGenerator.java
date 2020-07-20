package myplugin.generator.model;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import freemarker.template.TemplateException;
import myplugin.generator.BasicGenerator;
import myplugin.generator.fmmodel.FMEnumeration;
import myplugin.generator.fmmodel.FMMicroservice;
import myplugin.generator.fmmodel.FMModel;
import myplugin.generator.options.GeneratorOptions;

public class EnumerationGenerator extends BasicGenerator {
	public EnumerationGenerator(GeneratorOptions generatorOptions) {
		super(generatorOptions);
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

			for (FMEnumeration enumeration : microservice.getEnumerations()) {
				// generisi datoteku
				Writer out;
				Map<String, Object> context = new HashMap<String, Object>();
				try {
					// parametar 1 je cime se menja {0} u imenu datoteke
					// parametar 2 je naziv paketa, ovo je da bismo lakse mogli, npr u modelu imati
					// podpakete
					out = getWriter(enumeration.getName(), enumeration.getTypePackage());
					if (out != null) {
						context.clear();
						context.put("package", enumeration.getTypePackage());
						context.put("name", enumeration.getName());
						context.put("literals", enumeration.getValues());
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
}
