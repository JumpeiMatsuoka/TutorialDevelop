package com.techacademy.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                    .webAppContextSetup(webApplicationContext)
                    .apply(springSecurity())
                    .build();
    }

    @Test
    @DisplayName("Userリストページの検証")
    @WithMockUser
    public void testGetUserList() throws Exception {
        mockMvc.perform(get("/user/list"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("userlist"))
                .andExpect(model().attribute("userlist", hasSize(3)))
                .andExpect(model().attribute("userlist", hasItem(
                    allOf(
                        hasProperty("id", is(1)),
                        hasProperty("name", is("キラメキ太郎"))
                    )
                )))
                .andExpect(model().attribute("userlist", hasItem(
                    allOf(
                        hasProperty("id", is(2)),
                        hasProperty("name", is("キラメキ次郎"))
                    )
                )))
                .andExpect(model().attribute("userlist", hasItem(
                    allOf(
                        hasProperty("id", is(3)),
                        hasProperty("name", is("キラメキ花子"))
                    )
                )))
                .andExpect(view().name("user/list"));
    }
}
