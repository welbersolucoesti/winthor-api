package br.dev.farmes.modules.pedidos.dto;

import java.time.LocalDateTime;
import java.util.List;

public record PedidoResponse(
		String branchId,
	    int carrierId,
	    String chargingId,
	    LocalDateTime createData,
	    Customer customer,
	    LocalDateTime deliveryDate,
	    LocalDateTime lastChange,
	    List<OrderItem> listOfOrderItem,
	    Permissions permissions,
	    String orderId,
	    String orderStatus,
	    int paymentPlanId,
	    String saleOrigin,
	    int saleType,
	    String saleTypePayment,
	    String totalValue,
	    String trackingNumber) {
	
	public record Customer(
	        int activityId,
	        String addressInfo,
	        String billingAddress,
	        String billingAddressNumber,
	        String billingState,
	        String billingZipCode,
	        String businessCity,
	        String businessDistrict,
	        String businessState,
	        int cityId,
	        int ibgeId,
	        int businessCityId,
	        String commercialAddress,
	        String commercialAddressNumber,
	        String commercialZipCode,
	        String complementBillingAddress,
	        String complementBusinessAddress,
	        String complementDeliveryAddress,
	        boolean corporate,
	        String corporatePhone,
	        int countryId,
	        LocalDateTime createDate,
	        String deliveryAddress,
	        String deliveryAddressNumber,
	        String deliveryDistrict,
	        String deliveryState,
	        String deliveryZipCode,
	        String documentType,
	        String email,
	        String emailNfe,
	        boolean finalCostumer,
	        int id,
	        LocalDateTime lastChange,
	        String name,
	        int paymentPlanId,
	        String personIdentificationNumber,
	        String phone,
	        int sellerId,
	        int squareId,
	        String stateInscription,
	        String tradeName,
	        String billingPhone,
	        String deliveryPhone,
	        int active,
	        LocalDateTime dataNascimento,
	        int mainClientId,
	        int regionId,
	        String wholesalePriceUses,
	        Object permissions,
	        Object deliveryAddresses
	    ) {}

	    public record OrderItem(
	        double accountingCost,
	        double basePriceRCA,
	        int comissionPercent,
	        int discountPercentage,
	        int fecp,
	        double financialCost,
	        boolean gift,
	        int giftPrice,
	        long packingId,
	        int position,
	        int productId,
	        String productSKUERPReferenceKey,
	        int quantity,
	        double realCost,
	        double replacementCost,
	        int resourceValueCMV,
	        int resourceValueCustumerCMV,
	        double sellPrice,
	        DeductionsCmv deductionsCmv,
	        int sellPriceBaseST,
	        int sellPriceIPI,
	        int sellPriceST,
	        int sellPriceIcmsPart,
	        int stGNRE,
	        double tablePrice,
	        int tablePriceIPI,
	        int tablePriceST,
	        int taxFigureId,
	        boolean truncItem,
	        int wantageQuantity,
	        String warehouseBranchId,
	        String deliveryStatus
	    ) {}

	    public record DeductionsCmv(
	        int discountsCofins,
	        int discountsIcms,
	        int discountsPis
	    ) {}

	    public record Permissions(
	        boolean acceptOrderWithoutTax,
	        boolean acceptSearchAutomaticFreightType,
	        boolean withDeliveryAddress
	    ) {}
}
