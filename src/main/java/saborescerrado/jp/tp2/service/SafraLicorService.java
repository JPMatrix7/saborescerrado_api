package saborescerrado.jp.tp2.service;

import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import saborescerrado.jp.tp2.dto.SafraLicorDTO;

public interface SafraLicorService {

    public Response getAll();

    public Response getAllAdmin(int page, int pageSize);

    public long count();

    public Response getId(@PathParam("id") long id);

    public Response getAdminId(@PathParam("id") long id);

    public Response getFazenda(@PathParam("fazenda") String fazenda);

    public Response insert(SafraLicorDTO safraLicor);

    public Response update(@PathParam("id") long id, SafraLicorDTO safraLicor);

    public Response delete(@PathParam("id") Long id);
}
