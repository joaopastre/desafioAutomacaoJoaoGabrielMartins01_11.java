package tests;

import static org.junit.Assert.*;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.io.IOException;

public class myAccount {
    private WebDriver navegador;

    @Before
    public void setUp(){
        //abrindo navegador
        System.setProperty("webdriver.chrome.driver","C:\\Users\\jgapm\\Drivers\\ChromeDriver\\chromedriver.exe");
        navegador = new ChromeDriver();
        navegador.manage().window().maximize();
        //acessar pagina Mantis
        navegador.get("https://mantis-prova.base2.com.br/login_page.php");
        //identificar formulario Login
        WebElement formularioLogin = navegador.findElement(By.name("login_form"));
        //preencher campo Usuario Valido
        formularioLogin.findElement(By.name("username")).sendKeys("joao.martins");
        //preencher campo Senha Valida
        formularioLogin.findElement(By.name("password")).sendKeys("qwe123''");
        //acionar o botão Login
        formularioLogin.findElement(By.className("button")).click(); //aciona botão "Login" utilizando a class

        //acessar tela "My Account"
        navegador.findElement(By.linkText("My Account")).click();
    }

    @Test
    public void acessarMyAccount() throws IOException {
        //validar abertura do link My Account
        WebElement validacaoTelaEditAccount = navegador.findElement(By.className("form-title"));
        String tituloEditAccount = validacaoTelaEditAccount.getText();
        assertEquals("Edit Account", tituloEditAccount);

        //Print da tela de Account
        File screenshotFile = ((TakesScreenshot) navegador).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenshotFile, new File(".//screenshot/Cenario001_AcessarPagina.png"));
    }

    @Test
    public void atualizarInformacoes() throws IOException {
        //identificar formulario Edit Account
        WebElement formularioEditAccount = navegador.findElement(By.className("form-title"));

        //limpar campo nome com comando clear
        formularioEditAccount.findElement(By.xpath("/html/body/div[2]/form/table/tbody/tr[6]/td[2]/input")).clear();

        //preencher campo Usuario Valido
        formularioEditAccount.findElement(By.xpath("/html/body/div[2]/form/table/tbody/tr[6]/td[2]/input")).sendKeys("joao gabriel martins");

        //aciona botão Update User
        navegador.findElement(By.xpath("/html/body/div[2]/form/table/tbody/tr[10]/td[2]/input")).click();

        //Print da validação de Category Obrigatorio
        File screenshotFile = ((TakesScreenshot) navegador).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenshotFile, new File(".//screenshot/Cenario002_AtualizarInformacoes.png"));
    }

    @Test
    public void atualizarPreferencias() throws IOException {
        //Acessar formulario Preferences
        navegador.findElement(By.linkText("Preferences")).click();

        //Identificar formulario Preferences
        WebElement formularioPreferences = navegador.findElement(By.xpath("/html/body/div[2]/form/table/tbody"));

        //Alterar valor do combo Default Project
        WebElement campoProject = formularioPreferences.findElement(By.xpath("/html/body/div[2]/form/table/tbody/tr[2]/td[2]/select"));
        new Select(campoProject).selectByIndex(4);

        //Acionar o botão Update Prefs
        navegador.findElement(By.xpath("/html/body/div[2]/form/table/tbody/tr[18]/td/input")).click();

        //Validar Mensagem de Confirmacao
        WebElement confirmacao = navegador.findElement(By.xpath("/html/body/div[2]"));
        String mensagemConfirmacao = confirmacao.getText();
        assertEquals("Operation successful.\n" + "[ Proceed ]", mensagemConfirmacao);

        //Print da validação de Category Obrigatorio
        File screenshotFile = ((TakesScreenshot) navegador).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenshotFile, new File(".//screenshot/Cenario003_AtualizarPreferencias.png"));
    }

    @After
    public void TearDown(){
        //Fechar navegador
        navegador.quit();
    }
}