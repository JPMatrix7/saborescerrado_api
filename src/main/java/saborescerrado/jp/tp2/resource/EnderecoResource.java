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
import saborescerrado.jp.tp2.dto.EnderecoDTO;
import saborescerrado.jp.tp2.service.EnderecoService;

@Path("/endereco")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EnderecoResource {

    @Inject
    EnderecoService enderecoService;

    @GET
    public Response getAll() {
        return enderecoService.getAll();
    }

    @GET
    @Path("/admin/{page}/{pageSize}")
    public Response getAllAdmin(@PathParam("page") int page, @PathParam("pageSize") int pageSize) {
        return enderecoService.getAllAdmin(page, pageSize);
    }

    @GET
    @Path("/count")
    public long count() {
        return enderecoService.count();
    }

    @GET
    @Path("/{id}")
    public Response getId(@PathParam("id") long id) {
        return enderecoService.getId(id);
    }

    @GET
    @Path("/admin/id/{id}")
    public Response getAdminId(@PathParam("id") long id) {
        return enderecoService.getAdminId(id);
    }

    @GET
    @Path("/cep/{cep}")
    public Response getCep(@PathParam("cep") String cep) {
        return enderecoService.getCep(cep);
    }

    @POST
    public Response insert(EnderecoDTO enderecoDTO) {
        return enderecoService.insert(enderecoDTO);
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, EnderecoDTO enderecoDTO) {
        return enderecoService.update(id, enderecoDTO);
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        return enderecoService.delete(id);
    }
}
