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

import javax.inject.Inject;

import fr.paris.lutece.plugins.workflow.modules.tipi.business.ITipiDAO;
import fr.paris.lutece.plugins.workflow.modules.tipi.business.Tipi;

/**
 * This class provides instances management methods (create, find, ...) for Tipi objects
 */

public final class TipiService
{

    // BEAN
    public static final String BEAN_NAME = "workflow-tipi.tipiService";

    @Inject
    private ITipiDAO _dao;

    /**
     * Create an instance of the tipi class
     * 
     * @param tipi
     *            The instance of the Tipi which contains the informations to store
     * @return The instance of tipi which has been created with its primary key.
     */

    public Tipi create( Tipi tipi )
    {
        _dao.insert( tipi );

        return tipi;
    }

    /**
     * Update of the tipi data specified in parameter
     * 
     * @param tipi
     *            The instance of the Tipi which contains the data to store
     * @return The instance of the tipi which has been updated
     */

    public Tipi update( Tipi tipi )
    {
        _dao.store( tipi );

        return tipi;
    }

    /**
     * Remove the tipi whose identifier is specified in parameter
     * 
     * @param nTipiId
     *            The tipi Id
     */

    public void remove( int nTipiId )
    {
        _dao.delete( nTipiId );
    }

    // /////////////////////////////////////////////////////////////////////////
    // Finders

    /**
     * Returns an instance of a tipi whose identifier is specified in parameter
     * 
     * @param nKey
     *            The tipi primary key
     * @return an instance of Tipi
     */

    public Tipi findByPrimaryKey( String refDet )
    {
        return _dao.load( refDet );
    }

}
