package io.github.robertomessabrasil.test.imagestore;

import io.github.robertomessabrasil.test.imagestore.controller.user.dto.CreateUserDto;
import io.github.robertomessabrasil.test.imagestore.controller.user.dto.LoginUserDto;
import io.github.robertomessabrasil.test.imagestore.controller.user.dto.RecoveryJwtTokenDto;
import io.github.robertomessabrasil.test.imagestore.controller.user.dto.UserDto;
import io.github.robertomessabrasil.test.imagestore.model.RoleName;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.Arrays;

class ImageProcessorApiApplicationTests {

    @Test
    void Given_EmailAndPassword_Then_CreateUser_Return_HttpStatus() {

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/image-user";
        CreateUserDto createUserDto = new CreateUserDto("wardog1@gmail.com", "a", RoleName.ROLE_CUSTOMER);
        HttpEntity<CreateUserDto> httpEntity = new HttpEntity<>(createUserDto);
        ResponseEntity<UserDto> res = restTemplate.exchange(url, HttpMethod.POST, httpEntity, UserDto.class);
        System.out.println(res);
        Assertions.assertEquals(res.getStatusCode(), HttpStatus.OK);

    }

    @Test
    void Given_EmailAndPassword_Then_Login_Return_HttpStatus() {

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/image-user/login";
        LoginUserDto createUserDto = new LoginUserDto("wardog1@gmail.com", "a");
        HttpEntity<LoginUserDto> httpEntity = new HttpEntity<>(createUserDto);
        ResponseEntity<RecoveryJwtTokenDto> res = restTemplate.exchange(url, HttpMethod.POST, httpEntity, RecoveryJwtTokenDto.class);
        System.out.println(res);
        Assertions.assertEquals(res.getStatusCode(), HttpStatus.OK);

    }

    @Test
    void Given_JWT_Then_ListFiles_Return_HttpStatus() {

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/image-store";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJpbWFnZS1zdG9yZS1hcGkiLCJpYXQiOjE3NDQwNDg1MzYsImV4cCI6MTc0NDA2MjkzNiwic3ViIjoid2FyZG9nMUBnbWFpbC5jb20ifQ.lFHYsjdgs1yLXrrt0eGQVH6TjYo6xermVWHM8ovZwig");
        HttpEntity<LoginUserDto> httpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<String[]> res = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String[].class);
        System.out.println(res);
        System.out.println(Arrays.toString(res.getBody()));
        Assertions.assertEquals(res.getStatusCode(), HttpStatus.OK);

    }

    @Test
    void Given_JWTandFile_Then_upload_Return_HttpStatus() {

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/image-store/upload";

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new FileSystemResource("/home/wardog1/Pictures/Screenshots/resttemplate1.png"));

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJpbWFnZS1zdG9yZS1hcGkiLCJpYXQiOjE3NDQwNDg1MzYsImV4cCI6MTc0NDA2MjkzNiwic3ViIjoid2FyZG9nMUBnbWFpbC5jb20ifQ.lFHYsjdgs1yLXrrt0eGQVH6TjYo6xermVWHM8ovZwig");
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(body, httpHeaders);

        ResponseEntity<String> res = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        System.out.println(res);
        System.out.println(res.getBody());
        Assertions.assertEquals(res.getStatusCode(), HttpStatus.OK);

    }

}
