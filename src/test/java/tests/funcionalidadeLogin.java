package tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;

import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.OutputType;


public class funcionalidadeLogin {
    private WebDriver navegador;

    @Before
    public void setUp(){
        //abrindo navegador
        System.setProperty("webdriver.chrome.driver","C:\\Users\\jgapm\\Drivers\\ChromeDriver\\chromedriver.exe");
        navegador = new ChromeDriver();
        navegador.manage().window().maximize();
        //acessar pagina Mantis
        navegador.get("https://mantis-prova.base2.com.br/login_page.php");
    }

    @Test
    public void validarLoginComSucesso() throws IOException {
         //identificar formulario Login
         WebElement formularioLogin = navegador.findElement(By.name("login_form"));
         //preencher campo Usuario Valido
         formularioLogin.findElement(By.name("username")).sendKeys("joao.martins");
         //preencher campo Senha Valida
         formularioLogin.findElement(By.name("password")).sendKeys("qwe123''");
         //acionar o botão Login
         formularioLogin.findElement(By.className("button")).click(); //aciona botão "Login" utilizando a class
         //validar login realizado através do elemento com class "login-info-left" com o texto "joao.gabriel (João Gabriel Augusto - reporter)"
         WebElement validaClass = navegador.findElement(By.className("login-info-left"));
         String textoBoasVindas = validaClass.getText();
         assertEquals("Logged in as: joao.martins (Joao Martins - reporter)", textoBoasVindas);

        //Print da tela inicial
        File screenshotFile = ((TakesScreenshot) navegador).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenshotFile, new File(".//screenshot/Cenario001 - LoginComSucesso.png"));
    }

    @Test
    public void validarLoginComUsuarioInvalido() throws IOException {
         //identificar formulario Login
         WebElement formularioLogin = navegador.findElement(By.name("login_form"));
         //preencher campo Usuario Invalido
         formularioLogin.findElement(By.name("username")).sendKeys("tst123456789tst");
         //preencher campo Senha Valida
         formularioLogin.findElement(By.name("password")).sendKeys("qwe123''");
         //acionar o botão Login
         formularioLogin.findElement(By.className("button")).click();
         //validar login não realizado atraves do texto "Your account may be disabled or blocked or the username/password you entered is incorrect."
         WebElement validacaoLogin = navegador.findElement(By.xpath("//*[@color='red']"));
         String textoUsuarioInvalido = validacaoLogin.getText();
         assertEquals("Your account may be disabled or blocked or the username/password you entered is incorrect.", textoUsuarioInvalido);

        //Print da validação de usuário inválido
        File screenshotFile = ((TakesScreenshot) navegador).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenshotFile, new File(".//screenshot/Cenario002 - UsuarioInvalido.png"));
     }

    @Test
    public void validarLoginComSenhaInvalida() throws  IOException{
        //identificar formulario Login
        WebElement formularioLogin = navegador.findElement(By.name("login_form"));
        //preencher campo Usuario Valido
        formularioLogin.findElement(By.name("username")).sendKeys("joao.martins");
        //preencher campo Senha Valida
        formularioLogin.findElement(By.name("password")).sendKeys("999tst999");
        //acionar o botão Login
        formularioLogin.findElement(By.className("button")).click();
                //validar login não realizado atraves do texto "Your account may be disabled or blocked or the username/password you entered is incorrect."
                WebElement validacaoLogin = navegador.findElement(By.xpath("//*[@color='red']"));
        String textoSenhaInvalida = validacaoLogin.getText();
        assertEquals("Your account may be disabled or blocked or the username/password you entered is incorrect.", textoSenhaInvalida);

        //Print da validação senha inválida
        File screenshotFile = ((TakesScreenshot) navegador).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenshotFile, new File(".//screenshot/Cenario003 - SenhaInvalida.png"));
    }

    @Test
    public void validarLostPasswordComSucesso() throws IOException{
        navegador.findElement(By.linkText("Lost your password?")).click();
        //identificar formulario Password Reset
        WebElement formularioPasswordReset = navegador.findElement(By.name("lost_password_form"));
        //preencher campo Usuario Valido
        formularioPasswordReset.findElement(By.name("username")).sendKeys("joao.martins");
        //preencher campo E-mail Valido
        formularioPasswordReset.findElement(By.name("email")).sendKeys("jgapmartins@gmail.com");
        //acionar o botão Submit
        formularioPasswordReset.findElement(By.className("button")).click();
        //validar mensagem de mensagem de para troca de password enviada com sucesso
        WebElement validacaoPasswordReset = navegador.findElement(By.className("center"));
        String textoPasswordMessageSend = validacaoPasswordReset.getText();
        assertEquals("Password Message Sent", textoPasswordMessageSend);

        //Print da password reset
        File screenshotFile = ((TakesScreenshot) navegador).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenshotFile, new File(".//screenshot/Cenario004 - PasswordReset.png"));
    }

    @Test
    public void validarLostPasswordUsuarioNaoCadastrado() throws IOException {
        navegador.findElement(By.linkText("Lost your password?")).click();
        //identificar formulario Password Reset
        WebElement formularioPasswordReset = navegador.findElement(By.name("lost_password_form"));
        //preencher campo Usuario Invalido
        formularioPasswordReset.findElement(By.name("username")).sendKeys("tst123456789tst");
        //preencher campo E-mail Valido
        formularioPasswordReset.findElement(By.name("email")).sendKeys("jgapmartins@gmail.com");
        //acionar o botão Submit
        formularioPasswordReset.findElement(By.className("button")).click();
        //validar que não é possível realizaro envio do e-mail através do texto "The provided information does not match any registered account!"
        WebElement validacaoPasswordReset = navegador.findElement(By.className("center"));
        String textoUsuarioNaoCadastrado = validacaoPasswordReset.getText();
        assertEquals("The provided information does not match any registered account!", textoUsuarioNaoCadastrado);

        //Print da password reset usuario nao cadastrado
        File screenshotFile = ((TakesScreenshot) navegador).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenshotFile, new File(".//screenshot/Cenario005 - PasswordResetUsuarioNaoCadastrado.png"));
    }

    @Test
    public void validarLostPasswordEmailNaoCadastrado() throws IOException {
        navegador.findElement(By.linkText("Lost your password?")).click();
        //identificar formulario Password Reset
        WebElement formularioPasswordReset = navegador.findElement(By.name("lost_password_form"));
        //preencher campo Usuario Valido
        formularioPasswordReset.findElement(By.name("username")).sendKeys("joao.gabriel");
        //preencher campo E-mail com e-mail não relacionado com o usuario
        formularioPasswordReset.findElement(By.name("email")).sendKeys("teste@teste.com");
        //acionar o botão Submit
        formularioPasswordReset.findElement(By.className("button")).click();
        //validar que não é possível realizaro envio do e-mail através do texto "The provided information does not match any registered account!"
        WebElement validacaoPasswordReset = navegador.findElement(By.className("center"));
        String textoEmailNaoCadastrado = validacaoPasswordReset.getText();
        assertEquals("The provided information does not match any registered account!", textoEmailNaoCadastrado);

        //Print da password reset email nao cadastrado
        File screenshotFile = ((TakesScreenshot) navegador).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenshotFile, new File(".//screenshot/Cenario006 - PasswordResetEmailNaoCadastrado.png"));
    }

    @Test
    public void validarLostPasswordEmailInvalido() throws IOException {
        navegador.findElement(By.linkText("Lost your password?")).click();
        //identificar formulario Password Reset
        WebElement formularioPasswordReset = navegador.findElement(By.name("lost_password_form"));
        //preencher campo Usuario Valido
        formularioPasswordReset.findElement(By.name("username")).sendKeys("joao.gabriel");
        //preencher campo E-mail com e-mail Invalido
        formularioPasswordReset.findElement(By.name("email")).sendKeys("123456789tst");
        //acionar o botão Submit
        formularioPasswordReset.findElement(By.className("button")).click();
        //validar que não é possível realizaro envio do e-mail através do texto "Invalid e-mail address."
        WebElement validacaoPasswordReset = navegador.findElement(By.className("center"));
        String textoEmailInvalido = validacaoPasswordReset.getText();
        assertEquals("Invalid e-mail address.", textoEmailInvalido);

        //Print da password reset email invalido
        File screenshotFile = ((TakesScreenshot) navegador).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenshotFile, new File(".//screenshot/Cenario007 - PasswordResetEmailInvalido.png"));

    }

    @Test
    public  void realizarLogout () throws IOException {
        //identificar formulario Login
        WebElement formularioLogin = navegador.findElement(By.name("login_form"));
        //preencher campo Usuario Valido
        formularioLogin.findElement(By.name("username")).sendKeys("joao.martins");
        //preencher campo Senha Valida
        formularioLogin.findElement(By.name("password")).sendKeys("qwe123''");
        //acionar o botão Login
        formularioLogin.findElement(By.className("button")).click();
        //acionar botão de Logout
        navegador.findElement(By.linkText("Logout")).click();
        //identificar formulario Login após Logout
        WebElement validacaoTelaLogin = navegador.findElement(By.className("form-title"));
        String tituloTelaLogin = validacaoTelaLogin.getText();
        assertEquals("Login", tituloTelaLogin);

        //Print da tela inicial
        File screenshotFile = ((TakesScreenshot) navegador).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenshotFile, new File(".//screenshot/Cenario008 - Logout.png"));

    }

    @After
    public void TearDown(){
        //Fechar navegador
        navegador.quit();
    }
}
