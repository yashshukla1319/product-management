//package com.coachbar.product.management;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//import com.coachbar.product.management.exceptions.InvalidResourceException;
//import com.coachbar.product.management.exceptions.ResourceNotFoundException;
//import com.coachbar.product.management.model.entity.Product;
//import com.coachbar.product.management.repository.ProductRepository;
//import com.coachbar.product.management.service.ProductService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//class ProductServiceTest {
//
//    @Mock
//    private ProductRepository productRepository;
//
//    @InjectMocks
//    private ProductService productService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testGetAllProducts() {
//        List<Product> products = Arrays.asList(
//                new Product(1L, "Product1", "Description1", 100.0, 10L),
//                new Product(2L, "Product2", "Description2", 200.0, 20L)
//        );
//        when(productRepository.findAll()).thenReturn(products);
//
//        List<Product> result = productService.getProducts();
//
//        assertEquals(2, result.size());
//        verify(productRepository, times(1)).findAll();
//    }
//
//    @Test
//    void testGetProductById_Found() {
//        Product product = new Product(1L, "Product1", "Description1", 100.0, 10L);
//        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
//
//        Optional<Product> result = productService.getProductById(1L);
//
//        assertTrue(result.isPresent());
//        assertEquals("Product1", result.get().getName());
//        verify(productRepository, times(1)).findById(1L);
//    }
//
//    @Test
//    void testGetProductByIdNotFound() {
//        when(productRepository.findById(99L)).thenReturn(Optional.empty());
//
//        //Optional<Product> result = productService.getProductById(99L);
//        ResourceNotFoundException exception = assertThrows(
//                ResourceNotFoundException.class,
//                () -> productService.getProductById(99L)
//        );
//
//        assertNotNull(exception.getMessage());
//
//        //assertTrue(result.isEmpty());
//        verify(productRepository, times(1)).findById(99L);
//    }
//
//    @Test
//    void testCreateProductSuccess() {
//        Product product = new Product(null, "NewProduct", "NewDescription", 300.0, 30L);
//        Product savedProduct = new Product(1L, "NewProduct", "NewDescription", 300.0, 30L);
//
//        when(productRepository.findByName("NewProduct")).thenReturn(Optional.empty());
//        when(productRepository.save(product)).thenReturn(savedProduct);
//
//        Product result = productService.createProduct(product);
//
//        assertNotNull(result.getId());
//        assertEquals("NewProduct", result.getName());
//        verify(productRepository, times(1)).findByName("NewProduct");
//        verify(productRepository, times(1)).save(product);
//    }
//
//    @Test
//    void testCreateProductAlreadyExists() {
//        Product product = new Product(null, "ExistingProduct", "Description", 300.0, 30L);
//        when(productRepository.findByName("ExistingProduct")).thenReturn(Optional.of(product));
//
//        InvalidResourceException exception = assertThrows(
//                InvalidResourceException.class,
//                () -> productService.createProduct(product)
//        );
//
//        assertEquals("Product already exists.", exception.getMessage());
//        verify(productRepository, times(1)).findByName("ExistingProduct");
//        verify(productRepository, never()).save(any(Product.class));
//    }
//
//    @Test
//    void testUpdateProductSuccess() {
//        Product existingProduct = new Product(1L, "ExistingProduct", "Description", 300.0, 30L);
//        Product updatedProduct = new Product(1L, "UpdatedProduct", "UpdatedDescription", 400.0, 40L);
//
//        when(productRepository.findById(1L)).thenReturn(Optional.of(existingProduct));
//        //when(productRepository.save(updatedProduct)).thenReturn(updatedProduct);
//
//        Product result = productService.updateProduct(1L, updatedProduct);
//
//        assertNotNull(result);
//        //assertEquals("UpdatedProduct", result.get().getName());
//        verify(productRepository, times(1)).findById(1L);
//        verify(productRepository, times(1)).save(updatedProduct);
//    }
//
//    @Test
//    void testUpdateProductNotFound() {
//        Product updatedProduct = new Product(1L, "UpdatedProduct", "UpdatedDescription", 400.0, 40L);
//
//        when(productRepository.findById(1L)).thenReturn(Optional.empty());
//
//        //Product result = productService.updateProduct(1L, updatedProduct);
//
//        ResourceNotFoundException exception = assertThrows(
//                ResourceNotFoundException.class,
//                () -> productService.updateProduct(99L, updatedProduct)
//        );
//
//        assertNotNull(exception.getMessage());
//
//        //assertTrue(result.isEmpty());
//        //verify(productRepository, times(1)).findById(99L);
//        verify(productRepository, never()).save(any(Product.class));
//    }
//
//    @Test
//    void testDeleteProductSuccess() {
//        Product product = new Product(1L, "Product1", "Description1", 100.0, 10L);
//
//        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
//        doNothing().when(productRepository).deleteById(1L);
//
//        productService.deleteProduct(1L);
//
//        verify(productRepository, times(1)).findById(1L);
//        verify(productRepository, times(1)).deleteById(1L);
//    }
//
//    @Test
//    void testDeleteProductNotFound() {
//        when(productRepository.findById(99L)).thenReturn(Optional.empty());
//
//        ResourceNotFoundException exception = assertThrows(
//                ResourceNotFoundException.class,
//                () -> productService.deleteProduct(99L)
//        );
//
//        assertNotNull(exception.getMessage());
//        verify(productRepository, times(1)).findById(99L);
//        verify(productRepository, never()).deleteById(anyLong());
//    }
//}
