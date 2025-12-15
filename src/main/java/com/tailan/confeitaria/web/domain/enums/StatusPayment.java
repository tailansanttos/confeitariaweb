package com.tailan.confeitaria.web.domain.enums;

public enum StatusPayment {
    PENDING(1),
    PAID_OFF(2),
    CANCELED(3);
    private  int  code;
    StatusPayment(int code){
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static StatusPayment valueOf(int code){
        for(StatusPayment statusPayment : StatusPayment.values()){
            if(statusPayment.getCode() == code){
                return statusPayment;
            }
        }
        throw new IllegalArgumentException("Código de OrderStatus de Payment inválido");
    }
}
