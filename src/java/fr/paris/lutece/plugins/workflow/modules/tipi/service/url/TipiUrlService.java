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
package fr.paris.lutece.plugins.workflow.modules.tipi.service.url;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import fr.paris.lutece.plugins.workflow.modules.tipi.business.Tipi;
import fr.paris.lutece.plugins.workflowcore.business.resource.ResourceHistory;
import fr.paris.lutece.plugins.workflowcore.service.task.ITask;
import fr.paris.lutece.portal.service.util.AppPathService;
import fr.paris.lutece.util.signrequest.AbstractPrivateKeyAuthenticator;
import fr.paris.lutece.util.url.UrlItem;

public class TipiUrlService implements ITipiUrlService
{
    private static final String PARAMETER_TIPI_JSP_PAYMENT = "jsp/site/plugins/workflow/modules/tipi/DoProcessPayment.jsp";
    private static final String PARAMETER_ID_HISTORY = "id_history";
    private static final String PARAMETER_ID_TASK = "id_task";
    private static final String PARAMETER_SIGNATURE = "signature";
    private static final String PARAMETER_TIMESTAMP = "timestamp";

    private final AbstractPrivateKeyAuthenticator _requestAuthenticator;

    public TipiUrlService( AbstractPrivateKeyAuthenticator requestAuthenticator )
    {
        _requestAuthenticator = requestAuthenticator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String generatePaymentUrl( ResourceHistory resourceHistory, ITask task, HttpServletRequest request )
    {
        String strUrl = StringUtils.EMPTY;
        List<String> listElements = new ArrayList<String>( );
        int nIdHistory = resourceHistory.getId( );
        int nIdTask = task.getId( );
        listElements.add( Integer.toString( nIdHistory ) );
        listElements.add( Integer.toString( nIdTask ) );

        String strTimestamp = Long.toString( new Date( ).getTime( ) );
        String strSignature = _requestAuthenticator.buildSignature( listElements, strTimestamp );
        StringBuilder sbUrl = new StringBuilder( AppPathService.getBaseUrl( request ) );
        sbUrl.append( PARAMETER_TIPI_JSP_PAYMENT );

        UrlItem url = new UrlItem( sbUrl.toString( ) );
        url.addParameter( PARAMETER_ID_HISTORY, nIdHistory );
        url.addParameter( PARAMETER_ID_TASK, nIdTask );
        url.addParameter( PARAMETER_SIGNATURE, strSignature );
        url.addParameter( PARAMETER_TIMESTAMP, strTimestamp );

        strUrl = url.getUrl( );

        return strUrl;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isPaymentUrlAuthenticated( HttpServletRequest request )
    {
        return _requestAuthenticator.isRequestAuthenticated( request );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String generateTipiUrl( Tipi tipi )
    {
        // TODO
        return null;
    }
}
