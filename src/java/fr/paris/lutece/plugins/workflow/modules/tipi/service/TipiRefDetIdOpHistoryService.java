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

import javax.inject.Inject;

import fr.paris.lutece.plugins.workflow.modules.tipi.business.ITipiRefDetIdOpHistoryDAO;
import fr.paris.lutece.plugins.workflow.modules.tipi.business.TipiRefDetIdOpHistory;

/**
 * This class provides instances management methods (create, find, ...) for TipiRefDetHistory objects
 */

public final class TipiRefDetIdOpHistoryService implements ITipiRefDetIdOpHistoryService
{
    @Inject
    private ITipiRefDetIdOpHistoryDAO _dao;

    @Override
    public void create( TipiRefDetIdOpHistory tipiRefDetHistory )
    {
        _dao.insert( tipiRefDetHistory );

        
    }

    @Override
    public void removeByIdop( String strIdOp )
    {
        _dao.deleteByIdop( strIdOp );
        
    }

    @Override
    public List<TipiRefDetIdOpHistory> getByRefDet( String strRefDet )
    {
        // TODO Auto-generated method stub
        return _dao.getByRefDet( strRefDet );
    }

    

}
