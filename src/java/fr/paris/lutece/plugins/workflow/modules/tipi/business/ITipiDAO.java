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

import java.util.List;

/**
 * ITipiDAO Interface
 */

public interface ITipiDAO
{

    /**
     * Insert a new record in the table.
     * 
     * @param tipi
     *            instance of the Tipi object to inssert
     */

    void insert( Tipi tipi );

    /**
     * Update the record in the table
     * 
     * @param tipi
     *            the reference of the Tipi
     */

    void store( Tipi tipi );

    /**
     * Delete a record from the table
     * 
     * @param strRefDet
     *            string identifier of the Tipi to delete
     */

    void delete( String strRefDet );

    // /////////////////////////////////////////////////////////////////////////
    // Finders

    /**
     * Load the data from the table
     * 
     * @param strRefDet
     *            The identifier of the tipi
     * @return The instance of the tipi
     */

    Tipi load( String strRefDet );

    /**
     * Load the data from the table by Idop
     * 
     * 
     * @param strIdop
     *            The Idop of the tipi
     * @return The instance of the tipi
     */
    Tipi loadByIdop( String strIdop );

    /**
     * Loads all the TIPI objects for which an idop exists but without transaction result
     * 
     * @return The list of TIPI objects
     */
    List<Tipi> selectAllWithoutTransactionResult( );

}
