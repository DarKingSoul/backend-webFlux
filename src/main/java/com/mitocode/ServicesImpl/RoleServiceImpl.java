package com.mitocode.ServicesImpl;

import com.mitocode.Models.Roles;
import com.mitocode.Repository.IGenericRepo;
import com.mitocode.Repository.RoleRepository;
import com.mitocode.Services.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl extends CRUDImpl<Roles, String> implements IRoleService {

    private final RoleRepository roleRepository;

    @Override
    protected IGenericRepo<Roles, String> getRepo() {
        return roleRepository;
    }

}
