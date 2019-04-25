/*
 * Copyright (c) 2002-2018, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.workflow.modules.tipi.service;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;

import fr.paris.lutece.plugins.workflowcore.business.action.Action;
import fr.paris.lutece.plugins.workflowcore.business.resource.ResourceHistory;
import fr.paris.lutece.plugins.workflowcore.business.resource.ResourceWorkflow;
import fr.paris.lutece.plugins.workflowcore.business.state.State;
import fr.paris.lutece.plugins.workflowcore.business.state.StateFilter;
import fr.paris.lutece.plugins.workflowcore.service.action.IActionService;
import fr.paris.lutece.plugins.workflowcore.service.resource.IResourceHistoryService;
import fr.paris.lutece.plugins.workflowcore.service.resource.IResourceWorkflowService;
import fr.paris.lutece.plugins.workflowcore.service.state.IStateService;
import fr.paris.lutece.portal.service.workflow.WorkflowService;
import fr.paris.lutece.util.ReferenceList;

/**
 * 
 * Service to get the list of states of the task
 * 
 *
 */

public class TipiWorkflowStateService implements ITipiWorkflowStateService
{
    // CONSTANTS
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String ID_NULL = "-1";

    // SERVICES
    @Inject
    private IActionService _actionService;
    @Inject
    private IStateService _stateService;
    @Inject
    private IResourceWorkflowService _resourceWorkflowService;
    @Inject
    private IResourceHistoryService _resourceHistoryService;

    /**
     * {@inheritDoc }
     */
    @Override
    public ReferenceList getListStates( int nIdAction )
    {
        ReferenceList referenceListStates = new ReferenceList( );
        Action action = _actionService.findByPrimaryKey( nIdAction );

        StateFilter stateFilter = new StateFilter( );
        stateFilter.setIdWorkflow( action.getWorkflow( ).getId( ) );

        List<State> listStates = _stateService.getListStateByFilter( stateFilter );

        referenceListStates.addItem( ID_NULL, StringUtils.EMPTY );
        referenceListStates.addAll( ReferenceList.convert( listStates, ID, NAME, true ) );

        return referenceListStates;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void changeState( int nIdStartState, int nIdState, int nIdResourceHistory )
    {
        ResourceHistory resourceHistory = _resourceHistoryService.findByPrimaryKey( nIdResourceHistory );
        
        int nIdResource = resourceHistory.getIdResource( );
        String strResourceType = resourceHistory.getResourceType( );
        int nIdWorkflow = resourceHistory.getWorkflow( ).getId( );
        ResourceWorkflow resourceWorkflow = _resourceWorkflowService.findByPrimaryKey( nIdResource, strResourceType, nIdWorkflow );
        
        //First check if the ressource is on state = start state
        if ( resourceWorkflow.getState( ).getId( ) != nIdStartState )
        {
            return;
        }

        State state = _stateService.findByPrimaryKey( nIdState );
        // Update Resource
        
        int nExternalParentId = resourceWorkflow.getExternalParentId( );
        resourceWorkflow.setState( state );
        _resourceWorkflowService.update( resourceWorkflow );

        WorkflowService.getInstance( ).doProcessAutomaticReflexiveActions( nIdResource, strResourceType, nIdWorkflow, nExternalParentId, Locale.getDefault( ) );
        // if new state have action automatic
        WorkflowService.getInstance( ).executeActionAutomatic( nIdResource, strResourceType, nIdWorkflow, nExternalParentId );
    }

}
