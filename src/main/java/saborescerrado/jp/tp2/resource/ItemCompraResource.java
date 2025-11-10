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
import saborescerrado.jp.tp2.dto.ItemCompraDTO;
import saborescerrado.jp.tp2.service.ItemCompraService;

@Path("/itemcompra")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ItemCompraResource {

    @Inject
    ItemCompraService itemCompraService;

    @GET
    public Response getAll() {
        return itemCompraService.getAll();
    }

    @GET
    @Path("/admin/{page}/{pageSize}")
    public Response getAllAdmin(@PathParam("page") int page, @PathParam("pageSize") int pageSize) {
        return itemCompraService.getAllAdmin(page, pageSize);
    }

    @GET
    @Path("/count")
    public long count() {
        return itemCompraService.count();
    }

    @GET
    @Path("/{id}")
    public Response getId(@PathParam("id") long id) {
        return itemCompraService.getId(id);
    }

    @GET
    @Path("/admin/id/{id}")
    public Response getAdminId(@PathParam("id") long id) {
        return itemCompraService.getAdminId(id);
    }

    @POST
    public Response insert(ItemCompraDTO itemCompraDTO) {
        return itemCompraService.insert(itemCompraDTO);
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, ItemCompraDTO itemCompraDTO) {
        return itemCompraService.update(id, itemCompraDTO);
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        return itemCompraService.delete(id);
    }
}
