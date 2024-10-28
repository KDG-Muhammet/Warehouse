package be.kdg.sa.warehouse.service.seller;

import be.kdg.sa.warehouse.domain.Seller;
import be.kdg.sa.warehouse.repository.SellerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
public class SellerService {
    private final SellerRepository sellerRepository;
    private final Logger logger = LoggerFactory.getLogger(SellerService.class);

    public SellerService(SellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }

    @Transactional(readOnly = true)
    public Seller findSellerByNameAndAddress(String name, String address){
        logger.info("   Finding {} with address {}", name, address);
        return sellerRepository.findSellerByNameAndAddress(name, address);
    }

    @Transactional(readOnly = true)
    public Optional<Seller> findSellerByName(String name){
        logger.info("   Finding seller with name {}", name);
        return sellerRepository.findSellerByName(name);
    }

    @Transactional
    public Seller createSeller(Seller seller){
        logger.info("   Creating {}", seller.getName());
        return sellerRepository.save(seller);
    }


    public Collection<Seller> findAllSellers() {
        return sellerRepository.findAll();
    }
}
