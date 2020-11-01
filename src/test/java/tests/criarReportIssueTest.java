package tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.support.ui.Select;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.OutputType;

public class criarReportIssueTest {
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
        formularioLogin.findElement(By.name("username")).sendKeys("joao.gabriel");
        //preencher campo Senha Valida
        formularioLogin.findElement(By.name("password")).sendKeys("q1w2e3''");
        //acionar o botão Login
        formularioLogin.findElement(By.className("button")).click();

        //acessar tela "Report Issue"
        navegador.findElement(By.linkText("Report Issue")).click();
    }

    @Test//CT001
    public void criarReportIssueProjetoPadrao() throws IOException {
        WebElement formularioSelectProject = navegador.findElement(By.xpath("/html/body/div[2]/form/table"));

        //Identificar no formulario Select Project a linha que possui o campo Choose Project
        WebElement linhaSelectProject = navegador.findElement(By.className("row-1"));

        //Alterar valor do combo Choose Project
        WebElement campoChooseProject = linhaSelectProject.findElement(By.name("project_id"));
        new Select(campoChooseProject).selectByValue("203");

        //Identificar no formulario "Select Project" a linha que possui o campo "Make Default"
        WebElement linhaMakeDefault = navegador.findElement(By.className("row-2"));
        //Marcar o check "Make Default"
        linhaMakeDefault.findElement(By.name("make_default")).click();

        //Acionar botao Select Project
        formularioSelectProject.findElement(By.className("button")).click();

        //Identificar no formulario Enter Report Details a linha que possui o campo Category
        WebElement formularioReportDetails = navegador.findElement(By.xpath("/html/body/div[3]/form/table/tbody/tr[2]"));

        //Alterar valor do combo Category
        WebElement campoCategory = formularioReportDetails.findElement(By.xpath("/html/body/div[3]/form/table/tbody/tr[2]/td[2]/select"));
        new Select(campoCategory).selectByVisibleText("[All Projects] Teste");

        //Preencher campo Summary
        formularioReportDetails.findElement(By.xpath("/html/body/div[3]/form/table/tbody/tr[8]/td[2]/input")).sendKeys("tst campo summary");

        //Preencher campo Description
        formularioReportDetails.findElement(By.xpath("/html/body/div[3]/form/table/tbody/tr[9]/td[2]/textarea")).sendKeys("tst campo description");

        //Acionar botão Submite Report
        formularioReportDetails.findElement(By.xpath("/html/body/div[3]/form/table/tbody/tr[15]/td[2]/input")).click();

        //Validar mensagem Operation successful
        WebElement confirmacao = navegador.findElement(By.xpath("/html/body/div[2]"));
        String mensagemConfirmacao = confirmacao.getText();
        assertNotEquals("Operation successul.\n" + "View Submitted Issue"+"View Issues","####",mensagemConfirmacao);

        //Print da tela
        File screenshotFile = ((TakesScreenshot) navegador).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenshotFile, new File(".//screenshot/Cenario001_CriarComSucesso.png"));

    }

    @Test//ct002
    public void criarReportIssueSemProjetoPadrao() throws IOException {
        //Identificar no formulario Enter Report Details a linha que possui o campo Category
        WebElement formularioReportDetails = navegador.findElement(By.name("category_id"));

        //Alterar valor do combo Category
        WebElement campoCategory = formularioReportDetails.findElement(By.xpath("//*[@name='category_id']"));
        new Select(campoCategory).selectByVisibleText("[All Projects] Teste");

        //Preencher campo Summary
        formularioReportDetails.findElement(By.xpath("//*[@name='summary']")).sendKeys("tst campo summary");

        //Preencher campo Description
        formularioReportDetails.findElement(By.xpath("//*[@name='description']")).sendKeys("tst campo description");

        //Acionar botão Submite Report
        //formularioReportDetails.findElement(By.xpath("/html/body/div[3]/form/table/tbody/tr[15]/td[2]/input")).click();
        formularioReportDetails.findElement(By.xpath("//*[@class='button']")).click();

        //Validar mensagem Operation successful
        WebElement confirmacao = navegador.findElement(By.xpath("/html/body/div[2]"));
        String mensagemConfirmacao = confirmacao.getText();
        assertNotEquals("Operation successul.\n" + "View Submitted Issue"+"View Issues","####",mensagemConfirmacao);

        //Print da tela de Sucesso
        File screenshotFile = ((TakesScreenshot) navegador).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenshotFile, new File(".//screenshot/Cenario002_OperationSuccessful.png"));
    }

    @Test//CT003
    public void validarCampoCategoryObrigatorio() throws IOException {
        //Identificar no formulario Enter Report Details
        WebElement formularioReportDetails = navegador.findElement(By.xpath("/html/body/div[3]/form/table"));

        //Preencher campo Summary
        formularioReportDetails.findElement(By.xpath("/html/body/div[3]/form/table/tbody/tr[8]/td[2]/input")).sendKeys("tst campo summary");

        //Preencher campo Description
        formularioReportDetails.findElement(By.xpath("/html/body/div[3]/form/table/tbody/tr[9]/td[2]/textarea")).sendKeys("tst campo description");

        //Acionar botão Submite Report
        formularioReportDetails.findElement(By.xpath("/html/body/div[3]/form/table/tbody/tr[15]/td[2]/input")).click();

        //Validar mensagem Operation successful
        WebElement validacaoCategory = navegador.findElement(By.xpath("/html/body/div[2]"));
        String mensagemValidacaoCategory = validacaoCategory.getText();
        assertEquals("APPLICATION ERROR #11\n" +
                "A necessary field \"Category\" was empty. Please recheck your inputs.\n" +
                "Please use the \"Back\" button in your web browser to return to the previous page. There you can correct whatever problems were identified in this error or select another action. You can also click an option from the menu bar to go directly to a new section.", mensagemValidacaoCategory);

        //Print da validação de Category Obrigatorio
        File screenshotFile = ((TakesScreenshot) navegador).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenshotFile, new File(".//screenshot/Cenario003_CategoryObrigatoria.png"));
    }

    @Test //CT004
    public void validarCampoSummaryObrigatorio() throws IOException {
        //Identificar no formulario Enter Report Details a linha que possui o campo Category
        WebElement formularioReportDetails = navegador.findElement(By.xpath("/html/body/div[3]/form/table/tbody/tr[2]"));

        //Alterar valor do combo Category
        WebElement campoCategory = formularioReportDetails.findElement(By.xpath("/html/body/div[3]/form/table/tbody/tr[2]/td[2]/select"));
        new Select(campoCategory).selectByVisibleText("[All Projects] Teste");

        //Preencher campo Description
        formularioReportDetails.findElement(By.xpath("/html/body/div[3]/form/table/tbody/tr[9]/td[2]/textarea")).sendKeys("tst campo description");

        //Acionar botão Submite Report
        formularioReportDetails.findElement(By.xpath("/html/body/div[3]/form/table/tbody/tr[15]/td[2]/input")).click();

        //Validar mensagem Operation successful
        WebElement validacaoSummary = navegador.findElement(By.xpath("/html/body/div[2]"));
        String mensagemValidacaoSummary = validacaoSummary.getText();
        assertEquals("APPLICATION ERROR #11\n" +
                "A necessary field \"Summary\" was empty. Please recheck your inputs.\n" +
                "Please use the \"Back\" button in your web browser to return to the previous page. There you can correct whatever problems were identified in this error or select another action. You can also click an option from the menu bar to go directly to a new section.", mensagemValidacaoSummary);

        //Print da validação de Summary Obrigatorio
        File screenshotFile = ((TakesScreenshot) navegador).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenshotFile, new File(".//screenshot/Cenario004_SummeryObrigatoria.png"));
    }

    @Test //CT005
    public void validarCampoDescriptionObrigatorio() throws IOException {
        //Identificar no formulario Enter Report Details a linha que possui o campo Category
        WebElement formularioReportDetails = navegador.findElement(By.xpath("/html/body/div[3]/form/table/tbody/tr[2]"));

        //Alterar valor do combo Category
        WebElement campoCategory = formularioReportDetails.findElement(By.xpath("/html/body/div[3]/form/table/tbody/tr[2]/td[2]/select"));
        new Select(campoCategory).selectByVisibleText("[All Projects] Teste");

        //Preencher campo Summary
        formularioReportDetails.findElement(By.xpath("/html/body/div[3]/form/table/tbody/tr[8]/td[2]/input")).sendKeys("tst campo summary");

        //Acionar botão Submite Report
        formularioReportDetails.findElement(By.xpath("/html/body/div[3]/form/table/tbody/tr[15]/td[2]/input")).click();

        //Validar mensagem Operation successful
        WebElement validacaoDescription = navegador.findElement(By.xpath("/html/body/div[2]"));
        String mensagemValidacaoDescription = validacaoDescription.getText();
        assertEquals("APPLICATION ERROR #11\n" +
                "A necessary field \"Description\" was empty. Please recheck your inputs.\n" +
                "Please use the \"Back\" button in your web browser to return to the previous page. There you can correct whatever problems were identified in this error or select another action. You can also click an option from the menu bar to go directly to a new section.", mensagemValidacaoDescription);

        //Print da validação de Description Obrigatorio
        File screenshotFile = ((TakesScreenshot) navegador).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenshotFile, new File(".//screenshot/Cenario005_DescriptionObrigatoria.png"));
    }

    @After
    public void TearDown(){
        //Fechar navegador
        navegador.quit();
    }
}
