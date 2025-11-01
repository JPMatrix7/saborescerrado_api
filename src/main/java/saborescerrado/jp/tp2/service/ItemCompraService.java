package saborescerrado.jp.tp2.service;

import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import saborescerrado.jp.tp2.dto.ItemCompraDTO;

public interface ItemCompraService {

    public Response getAll();

    public Response getAllAdmin(int page, int pageSize);

    public long count();

    public Response getId(@PathParam("id") long id);

    public Response getAdminId(@PathParam("id") long id);

    public Response insert(ItemCompraDTO itemCompra);

    public Response update(@PathParam("id") long id, ItemCompraDTO itemCompra);

    public Response delete(@PathParam("id") Long id);
}
