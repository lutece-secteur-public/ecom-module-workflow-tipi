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
package fr.paris.lutece.plugins.workflow.modules.tipi.service.task;

import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import fr.paris.lutece.plugins.workflow.modules.tipi.business.TipiRefDetHistory;
import fr.paris.lutece.plugins.workflow.modules.tipi.business.task.TaskTipiConfigDAO;
import fr.paris.lutece.plugins.workflow.modules.tipi.service.ITipiRefDetHistoryService;
import fr.paris.lutece.plugins.workflowcore.service.task.SimpleTask;
import fr.paris.lutece.portal.service.i18n.I18nService;

/**
 * 
 * Service for processing the tipi task and delete the config of the tipi task
 *
 */
public class TipiTask extends SimpleTask
{
    // Message
    private static final String MESSAGE_TASK_TITLE = "module.workflow.tipi.task_title";

    private final TaskTipiConfigDAO _taskTipiConfigDAO;
    private final ITipiRefDetHistoryService _tipiRefDetHistoryService;

    /**
     * Constructor
     * 
     * @param taskTipiConfigDAO
     *            the TIPI config DAO
     * @param tipiRefDetHistoryService
     *            the TIPI RefDet history service
     */
    @Inject
    public TipiTask( TaskTipiConfigDAO taskTipiConfigDAO, ITipiRefDetHistoryService tipiRefDetHistoryService )
    {
        super( );

        _taskTipiConfigDAO = taskTipiConfigDAO;
        _tipiRefDetHistoryService = tipiRefDetHistoryService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTitle( Locale local )
    {
        return I18nService.getLocalizedString( MESSAGE_TASK_TITLE, local );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void processTask( int nIdHistory, HttpServletRequest request, Locale local )
    {
        TipiRefDetHistory tipiRefDetHistory = _tipiRefDetHistoryService.findByPrimaryKey( nIdHistory );
        tipiRefDetHistory.setIdTask( getId( ) );
        _tipiRefDetHistoryService.update( tipiRefDetHistory );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doRemoveConfig( )
    {
        _taskTipiConfigDAO.delete( getId( ) );
    }

}
