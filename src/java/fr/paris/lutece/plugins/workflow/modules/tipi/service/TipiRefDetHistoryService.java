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

import fr.paris.lutece.plugins.workflow.modules.tipi.business.ITipiRefDetHistoryDAO;
import fr.paris.lutece.plugins.workflow.modules.tipi.business.TipiRefDetHistory;

import javax.inject.Inject;

/**
 * This class provides instances management methods (create, find, ...) for TipiRefDetHistory objects
 */

public final class TipiRefDetHistoryService
{

    // BEAN
    public static final String BEAN_NAME = "workflow-tipi.tipiRefDetHistoryService";

    @Inject
    private ITipiRefDetHistoryDAO _dao;

    /**
     * Create an instance of the tipiRefDetHistory class
     * 
     * @param tipiRefDetHistory
     *            The instance of the TipiRefDetHistory which contains the informations to store
     * @return The instance of tipiRefDetHistory which has been created with its primary key.
     */

    public TipiRefDetHistory create( TipiRefDetHistory tipiRefDetHistory )
    {
        _dao.insert( tipiRefDetHistory );

        return tipiRefDetHistory;
    }

    /**
     * Update of the tipiRefDetHistory data specified in parameter
     * 
     * @param tipiRefDetHistory
     *            The instance of the TipiRefDetHistory which contains the data to store
     * @return The instance of the tipiRefDetHistory which has been updated
     */

    public TipiRefDetHistory update( TipiRefDetHistory tipiRefDetHistory )
    {
        _dao.store( tipiRefDetHistory );

        return tipiRefDetHistory;
    }

    /**
     * Remove the tipiRefDetHistory whose identifier is specified in parameter
     * 
     * @param nIdHistory
     *            The idHistory
     */

    public void remove( int nIdHistory )
    {
        _dao.delete( nIdHistory );
    }

    // /////////////////////////////////////////////////////////////////////////
    // Finders

    /**
     * Returns an instance of a tipiRefDetHistory whose identifier is specified in parameter
     * 
     * @param nIdHistory
     *            The tipiRefDetHistory primary key
     * @return an instance of TipiRefDetHistory
     */

    public TipiRefDetHistory findByPrimaryKey( int nIdHistory )
    {
        return _dao.load( nIdHistory );
    }

}
