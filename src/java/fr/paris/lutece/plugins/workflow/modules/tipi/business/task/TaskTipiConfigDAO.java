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
package fr.paris.lutece.plugins.workflow.modules.tipi.business.task;

import fr.paris.lutece.plugins.workflow.modules.tipi.service.TipiPlugin;
import fr.paris.lutece.plugins.workflowcore.business.config.ITaskConfigDAO;
import fr.paris.lutece.util.sql.DAOUtil;

/**
 * 
 * CRUD SQL queries for TaskTipiConfig Object
 * 
 */

public class TaskTipiConfigDAO implements ITaskConfigDAO<TaskTipiConfig>
{

    // SQL QUERY
    private static final String SQL_QUERY_INSERT = " INSERT INTO workflow_task_tipi_cf ( id_task,  id_state_after_success_payment, "
            + " id_state_after_canceled_payment, id_state_after_failure_payment, id_state_for_processing_state_modif )  VALUES ( ?,?,?,?, ? ) ";

    private static final String SQL_QUERY_FIND_BY_PRIMARY_KEY = " SELECT id_task, id_state_after_success_payment, "
            + " id_state_after_canceled_payment, id_state_after_failure_payment, id_state_for_processing_state_modif FROM workflow_task_tipi_cf  WHERE id_task = ? ";

    private static final String SQL_QUERY_UPDATE = "UPDATE workflow_task_tipi_cf SET id_State_After_Success_Payment = ?, "
            + " id_state_after_canceled_payment = ?,  id_state_after_failure_payment = ?, id_state_for_processing_state_modif = ?  WHERE id_task = ? ";

    private static final String SQL_QUERY_DELETE = " DELETE FROM workflow_task_tipi_cf WHERE id_task = ? ";

    /**
     * {@inheritDoc}
     */
    @Override
    public void insert( TaskTipiConfig config )
    {

        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, TipiPlugin.getPlugin( ) );

        int nIndex = 1;

        daoUtil.setInt( nIndex++, config.getIdTask( ) );
        daoUtil.setInt( nIndex++, config.getIdStateAfterSuccessPayment( ) );
        daoUtil.setInt( nIndex++, config.getIdStateAfterCanceledPayment( ) );
        daoUtil.setInt( nIndex++, config.getIdStateAfterFailurePayment( ) );
        daoUtil.setInt( nIndex++, config.getIdStateForProcessingStateModif( ) );

        daoUtil.executeUpdate( );
        daoUtil.close( );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TaskTipiConfig load( int nIdTask )
    {

        TaskTipiConfig config = null;
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_FIND_BY_PRIMARY_KEY, TipiPlugin.getPlugin( ) );

        daoUtil.setInt( 1, nIdTask );

        daoUtil.executeQuery( );
        int nIndex = 1;

        if ( daoUtil.next( ) )
        {
            config = new TaskTipiConfig( );
            config.setIdTask( daoUtil.getInt( nIndex++ ) );
            config.setIdStateAfterSuccessPayment( daoUtil.getInt( nIndex++ ) );
            config.setIdStateAfterCanceledPayment( daoUtil.getInt( nIndex++ ) );
            config.setIdStateAfterFailurePayment( daoUtil.getInt( nIndex++ ) );
            config.setIdStateForProcessingStateModif( daoUtil.getInt( nIndex++ ) );

        }

        daoUtil.close( );

        return config;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void store( TaskTipiConfig config )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, TipiPlugin.getPlugin( ) );

        int nIndex = 1;

        daoUtil.setInt( nIndex++, config.getIdStateAfterSuccessPayment( ) );
        daoUtil.setInt( nIndex++, config.getIdStateAfterCanceledPayment( ) );
        daoUtil.setInt( nIndex++, config.getIdStateAfterFailurePayment( ) );
        daoUtil.setInt( nIndex++, config.getIdStateForProcessingStateModif( ) );

        daoUtil.setInt( nIndex++, config.getIdTask( ) );

        daoUtil.executeUpdate( );
        daoUtil.close( );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete( int nIdTask )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, TipiPlugin.getPlugin( ) );

        daoUtil.setInt( 1, nIdTask );
        daoUtil.executeUpdate( );
        daoUtil.close( );

    }

}
