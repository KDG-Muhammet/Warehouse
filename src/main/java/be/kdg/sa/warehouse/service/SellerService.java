package be.kdg.sa.warehouse.service;

import be.kdg.sa.warehouse.domain.Buyer;
import be.kdg.sa.warehouse.domain.Seller;
import be.kdg.sa.warehouse.repository.BuyerRepository;
import be.kdg.sa.warehouse.repository.SellerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SellerService {

    private SellerRepository sellerRepository;

    public SellerService(SellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }

    @Transactional(readOnly = true)
    public Seller findSellerByNameAndAddress(String name, String address){
      return sellerRepository.findSellerByNameAndAddress(name, address);
    };

}
