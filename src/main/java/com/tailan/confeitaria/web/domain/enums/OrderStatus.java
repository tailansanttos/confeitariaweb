package com.tailan.confeitaria.web.domain.enums;

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
