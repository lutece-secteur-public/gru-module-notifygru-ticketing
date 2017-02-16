package fr.paris.lutece.plugins.notifygru.modules.ticketing.service.provider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import fr.paris.lutece.plugins.workflow.modules.notifygru.service.provider.IProvider;
import fr.paris.lutece.plugins.workflow.modules.notifygru.service.provider.AbstractProviderManager;
import fr.paris.lutece.plugins.workflow.modules.notifygru.service.provider.ProviderDescription;
import fr.paris.lutece.plugins.workflowcore.business.resource.ResourceHistory;
import fr.paris.lutece.plugins.workflowcore.service.task.ITask;
import fr.paris.lutece.portal.service.i18n.I18nService;

public class TicketProviderManager extends AbstractProviderManager
{
    private static final String PROVIDER_ID = "ticket";

    private static final String MESSAGE_PROVIDER_LABEL = "module.notifygru.ticketing.module.provider.ticketing";

    public TicketProviderManager( String strId )
    {
        super( strId );
    }

    @Override
    public Collection<ProviderDescription> getAllProviderDescriptions( ITask task )
    {
        Collection<ProviderDescription> collectionProviderDescriptions = new ArrayList<>( );

        collectionProviderDescriptions.add( getProviderDescription( null ) );

        return collectionProviderDescriptions;
    }

    @Override
    public ProviderDescription getProviderDescription( String strProviderId )
    {
        ProviderDescription providerDescription = new ProviderDescription( PROVIDER_ID, I18nService.getLocalizedString( MESSAGE_PROVIDER_LABEL,
                I18nService.getDefaultLocale( ) ) );

        providerDescription.setMarkerDescriptions( TicketProvider.getProviderMarkerDescriptions( ) );

        return providerDescription;
    }

    @Override
    public IProvider createProvider( String strProviderId, ResourceHistory resourceHistory )
    {
        return new TicketProvider( resourceHistory );
    }

}
