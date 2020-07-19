package myplugin;

import java.io.FileNotFoundException;

import javax.swing.JOptionPane;

import com.nomagic.actions.NMAction;
import com.nomagic.magicdraw.actions.ActionsConfiguratorsManager;

import myplugin.action.GenerateAction;
import myplugin.action.SaveToXmlAction;
import myplugin.generator.options.OptionsLoader;
import myplugin.generator.options.ProjectOptions;
import myplugin.generator.options.SerializableProjectOptions;

/** MagicDraw plugin that performes code generation */
public class MyPlugin extends com.nomagic.magicdraw.plugins.Plugin {
	
	String pluginDir = null; 
	
	public void init() {
		JOptionPane.showMessageDialog( null, "Micronaut Plugin Init");
		
		pluginDir = getDescriptor().getPluginDirectory().getPath();
		
		// Creating submenu in the MagicDraw main menu 	
		ActionsConfiguratorsManager manager = ActionsConfiguratorsManager.getInstance();		
		manager.addMainMenuConfigurator(new MainMenuConfigurator(getSubmenuActions()));
		
		/** @Todo: Ucitavanje opcija za generator*/		
		OptionsLoader optionsLoader = new OptionsLoader();
		try {			
			SerializableProjectOptions projectOptions = optionsLoader.loadProjectOptionsFromXML(pluginDir, "ProjectOptions.xml");			
			ProjectOptions.getProjectOptions().setGeneratorOptions(projectOptions.getGeneratorOptions());
			ProjectOptions.getProjectOptions().setTypeMappings(projectOptions.getTypeMappings());
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog( null, "Loading Micronaut plugin options failed.");
		} catch (Exception e) {
			JOptionPane.showMessageDialog( null, e.getStackTrace());
		}
		
	}

	private NMAction[] getSubmenuActions()
	{
	   return new NMAction[]{
			new GenerateAction("Generate"),
			new SaveToXmlAction("Save Model To Xml")
	   };
	}
	
	public boolean close() {
		return true;
	}
	
	public boolean isSupported() {				
		return true;
	}
}


