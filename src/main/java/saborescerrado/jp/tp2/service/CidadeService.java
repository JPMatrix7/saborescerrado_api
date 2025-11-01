package saborescerrado.jp.tp2.service;

import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import saborescerrado.jp.tp2.dto.CidadeDTO;

public interface CidadeService {

    public Response getAll();

    public Response getAllAdmin(int page, int pageSize);

    public long count();

    public Response getId(@PathParam("id") long id);

    public Response getAdminId(@PathParam("id") long id);

    public Response getNome(@PathParam("nome") String nome);

    public Response insert(CidadeDTO cidade);

    public Response update(@PathParam("id") long id, CidadeDTO cidade);

    public Response delete(@PathParam("id") Long id);
}
