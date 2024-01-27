package com.main.entities.vendingPoints;

import lombok.Data;

import java.util.List;


@Data
public class VendingPointWithFunctionVariant {
    private Long vendingPointId;
    private String vendingPointAddress;
    private String vendingPointDescription;
    private Long vendingPointNumberMachines;
    private Double[] vendingPointCords;
    private List<FunctionVariantEntity> functionVariants;

    public VendingPointWithFunctionVariant(
            Long vendingPointId,
            String vendingPointAddress,
            String vendingPointDescription,
            Long vendingPointNumberMachines,
            Double[] vendingPointCords) {
        this.vendingPointId = vendingPointId;
        this.vendingPointAddress = vendingPointAddress;
        this.vendingPointDescription = vendingPointDescription;
        this.vendingPointNumberMachines = vendingPointNumberMachines;
        this.vendingPointCords = vendingPointCords;
    }

    public VendingPointWithFunctionVariant(
            Long vendingPointId,
            String vendingPointAddress,
            String vendingPointDescription,
            Long vendingPointNumberMachines,
            Double[] vendingPointCords,
            List<FunctionVariantEntity> functionVariants) {
        this.vendingPointId = vendingPointId;
        this.vendingPointAddress = vendingPointAddress;
        this.vendingPointDescription = vendingPointDescription;
        this.vendingPointNumberMachines = vendingPointNumberMachines;
        this.vendingPointCords = vendingPointCords;
        this.functionVariants = functionVariants;
    }
}
