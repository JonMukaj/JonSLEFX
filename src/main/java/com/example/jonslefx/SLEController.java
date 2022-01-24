package com.example.jonslefx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


import java.awt.*;
import java.io.*;
import java.util.concurrent.TimeUnit;

public class SLEController {
    @FXML
    private TextField numberOfUnknownsField;

    @FXML
    private TextArea solutionArea;

    @FXML
    protected void showStatus() {
        String numberOfUnknowns = numberOfUnknownsField.getText();
        try {
            executeScript(numberOfUnknowns);
            convertToPDF();
            solutionArea.setText("SLE GENERATED SUCCESSFULLY!");
        } catch (Exception e) {
            solutionArea.setText("Oops! Something went wrong. The matrix could not be generated. Try again!");
            solutionArea.setStyle("-fx-text-fill: #ff0000;");
            e.printStackTrace();
        }
    }

    private void executeScript(String numberOfUnknowns) throws IOException, InterruptedException {
        String script = System.getProperty("user.dir") + "\\Jon.py";
        ProcessBuilder executeScript = new ProcessBuilder(
                "python", "\"" + script + "\"", numberOfUnknowns);
       executeScript.start();
       TimeUnit.SECONDS.sleep(1);
    }

    private void convertToPDF() throws IOException {
        String locationOfPdf = System.getProperty("user.dir") + "\\GeneratedSleMatrix.pdf";
        File pdf = new File(locationOfPdf);
        boolean status = pdf.exists();
        if (status) {
            pdf.delete();
        }

        String commandTex = "pdflatex";
        String locationOfTex = System.getProperty("user.dir") + "\\GeneratedSleMatrix.tex";
        ProcessBuilder convertToLatex = new ProcessBuilder(commandTex, "\"" + locationOfTex + "\"");

        File latex = new File(locationOfTex);
        boolean texExists = latex.exists();
        if (texExists) {
            convertToLatex.start();
        }
    }

    public void openEquation() throws IOException, InterruptedException {
        String locationOfPdf = System.getProperty("user.dir") + "\\GeneratedSleMatrix.pdf";
        File pdf = new File(locationOfPdf);
        if (pdf.exists()) {
            Desktop.getDesktop().open(pdf);
            TimeUnit.SECONDS.sleep(1);
        }
    }


    public void openSolution(ActionEvent event) throws IOException,InterruptedException {
        String locationOfSolution = System.getProperty("user.dir") + "\\Solution.txt";
        File txt = new File(locationOfSolution);
        if (txt.exists()) {
            Desktop.getDesktop().open(txt);
            TimeUnit.SECONDS.sleep(1);
        }
    }

    public void showSolution(ActionEvent event) throws IOException{
        String locationOfSolution = System.getProperty("user.dir") + "\\Solution.txt";
        File txtSol = new File(locationOfSolution);
        BufferedReader outputSolution = new BufferedReader(new FileReader(txtSol));

        String output, result = "";
        while ((output = outputSolution.readLine()) != null) {
            result += output + "\n";
        }
        //System.out.println(result);
        solutionArea.setText(result);
        outputSolution.close();
    }
    public void showMatrices(ActionEvent event) throws IOException{
        String locationOfMatrices = System.getProperty("user.dir") + "\\Matrices.txt";
        File txtMatrix = new File(locationOfMatrices);
        BufferedReader outputMatrices = new BufferedReader(new FileReader(txtMatrix));

        String output, result = "";
        while ((output = outputMatrices.readLine()) != null) {
            result += output + "\n";
        }
        //System.out.println(result);
        solutionArea.setText(result);
        outputMatrices.close();

    }
    public void openSourceCode(ActionEvent event) {
       SLEApplication sle = new SLEApplication();
       sle.openLink();
    }


}