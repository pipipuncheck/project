package com.example.jwtauth.presentation.controller.admin;

import com.example.jwtauth.application.admin.service.AdminService;
import com.example.jwtauth.application.backup.service.BackUpService;
import com.example.jwtauth.presentation.controller.admin.dto.query.UserQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;
    private final BackUpService backUpService;

    @Autowired
    public AdminController(AdminService adminService, BackUpService backUpService) {
        this.adminService = adminService;
        this.backUpService = backUpService;
    }


    @GetMapping("/users")
    public List<UserQuery> allUsers(){
        return adminService.showAllUsers();
    }

    @GetMapping("/users/{id}")
    public UserQuery oneUser(@PathVariable("id") int id){
        return adminService.showUserById(id);
    }

    @GetMapping("/users/{id}/change_role_to_admin")
    public String changeRoleToAdmin(@PathVariable("id") int id){
        adminService.makeUserAdmin(id);
        return "Роль пользователя успешно изменена на админа";
    }

    @GetMapping("/users/{id}/change_role_to_manager")
    public String changeRoleToManager(@PathVariable("id") int id){
        adminService.makeUserManager(id);
        return "Роль пользователя успешно изменена на менеджера";
    }

    @DeleteMapping("/users/{id}")
    public String delete(@PathVariable("id") int id){
        adminService.deleteUser(id);
        return "учётная запись пользователя успешно удалена";
    }

    @GetMapping("/backup")
    public String backup(@AuthenticationPrincipal UserDetails userDetails){
        backUpService.backUp(userDetails);
        return "Backup was completed successfully";
    }
}
