package lebib.team.controller;

import lebib.team.dto.InventoryDto;
import lebib.team.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user/products/{productId}/inventories")
@CrossOrigin("*")
public class InventoryController {

    private final InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/{inventoryId}")
    public ResponseEntity<InventoryDto> getInventory(
            @PathVariable Long productId,
            @PathVariable Long inventoryId
    ) {
        return ResponseEntity.ok(inventoryService.getInventory(inventoryId, productId));
    }
}
