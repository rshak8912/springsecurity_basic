package me.study.springsecurity.account;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    AccountService accountService;

    @Test
    @WithAnonymousUser
    public void index_anonymous() throws Exception {
        mockMvc.perform(get("/").with(anonymous()))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    @WithMockUser(username = "sh", roles = "USER")
    public void index_user() throws Exception {
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithUser
    /*@WithMockUser(username = "sh", roles = "USER")*/
    public void admin_user() throws Exception {
        mockMvc.perform(get("/admin")
                /*.with(user("sh").roles("USER"))*/)
                .andDo(print())
                .andExpect(status().isForbidden());
    }
    @Test
    @WithMockUser(username = "sh", roles = "ADMIN")
    public void admin_admin() throws Exception {
        mockMvc.perform(get("/admin")
                /*.with(user("sh").roles("ADMIN"))*/)
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    @Transactional
    public void login_성공() throws Exception {
        String username = "sh";
        String password = "123";
        Account user = this.createUser(username, password);
        mockMvc.perform(formLogin().user(username).password(password))
                .andExpect(authenticated());
    }
    @Test
    @Transactional
    public void login_성공2() throws Exception {
        String username = "sh";
        String password = "123";
        Account user = this.createUser(username, password);
        mockMvc.perform(formLogin().user(username).password(password))
                .andExpect(authenticated());
    }
    @Test
    @Transactional
    public void login_fail() throws Exception {
        String username = "nsh";
        String password = "123";
        Account user = this.createUser(username, password);
        mockMvc.perform(formLogin().user(username).password("12345"))
                .andExpect(unauthenticated());
    }
    private Account createUser(String username, String password) {
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(password);
        account.setRole(Role.USER);
        return accountService.createNew(account);
    }
}