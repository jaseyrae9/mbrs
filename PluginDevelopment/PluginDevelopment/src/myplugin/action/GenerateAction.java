package myplugin.action;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Map;

import javax.swing.JOptionPane;

import com.nomagic.magicdraw.actions.MDAction;
import com.nomagic.magicdraw.core.Application;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Package;

import myplugin.analyzer.AnalyzeException;
import myplugin.analyzer.ModelAnalyzer;
import myplugin.generator.BasicGenerator;
import myplugin.generator.GeneratorFactory;
import myplugin.generator.StaticFilesGenerator;
import myplugin.generator.options.GeneratorOptions;
import myplugin.generator.options.ProjectOptions;

/** Action that activate code generation */
public class GenerateAction extends MDAction{	
	private static final long serialVersionUID = 1L;

	public GenerateAction(String name) {			
		super("", name, null, null);		
	}

	public void actionPerformed(ActionEvent evt) {
		//provere da li je otvoren projekat itd
		if (Application.getInstance().getProject() == null) return;
		Package root = Application.getInstance().getProject().getModel();		
		if (root == null) return;	
				
		ModelAnalyzer analyzer = new ModelAnalyzer(root, "");	
		
		try {
			//napravi model u radnoj memoriji
			analyzer.prepareModel();	
					
			/**  @ToDo: Call generators*/ 
			//pokreni generatore koji rade sa templejtima
			Map<String, GeneratorOptions> map = ProjectOptions.getProjectOptions().getGeneratorOptions();
			for(String name : map.keySet()) {
				BasicGenerator generator = GeneratorFactory.getGenerator(name, map.get(name));
				if(generator != null) {
					generator.generate();
				}
				else {
					JOptionPane.showMessageDialog(null, "Generator " + name + " not found.");
				}
			}
			//pokreni generator statickih fajlova, tj. kopirator
			StaticFilesGenerator generator = GeneratorFactory.getStaticFilesGenerator();
			generator.generate();
			
			//kraj generisanja
			JOptionPane.showMessageDialog(null, "Micronaut generate finished.");
			
		} catch (AnalyzeException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		} 	
	}		  

}