package saborescerrado.jp.tp2.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import saborescerrado.jp.tp2.model.FormaPagamento;
import saborescerrado.jp.tp2.model.StatusPedido;

public record CompraDTO(
    LocalDateTime dataCompra,
    StatusPedido status,
    FormaPagamento formaPagamento,
    Double valorTotal,
    String codigoRastreio,
    LocalDate dataPrevista,
    LocalDate dataEntrega,
    Boolean pago,
    List<ItemCompraDTO> itens
) {}
