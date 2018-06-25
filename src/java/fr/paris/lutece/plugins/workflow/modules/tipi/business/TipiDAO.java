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

/**
 * This class provides Data Access methods for Tipi objects
 */

public final class TipiDAO implements ITipiDAO
{

    // Constants
    private static final String SQL_QUERY_SELECT = "SELECT ref_det, amount, email, id_op, result_transaction FROM workflow_tipi_tipi WHERE ref_det = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO workflow_tipi_tipi ( ref_det, amount, email, id_op, result_transaction ) VALUES ( ?, ?, ?, ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM workflow_tipi_tipi WHERE ref_det = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE workflow_tipi_tipi SET ref_det = ?, amount = ?, email = ?, id_op = ?, result_transaction = ? WHERE ref_det = ?";

    /**
     * {@inheritDoc }
     */
    @Override
    public void insert( Tipi tipi )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT );

        daoUtil.setString( 1, tipi.getRefDet( ) );
        daoUtil.setDouble( 2, tipi.getAmount( ) );
        daoUtil.setString( 3, tipi.getEmail( ) );
        daoUtil.setString( 4, tipi.getIdOp( ) );
        daoUtil.setString( 5, tipi.getResultTransaction( ) );

        daoUtil.executeUpdate( );
        daoUtil.close( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Tipi load( String strRefDet )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT );
        daoUtil.setString( 1, strRefDet );
        daoUtil.executeQuery( );

        Tipi tipi = null;

        if ( daoUtil.next( ) )
        {
            tipi = new Tipi( );
            int nIndex = 0;
            tipi.setRefDet( daoUtil.getString( ++nIndex ) );
            tipi.setAmount( daoUtil.getDouble( ++nIndex ) );
            tipi.setEmail( daoUtil.getString( ++nIndex ) );
            tipi.setIdOp( daoUtil.getString( ++nIndex ) );
            tipi.setResultTransaction( daoUtil.getString( ++nIndex ) );
        }

        daoUtil.close( );

        return tipi;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void delete( String refDet )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE );
        daoUtil.setString( 1, refDet );
        daoUtil.executeUpdate( );
        daoUtil.close( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void store( Tipi tipi )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE );

        int nIndex = 0;
        daoUtil.setString( ++nIndex, tipi.getRefDet( ) );
        daoUtil.setDouble( ++nIndex, tipi.getAmount( ) );
        daoUtil.setString( ++nIndex, tipi.getEmail( ) );
        daoUtil.setString( ++nIndex, tipi.getIdOp( ) );
        daoUtil.setString( ++nIndex, tipi.getResultTransaction( ) );
        daoUtil.setString( ++nIndex, tipi.getRefDet( ) );

        daoUtil.executeUpdate( );
        daoUtil.close( );
    }

}
