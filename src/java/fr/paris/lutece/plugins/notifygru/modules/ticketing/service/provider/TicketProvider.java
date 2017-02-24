package fr.paris.lutece.plugins.notifygru.modules.ticketing.service.provider;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang.StringEscapeUtils;

import fr.paris.lutece.plugins.notifygru.modules.ticketing.Constants;
import fr.paris.lutece.plugins.ticketing.business.ticket.Ticket;
import fr.paris.lutece.plugins.ticketing.business.ticket.TicketHome;
import fr.paris.lutece.plugins.ticketing.business.tickettype.TicketTypeHome;
import fr.paris.lutece.plugins.workflow.modules.notifygru.service.provider.IProvider;
import fr.paris.lutece.plugins.workflow.modules.notifygru.service.provider.NotifyGruMarker;
import fr.paris.lutece.plugins.workflowcore.business.resource.ResourceHistory;
import fr.paris.lutece.portal.service.i18n.I18nService;

public class TicketProvider implements IProvider
{
    private static final String MESSAGE_MARKER_TICKET_REFERENCE = "ticketing.manage_tickets.columnReference";
    private static final String MESSAGE_MARKER_TICKET_DOMAIN = "ticketing.create_ticket.labelTicketDomain";
    private static final String MESSAGE_MARKER_TICKET_TYPE = "ticketing.create_ticket.labelTicketType";
    private static final String MESSAGE_MARKER_TICKET_CATEGORY = "ticketing.create_ticket.labelTicketCategory";
    private static final String MESSAGE_MARKER_TICKET_CATEGORY_PRECISION = "ticketing.create_ticketcategory.labelPrecision";
    private static final String MESSAGE_MARKER_TICKET_CHANNEL = "ticketing.create_instantresponse.labelChannel";
    private static final String MESSAGE_MARKER_TICKET_COMMENT = "ticketing.create_ticket.labelTicketComment";
    private static final String MESSAGE_MARKER_USER_TITLE = "ticketing.create_ticket.labelUserTitle";
    private static final String MESSAGE_MARKER_USER_FIRSTNAME = "ticketing.create_ticket.labelFirstname";
    private static final String MESSAGE_MARKER_USER_LASTNAME = "ticketing.create_ticket.labelLastname";
    private static final String MESSAGE_MARKER_USER_UNIT = "ticketing.manage_tickets.columnTicketAssignee";
    private static final String MESSAGE_MARKER_USER_CONTACT_MODE = "ticketing.create_ticket.labelContactMode";
    private static final String MESSAGE_MARKER_USER_FIXED_PHONE_NUMBER = "ticketing.create_ticket.labelFixedPhoneNumber";
    private static final String MESSAGE_MARKER_USER_MOBILE_PHONE_NUMBER = "ticketing.create_ticket.labelMobilePhoneNumber";
    private static final String MESSAGE_MARKER_USER_EMAIL = "ticketing.create_ticket.labelEmail";
    private static final String MESSAGE_MARKER_USER_MESSAGE = "module.notifygru.ticketing.provider.ticket.marker.userMessage";
    private static final String MESSAGE_MARKER_TECHNICAL_URL_COMPLETE = "module.notifygru.ticketing.provider.ticket.marker.urlComplete";

    private Ticket _ticket;

    public TicketProvider( ResourceHistory resourceHistory )
    {
        _ticket = TicketHome.findByPrimaryKey( resourceHistory.getIdResource( ) );
    }

    @Override
    public String provideDemandId( )
    {
        return String.valueOf( _ticket.getId( ) );
    }

    @Override
    public String provideDemandTypeId( )
    {
        return String.valueOf( TicketTypeHome.findByPrimaryKey( _ticket.getIdTicketType( ) ).getDemandTypeId( ) );
    }

    @Override
    public String provideDemandReference( )
    {
        return _ticket.getReference( );
    }

    @Override
    public String provideCustomerConnectionId( )
    {
        return _ticket.getGuid( );
    }

