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
package fr.paris.lutece.plugins.workflow.modules.tipi.service.provider.impl;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import fr.paris.lutece.plugins.workflow.modules.tipi.business.Tipi;
import fr.paris.lutece.plugins.workflow.modules.tipi.service.url.SpyTipiUrlService;
import fr.paris.lutece.plugins.workflowcore.service.provider.InfoMarker;
import junit.framework.TestCase;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class TipiMarkerProviderTest extends TestCase
{
    private static final String MARK_TIPI_URL = "tipi_url";

    private SpecificSpyTipiUrlService _tipiUrlService;
    private TipiMarkerProvider _markerProvider;

    public void setUp( ) throws Exception
    {
        super.setUp( );

        _tipiUrlService = new SpecificSpyTipiUrlService( );
        _markerProvider = new TipiMarkerProvider( _tipiUrlService );
    }

    public void testMarkerDescriptions( )
    {
        Collection<InfoMarker> collectionMarkers = _markerProvider.provideMarkerDescriptions( );

        assertThat( collectionMarkers.size( ), is( 1 ) );
        InfoMarker tipiMarker = extractTipiMarker( collectionMarkers );
        assertThat( tipiMarker.getMarker( ), is( MARK_TIPI_URL ) );
        assertThat( tipiMarker.getDescription( ), is( not( nullValue( ) ) ) );
    }

    private InfoMarker extractTipiMarker( Collection<InfoMarker> collectionMarkers )
    {
        Iterator<InfoMarker> iterator = collectionMarkers.iterator( );

        return iterator.next( );
    }

    public void testMarkerValues( )
    {
        Collection<InfoMarker> collectionMarkers = _markerProvider.provideMarkerDescriptions( );

        assertThat( collectionMarkers.size( ), is( 1 ) );
        InfoMarker tipiMarker = extractTipiMarker( collectionMarkers );
        assertThat( tipiMarker.getMarker( ), is( MARK_TIPI_URL ) );
        assertThat( tipiMarker.getValue( ), is( _tipiUrlService._strGeneratedPaymentUrl ) );
    }

    private class SpecificSpyTipiUrlService extends SpyTipiUrlService
    {
        @Override
        public boolean isPaymentUrlAuthenticated( HttpServletRequest request )
        {
            throw new UnsupportedOperationException( );
        }

        @Override
        public String generateTipiUrl( Tipi tipi )
        {
            throw new UnsupportedOperationException( );
        }
    }
}
