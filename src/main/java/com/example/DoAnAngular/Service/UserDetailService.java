package com.example.DoAnAngular.Service;


import com.example.DoAnAngular.Entities.User;
import com.example.DoAnAngular.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService implements UserDetailsService {
   @Autowired
   UserRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUserName(username);
        return user;
    }
    public void changePassword(String username, String newPassword) {
        User user = repository.findByUserName(username);
        user.setPassWord(new BCryptPasswordEncoder().encode(newPassword));
        repository.save(user);
    }





    public User getCurrentUser(){
        User user;
        String userName;
        //Lay 1 object principal trong SecurityContexHolder
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //So sanh obj voi Userdetails neu ma dung thi gan userName = principal.getUsername();
        if(principal instanceof UserDetails){
            userName = ((UserDetails) principal).getUsername();
        } else {
            //neu khong phai user hien tai thi userName = principal.toString();
            userName = principal.toString();
        }
        //kiem tra neu userName ton tai trong DB thi gan user = ham tim kiem trong DB theo userName do
        if(repository.existsByUserName(userName)){
            user = repository.findByUserName(userName);
        } else {user =null;
        }
        return user;
    }

}
