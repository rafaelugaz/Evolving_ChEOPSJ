/*******************************************************************************
 * Copyright (c) 2011 Quinten David Soetens
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v2.1
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Quinten David Soetens - initial API and implementation
 ******************************************************************************/

package be.ac.ua.ansymo.cheopsj.changerecorders;

import java.util.List;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.Type;

import be.ac.ua.ansymo.cheopsj.model.changes.Add;
import be.ac.ua.ansymo.cheopsj.model.changes.AtomicChange;
import be.ac.ua.ansymo.cheopsj.model.changes.Change;
import be.ac.ua.ansymo.cheopsj.model.changes.IChange;
import be.ac.ua.ansymo.cheopsj.model.changes.Remove;
import be.ac.ua.ansymo.cheopsj.model.changes.Subject;
import be.ac.ua.ansymo.cheopsj.model.famix.FamixClass;
import be.ac.ua.ansymo.cheopsj.model.famix.FamixEntity;

/**
 * @author quinten
 * 
 */
public abstract class AbstractEntityRecorder {

	/**
	 * @param change
	 */
	public void storeChange(IChange change) {
		createAndLinkFamixElement();
		createAndLinkChange((AtomicChange) change);
	}

	abstract protected void createAndLinkFamixElement();

	abstract protected void createAndLinkChange(AtomicChange change);
	
	protected void setStructuralDependencies(AtomicChange change, Subject subject, 
			FamixEntity parent, Object classObj) {
		
		if (change instanceof Add) {
			if (parent != null) {
				Change parentChange = parent.getLatestAddition();
				if (parentChange != null) {
					change.addStructuralDependency(parentChange);
				}//The parent of the class, be it a class or a package should already exist.
			}
			Remove removalChange = subject.getLatestRemoval();
			if (removalChange != null) {
				change.addStructuralDependency(removalChange);
			}
		} else if (change instanceof Remove) {
			// set dependency to addition of this entity
			AtomicChange additionChange = subject.getLatestAddition();
			if (additionChange != null) {
				change.addStructuralDependency(additionChange);
				
				//Dependencies to removes of child entities:
				if(classObj instanceof ClassRecorder) {
					((ClassRecorder) classObj).removeAllContainedWithin(change, additionChange);
				}
				else {
					((MethodRecorder) classObj).removeAllContainedWithin(change, additionChange);
				}
			}
		}
	}
	
	//  Search for fully qualified classname in import statments
	// the first param field_type can be of type FieldDeclaration or Type depending on
	// where the function is called
	protected void searchClassname(Object field_type, String declaredClassName) {
		CompilationUnit cu;
		
		if(field_type instanceof FieldDeclaration) {
			cu = (CompilationUnit)((FieldDeclaration)field_type).getRoot();
		}
		else {
			cu = (CompilationUnit)((Type)field_type).getRoot();
		}
		List<ImportDeclaration> imports = cu.imports();
		for(ImportDeclaration imp : imports){
			Name impname = imp.getName();
			if(impname.getFullyQualifiedName().endsWith(declaredClassName)){
				declaredClassName = impname.getFullyQualifiedName();
				break;
			}
		}
		
		//if still not found there, need to assume that the class is in same package as this class.
		if(!declaredClassName.contains(".")){
			declaredClassName = cu.getPackage().getName().getFullyQualifiedName() + "." + declaredClassName;
		}
	}
}
