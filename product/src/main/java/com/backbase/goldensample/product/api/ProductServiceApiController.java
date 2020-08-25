package com.backbase.goldensample.product.api;

import com.backbase.goldensample.product.exception.NotFoundException;
import com.backbase.goldensample.product.service.ProductService;
import com.backbase.product.api.service.v2.ProductServiceImplApi;
import com.backbase.product.api.service.v2.model.Product;
import com.backbase.product.api.service.v2.model.ProductId;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Class <code>ProductController</code> is the implementation of the main Product Endpoint API
 * definition.
 *
 * @see ProductServiceImplApi
 */
@RestController
@RequestMapping
public class ProductServiceApiController implements ProductServiceImplApi {

  /** Product service business logic interface. */
  private final ProductService prodService;

  @Autowired
  public ProductServiceApiController(@Qualifier("ProductServiceImpl") ProductService prodService) {
    this.prodService = prodService;
  }


  @Override
  public ResponseEntity<Void> deleteProduct(Long productId) {
    try {
      prodService.deleteProduct(productId);
    } catch (NotFoundException e) {
      e.printStackTrace();
      //TODO add log
    }
    return ResponseEntity.noContent().build();
  }

  @Override
  public ResponseEntity<List<Product>> getProductListUsingGET() {
      return ResponseEntity.ok(prodService.getAllProducts());
  }

  @Override
    public ResponseEntity<Product> getProductUsingGET(Long productId) {
      try {
        return ResponseEntity.ok(prodService.getProduct(productId, 0, 0));
      } catch (NotFoundException e) {
        e.printStackTrace();
        //TODO add log
      }
      return ResponseEntity.ok().build();
    }

  @Override
  public ResponseEntity<ProductId> postProduct(@Valid Product product) {
    Product productWithId = prodService.createProduct(product);
    ProductId productId = new ProductId();
    productId.setId(productWithId.getProductId());

    return ResponseEntity.ok(productId);
  }

  @Override
  public ResponseEntity<Void> putProduct(@Valid Product product) {
    Product productWithId = prodService.updateProduct(product);

    return ResponseEntity.noContent().build();
  }

  @Override
  public ResponseEntity<Void> putProductById(Long productId, @Valid Product product) {
    product.setProductId(productId);
    Product productWithId = prodService.updateProduct(product);

    return ResponseEntity.noContent().build();
  }
}
