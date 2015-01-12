
package com.teotigraphix.caustk.gdx.app;

import com.teotigraphix.caustk.node.machine.MachineNode;
import com.teotigraphix.caustk.node.machine.sequencer.PatternNode;
import com.teotigraphix.caustk.gdx.app.IProjectModel.ProjectModelEvent;

import java.util.ArrayList;
import java.util.List;

public class ProjectModelMachineAPI extends AbstractProjectModelAPI {

    private List<Integer> triggereddMachines = new ArrayList<Integer>();

    //----------------------------------
    // triggereddMachines
    //----------------------------------

    public List<Integer> getTriggeredMachines() {
        return triggereddMachines;
    }

    public void setTriggeredMachines(List<Integer> triggereddMachines) {
        this.triggereddMachines = triggereddMachines;
    }

    public boolean hasMachine(int index) {
        return getProject().getRackNode().containsMachine(index);
    }

    public final int getSelectedMachineIndex() {
        return getSelectedMachine().getIndex();
    }

    public void setSelectedMachineIndex(int machineIndex) {
        if (getProject().getRackNode().getSelectedIndex() == machineIndex)
            return;
        getProject().getRackNode().setSelectedIndex(machineIndex);
        post(new ProjectModelEvent(ProjectModelEvent.Kind.MachineSelectionChange, getProjectModel()));
    }

    public MachineNode getMachine(int index) {
        return getProject().getRackNode().getMachine(index);
    }

    public final MachineNode getSelectedMachine() {
        return getProject().getRackNode().getSelectedMachine();
    }

    public final PatternNode getSelectedMachinePattern() {
        MachineNode selectedMachine = getSelectedMachine();
        if (selectedMachine == null)
            return null;
        return selectedMachine.getSequencer().getSelectedPattern();
    }

    public ProjectModelMachineAPI(ProjectModel projectModel) {
        super(projectModel);
    }

    @Override
    public void restore(ProjectState state) {
        getProjectModel().getEventBus().post(
                new ProjectModelEvent(ProjectModelEvent.Kind.MachineSelectionChange,
                        getProjectModel()));
    }
}