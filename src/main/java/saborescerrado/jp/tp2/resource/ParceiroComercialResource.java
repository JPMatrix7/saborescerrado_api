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
import saborescerrado.jp.tp2.dto.ParceiroComercialDTO;
import saborescerrado.jp.tp2.service.ParceiroComercialService;

@Path("/parceirocomercial")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ParceiroComercialResource {

    @Inject
    ParceiroComercialService parceiroComercialService;

    @GET
    public Response getAll() {
        return parceiroComercialService.getAll();
    }

    @GET
    @Path("/admin/{page}/{pageSize}")
    public Response getAllAdmin(@PathParam("page") int page, @PathParam("pageSize") int pageSize) {
        return parceiroComercialService.getAllAdmin(page, pageSize);
    }

    @GET
    @Path("/count")
    public long count() {
        return parceiroComercialService.count();
    }

    @GET
    @Path("/{id}")
    public Response getId(@PathParam("id") long id) {
        return parceiroComercialService.getId(id);
    }

    @GET
    @Path("/admin/id/{id}")
    public Response getAdminId(@PathParam("id") long id) {
        return parceiroComercialService.getAdminId(id);
    }

    @GET
    @Path("/cnpj/{cnpj}")
    public Response getCnpj(@PathParam("cnpj") String cnpj) {
        return parceiroComercialService.getCnpj(cnpj);
    }

    @GET
    @Path("/nome-fantasia/{nomeFantasia}")
    public Response getNomeFantasia(@PathParam("nomeFantasia") String nomeFantasia) {
        return parceiroComercialService.getNomeFantasia(nomeFantasia);
    }

    @POST
    public Response insert(ParceiroComercialDTO parceiroDTO) {
        return parceiroComercialService.insert(parceiroDTO);
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, ParceiroComercialDTO parceiroDTO) {
        return parceiroComercialService.update(id, parceiroDTO);
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        return parceiroComercialService.delete(id);
    }
}
