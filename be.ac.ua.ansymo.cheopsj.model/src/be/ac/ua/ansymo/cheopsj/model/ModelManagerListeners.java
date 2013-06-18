package be.ac.ua.ansymo.cheopsj.model;

import java.util.ArrayList;
import java.util.List;

import be.ac.ua.ansymo.cheopsj.model.changes.IChange;

public class ModelManagerListeners {
	
	//This list contains all ModelManagerListeners, i.e. listeners that need to be notified when a new change was added to the model.
	//For instance the views in the ...model.ui plugin
	private List<ModelManagerListener> listeners;
	
	public ModelManagerListeners() {
		listeners = new ArrayList<ModelManagerListener>();
	}
	
	public List<ModelManagerListener> getListeners() {
		return listeners;
	}
	
	public void setListeners(List<ModelManagerListener> listeners) {
		this.listeners = listeners;
	}
	
	/**
	 * Add a listener to the modelmanager, this listener will be updated when a change is added to the model.
	 * @param listener
	 */
	public void addModelManagerListener(ModelManagerListener listener) {
		if (!listeners.contains(listener))
			listeners.add(listener);
	}

	/**
	 * remove a given listener
	 * @param listener
	 */
	public void removeModelManagerListener(ModelManagerListener listener) {
		listeners.remove(listener);
	}
	
	//One change added to ModelManager, this method is used to interate the ModelMangerListeners and notify them.
	public void fireChangeAdded(IChange newChange) {
		ModelManagerEvent event = new ModelManagerEvent(ModelManager.getInstance(), new IChange[] {newChange});
		for (ModelManagerListener i : listeners) {
			i.changesAdded(event);
		}
		// printAllChanges();
	}
	
	//Several changes added to ModelManager, this method is used to interate the ModelMangerListeners and notify them.
	public void fireChangesAdded(IChange[] newChanges) {
		ModelManagerEvent event = new ModelManagerEvent(ModelManager.getInstance(), newChanges);
		for (ModelManagerListener i : listeners) {
			i.changesAdded(event);
		}
		// printAllChanges();
	}
}
