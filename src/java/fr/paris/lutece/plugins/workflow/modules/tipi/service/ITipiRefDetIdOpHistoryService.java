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

import fr.paris.lutece.plugins.workflow.modules.tipi.business.TipiRefDetIdOpHistory;

/**
 * 
 * Interface for managment of TipiRefDetHistory object
 *
 */
public interface ITipiRefDetIdOpHistoryService
{

    // BEAN
    String BEAN_NAME = "workflow-tipi.tipiRefDetIdOpHistoryService";

    /**
     * Insert a new record in the table.
     * 
     * @param tipiRefDetHistory
     *            instance of the TipiRefDetHistory object to inssert
     */

    void create( TipiRefDetIdOpHistory tipiRefDetHistory );

    /**
   
    /**
     * Delete a record from the table
     * 
     * @param nIdHistory
     *            int identifier of the TipiRefDetHistory to delete
     */

    void  removeByIdop( String strIdOp );

    // /////////////////////////////////////////////////////////////////////////
    // Finders

    

    /**
     * Load the data from the table find by RefDet
     * 
     * 
     * @param strRefDet
     *            The refDet of the tipiRefDetHistory
     * @return The instance of the tipiRefDetHistory
     */
    List<TipiRefDetIdOpHistory> getByRefDet( String strRefDet );
}
