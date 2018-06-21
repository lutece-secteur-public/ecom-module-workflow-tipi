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

package fr.paris.lutece.plugins.workflow.modules.tipi.business;

import fr.paris.lutece.util.sql.DAOUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * This class provides Data Access methods for TipiRefDetHistory objects
 */

public final class TipiRefDetHistoryDAO implements ITipiRefDetHistoryDAO
{

    // Constants
    private static final String SQL_QUERY_SELECT = "SELECT id_history, id_task, ref_det FROM workflow_task_tipi_refdet_history WHERE id_history = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO workflow_task_tipi_refdet_history ( id_history, id_task, ref_det ) VALUES ( ?, ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM workflow_task_tipi_refdet_history WHERE id_history = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE workflow_task_tipi_refdet_history SET id_history = ?, id_task = ?, ref_det = ? WHERE id_history = ?";

    /**
     * {@inheritDoc }
     */
    @Override
    public void insert( TipiRefDetHistory tipiRefDetHistory )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT );

        int nIndex = 0;
        daoUtil.setInt( ++nIndex, tipiRefDetHistory.getIdHistory( ) );
        daoUtil.setInt( ++nIndex, tipiRefDetHistory.getIdTask( ) );
        daoUtil.setString( ++nIndex, tipiRefDetHistory.getRefDet( ) );

        daoUtil.executeUpdate( );
        daoUtil.close( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public TipiRefDetHistory load( int nIdHistory )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT );
        daoUtil.setInt( 1, nIdHistory );
        daoUtil.executeQuery( );

        TipiRefDetHistory tipiRefDetHistory = null;

        if ( daoUtil.next( ) )
        {
            int nIndex = 0;
            tipiRefDetHistory = new TipiRefDetHistory( );
            tipiRefDetHistory.setIdHistory( daoUtil.getInt( ++nIndex ) );
            tipiRefDetHistory.setIdTask( daoUtil.getInt( ++nIndex ) );
            tipiRefDetHistory.setRefDet( daoUtil.getString( ++nIndex ) );
        }

        daoUtil.close( );

        return tipiRefDetHistory;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void delete( int nIdHistory )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE );
        daoUtil.setInt( 1, nIdHistory );
        daoUtil.executeUpdate( );
        daoUtil.close( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void store( TipiRefDetHistory tipiRefDetHistory )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE );
        int nIndex = 0;
        daoUtil.setInt( ++nIndex, tipiRefDetHistory.getIdHistory( ) );
        daoUtil.setInt( ++nIndex, tipiRefDetHistory.getIdTask( ) );
        daoUtil.setString( ++nIndex, tipiRefDetHistory.getRefDet( ) );

        daoUtil.executeUpdate( );
        daoUtil.close( );
    }

}
