package be.ac.ua.ansymo.cheopsj.model;

import java.util.ArrayList;
import java.util.List;

import be.ac.ua.ansymo.cheopsj.model.changes.Change;
import be.ac.ua.ansymo.cheopsj.model.changes.IChange;

public class ModelManagerChange {

	private List<IChange> changes;

	private ModelManager modelManager;
	
	//The ModelManagerChange is a Singleton entity, hence the constructor is private.
	//You should always call the static method getInstance() to get the ModelManager instance.
	private static ModelManagerChange INSTANCE = new ModelManagerChange();
	
	private ModelManagerChange() {
		changes = new ArrayList<IChange>();
		modelManager = ModelManager.getInstance();
	}
	
	/**
	 * The ModelManger is a Singleton entity. Therefore the constructor is private.
	 * This method returns an instance of the ModelManger. If no instance existed 
	 * before it will call the private constructor to create a new instance. Else
	 * It will return the existing instance. 
	 *  
	 * @return the Singleton ModelManager instance
	 */
	public static ModelManagerChange getInstance() {
		if (INSTANCE == null)
			INSTANCE = new ModelManagerChange();
		return INSTANCE;
	}
	
	/**
	 * @return the list of changes maintained in the ModelManagerChange
	 */
	public List<IChange> getChanges() {
		return changes;
	}
	
	public void setChanges(List<IChange> changes) {
		this.changes = changes;
	}
	
	/**
	 * Add a change to the ModelManager and alert the listeners of a new change.
	 * @param change
	 */
	public void addChange(Change change) {
		//add change to list
		changes.add(change);
	}
}
