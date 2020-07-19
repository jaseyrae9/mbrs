package myplugin.action;

import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.nomagic.magicdraw.actions.MDAction;
import com.nomagic.magicdraw.core.Application;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Package;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import myplugin.analyzer.AnalyzeException;
import myplugin.analyzer.ModelAnalyzer;
import myplugin.generator.fmmodel.FMModel;

/**
 * Akcija za cuvanje modela iz radne memorije u xml.
 */
public class SaveToXmlAction extends MDAction {
	private static final long serialVersionUID = 1L;

	public SaveToXmlAction(String name) {
		super("", name, null, null);
	}

	public void actionPerformed(ActionEvent evt) {
		//provere da li je otvoren projekat itd
		if (Application.getInstance().getProject() == null) return;
		Package root = Application.getInstance().getProject().getModel();		
		if (root == null) return;	
				
		ModelAnalyzer analyzer = new ModelAnalyzer(root, "micronaut");
		try {
			analyzer.prepareModel();
		} catch (AnalyzeException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		
		if (JOptionPane.showConfirmDialog(null, "Do you want to save FM Model?") == JOptionPane.OK_OPTION) {
			JFileChooser jfc = new JFileChooser();
			if (jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
				String fileName = jfc.getSelectedFile().getAbsolutePath();

				XStream xstream = new XStream(new DomDriver());
				BufferedWriter out;
				try {
					out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "UTF8"));
					/** @ToDo: Serijalizacija modela u xml */
					xstream.toXML(FMModel.getInstance().getMicroservices(), out);

				} catch (UnsupportedEncodingException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				} catch (FileNotFoundException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
		}
	}
}
