/*
 * Copyright (c) 2002-2015, Mairie de Paris
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
package fr.paris.lutece.plugins.notifygru.modules.ticketing;

import fr.paris.lutece.plugins.notifygru.modules.ticketing.services.IDemandTypeService;
import fr.paris.lutece.plugins.ticketing.business.Channel;
import fr.paris.lutece.plugins.ticketing.business.ChannelHome;
import fr.paris.lutece.plugins.ticketing.business.Ticket;
import fr.paris.lutece.plugins.ticketing.business.TicketHome;
import fr.paris.lutece.plugins.workflow.modules.notifygru.service.AbstractServiceProvider;
import fr.paris.lutece.plugins.workflowcore.business.resource.ResourceHistory;
import fr.paris.lutece.plugins.workflowcore.service.resource.IResourceHistoryService;
import fr.paris.lutece.plugins.workflowcore.service.task.ITask;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.html.HtmlTemplate;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;


/**
 * The Class NotifyGruTicketing.
 */
public class NotifyGruTicketing extends AbstractServiceProvider
{
    /** The Constant TEMPLATE_FREEMARKER_LIST. */
    private static final String TEMPLATE_FREEMARKER_LIST = "admin/plugins/workflow/modules/notifygru/ticketing/freemarker_list.html";
    private static final String BEAN_SERVICE_DEMAND_TYPE = "notifygru-ticketing.DefaultDemandTypeService";
    private static IDemandTypeService _beanDemandTypeService;

    /** The _resource history service. */
    @Inject
    private IResourceHistoryService _resourceHistoryService;

    /* (non-Javadoc)
     * @see fr.paris.lutece.plugins.workflow.modules.notifygru.service.IProvider#getUserEmail(int)
     */
    @Override
    public String getUserEmail( int nIdResourceHistory )
    {
        ResourceHistory resourceHistory = _resourceHistoryService.findByPrimaryKey( nIdResourceHistory );
        int nIdTicket = resourceHistory.getIdResource(  );
        Ticket ticket = TicketHome.findByPrimaryKey( nIdTicket );

        return ticket.getEmail(  );
    }

    /* (non-Javadoc)
     * @see fr.paris.lutece.plugins.workflow.modules.notifygru.service.IProvider#getUserGuid(int)
     */
    @Override
    public String getUserGuid( int nIdResourceHistory )
    {
        ResourceHistory resourceHistory = _resourceHistoryService.findByPrimaryKey( nIdResourceHistory );
        int nIdTicket = resourceHistory.getIdResource(  );
        Ticket ticket = TicketHome.findByPrimaryKey( nIdTicket );

        return ticket.getGuid(  );
    }

    /* (non-Javadoc)
     * @see fr.paris.lutece.plugins.workflow.modules.notifygru.service.IProvider#getInfosHelp(java.util.Locale)
     */
    @Override
    public String getInfosHelp( Locale local )
    {
        Ticket ticket = new Ticket(  );

        Map<String, Object> model = buildModelNotifyGruTicketing( ticket );
        @SuppressWarnings( "deprecation" )
        HtmlTemplate t = AppTemplateService.getTemplateFromStringFtl( AppTemplateService.getTemplate( 
                    TEMPLATE_FREEMARKER_LIST, local, model ).getHtml(  ), local, model );
        String strResourceInfo = t.getHtml(  );

        return strResourceInfo;
    }

    /**
     * Builds the model notify gru ticketing.
     *
     * @param ticket the ticket
     * @return the map
     */
    private Map<String, Object> buildModelNotifyGruTicketing( Ticket ticket )
    {
        Map<String, Object> model = new HashMap<String, Object>(  );
        model.put( Constants.MARK_GUID, ( ticket.getGuid(  ) != null ) ? ticket.getGuid(  ) : "" );
        model.put( Constants.MARK_FIRSTNAME, ( ticket.getFirstname(  ) != null ) ? ticket.getFirstname(  ) : "" );
        model.put( Constants.MARK_LASTNAME, ( ticket.getLastname(  ) != null ) ? ticket.getLastname(  ) : "" );
        model.put( Constants.MARK_FIXED_PHONE,
            ( ticket.getFixedPhoneNumber(  ) != null ) ? ticket.getFixedPhoneNumber(  ) : "" );
        model.put( Constants.MARK_MOBILE_PHONE,
            ( ticket.getMobilePhoneNumber(  ) != null ) ? ticket.getMobilePhoneNumber(  ) : "" );
        model.put( Constants.MARK_EMAIL, ( ticket.getEmail(  ) != null ) ? ticket.getEmail(  ) : "" );
        model.put( Constants.MARK_UNIT_NAME,
            ( ( ticket.getAssigneeUnit(  ) != null ) && ( ticket.getAssigneeUnit(  ).getName(  ) != null ) )
            ? ticket.getAssigneeUnit(  ).getName(  ) : "" );
        model.put( Constants.MARK_REFERENCE, ( ticket.getReference(  ) != null ) ? ticket.getReference(  ) : "" );
        model.put( Constants.MARK_TICKET, ticket );
        model.put( Constants.MARK_USER_TITLES, ( ticket.getUserTitle(  ) != null ) ? ticket.getUserTitle(  ) : "" );
        model.put( Constants.MARK_TICKET_TYPES, ( ticket.getTicketType(  ) != null ) ? ticket.getTicketType(  ) : "" );
        model.put( Constants.MARK_TICKET_DOMAINS,
            ( ticket.getTicketDomain(  ) != null ) ? ticket.getTicketDomain(  ) : "" );
        model.put( Constants.MARK_TICKET_CATEGORIES,
            ( ticket.getTicketCategory(  ) != null ) ? ticket.getTicketCategory(  ) : "" );
        model.put( Constants.MARK_CONTACT_MODES, ( ticket.getContactMode(  ) != null ) ? ticket.getContactMode(  ) : "" );
        model.put( Constants.MARK_COMMENT, ( ticket.getTicketComment(  ) != null ) ? ticket.getTicketComment(  ) : "" );

        int nidChannel = ( ticket.getIdChannel(  ) >= 0 ) ? ticket.getIdChannel(  ) : 0;
        Channel channel = ChannelHome.findByPrimaryKey( nidChannel );
        model.put( Constants.MARK_CHANNEL,
            ( ( channel != null ) && ( channel.getLabel(  ) != null ) ) ? channel.getLabel(  ) : "" );

        String strUrlCompleted = ( ticket.getUrl(  ) != null ) ? ticket.getUrl(  ) : "";

        model.put( Constants.MARK_URL_COMPLETED, strUrlCompleted.replaceAll( "&", "&amp;" ) );
        model.put( Constants.MARK_USER_MESSAGE, ( ticket.getUserMessage(  ) != null ) ? ticket.getUserMessage(  ) : "" );

        return model;
    }

