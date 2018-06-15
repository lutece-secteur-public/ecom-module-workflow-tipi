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

import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import fr.paris.lutece.plugins.workflow.modules.tipi.business.Tipi;
import fr.paris.lutece.plugins.workflow.modules.tipi.service.url.ITipiUrlService;
import fr.paris.lutece.plugins.workflowcore.business.resource.ResourceHistory;
import fr.paris.lutece.plugins.workflowcore.service.task.ITask;

public class SpyTipiUrlService implements ITipiUrlService
{
    private static final String URL_PAYMENT = "FakeTipiUrlService_payment";
    private static final String URL_TIPI = "FakeTipiUrlService_tipi";
    private static final Random _random = new Random( );

    public String _strGeneratedPaymentUrl;
    public boolean _bIsPaymentUrlAuthenticated = true;
    public String _strGeneratedTipiUrl;

    @Override
    public String generatePaymentUrl( ResourceHistory resourceHistory, ITask task, HttpServletRequest request )
    {
        _strGeneratedPaymentUrl = URL_PAYMENT + _random.nextInt( 50 );

        return _strGeneratedPaymentUrl;
    }

    @Override
    public boolean isPaymentUrlAuthenticated( HttpServletRequest request )
    {
        return _bIsPaymentUrlAuthenticated;
    }

    @Override
    public String generateTipiUrl( Tipi tipi )
    {
        _strGeneratedTipiUrl = URL_TIPI + _random.nextInt( 50 );

        return _strGeneratedTipiUrl;
    }
}
