package saborescerrado.jp.tp2.service;

import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import saborescerrado.jp.tp2.dto.PremiacaoDTO;

public interface PremiacaoService {

    public Response getAll();

    public Response getAllAdmin(int page, int pageSize);

    public long count();

    public Response getId(@PathParam("id") long id);

    public Response getAdminId(@PathParam("id") long id);

    public Response getEvento(@PathParam("evento") String evento);

    public Response getAno(@PathParam("ano") Integer ano);

    public Response insert(PremiacaoDTO premiacao);

    public Response update(@PathParam("id") long id, PremiacaoDTO premiacao);

    public Response delete(@PathParam("id") Long id);
}
