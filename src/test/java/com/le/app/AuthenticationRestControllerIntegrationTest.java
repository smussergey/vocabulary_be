//package com.le.app;
//
//import java.nio.charset.Charset;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import com.le.app.repository.UserRepository;
//import com.le.app.rest.AuthenticationRestController;
//import com.le.app.service.UserService;
//import org.hamcrest.core.Is;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//@RunWith(SpringRunner.class)
//@WebMvcTest
//@AutoConfigureMockMvc
//@SpringBootTest()
//public class AuthenticationRestControllerIntegrationTest {
//
//    @MockBean
//    private UserService userService;
//    @MockBean
//    private UserRepository userRepository;
//
//    @Autowired
//    AuthenticationRestController authenticationRestController;
//    // UserController userController;
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Test
//    public void contextLoads() {
//    }
//
//
//    @Test
//    public void whenUserControllerInjected_thenNotNull() throws Exception {
//        assertThat(authenticationRestController).isNotNull();
//    }
//
//    @Test
//    public void whenGetRequestToUsers_thenCorrectResponse() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/users")
//                .contentType(MediaType.APPLICATION_JSON_UTF8))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8));
//
//    }
//
//    @Test
//    public void whenPostRequestToUsersAndValidUser_thenCorrectResponse() throws Exception {
//        MediaType textPlainUtf8 = new MediaType(MediaType.TEXT_PLAIN, Charset.forName("UTF-8"));
//        String userDto = "{\"username\": \"sem\", \"firstName\": \"semFirst\", \"lastName\": \"semLast\", \"email\": \"sem@ukr.net\", \"password\" : \"sempas\"}";
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/register")
//                .content(userDto)
//                .contentType(MediaType.APPLICATION_JSON_UTF8))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().contentType(textPlainUtf8));
//    }
//
//    @Test
//    public void whenPostRequestToUsersAndInValidUser_thenCorrectReponse() throws Exception {
//        String userDto = "{\"username\": \"sem\", \"firstName\": \"\", \"lastName\": \"semLast\", \"email\": \"\", \"password\" : \"sempas\"}";
//        String user = "{\"name\": \"\", \"email\" : \"bob@domain.com\"}";
//        mockMvc.perform(MockMvcRequestBuilders.post("/users")
//                .content(user)
//                .contentType(MediaType.APPLICATION_JSON_UTF8))
//                .andExpect(MockMvcResultMatchers.status().isBadRequest())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Is.is("Name is mandatory")))
//                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8));
//    }
//}