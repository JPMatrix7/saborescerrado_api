package saborescerrado.jp.tp2.service;

import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import saborescerrado.jp.tp2.dto.CategoriaDTO;

public interface CategoriaService {

    public Response getAll();

    public Response getAllAdmin(int page, int pageSize);

    public long count();

    public Response getId(@PathParam("id") long id);

    public Response getAdminId(@PathParam("id") long id);

    public Response getNome(@PathParam("nome") String nome);

    public Response insert(CategoriaDTO categoria);

    public Response update(@PathParam("id") long id, CategoriaDTO categoria);

    public Response delete(@PathParam("id") Long id);
}
