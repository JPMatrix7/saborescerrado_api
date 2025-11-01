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
import saborescerrado.jp.tp2.dto.LicorDTO;
import saborescerrado.jp.tp2.service.LicorService;

@Path("/licores")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LicorResource {

    @Inject
    LicorService licorService;

    @GET
    public Response getAll() {
        return licorService.getAll();
    }

    @GET
    @Path("/admin/{page}/{pageSize}")
    public Response getAllAdmin(@PathParam("page") int page, @PathParam("pageSize") int pageSize) {
        return licorService.getAllAdmin(page, pageSize);
    }

    @GET
    @Path("/count")
    public long count() {
        return licorService.count();
    }

    @GET
    @Path("/{id}")
    public Response getId(@PathParam("id") long id) {
        return licorService.getId(id);
    }

    @GET
    @Path("/admin/id/{id}")
    public Response getAdminId(@PathParam("id") long id) {
        return licorService.getAdminId(id);
    }

    @GET
    @Path("/nome/{nome}")
    public Response getNome(@PathParam("nome") String nome) {
        return licorService.getNome(nome);
    }

    @GET
    @Path("/tipo/{tipo}")
    public Response getTipo(@PathParam("tipo") String tipo) {
        return licorService.getTipo(tipo);
    }

    @GET
    @Path("/visiveis")
    public Response getVisivel() {
        return licorService.getVisivel();
    }

    @POST
    public Response insert(LicorDTO licorDTO) {
        return licorService.insert(licorDTO);
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, LicorDTO licorDTO) {
        return licorService.update(id, licorDTO);
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        return licorService.delete(id);
    }
}
