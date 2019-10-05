package otus.spring.main.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.CommandNotCurrentlyAvailable;
import org.springframework.shell.Shell;
import org.springframework.test.annotation.DirtiesContext;
@DisplayName("Тест команд shell ")
@SpringBootTest
class ShellCommandsTest {

    @Autowired
    private Shell shell;
    
    @MockBean
	private Examinator exam;
	
    private static final String GREETING_PATTERN = "%s is successfully logged in";
    private static final String COMMAND_PATTERN = "%s %s";
    private static final String DEFAULT_LOGIN = "student";
    private static final String CUSTOM_LOGIN = "Вася";
    private static final String COMMAND_LOGIN = "login";
    private static final String COMMAND_LOGIN_SHORT = "l";
    private static final String COMMAND_EXAM = "exam";
    private static final String COMMAND_RESULT = "result";

    @DisplayName(" должен возвращать приветствие для всех форм команды логина")
    @Test
    void shouldReturnExpectedGreetingAfterLoginCommandEvaluated() 
    {
        String res = (String) shell.evaluate(() -> COMMAND_LOGIN);
        assertThat(res).isEqualTo(String.format(GREETING_PATTERN, DEFAULT_LOGIN));

        res = (String) shell.evaluate(() -> COMMAND_LOGIN_SHORT);
        assertThat(res).isEqualTo(String.format(GREETING_PATTERN, DEFAULT_LOGIN));

        res = (String) shell.evaluate(() -> String.format(COMMAND_PATTERN, COMMAND_LOGIN_SHORT, CUSTOM_LOGIN));
        assertThat(res).isEqualTo(String.format(GREETING_PATTERN, CUSTOM_LOGIN));
    }
    
    @DisplayName(" должен возвращать CommandNotCurrentlyAvailable для команды exam, если пользователь не выполнил вход, и для команды result, если пользователь не выполнил экзамен")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void shouldReturnCommandNotCurrentlyAvailableObjectWhenUserDoesNotLoginOrDoesNotPassedAnExam() 
    {
        Object res =  shell.evaluate(() -> COMMAND_EXAM);
        assertThat(res).isInstanceOf(CommandNotCurrentlyAvailable.class);
        
        res =  shell.evaluate(() -> COMMAND_RESULT);
        assertThat(res).isInstanceOf(CommandNotCurrentlyAvailable.class);
    }
    
    @DisplayName(" должен вызывать takeExam для команды exam, если вход выполнен")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void shouldCallTakeExamOnExamCommand() 
    {
    	shell.evaluate(() -> COMMAND_LOGIN);
        shell.evaluate(() -> COMMAND_EXAM);
        org.mockito.Mockito.verify(exam,times(1)).takeExam();
    }
    
    @DisplayName(" должен вызывать printResult для команды result, если вход и экзамен выполнен")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void shouldCallPrintResultOnResutlCommand() 
    {
    	shell.evaluate(() -> COMMAND_LOGIN);
        shell.evaluate(() -> COMMAND_EXAM);
        shell.evaluate(() -> COMMAND_RESULT);
        org.mockito.Mockito.verify(exam,times(1)).getStringResult();
    }

}
