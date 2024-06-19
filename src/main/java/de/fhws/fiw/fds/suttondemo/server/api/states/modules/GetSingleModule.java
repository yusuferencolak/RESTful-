package de.fhws.fiw.fds.suttondemo.server.api.states.modules;

import de.fhws.fiw.fds.sutton.server.api.caching.CachingUtils;
import de.fhws.fiw.fds.sutton.server.api.caching.EtagGenerator;
import de.fhws.fiw.fds.sutton.server.api.services.ServiceContext;
import de.fhws.fiw.fds.sutton.server.api.states.get.AbstractGetState;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;
import de.fhws.fiw.fds.suttondemo.server.api.models.Module;
import de.fhws.fiw.fds.suttondemo.server.api.states.universities.UniversityUri;
import de.fhws.fiw.fds.suttondemo.server.database.DaoFactory;
import jakarta.ws.rs.core.Response;

public class GetSingleModule extends AbstractGetState<Response, Module> {

    public GetSingleModule(ServiceContext serviceContext, long requestedId) {
        super(serviceContext, requestedId);
    }


    @Override protected SingleModelResult<Module> loadModel( )
    {
        return DaoFactory.getInstance( ).getModuleDao( ).readById( this.requestedId );
    }

    @Override
    protected boolean clientKnowsCurrentModelState(AbstractModel modelFromDatabase) {
        return this.suttonRequest.clientKnowsCurrentModel(modelFromDatabase);
    }

    @Override
    protected void defineHttpCaching() {
        final String eTagOfModel = EtagGenerator.createEtag(this.requestedModel.getResult());
        this.suttonResponse.entityTag(eTagOfModel);
        this.suttonResponse.cacheControl(CachingUtils.create30SecondsPublicCaching());
    }

    @Override protected void defineTransitionLinks( )
    {
        addLink( ModuleUri.REL_PATH_ID, ModuleRelTypes.UPDATE_SINGLE_MODULE, getAcceptRequestHeader( ),
                this.requestedId );
        addLink( UniversityUri.REL_PATH_ID, ModuleRelTypes.DELETE_SINGLE_MODULE, getAcceptRequestHeader( ),
                this.requestedId );
    }
}
