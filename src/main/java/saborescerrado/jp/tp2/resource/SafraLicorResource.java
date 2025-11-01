package saborescerrado.jp.tp2.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import saborescerrado.jp.tp2.dto.SafraLicorDTO;
import saborescerrado.jp.tp2.service.SafraLicorService;

@Path("/safras")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SafraLicorResource {

    @Inject
    SafraLicorService safraLicorService;

    @GET
    public Response getAll() {
        return safraLicorService.getAll();
    }

    @GET
    @Path("/admin/{page}/{pageSize}")
    public Response getAllAdmin(@PathParam("page") int page, @PathParam("pageSize") int pageSize) {
        return safraLicorService.getAllAdmin(page, pageSize);
    }

    @GET
    @Path("/count")
    public long count() {
        return safraLicorService.count();
    }

    @GET
    @Path("/{id}")
    public Response getId(@PathParam("id") long id) {
        return safraLicorService.getId(id);
    }

    @GET
    @Path("/admin/id/{id}")
    public Response getAdminId(@PathParam("id") long id) {
        return safraLicorService.getAdminId(id);
    }

    @GET
    @Path("/fazenda/{fazenda}")
    public Response getFazenda(@PathParam("fazenda") String fazenda) {
        return safraLicorService.getFazenda(fazenda);
    }

    @POST
    public Response insert(SafraLicorDTO safraLicorDTO) {
        return safraLicorService.insert(safraLicorDTO);
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, SafraLicorDTO safraLicorDTO) {
        return safraLicorService.update(id, safraLicorDTO);
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        return safraLicorService.delete(id);
    }
}
