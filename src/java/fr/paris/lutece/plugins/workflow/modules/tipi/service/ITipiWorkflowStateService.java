package fr.paris.lutece.plugins.workflow.modules.tipi.service;

import fr.paris.lutece.util.ReferenceList;

public interface ITipiWorkflowStateService
{

    // BEAN
    String BEAN_NAME = "workflow-tipi.tipiWorkflowStateService";

    /**
     * Get The List of States
     * 
     * @param nIdAction
     *            the Action Id
     * 
     * @return a list of states
     */
    ReferenceList getListStates( int nIdAction );

    /**
     * Changes the workflow state of a workflow resource
     * 
     * @param nIdState
     *            the id of the target state
     * @param nIdResourceHistory
     *            the id of the resource history associated to the workflow resource
     */
    void changeState( int nIdState, int nIdResourceHistory );

}
