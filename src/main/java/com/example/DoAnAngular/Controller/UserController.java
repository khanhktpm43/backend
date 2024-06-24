package com.example.DoAnAngular.Controller;


import com.example.DoAnAngular.Dto.infoDTO;
import com.example.DoAnAngular.Entities.RoleName;
import com.example.DoAnAngular.Entities.User;
import com.example.DoAnAngular.JWT.JwtProvider;
import com.example.DoAnAngular.JWT.JwtTokenFilter;
import com.example.DoAnAngular.Repository.UserRepository;
import com.example.DoAnAngular.RequestResponse.JwtResponse;
import com.example.DoAnAngular.RequestResponse.ResponseObject;
import com.example.DoAnAngular.Service.BlackList;
import com.example.DoAnAngular.Service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/user")
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    UserRepository repository;
    @Autowired
    UserDetailService service;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    JwtTokenFilter filter;
    @Autowired
    BlackList BackList;

    @PreAuthorize("hasRole('ADMIN')")
@GetMapping("/")
public ResponseEntity<ResponseObject> getAll(){
    List<User> list = repository.findAll();
    return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok","",list));
}
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register")
    public ResponseEntity<ResponseObject> register(@RequestBody User user){

        if(repository.existsByUserName(user.getUsername())){
            return ResponseEntity.status(HttpStatus.FOUND).body(new ResponseObject("fail","username exist",null));
        }
        try {
            RoleName.valueOf(user.getRole().toString());
            user.setPassWord(passwordEncoder.encode(user.getPassword()));
            repository.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseObject("ok", "", user));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.FOUND).body(new ResponseObject("fail","set role fail",null));
        }

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody User user){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.createToken(authentication);
        User userPrinciple = (User) authentication.getPrincipal();
        return ResponseEntity.ok(new JwtResponse(token, userPrinciple.getId(), userPrinciple.getRole()));
    }
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null){
            String token = filter.getJwt(request);
            BackList.addItemToList(token);
            logoutHandler.logout(request, response, authentication);
        }
        return ResponseEntity.ok("Logged out successfully");
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("edit-info/{id}")
    public ResponseEntity<?> editInfo(@PathVariable Long id,@RequestBody infoDTO infoDTO){
        User user = repository.getById(id);
        if(user != null){
            user.setName(infoDTO.name);
            user.setCccd(infoDTO.cccd);
            user.setPhone(infoDTO.phone);
        }
        repository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok","",infoDTO));
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getByID(@PathVariable Long id){
        User user = repository.getById(id);
        infoDTO infoDTO = new infoDTO(user.getName(),user.getCccd(),user.getPhone());
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok","",infoDTO));
    }
//    @PostMapping("/update")
//    public ResponseEntity<?> uơdate(@Valid @RequestBody PassRequest request){
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = authentication.getName();
//
//        // Check if old password is correct
//        UserDetails userDetails = service.loadUserByUsername(username);
//        if (!new BCryptPasswordEncoder().matches(request.getPass(), userDetails.getPassword())) {
//
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject("fail","không đúng mật khẩu",null));
//        }
//
//        // Check if new password and confirm password match
//        if (!request.getNewPass().equals(request.getRePass())) {
//
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject("fail","pass không trùng",null));
//        }
//
//        // Update password
//        service.changePassword(username, request.getNewPass());
//        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok","",null));
//    }

    @GetMapping("/test")
    public ResponseEntity<?> test(){

        return ResponseEntity.ok(service.getCurrentUser());
    }
}
