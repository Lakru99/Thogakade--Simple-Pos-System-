package dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString

public class Item {
    private String itemCode;
    private String description;
    private String packSize;
    private Double unitPrice;
    private Integer qtyOnHand;

    public Item(String itemCode, String description, String packSize, Double unitPrice, Integer qtyOnHand) {
        this.itemCode = itemCode;
        this.description = description;
        this.packSize = packSize;
        this.unitPrice = unitPrice;
        this.qtyOnHand = qtyOnHand;
    }
}
