package be.ac.ua.ansymo.cheopsj.model;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import be.ac.ua.ansymo.cheopsj.model.changes.Add;
import be.ac.ua.ansymo.cheopsj.model.changes.AtomicChange;
import be.ac.ua.ansymo.cheopsj.model.changes.Change;
import be.ac.ua.ansymo.cheopsj.model.changes.CompositeChange;
import be.ac.ua.ansymo.cheopsj.model.changes.IChange;
import be.ac.ua.ansymo.cheopsj.model.changes.Remove;
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
	private ModelManagerChange modelChange;
	private FamixObject fe1, fe2, fe3, fe4, fe5, fe6;
	
	@Before
	public void setUp() throws Exception {
		change = new Change();
		model = ModelManager.getInstance();
		modelChange = ModelManagerChange.getInstance();
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
		size = model.getModelManagerChange().getChanges().size();
		
		model.getModelManagerChange().addChange(change);
		
		assertTrue(!model.getModelManagerChange().getChanges().isEmpty());
		assertTrue(model.getModelManagerChange().getChanges().size() == (size + 1));
	}
	
	@Test
	public void testAddFamixElement() {
		int sizeFamEnt;
		FamixObject[] fes = {fe1, fe2, fe3, fe4, fe5, fe6};
		String nameFamPack, nameFamClass, nameFamMeth, nameFamAtt, nameFamVar, nameFamInv;
		
		for(FamixObject fe : fes) {
			sizeFamEnt = model.getFamixEntities().size();
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
			assertTrue(model.getFamixEntities().size() == (sizeFamEnt + 1));
		}
	}
	
	/*
	@SuppressWarnings("rawtypes")
	@Test
	public void testLoadModel() throws NoSuchMethodException, SecurityException, 
	IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		model.loadModel();
		
	}*/
	
	@Test
	public void testGetSummary() {
		model.getModelManagerChange().addChange(new Change());
		model.getModelManagerChange().addChange(new Add());
		model.getModelManagerChange().addChange(new Add());
		model.getModelManagerChange().addChange(new Remove());
		model.getModelManagerChange().addChange(new Remove());
		model.getModelManagerChange().addChange(new Remove());
		model.getModelManagerChange().addChange(new AtomicChange());
		model.getModelManagerChange().addChange(new CompositeChange());
		model.getModelManagerChange().addChange(new CompositeChange());
		
		int changeCountLoc = model.getModelManagerChange().getChanges().size();
		String result;
		int changeCount, addCount, removeCount;
		int addCountLoc = 0, removeCountLoc = 0;
		
		assertTrue(changeCountLoc >= 0);
		
		for(IChange change: model.getModelManagerChange().getChanges()){
			if(change instanceof Add){
				addCountLoc++;
			}else if(change instanceof Remove){
				removeCountLoc++;
			}
		}
		
		result = model.getModelManagerChange().getSummary();
		
		String[] res_parts = result.split(" ");
		changeCount = Integer.parseInt(res_parts[0]);
		addCount = Integer.parseInt(res_parts[2]);
		removeCount = Integer.parseInt(res_parts[5]);
		
		assertTrue(changeCountLoc == changeCount);
		assertTrue(addCountLoc == addCount);
		assertTrue(removeCountLoc == removeCount);
		assertTrue(changeCount >= (addCount + removeCount));
	}
	
	/*
	@SuppressWarnings("rawtypes")
	@Test
	public void testTest() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class[] paramOIS = new Class[1];	
		paramOIS[0] = Integer.TYPE;
		Class<?> noparams[] = {};
		int a = 2, b;
		
		Method loadFamixEntitiesMethod = 
				ModelManager.class.getDeclaredMethod("test", noparams);
		loadFamixEntitiesMethod.setAccessible(true);
		
		b = (Integer) loadFamixEntitiesMethod.invoke(model, null);
		assertTrue(b == (5));
	}*/

}
