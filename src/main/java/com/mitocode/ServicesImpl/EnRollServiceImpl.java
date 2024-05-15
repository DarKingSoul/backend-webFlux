package com.mitocode.ServicesImpl;

import com.mitocode.Models.EnRolls;
import com.mitocode.Repository.EnRollRepository;
import com.mitocode.Repository.IGenericRepo;
import com.mitocode.Services.IEnRollService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnRollServiceImpl extends CRUDImpl<EnRolls, String> implements IEnRollService {

    private final EnRollRepository enRollRepository;

    @Override
    protected IGenericRepo<EnRolls, String> getRepo() {
        return enRollRepository;
    }

}
