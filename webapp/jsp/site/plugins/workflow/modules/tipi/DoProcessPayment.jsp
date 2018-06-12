<%@page import="fr.paris.lutece.portal.service.message.SiteMessageException"%>
<%@page import="fr.paris.lutece.portal.service.util.AppPathService"%>

<jsp:useBean id="tipiPayment" scope="session" class="fr.paris.lutece.plugins.workflow.modules.tipi.web.TipiPaymentJspBean" />

<% 
    try
    {
        response.sendRedirect( tipiPayment.doProcessPayment( request ) );
    }
    catch( SiteMessageException lme )
    {
        response.sendRedirect( AppPathService.getSiteMessageUrl( request ) );
    }
%>
