package saborescerrado.jp.tp2.service;

import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import saborescerrado.jp.tp2.dto.AvaliacaoDTO;

public interface AvaliacaoService {

    public Response getAll();

    public Response getAllAdmin(int page, int pageSize);

    public long count();

    public Response getId(@PathParam("id") long id);

    public Response getAdminId(@PathParam("id") long id);

    public Response getEstrelas(@PathParam("estrelas") Integer estrelas);

    public Response insert(AvaliacaoDTO avaliacao);

    public Response update(@PathParam("id") long id, AvaliacaoDTO avaliacao);

    public Response delete(@PathParam("id") Long id);
}
