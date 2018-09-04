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
package fr.paris.lutece.plugins.workflow.modules.tipi.web.task;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import fr.paris.lutece.plugins.workflow.web.task.AbstractTaskComponent;
import fr.paris.lutece.plugins.workflowcore.business.config.ITaskConfig;
import fr.paris.lutece.plugins.workflowcore.service.config.ITaskConfigService;
import fr.paris.lutece.plugins.workflowcore.service.task.ITask;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.util.html.HtmlTemplate;
import fr.paris.lutece.plugins.workflow.modules.tipi.business.Tipi;
import fr.paris.lutece.plugins.workflow.modules.tipi.business.TipiRefDetHistory;
import fr.paris.lutece.plugins.workflow.modules.tipi.service.ITipiRefDetHistoryService;
import fr.paris.lutece.plugins.workflow.modules.tipi.service.ITipiService;
import fr.paris.lutece.plugins.workflow.modules.tipi.service.ITipiWorkflowStateService;

/**
 * 
 * Controller to expose the task tipi in the tipi task config HTML page
 *
 */
public class TipiTaskComponent extends AbstractTaskComponent
{
    // MARKS
    private static final String MARK_CONFIG = "config";
    private static final String MARK_LIST_STATES = "list_states";
    private static final String MARK_TIPI = "tipi";

    // BEAN
    private static final String BEAN_CONFIG_SERVICE = "workflow-tipi.taskTipiConfigService";

    // TEMPLATE
    private static final String TEMPLATE_TASK_CONFIG = "admin/plugins/workflow/modules/tipi/tipi_task_config.html";
    private static final String TEMPLATE_TASK_INFORMATION = "admin/plugins/workflow/modules/tipi/tipi_task_information.html";

    // SERVICE
    @Inject
    private ITipiWorkflowStateService _tipiWorkFlowStateService;
    @Inject
    private ITipiRefDetHistoryService _tipiRefDetHistoryService;
    @Inject
    private ITipiService _tipiService;
    @Inject
    @Named( BEAN_CONFIG_SERVICE )
    private ITaskConfigService _taskConfigService;

    /**
     * {@inheritDoc}
     */
    @Override
    public String doValidateTask( int arg0, String arg1, HttpServletRequest arg2, Locale arg3, ITask arg4 )
    {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDisplayConfigForm( HttpServletRequest request, Locale locale, ITask task )
    {

        Map<String, Object> model = new HashMap<String, Object>( );
        model.put( MARK_CONFIG, _taskConfigService.findByPrimaryKey( task.getId( ) ) );
        model.put( MARK_LIST_STATES, _tipiWorkFlowStateService.getListStates( task.getAction( ).getId( ) ) );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_TASK_CONFIG, locale, model );

        return template.getHtml( );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDisplayTaskForm( int nIdResource, String strResourceType, HttpServletRequest request, Locale locale, ITask task )
    {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDisplayTaskInformation( int nIdResource, HttpServletRequest request, Locale locale, ITask task )
    {
        String strResult = StringUtils.EMPTY;

        TipiRefDetHistory tipiRefDetHistory = _tipiRefDetHistoryService.findByPrimaryKey( nIdResource );

        if ( tipiRefDetHistory != null )
        {
            Tipi tipi = _tipiService.findByPrimaryKey( tipiRefDetHistory.getRefDet( ) );

            Map<String, Object> model = new HashMap<String, Object>( );
            model.put( MARK_TIPI, tipi );

            HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_TASK_INFORMATION, locale, model );

            strResult = template.getHtml( );
        }

        return strResult;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTaskInformationXml( int arg0, HttpServletRequest arg1, Locale arg2, ITask arg3 )
    {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String validateConfig( ITaskConfig arg0, HttpServletRequest arg1 )
    {
        return null;
    }
}
