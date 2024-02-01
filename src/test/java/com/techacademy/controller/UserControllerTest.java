package com.techacademy.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.techacademy.entity.User;
import com.techacademy.service.UserService;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class UserControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private UserService userService;
    
    @Autowired
    public UserControllerTest(WebApplicationContext context){
       this.mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    @Test
    @DisplayName("User更新画面")
    @WithMockUser
    void testGetList() throws Exception{
        List<User> mockUserList = new ArrayList<>();
        mockUserList.add(new User(1, "キラメキ太郎", "男性", 27, "taro.kirameki@mail.com"));
        mockUserList.add(new User(2, "キラメキ次郎", "男性", 22, "jiro.kirameki@mail.com"));
        mockUserList.add(new User(3, "キラメキ花子", "女性", 25, "hanako.kirameki@mail.com"));

            // UserServiceの振る舞いを設定
            when(userService.getUserList()).thenReturn(mockUserList);

            // テストの実行
            mockMvc.perform(get("/user/list"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("userlist"))
                .andExpect(model().attribute("userlist", hasSize(3)))
                .andExpect(model().attribute("userlist", containsInAnyOrder(
                    hasProperty("id", is(1)),
                    hasProperty("name", is("キラメキ太郎")),
                    hasProperty("id", is(2)),
                    hasProperty("name", is("キラメキ次郎")),
                    hasProperty("id", is(3)),
                    hasProperty("name", is("キラメキ花子"))
                )))
                .andExpect(view().name("user/list"));
        }
    }
