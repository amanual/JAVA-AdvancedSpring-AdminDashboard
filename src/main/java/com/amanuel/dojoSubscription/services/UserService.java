package com.amanuel.dojoSubscription.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.amanuel.dojoSubscription.models.Pack;

import com.amanuel.dojoSubscription.models.User;
//import com.amanuel.dojoSubscription.models.Role;
import com.amanuel.dojoSubscription.repositories.PackageRepository;
import com.amanuel.dojoSubscription.repositories.RoleRepository;
import com.amanuel.dojoSubscription.repositories.UserRepository;
@Transactional
@Service
public class UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PackageRepository packageRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
//    
    public UserService(UserRepository userRepository, RoleRepository roleRepository,PackageRepository packageRepository, BCryptPasswordEncoder bCryptPasswordEncoder)     {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.packageRepository = packageRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    
    
    // 1
    public void saveWithUserRole(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(roleRepository.findByName("ROLE_USER"));
        userRepository.save(user);
    }
     
     // 2 
    public void saveUserWithAdminRole(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(roleRepository.findByName("ROLE_USER"));
        user.setRoles(roleRepository.findByName("ROLE_ADMIN"));
        userRepository.save(user);
    }    
    
    // 3
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }
    public void deleteUser(Long id) {
    		userRepository.delete(id);
    }
    public User findById(Long id) {
        return userRepository.findById(id);
    }
  
    public void saveSelecion(User selection) {
    		userRepository.save(selection);
	}

    
    public List<Pack> findAllPackages(){
    		return packageRepository.findAll();
    }
    
    public void saveNewPackage(Pack pack) {
    		packageRepository.save(pack);
    }
    public Pack findPackById(Long id) {
    		return packageRepository.findOne(id);
    }
    
    public void saveStatus(Pack status) {
    		packageRepository.save(status);
    }


	public void saveUser(User myUser) {
		userRepository.save(myUser);
		
	}


	public void deletePack(Long id) {
		packageRepository.delete(id);
		
	}
   
}
