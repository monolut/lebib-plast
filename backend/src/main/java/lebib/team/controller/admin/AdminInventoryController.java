package lebib.team.controller.admin;

import lebib.team.dto.InventoryDto;
import lebib.team.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/products/{productId}/inventories")
@CrossOrigin("*")
public class AdminInventoryController {

    private final InventoryService inventoryService;

    @Autowired
    public AdminInventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PutMapping("/{inventoryId}")
    public ResponseEntity<InventoryDto> updateInventory(@PathVariable Long productId, @PathVariable Long inventoryId, @RequestBody Integer quantity) {
        return ResponseEntity.ok(inventoryService.updateInventory(inventoryId, productId, quantity));
    }

    @DeleteMapping("/{inventoryId}")
    public ResponseEntity<Void> deleteInventory(@PathVariable Long productId, @PathVariable Long inventoryId) {
        inventoryService.deleteInventory(inventoryId, productId);
        return ResponseEntity.noContent().build();
    }
}