    @Override
    public String provideCustomerId( )
    {
        return _ticket.getCustomerId( );
    }

    @Override
    public String provideCustomerEmail( )
    {
        return _ticket.getEmail( );
    }

    @Override
    public String provideCustomerMobilePhone( )
    {
        return _ticket.getMobilePhoneNumber( );
    }

    @Override
    public Collection<NotifyGruMarker> provideMarkerValues( )
    {
        Collection<NotifyGruMarker> collectionNotifyGruMarkers = new ArrayList<>( );

        collectionNotifyGruMarkers.add( createMarkerValues( Constants.MARK_USER_TITLE, _ticket.getUserTitle( ) ) );
        collectionNotifyGruMarkers.add( createMarkerValues( Constants.MARK_USER_FIRSTNAME, _ticket.getFirstname( ) ) );
        collectionNotifyGruMarkers.add( createMarkerValues( Constants.MARK_USER_LASTNAME, _ticket.getLastname( ) ) );

        if ( _ticket.getAssigneeUnit( ) != null )
        {
            collectionNotifyGruMarkers.add( createMarkerValues( Constants.MARK_USER_UNIT_NAME, _ticket.getAssigneeUnit( ).getName( ) ) );
        }

        collectionNotifyGruMarkers.add( createMarkerValues( Constants.MARK_USER_CONTACT_MODE, _ticket.getContactMode( ) ) );
        collectionNotifyGruMarkers.add( createMarkerValues( Constants.MARK_USER_FIXED_PHONE, _ticket.getFixedPhoneNumber( ) ) );
        collectionNotifyGruMarkers.add( createMarkerValues( Constants.MARK_USER_MOBILE_PHONE, _ticket.getMobilePhoneNumber( ) ) );
        collectionNotifyGruMarkers.add( createMarkerValues( Constants.MARK_USER_EMAIL, _ticket.getEmail( ) ) );
        collectionNotifyGruMarkers.add( createMarkerValues( Constants.MARK_USER_MESSAGE, _ticket.getUserMessage( ) ) );
        collectionNotifyGruMarkers.add( createMarkerValues( Constants.MARK_TICKET_REFERENCE, _ticket.getReference( ) ) );
        collectionNotifyGruMarkers.add( createMarkerValues( Constants.MARK_TICKET_TYPE, _ticket.getTicketType( ) ) );
        collectionNotifyGruMarkers.add( createMarkerValues( Constants.MARK_TICKET_DOMAIN, _ticket.getTicketDomain( ) ) );

        if ( _ticket.getTicketCategory( ) != null )
        {
            collectionNotifyGruMarkers.add( createMarkerValues( Constants.MARK_TICKET_CATEGORY, _ticket.getTicketCategory( ).getLabel( ) ) );
            collectionNotifyGruMarkers.add( createMarkerValues( Constants.MARK_TICKET_CATEGORY_PRECISION, _ticket.getTicketCategory( ).getPrecision( ) ) );
        }

        if ( _ticket.getChannel( ) != null )
        {
            collectionNotifyGruMarkers.add( createMarkerValues( Constants.MARK_TICKET_CHANNEL, _ticket.getChannel( ).getLabel( ) ) );
        }

        collectionNotifyGruMarkers.add( createMarkerValues( Constants.MARK_TICKET_COMMENT, _ticket.getTicketComment( ) ) );

        if ( _ticket.getUrl( ) != null )
        {
            collectionNotifyGruMarkers.add( createMarkerValues( Constants.MARK_TECHNICAL_URL_COMPLETED, StringEscapeUtils.escapeHtml( _ticket.getUrl( ) ) ) );
        }

        return collectionNotifyGruMarkers;
    }