    /* (non-Javadoc)
     * @see fr.paris.lutece.plugins.workflow.modules.notifygru.service.IProvider#getInfos(int)
     */
    @Override
    public Map<String, Object> getInfos( int nIdResourceHistory )
    {
        Map<String, Object> model = new HashMap<String, Object>(  );

        if ( nIdResourceHistory > 0 )
        {
            ResourceHistory resourceHistory = _resourceHistoryService.findByPrimaryKey( nIdResourceHistory );

            int nIdTicket = resourceHistory.getIdResource(  );
            Ticket ticket = ( TicketHome.findByPrimaryKey( nIdTicket ) != null )
                ? TicketHome.findByPrimaryKey( nIdTicket ) : ( new Ticket(  ) );
            model = buildModelNotifyGruTicketing( ticket );
        }
        else
        {
            model = buildModelNotifyGruTicketing( new Ticket(  ) );
        }

        return model;
    }
    /* (non-Javadoc)
     * @see fr.paris.lutece.plugins.workflow.modules.notifygru.service.IProvider#getOptionalMobilePhoneNumber(int)
     */
    @Override
    public String getOptionalMobilePhoneNumber( int nIdResourceHistory )
    {
        ResourceHistory resourceHistory = _resourceHistoryService.findByPrimaryKey( nIdResourceHistory );
        int nIdTicket = resourceHistory.getIdResource(  );
        Ticket ticket = TicketHome.findByPrimaryKey( nIdTicket );
        

        return ticket.getMobilePhoneNumber(  );
    }

    /* (non-Javadoc)
     * @see fr.paris.lutece.plugins.workflow.modules.notifygru.service.IProvider#getOptionalDemandId(int)
     */
    @Override
    public int getOptionalDemandId( int nIdResourceHistory )
    {
        ResourceHistory resourceHistory = _resourceHistoryService.findByPrimaryKey( nIdResourceHistory );
        int nIdTicket = resourceHistory.getIdResource(  );
        Ticket ticket = TicketHome.findByPrimaryKey( nIdTicket );

        return ticket.getId(  );
    }

    @Override
    public String getDemandReference( int nIdResourceHistory )
    {
        ResourceHistory resourceHistory = _resourceHistoryService.findByPrimaryKey( nIdResourceHistory );
        int nIdTicket = resourceHistory.getIdResource(  );
        Ticket ticket = TicketHome.findByPrimaryKey( nIdTicket );

        AppLogService.debug( "\n\n\n\n TICKET TO STRING \n\n\n" + ticket.getEmail(  ) );

        return ticket.getReference(  );
    }

    @Override
    public String getCustomerId( int nIdResourceHistory )
    {
        ResourceHistory resourceHistory = _resourceHistoryService.findByPrimaryKey( nIdResourceHistory );
        int nIdTicket = resourceHistory.getIdResource(  );
        Ticket ticket = TicketHome.findByPrimaryKey( nIdTicket );

        return ticket.getCustomerId(  );
    }

    /* (non-Javadoc)
     * @see fr.paris.lutece.plugins.workflow.modules.notifygru.service.IProvider#getOptionalDemandIdType(int)
     */
    @Override
    public int getOptionalDemandIdType( int nIdResourceHistory )
    {
        ResourceHistory resourceHistory = _resourceHistoryService.findByPrimaryKey( nIdResourceHistory );
        int nIdTicket = resourceHistory.getIdResource(  );
        Ticket ticket = TicketHome.findByPrimaryKey( nIdTicket );

        return getDemandType( ticket );
    }

    /**
     * Return a demand type ID corresponding to a ticket type
     * @param ticket The ticket
     * @return The demand type id
     */
    private int getDemandType( Ticket ticket )
    {
        if ( _beanDemandTypeService == null )
        {
            _beanDemandTypeService = SpringContextService.getBean( BEAN_SERVICE_DEMAND_TYPE );
        }

        int nDemandType = _beanDemandTypeService.getDemandType( ticket );
        AppLogService.info( "DemandTypeId : " + nDemandType );

        return nDemandType;
    }

    @Override
    public void updateListProvider( ITask task )
    {
        throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ReferenceList buildReferenteListProvider(  )
    {
        throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean isKeyProvider( String strKey )
    {
        throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public AbstractServiceProvider getInstanceProvider( String strKey )
    {
        throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
    }
}
