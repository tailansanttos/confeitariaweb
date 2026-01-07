package com.tailan.confeitaria.web.domain.enums;

import com.fasterxml.jackson.annotation.JsonIgnore;

public enum OrderStatus {
    AWAITING_PAYMENT(1),
    PAID(2),
    IN_PREPARATION(3),
    OUT_FOR_DELIVERY(4),
    DELIVERED(5),
    CANCELED(6);

    private int code;
    private OrderStatus(int code) {
        this.code =  code;
    }

    @JsonIgnore //JSON MOSTRAR VALOR NUMERICO AO INVES DO NOME DO STATUS
    public int getCode() {
        return code;
    }

    //converter  os numeros do status de pedido que serão convertido para o enum
    public static OrderStatus valueOf(int code){
        for (OrderStatus orderStatus : OrderStatus.values()){
            if (orderStatus.getCode()==code){
                return orderStatus;
            }
        }
        throw new IllegalArgumentException("Código de OrderStatus inválido: " + code);
    }
}