    public static Collection<NotifyGruMarker> getProviderMarkerDescriptions( )
    {
        Collection<NotifyGruMarker> collectionNotifyGruMarkers = new ArrayList<>( );

        collectionNotifyGruMarkers.add( createMarkerDescriptions( Constants.MARK_USER_TITLE, MESSAGE_MARKER_USER_TITLE ) );
        collectionNotifyGruMarkers.add( createMarkerDescriptions( Constants.MARK_USER_FIRSTNAME, MESSAGE_MARKER_USER_FIRSTNAME ) );
        collectionNotifyGruMarkers.add( createMarkerDescriptions( Constants.MARK_USER_LASTNAME, MESSAGE_MARKER_USER_LASTNAME ) );
        collectionNotifyGruMarkers.add( createMarkerDescriptions( Constants.MARK_USER_UNIT_NAME, MESSAGE_MARKER_USER_UNIT ) );
        collectionNotifyGruMarkers.add( createMarkerDescriptions( Constants.MARK_USER_CONTACT_MODE, MESSAGE_MARKER_USER_CONTACT_MODE ) );
        collectionNotifyGruMarkers.add( createMarkerDescriptions( Constants.MARK_USER_FIXED_PHONE, MESSAGE_MARKER_USER_FIXED_PHONE_NUMBER ) );
        collectionNotifyGruMarkers.add( createMarkerDescriptions( Constants.MARK_USER_MOBILE_PHONE, MESSAGE_MARKER_USER_MOBILE_PHONE_NUMBER ) );
        collectionNotifyGruMarkers.add( createMarkerDescriptions( Constants.MARK_USER_EMAIL, MESSAGE_MARKER_USER_EMAIL ) );
        collectionNotifyGruMarkers.add( createMarkerDescriptions( Constants.MARK_USER_MESSAGE, MESSAGE_MARKER_USER_MESSAGE ) );
        collectionNotifyGruMarkers.add( createMarkerDescriptions( Constants.MARK_TICKET_REFERENCE, MESSAGE_MARKER_TICKET_REFERENCE ) );
        collectionNotifyGruMarkers.add( createMarkerDescriptions( Constants.MARK_TICKET_TYPE, MESSAGE_MARKER_TICKET_TYPE ) );
        collectionNotifyGruMarkers.add( createMarkerDescriptions( Constants.MARK_TICKET_DOMAIN, MESSAGE_MARKER_TICKET_DOMAIN ) );
        collectionNotifyGruMarkers.add( createMarkerDescriptions( Constants.MARK_TICKET_CATEGORY, MESSAGE_MARKER_TICKET_CATEGORY ) );
        collectionNotifyGruMarkers.add( createMarkerDescriptions( Constants.MARK_TICKET_CATEGORY_PRECISION, MESSAGE_MARKER_TICKET_CATEGORY_PRECISION ) );
        collectionNotifyGruMarkers.add( createMarkerDescriptions( Constants.MARK_TICKET_CHANNEL, MESSAGE_MARKER_TICKET_CHANNEL ) );
        collectionNotifyGruMarkers.add( createMarkerDescriptions( Constants.MARK_TICKET_COMMENT, MESSAGE_MARKER_TICKET_COMMENT ) );
        collectionNotifyGruMarkers.add( createMarkerDescriptions( Constants.MARK_TECHNICAL_URL_COMPLETED, MESSAGE_MARKER_TECHNICAL_URL_COMPLETE ) );

        return collectionNotifyGruMarkers;
    }

    private static NotifyGruMarker createMarkerValues( String strMarker, String strValue )
    {
        NotifyGruMarker notifyGruMarker = new NotifyGruMarker( strMarker );
        notifyGruMarker.setValue( strValue );

        return notifyGruMarker;
    }

    private static NotifyGruMarker createMarkerDescriptions( String strMarker, String strDescription )
    {
        NotifyGruMarker notifyGruMarker = new NotifyGruMarker( strMarker );
        notifyGruMarker.setDescription( I18nService.getLocalizedString( strDescription, I18nService.getDefaultLocale( ) ) );

        return notifyGruMarker;
    }

}
