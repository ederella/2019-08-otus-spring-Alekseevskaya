package main.security;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@AutoConfigureMockMvc
@SpringBootTest

public class SecurityConfigurationAccessUserTest {
	@Autowired
    private MockMvc mockMvc;
	
    @WithMockUser(
    		username = "user",
            authorities = "ROLE_USER")
    @Test
    public void testAuthenticatedOnUser() throws Exception {
    	long bookId = 1L;
    	mockMvc.perform(get("/library")).andExpect(status().isOk());
    	mockMvc.perform(get("/admin")).andExpect(status().isForbidden());
    	mockMvc.perform(get("/admin/edit?id="+ bookId)).andExpect(status().isForbidden());
    	mockMvc.perform(get("/admin/create")).andExpect(status().isForbidden());
    }
}
