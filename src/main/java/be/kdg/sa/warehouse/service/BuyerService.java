package be.kdg.sa.warehouse.service;

import be.kdg.sa.warehouse.domain.Buyer;
import be.kdg.sa.warehouse.repository.BuyerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BuyerService {

    private final BuyerRepository buyerRepository;

    public BuyerService(BuyerRepository buyerRepository) {
        this.buyerRepository = buyerRepository;
    }

    @Transactional(readOnly = true)
    public Buyer findBuyerByNameAndAddress(String name, String address){
      return buyerRepository.findBuyerByNameAndAddress(name, address);
    }

}
