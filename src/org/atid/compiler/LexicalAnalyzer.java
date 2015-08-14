/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.atid.compiler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Fabricia
 */
public class LexicalAnalyzer {
    
    private List<String> palavrasReservadas = new ArrayList<>(Arrays.asList(
            new String[]{"not", "dataAtual", "p", "di", "df", "in",
        "out", "and", "or", "n", "true", "false"}));
    
    private List<String> sinais = new ArrayList<>(Arrays.asList( new String[]
            {"=", "<", ">", "!"}));
    
    private List<String> letras = new ArrayList<>(Arrays.asList( new String[]
            {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k",
        "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "x", "z", "w"}));
    
    private List<String> digitos = new ArrayList<>(Arrays.asList( new String[]
            {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"}));
    
    private StringBuilder token = new StringBuilder();
    private List<String> tokens = new ArrayList<>();
    
    
    public boolean analisar(String valor){
        
        String[] simbolos = tratarTexto(valor);
        
        for (String simbolo : simbolos){
            
            if (isLetra(simbolo)){
                
                if ( (token.length() != 0) && !(isLetra(String.valueOf(token.toString().charAt(0)))) ){
                    return false;
                }
                token.append(simbolo);
            }
            else if (isDigito(simbolo)){
                token.append(simbolo);
            }
            else if (isSinal(simbolo)){
                token.append(simbolo);
            }
            else if (isEspaco(simbolo)){
                
                if ( (token.length() != 0) && (isLetra(String.valueOf(token.toString().charAt(0)))) ){
                    analisarPalavra();
                }
                else if ( (token.length() != 0) && (isDigito(String.valueOf(token.toString().charAt(0)))) ){
                    adicionarToken("NUM");
                }
                
                
            }
            else {
                return false;
            }
        }
        return true;
    }
    
    private void analisarPalavra(){
        
        if (isPalavraReservada(token.toString())){
            adicionarToken("PALAVRARESERVADA");
        }
        
        else{
            adicionarToken("ID");
        }
    }
    
    private void adicionarToken(String tipo){
        token.append(",");
        token.append(tipo);
        tokens.add(token.toString());
        
        token.delete(0, token.length());
    }
    
    private String[] tratarTexto(String valor){
        String texto = valor.replaceAll("\n", "");
        texto = texto.replaceAll("\t", "");
        
        return texto.split("");
    }
    
    private boolean isSinalDuplo(){
        
        String sinal1 = String.valueOf(token.toString().charAt(0));
        String sinal2 = String.valueOf(token.toString().charAt(1));
        
        if ( (sinal1.equals(">")) && (sinal2.equals("="))) {
            return true;
        }
        else if ( (sinal1.equals("<")) && (sinal2.equals("=")) ){
            return true;
        }
        else if ( (sinal1.equals("!")) && (sinal2.equals("=")) ){
            return true;
        }
        
        return false;
    }
    
    private boolean isEspaco(String valor){
        return (valor.equalsIgnoreCase(" "));
    }
    
    private boolean isLetra(String valor){
        return (letras.contains(valor));
    }
    
    private boolean isSinal(String valor){
        return (sinais.contains(valor));
    }
    
    private boolean isDigito(String valor){
        return (digitos.contains((valor)));
    }
    
    private boolean isPalavraReservada(String valor){
        return (palavrasReservadas.contains(valor));
    }
    
}
