package com.tailan.confeitaria.web.domain.enums;

public enum Status {
    AGUARDANDO_PAGAMENTO(1),
    PAGO(2),
    EM_PREPARACAO(3),
    SAIU_PARA_ENTREGA(4),
    ENTREGUE(5),
    CANCELADO(6);

    private int code;
    private Status(int code) {
        this.code =  code;
    }

    public int getCode() {
        return code;
    }

    //converter  os numeros do status de pedido que serão convertido para o enum
    public static Status valueOf(int code){
        for (Status status : Status.values()){
            if (status.getCode()==code){
                return status;
            }
        }
        throw new IllegalArgumentException("Código de Status inválido: " + code);
    }
}
