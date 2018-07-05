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

import fr.paris.lutece.plugins.workflow.modules.tipi.business.Tipi;
import fr.paris.lutece.plugins.workflow.modules.tipi.business.TipiRefDetHistory;
import fr.paris.lutece.plugins.workflow.modules.tipi.service.ITipiRefDetHistoryService;
import fr.paris.lutece.plugins.workflow.modules.tipi.service.ITipiService;
import fr.paris.lutece.plugins.workflowcore.business.resource.ResourceHistory;
import fr.paris.lutece.plugins.workflowcore.service.resource.IResourceHistoryService;
import fr.paris.lutece.plugins.workflowcore.service.task.SimpleTask;

/**
 * This class is an abstract {@code Task} which provides pieces of information for the TIPI transaction
 *
 */
public abstract class AbstractTipiProviderTask extends SimpleTask
{
    private final IResourceHistoryService _resourceHistoryService;
    private final ITipiService _tipiService;
    private final ITipiRefDetHistoryService _tipiRefDetHistoryService;

    /**
     * Constructor
     * 
     * @param resourceHistoryService
     *            the resource history service
     * @param tipiService
     *            the TIPI service
     * @param tipiRefDetHistoryService
     *            the TIPI RefDet history service
     */
    @Inject
    public AbstractTipiProviderTask( IResourceHistoryService resourceHistoryService, ITipiService tipiService,
            ITipiRefDetHistoryService tipiRefDetHistoryService )
    {
        super( );

        _resourceHistoryService = resourceHistoryService;
        _tipiService = tipiService;
        _tipiRefDetHistoryService = tipiRefDetHistoryService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void processTask( int nIdResourceHistory, HttpServletRequest request, Locale local )
    {
        ResourceHistory resourceHistory = findResourceHistory( nIdResourceHistory );

        if ( resourceHistory != null )
        {
            String strRefDet = provideRefDet( resourceHistory );
            int nAmount = provideAmount( resourceHistory );
            String strEmail = provideEmail( resourceHistory );

            Tipi tipi = createTipi( strRefDet, nAmount, strEmail );
            saveTipi( tipi );
            saveRefDetHistory( nIdResourceHistory, tipi.getRefDet( ) );
        }
    }

    /**
     * Finds the resource history with the specified id
     * 
     * @param nIdResourceHistory
     *            the id of the resource history to find
     * @return the resource history
     */
    private ResourceHistory findResourceHistory( int nIdResourceHistory )
    {
        return _resourceHistoryService.findByPrimaryKey( nIdResourceHistory );
    }

    /**
     * Creates a TIPI object
     * 
     * @param strRefDet
     *            the RefDet of the TIPI object
     * @param nAmount
     *            the amount of the TIPI object
     * @param strEmail
     *            the email of the TIPI object
     * @return the TIPI object
     */
    private Tipi createTipi( String strRefDet, int nAmount, String strEmail )
    {
        Tipi tipi = new Tipi( );

        tipi.setRefDet( strRefDet );
        tipi.setAmount( nAmount );
        tipi.setEmail( strEmail );

        return tipi;
    }

    /**
     * Saves the specified TIPI object
     * 
     * @param tipi
     *            the TIPI object to save
     */
    private void saveTipi( Tipi tipi )
    {
        Tipi tipiAlredaySaved = _tipiService.findByPrimaryKey( tipi.getRefDet( ) );

        if ( tipiAlredaySaved == null )
        {
            _tipiService.create( tipi );
        }
    }

    /**
     * Saves the RefDet history
     * 
     * @param nIdResourceHistory
     *            the resource history linked to the RefDet
     * @param strRefDet
     *            the RefDet to save
     */
    private void saveRefDetHistory( int nIdResourceHistory, String strRefDet )
    {
        TipiRefDetHistory tipiRefDetHistory = new TipiRefDetHistory( );
        tipiRefDetHistory.setIdHistory( nIdResourceHistory );
        tipiRefDetHistory.setRefDet( strRefDet );

        _tipiRefDetHistoryService.create( tipiRefDetHistory );
    }

    /**
     * Provides the RefDet of the TIPI transaction from the resource processed by the workflow
     * 
     * @param resourceHistory
     *            the resource history used to retrieve the resource processed by the workflow
     * @return the RefDet
     */
    protected abstract String provideRefDet( ResourceHistory resourceHistory );

    /**
     * Provides the amount of the TIPI transaction from the resource processed by the workflow. The amount is in centimes.
     * 
     * @param resourceHistory
     *            the resource history used to retrieve the resource processed by the workflow
     * @return the amount
     */
    protected abstract int provideAmount( ResourceHistory resourceHistory );

    /**
     * Provides the email used in the TIPI transaction from the resource processed by the workflow
     * 
     * @param resourceHistory
     *            the resource history used to retrieve the resource processed by the workflow
     * @return the email
     */
    protected abstract String provideEmail( ResourceHistory resourceHistory );
}
