package de.fhws.fiw.fds.suttondemo.server.api.states.modules;

import de.fhws.fiw.fds.sutton.server.api.services.ServiceContext;
import de.fhws.fiw.fds.sutton.server.api.states.delete.AbstractDeleteState;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import de.fhws.fiw.fds.suttondemo.server.api.models.Module;
import de.fhws.fiw.fds.suttondemo.server.api.models.University;
import de.fhws.fiw.fds.suttondemo.server.api.states.universities.UniversityRelTypes;
import de.fhws.fiw.fds.suttondemo.server.api.states.universities.UniversityUri;
import de.fhws.fiw.fds.suttondemo.server.database.DaoFactory;
import jakarta.ws.rs.core.Response;

public class DeleteSingleModule extends AbstractDeleteState<Response, Module> {

    public DeleteSingleModule(ServiceContext serviceContext, long modelIdToDelete) {
        super(serviceContext, modelIdToDelete);
    }


    @Override protected SingleModelResult<Module> loadModel( )
    {
        return DaoFactory.getInstance( ).getModuleDao( ).readById( this.modelIdToDelete );
    }

    @Override protected NoContentResult deleteModel( )
    {
        DaoFactory.getInstance( ).getUniversityModuleDao().deleteRelationsToSecondary( this.modelIdToDelete );
        return DaoFactory.getInstance( ).getModuleDao( ).delete( this.modelIdToDelete );
    }

    @Override protected void defineTransitionLinks( )
    {
        addLink( UniversityUri.REL_PATH, UniversityRelTypes.GET_ALL_UNIVERSITIES, getAcceptRequestHeader( ) );
    }
}
