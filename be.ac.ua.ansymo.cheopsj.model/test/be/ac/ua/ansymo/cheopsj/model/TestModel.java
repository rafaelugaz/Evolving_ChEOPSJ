package be.ac.ua.ansymo.cheopsj.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import be.ac.ua.ansymo.cheopsj.model.changes.Change;
import be.ac.ua.ansymo.cheopsj.model.changes.IChange;
import be.ac.ua.ansymo.cheopsj.model.famix.FamixAttribute;
import be.ac.ua.ansymo.cheopsj.model.famix.FamixClass;
import be.ac.ua.ansymo.cheopsj.model.famix.FamixInvocation;
import be.ac.ua.ansymo.cheopsj.model.famix.FamixLocalVariable;
import be.ac.ua.ansymo.cheopsj.model.famix.FamixMethod;
import be.ac.ua.ansymo.cheopsj.model.famix.FamixObject;
import be.ac.ua.ansymo.cheopsj.model.famix.FamixPackage;

public class TestModel {

	private Change change;
	private ModelManager model;
	private FamixObject fe1, fe2, fe3, fe4, fe5, fe6;
	
	@Before
	public void setUp() throws Exception {
		change = new Change();
		model = ModelManager.getInstance();
		fe1 = new FamixClass();
		fe2 = new FamixMethod();
		fe3 = new FamixPackage();
		fe4 = new FamixAttribute();
		fe5 = new FamixInvocation();
		fe6 = new FamixLocalVariable();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddChange() {
		int size;
		size = model.getChanges().size();
		
		model.addChange(change);
		
		assertTrue(!model.getChanges().isEmpty());
		assertTrue(model.getChanges().size() == (size + 1));
	}
	
	@Test
	public void testAddFamixElement() {
		int sizeFamEnt;
		FamixObject[] fes = {fe1, fe2, fe3, fe4, fe5, fe6};
		String nameFamPack, nameFamClass, nameFamMeth, nameFamAtt, nameFamVar, nameFamInv;
		
		for(FamixObject fe : fes) {
			sizeFamEnt = model.getFamixEntities().size();
			System.out.println(sizeFamEnt);
			model.addFamixElement(fe);
			
			if (fe instanceof FamixPackage) {
				nameFamPack = ((FamixPackage) fe).getUniqueName();
				model.getFamixPackage(nameFamPack);
				assertTrue(!model.getFamixPackagesMap().isEmpty());
			} 
			else if (fe instanceof FamixClass) {
				nameFamClass = ((FamixClass) fe).getUniqueName();
				model.getFamixClass(nameFamClass);
				assertTrue(!model.getFamixClassesMap().isEmpty());
			} 
			else if (fe instanceof FamixMethod) {
				nameFamMeth = ((FamixMethod) fe).getUniqueName();
				model.getFamixMethod(nameFamMeth);
				assertTrue(!model.getFamixMethodsMap().isEmpty());
			} 
			else if (fe instanceof FamixAttribute) {
				nameFamAtt = ((FamixAttribute) fe).getUniqueName();
				model.getFamixField(nameFamAtt);
				assertTrue(!model.getFamixFieldsMap().isEmpty());
			} 
			else if (fe instanceof FamixInvocation) {
				nameFamInv = ((FamixInvocation) fe).getStringRepresentation();
				model.getFamixInvocation(nameFamInv);
				assertTrue(!model.getFamixInvocationsMap().isEmpty());
			} 
			else if (fe instanceof FamixLocalVariable) {
				nameFamVar = ((FamixLocalVariable) fe).getUniqueName();
				model.getFamixVariable(nameFamVar);
				assertTrue(!model.getFamixVariablesMap().isEmpty());
			}
			
			assertTrue(!model.getFamixEntities().isEmpty());
			System.out.println(model.getFamixEntities().size());
			assertTrue(model.getFamixEntities().size() == (sizeFamEnt + 1));
		}
	}

}
