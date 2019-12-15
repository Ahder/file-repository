package com.rizomm.filemanager.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class UserController {

    @GetMapping("users")
    public String hello() {
        return "hello";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("file/upload")
    public String upload() {
        return "upload de fichier";
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("admin/files")
    public String getFileAdmin() {
        return "Files for admin";
    }

    @GetMapping("manager")
    public String getFile() {
        return "files for all";
    }

}
