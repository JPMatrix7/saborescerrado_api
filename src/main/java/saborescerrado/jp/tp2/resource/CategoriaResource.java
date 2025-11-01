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
import saborescerrado.jp.tp2.dto.CategoriaDTO;
import saborescerrado.jp.tp2.service.CategoriaService;

@Path("/categorias")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CategoriaResource {

    @Inject
    CategoriaService categoriaService;

    @GET
    public Response getAll() {
        return categoriaService.getAll();
    }

    @GET
    @Path("/admin/{page}/{pageSize}")
    public Response getAllAdmin(@PathParam("page") int page, @PathParam("pageSize") int pageSize) {
        return categoriaService.getAllAdmin(page, pageSize);
    }

    @GET
    @Path("/count")
    public long count() {
        return categoriaService.count();
    }

    @GET
    @Path("/{id}")
    public Response getId(@PathParam("id") long id) {
        return categoriaService.getId(id);
    }

    @GET
    @Path("/admin/id/{id}")
    public Response getAdminId(@PathParam("id") long id) {
        return categoriaService.getAdminId(id);
    }

    @GET
    @Path("/nome/{nome}")
    public Response getNome(@PathParam("nome") String nome) {
        return categoriaService.getNome(nome);
    }

    @POST
    public Response insert(CategoriaDTO categoriaDTO) {
        return categoriaService.insert(categoriaDTO);
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, CategoriaDTO categoriaDTO) {
        return categoriaService.update(id, categoriaDTO);
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        return categoriaService.delete(id);
    }
}
