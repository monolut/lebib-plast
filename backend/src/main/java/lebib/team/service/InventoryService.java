package lebib.team.service;

import lebib.team.dto.InventoryDto;
import lebib.team.entity.InventoryEntity;
import lebib.team.entity.ProductEntity;
import lebib.team.exception.InventoryNotFoundException;
import lebib.team.exception.ProductNotFoundException;
import lebib.team.mapper.InventoryMapper;
import lebib.team.repository.InventoryRepository;
import lebib.team.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryMapper inventoryMapper;
    private final ProductRepository productRepository;

    @Autowired
    public InventoryService(
            ProductRepository productRepository,
            InventoryRepository inventoryRepository,
            InventoryMapper inventoryMapper
    ) {
        this.inventoryMapper = inventoryMapper;
        this.inventoryRepository = inventoryRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public InventoryEntity createInventory(ProductEntity product, Integer quantity) {
        InventoryEntity inventoryEntity = new InventoryEntity();
        inventoryEntity.setQuantity(quantity);
        inventoryEntity.setProduct(product);
        return inventoryEntity;
    }

    public InventoryDto getInventory(Long inventoryId, Long productId) {
        InventoryEntity inventoryEntity = checkCorresponds(inventoryId, productId);

        return inventoryMapper.toDto(inventoryEntity);
    }

    @Transactional
    public InventoryDto updateInventory(Long inventoryId, Long productId, Integer quantity) {
        InventoryEntity inventoryEntity = checkCorresponds(inventoryId, productId);

        inventoryEntity.setQuantity(quantity);

        inventoryRepository.save(inventoryEntity);
        return inventoryMapper.toDto(inventoryEntity);
    }

    @Transactional
    public void deleteInventory(Long inventoryId, Long productId) {
        checkCorresponds(inventoryId, productId);

        inventoryRepository.deleteById(inventoryId);
    }

    private InventoryEntity checkCorresponds(Long inventoryId, Long productId) {
        InventoryEntity inventoryEntity = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> InventoryNotFoundException.byId(inventoryId));

        ProductEntity productEntity = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        if (!productEntity.getInventory().getId().equals(inventoryId)) {
            throw new IllegalArgumentException("Inventory does not correspond to this product");
        }

        return inventoryEntity;
    }
}
